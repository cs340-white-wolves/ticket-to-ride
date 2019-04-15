package cs340.TicketToRide.relational;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.db.IUserDao;

public class RDUserDao implements IUserDao {

    private DatabaseConnection connection = null;

    public RDUserDao() {
        connection = new DatabaseConnection();
        createUsersTable();
    }

    @Override
    public void saveUsers(Set<User> users) {

    }

    @Override
    public Set<User> loadUsers() {
        return null;
    }

    @Override
    public void saveTokens(AuthManager authManager) {

    }

    @Override
    public AuthManager loadTokens() {
        return null;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {

    }

    private void createUsersTable() {
        Connection conn = connection.openConnection();

        String create = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "\tuser_id VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                "\tuser BLOB NOT NULL,\n" +
                ");";

        try {
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            // todo: handle exception
        }

        connection.closeConnection(true);
    }

    private void createTokenTable() {
        Connection conn = connection.openConnection();

    }
}
