package cs340.TicketToRide.flatfile;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.Users;
import cs340.TicketToRide.model.db.IUserDao;

public class UserDao implements IUserDao {
    private Gson gson = new Gson();
    private File userFile = new File(DaoFactory.DATA_PATH + "users.json");
    private File tokenFile = new File(DaoFactory.DATA_PATH + "tokens.json");

    @Override
    public void saveUsers(Set<User> userSet) {
        Users users = new Users(userSet);
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter(this.userFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<User> loadUsers() {
        Users users = null;
        if (userFile.exists()) {
            try (FileReader reader = new FileReader(this.userFile)) {
                users = gson.fromJson(reader, Users.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (users == null) {
            return null;
        }

        return users.getUsers();
    }

    @Override
    public void saveTokens(AuthManager authManager) {
        String json = gson.toJson(authManager);
        try (FileWriter writer = new FileWriter(tokenFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthManager loadTokens() {
        AuthManager manager = null;
        if (tokenFile.exists()) {
            try (FileReader reader = new FileReader(tokenFile)) {
                manager = gson.fromJson(reader, AuthManager.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return manager;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {
        Set<User> users = new HashSet<>();
        AuthManager manager = new AuthManager();
        saveUsers(users);
        saveTokens(manager);
    }
}
