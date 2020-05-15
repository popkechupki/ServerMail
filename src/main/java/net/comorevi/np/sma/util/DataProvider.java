package net.comorevi.np.sma.util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataProvider {
    private static final DataProvider instance = new DataProvider();
    private Connection connection = null;

    public boolean existsMail(int id) {
        try {
            String sql = "SELECT id FROM mail WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            boolean result = statement.executeQuery().next();
            statement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsMail(String target) {
        try {
            String sql = "SELECT id FROM mail WHERE target = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, target);
            boolean result = statement.executeQuery().next();
            statement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addMail(MailData data) {
        try {
            String sql = "INSERT INTO mail ( subject, content, sender, target, read ) values ( ?, ?, ?, ?, ? )";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, data.subject);
            statement.setString(2, data.content);
            statement.setString(3, data.sender);
            statement.setString(4, data.target);
            statement.setInt(5, data.read ? 1 : 0);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMail(int id) {
        if (!existsMail(id)) return;
        try {
            String sql = "DELETE FROM mail WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMail(int id, MailData mailData) {
        if (!existsMail(id)) return;
        try {
            String sql = "UPDATE mail SET subject = ?, content = ?, sender = ?, target = ?, read = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mailData.subject);
            statement.setString(2, mailData.content);
            statement.setString(3, mailData.sender);
            statement.setString(4, mailData.target);
            statement.setInt(5, mailData.read ? 1 : 0);
            statement.setInt(6, id);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MailData getMail(int id) {
        if (!existsMail(id)) return null;
        try {
            String sql = "SELECT * FROM mail WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            MailData result = new MailData(rs.getInt("id"), rs.getString("subject"), rs.getString("content"), rs.getString("sender"), rs.getString("target"), rs.getInt("read") == 1);
            rs.close();
            statement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MailData> getMailBox(String target) {
        if (!existsMail(target)) return null;
        try {
            String sql = "SELECT * FROM mail WHERE target = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, target);

            ResultSet rs = statement.executeQuery();
            List<MailData> result = new LinkedList<>();
            while (rs.next()) {
                result.add(
                        new MailData(
                                rs.getInt("id"), rs.getString("subject"), rs.getString("content"), rs.getString("sender"), rs.getString("target"), rs.getInt("read") == 1
                        )
                );
            }
            rs.close();
            statement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DataProvider() {
        //
    }

    public void connectSQL() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./plugins/ServerMail/DataDB.db");
            String sql = "CREATE TABLE IF NOT EXISTS mail (" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " subject TEXT NOT NULL," +
                    " content TEXT NOT NULL," +
                    " sender TEXT NOT NULL," +
                    " target TEXT NOT NULL," +
                    " read INTEGER NOT NULL )";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
