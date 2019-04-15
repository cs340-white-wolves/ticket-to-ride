package cs340.TicketToRide.relational;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.Gson;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.game.Game;

public class RDGameDao implements IGameDao {

    private DatabaseConnection connection;
    private Gson gson = new Gson();

    public RDGameDao() {
        connection = new DatabaseConnection();
        createTables();
    }

    private void createTables() {
        Connection conn = connection.openConnection();
        String schema =
              "CREATE TABLE IF NOT EXISTS `Games` (\n" +
              "\t`Id`\t\tTEXT NOT NULL,\n" +
              "\t`Game`\tBLOB NOT NULL,\n" +
              "\tPRIMARY KEY(`GameId`)\n" +
              ");\n" +

              "CREATE TABLE IF NOT EXISTS `Commands` (\n" +
               "\t`Id`\t INT NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
               "\t`Command`\tBLOB NOT NULL,\n" +
              "\tPRIMARY KEY(`Id`)\n" +
                ");\n" +

                "CREATE TABLE IF NOT EXISTS `ClientProxy` (\n" +
                "\t`Id`\tINT NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                 "\t`Proxy`\tBLOB NOT NULL,\n" +
                "\tPRIMARY KEY(`Id`)\n" +
                ");\n";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(schema);
            connection.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.closeConnection(false);
        }

    }

    @Override
    public void saveGames(Games games) {
        clearGames();
        Connection conn = connection.openConnection();
        String sql = "\"INSERT INTO Games (Id, Game) \"\n" +
                "\t\t\t\t+ \"VALUES(?, ?);\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Game game: games.getGameList()) {
                stmt.setString(1, game.getGameID().toString());
                stmt.setString(2, gson.toJson(game));
                stmt.addBatch();
            }

            if (checkBatch(stmt.executeBatch())) {
                throw new SQLException("SQL statment not executed");
            }

            connection.closeConnection(true);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            connection.closeConnection(false);
        }
    }

    private void clearGames() {
        Connection conn = connection.openConnection();
        String sql = "DELETE FROM Games;";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            connection.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.closeConnection(false);
        }
    }

    private boolean checkBatch(int[] batch) {
        for (int result: batch) {
            if (result == 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Games loadGames() {
        Connection conn = connection.openConnection();
        Games games = new Games();
        String sql = "SELECT * FROM Games;";

        try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);

           while (rs.next()){
               String json = rs.getString("Game");
               games.addGame(gson.fromJson(json, Game.class));
           }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Games could not be loaded");
        }

        return games;
    }




    @Override
    public void saveCommand(Command command) {
        Connection conn = connection.openConnection();
        String sql = "\"INSERT INTO Commands (Command) \"\n" +
                "\t\t\t\t+ \"VALUES(?);\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,gson.toJson(command));

            if (stmt.executeUpdate() != 1) {
                throw new SQLException("Command could not be saved");
            }
            connection.closeConnection(true);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            connection.closeConnection(false);
        }

    }

    @Override
    public Commands loadCommands() {
        Connection conn = connection.openConnection();
        Commands commands = new Commands();
        String sql = "SELECT * FROM Commands;";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String json = rs.getString("Command");
                commands.add(gson.fromJson(json, Command.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commands;
    }

    @Override
    public void clearCommands() {
        Connection conn = connection.openConnection();
        String sql = "DELETE FROM Commands;";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            connection.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.closeConnection(false);
        }
    }

    @Override
    public void saveClientManager(ClientProxyManager manager) {
        Connection conn = connection.openConnection();
        String sql = "\"INSERT INTO ClientProxy (Proxy) \"\n" +
                "\t\t\t\t+ \"VALUES(?);\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,gson.toJson(manager));

            if (stmt.executeUpdate() != 1) {
                throw new SQLException("Proxy manager could not be saved");
            }
            connection.closeConnection(true);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            connection.closeConnection(false);
        }
    }

    @Override
    public ClientProxyManager loadClientManager() {
        Connection conn = connection.openConnection();
        ClientProxyManager proxy = null;
        String sql = "SELECT * FROM ClientProxy;";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String json = rs.getString("Proxy");
                proxy = gson.fromJson(json, ClientProxyManager.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proxy;
    }



    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {
        Connection conn = connection.openConnection();
        String sql = "DELETE FROM Commands; DELETE FROM ClientProxy; DELETE FROM Games";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);

            connection.closeConnection(true);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.closeConnection(false);
        }
    }
}