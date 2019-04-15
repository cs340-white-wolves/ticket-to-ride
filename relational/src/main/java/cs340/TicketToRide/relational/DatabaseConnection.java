package cs340.TicketToRide.relational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;

    public Connection getConnection() {
        return conn;
    }


    public Connection openConnection() {  // throws DatabaseConnection.DatabaseException
        try {
            final String dbname = "jdbc:sqlite:ttr.sqlite"; // url of database to connect to
            conn = DriverManager.getConnection(dbname); // opens database connection
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
//            System.out.println("openConnection failed");
//            throw new DatabaseException("openConnection failed", e);
        }
        return null;
    }

    public Connection closeConnection(boolean commit) {  // throws DatabaseException
        if (conn == null) {return null;}
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }
            conn.close();
            conn = null;
            return conn;
        } catch (SQLException e) {
//            throw new DatabaseException("closeConnection failed", e);
        }
        return null;
    }

    public static class DatabaseException extends Exception {
        public DatabaseException(String s, SQLException e) {

        }
    }
}