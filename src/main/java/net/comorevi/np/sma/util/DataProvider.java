package net.comorevi.np.sma.util;

import net.comorevi.np.sma.ServerMailAPI;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataProvider {
    private static final DataProvider instance = new DataProvider();
    private Connection connection = null;
    private DatabaseType dbType;

    private DataProvider() {
        //
    }

    public boolean existsMail(int id) {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT id FROM mail WHERE id = ? LIMIT 1";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
        return false;
    }

    public boolean existsMail(String target) {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT id FROM mail WHERE target = ? LIMIT 1";
            statement = connection.prepareStatement(sql);
            statement.setString(1, target);

            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
        return false;
    }

    public void addMail(MailData data) {
        PreparedStatement statement = null;
        try {
            String sql = "";
            switch (dbType) {
                case SQLITE:
                    sql = "INSERT INTO mail ( subject, content, sender, target, read ) VALUES ( ?, ?, ?, ?, ? )";
                    break;
                case MYSQL:
                    sql = "INSERT INTO mail ( subject, content, sender, target, isRead ) VALUES ( ?, ?, ?, ?, ? )";
                    break;
            }
            statement = connection.prepareStatement(sql);
            statement.setString(1, data.subject);
            statement.setString(2, data.content);
            statement.setString(3, data.sender);
            statement.setString(4, data.target);
            statement.setInt(5, data.read ? 1 : 0);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void removeMail(int id) {
        if (!existsMail(id)) return;
        PreparedStatement statement = null;
        try {
            String sql = "DELETE FROM mail WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void setMail(int id, MailData mailData) {
        if (!existsMail(id)) return;
        PreparedStatement statement = null;
        try {
            String sql = "";
            switch (dbType) {
                case SQLITE:
                    sql = "UPDATE mail SET subject = ?, content = ?, sender = ?, target = ?, read = ? WHERE id = ?";
                    break;
                case MYSQL:
                    sql = "UPDATE mail SET subject = ?, content = ?, sender = ?, target = ?, isRead = ? WHERE id = ?";
                    break;
            }
            statement = connection.prepareStatement(sql);
            statement.setString(1, mailData.subject);
            statement.setString(2, mailData.content);
            statement.setString(3, mailData.sender);
            statement.setString(4, mailData.target);
            statement.setInt(5, mailData.read ? 1 : 0);
            statement.setInt(6, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public MailData getMail(int id) {
        if (!existsMail(id)) return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM mail WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new MailData(resultSet.getInt("id"), resultSet.getString("subject"), resultSet.getString("content"), resultSet.getString("sender"), resultSet.getString("target"), resultSet.getInt(dbType == DatabaseType.SQLITE ? "read" : "isRead") == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignore) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
        return null;
    }

    public List<MailData> getMailBox(String target) {
        if (!existsMail(target)) return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM mail WHERE target = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, target);

            resultSet = statement.executeQuery();
            List<MailData> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(
                        new MailData(
                                resultSet.getInt("id"), resultSet.getString("subject"), resultSet.getString("content"), resultSet.getString("sender"), resultSet.getString("target"), resultSet.getInt(dbType == DatabaseType.SQLITE ? "read" : "isRead") == 1
                        )
                );
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignore) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
        }
        return null;
    }

    public void connectSQL(DatabaseType type) {
        this.dbType = type;
        PreparedStatement statement = null;
        String sql = "";
        switch (type) {
            case SQLITE:
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:./plugins/ServerMail/DataDB.db");
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                sql = "CREATE TABLE IF NOT EXISTS mail (" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " subject TEXT NOT NULL," +
                        " content TEXT NOT NULL," +
                        " sender TEXT NOT NULL," +
                        " target TEXT NOT NULL," +
                        " read INTEGER NOT NULL )";
                break;
            case MYSQL:
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://" + ServerMailAPI.getConfigUtil().getConfig().getString("host") + "/" + ServerMailAPI.getConfigUtil().getConfig().getString("database"),
                            ServerMailAPI.getConfigUtil().getConfig().getString("username"),
                            ServerMailAPI.getConfigUtil().getConfig().getString("password")
                    );
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                sql = "CREATE TABLE IF NOT EXISTS mail (" +
                        " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        " subject TEXT NOT NULL," +
                        " content TEXT NOT NULL," +
                        " sender TEXT NOT NULL," +
                        " target TEXT NOT NULL," +
                        " isRead INT NOT NULL )";
                break;
        }
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    public void disconnectSQL() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DataProvider getInstance() {
        return instance;
    }
}
