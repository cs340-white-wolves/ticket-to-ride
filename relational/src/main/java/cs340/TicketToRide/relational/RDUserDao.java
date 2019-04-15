package cs340.TicketToRide.relational;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import com.google.gson.Gson;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.Users;
import cs340.TicketToRide.model.db.IUserDao;

public class RDUserDao implements IUserDao {

    private DatabaseConnection connection = null;
    private Connection conn = null;
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
        String insert = "INSERT INTO Users VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, user.getUserID().toString());
            stmt.setString(2, gson.toJson(user));
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return users;
    }

    private void insertToken(String token, User user) {
        String insert = "INSERT INTO Tokens VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, token);
            stmt.setString(2, user.getUserID().toString());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void saveTokens(AuthManager authManager) {
        clearTokenTable();

        for (Map.Entry<String, User> entry : authManager.getTokenUserMap().entrySet()) {
            String token = entry.getKey();
            User user = entry.getValue();
            insertToken(token, user);
        }
    }

    private User getUser(String userId) {
        User user = null;
        String query = "SELECT * FROM Users WHERE Id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet result = stmt.executeQuery();
            result.next();
            user = gson.fromJson(result.getString(2), User.class);

            result.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return user;
    }

    @Override
    public AuthManager loadTokens() {
        AuthManager authManager = new AuthManager();
        String query = "SELECT * FROM Tokens";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                AuthToken token = new AuthToken(result.getString(1));
                User user = getUser(result.getString(2));

                authManager.addTokenUser(token, user);
            }

            result.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return authManager;
    }

    @Override
    public void beginTransaction() {
        conn = connection.openConnection();
    }

    @Override
    public void endTransaction() {
        connection.closeConnection(true);
    }

    private void clearUserTable() {
        String clear = "DELETE FROM Users";

        try {
            PreparedStatement stmt = conn.prepareStatement(clear);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void clearTokenTable() {
        String clear = "DELETE FROM Tokens";

        try {
            PreparedStatement stmt = conn.prepareStatement(clear);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void clearAll() {
        clearUserTable();
        clearTokenTable();
    }

    private void createUsersTable() {
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
            throw new RuntimeException(ex);
        }
    }

    private void createTokensTable() {
        String create = "CREATE TABLE IF NOT EXISTS Tokens(\n" +
                "\tId VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                "\tUserId VARCHAR(255) NOT NULL,\n" +
                "    FOREIGN KEY (UserId) REFERENCES Users(Id)\n" +
                ");";

        try {
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
