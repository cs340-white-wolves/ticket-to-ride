package cs340.TicketToRide.relational;

import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.db.IUserDao;

public class RDUserDao implements IUserDao {

    private DatabaseConneciton conn = null;

    public RDUserDao() {
        conn = new DatabaseConnection();
        createTable();
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
    public void clearAll() {

    }

    public void createTable() {
        Connection conn = dbconn.openConnection();

        String create = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "\tuser_id VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                "\tuser BLOB NOT NULL,\n" +
                ");";
        PreparedStatement stmt = conn.prepareStatement(create);
        stmt.executeUpdate();

        stmt.close();
        dbconn.closeConnection();
    }
}
