package cs340.TicketToRide.relational;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import com.google.gson.Gson;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.Users;
import cs340.TicketToRide.model.db.IUserDao;

public class RDUserDao implements IUserDao {

    private DatabaseConnection connection = null;
    private Gson gson = new Gson();


    public RDUserDao() {
        connection = new DatabaseConnection();
        createTables();
    }

    private void createTables() {
        createUsersTable();
        createTokensTable();
    }

    private void insertUser(User user) {
        Connection conn = connection.openConnection();
        String insert = "INSERT INTO Users VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, user.getUserID().toString());
            stmt.setString(2, gson.toJson(user));
            stmt.executeUpdate();

            stmt.close();
            connection.closeConnection(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUsers(Users users) {
        clearUserTable();

        for (User user : users.getUsers()) {
            insertUser(user);
        }

    }

    @Override
    public Users loadUsers() {
        Connection conn = connection.openConnection();
        Users users = new Users();
        String query = "SELECT * FROM Users";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String json = result.getString("User");
                users.addUser(gson.fromJson(json, User.class));
            }

            result.close();
            stmt.close();
            connection.closeConnection(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public void saveTokens(AuthManager authManager) {

    }

    private User getUser(String userId) {
        // todo: implement
        return null;
    }

    @Override
    public AuthManager loadTokens() {
        Connection conn = connection.openConnection();
        AuthManager authmanager = new AuthManager();
        String query = "SELECT * FROM Tokens";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                // todo: get authManager data
                String token = result.getString(1);
                User user = getUser(result.getString(2));

            }

            result.close();
            stmt.close();
            connection.closeConnection(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return authmanager;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    private void clearUserTable() {
        Connection conn = connection.openConnection();

        String clear = "DELETE FROM Users";

        try {
            PreparedStatement stmt = conn.prepareStatement(clear);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        connection.closeConnection(true);
    }

    private void clearTokenTable() {
        Connection conn = connection.openConnection();

        String clear = "DELETE FROM Tokens";

        try {
            PreparedStatement stmt = conn.prepareStatement(clear);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        connection.closeConnection(true);
    }

    @Override
    public void clearAll() {
        clearUserTable();
        clearTokenTable();
    }

    private void createUsersTable() {
        Connection conn = connection.openConnection();

        String create = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "\tId VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                "\tUser BLOB NOT NULL,\n" +
                ");";

        try {
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        connection.closeConnection(true);
    }

    private void createTokensTable() {
        Connection conn = connection.openConnection();

        String create = "CREATE TABLE IF NOT EXISTS Tokens(\n" +
                "\tId VARCHAR(255) NOT NULL,\n" +
                "\tUserId BLOB NOT NULL,\n" +
                "    FOREIGN KEY (UserId) REFERENCES Users(UserId)\n" +
                ");";

        try {
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        connection.closeConnection(true);
    }
}
