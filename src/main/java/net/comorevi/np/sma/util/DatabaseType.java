package net.comorevi.np.sma.util;

public enum DatabaseType {
    SQLITE(0, "SQLite"),
    MYSQL(1, "MySQL");

    private final int id;
    private final String str;

    private DatabaseType(int id, String str) {
        this.id = id;
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "DatabaseType{" +
                "id=" + id +
                ", str='" + str + '\'' +
                '}';
    }
}
