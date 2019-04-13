package cs340.TicketToRide.flatfile;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.game.Game;

public class GameDao implements IGameDao {
    private Gson gson = new Gson();
    private File gameFile = new File("./games.json");
    private File cmdFile = new File("./commands.json");
    private File clientFile = new File("./clientProxies.json");

    public void saveGames(Games games) {
        String json = gson.toJson(games);
        try (FileWriter writer = new FileWriter(this.gameFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCommand(Command command) {
        Commands commands;
        if (cmdFile.exists()) {
            commands = loadCommands();
        } else {
            commands = new Commands();
        }
        commands.add(command);
        saveCommands(commands);
    }

    private void saveCommands(Commands commands) {
        String json = gson.toJson(commands);
        try (FileWriter writer = new FileWriter(this.cmdFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveClientManager(ClientProxyManager manager) {
        String json = gson.toJson(manager);
        try (FileWriter writer = new FileWriter(this.clientFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Games loadGames() {
        Games games = null;
        try (FileReader reader = new FileReader(this.gameFile)) {
            games = gson.fromJson(reader, Games.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public Commands loadCommands() {
        Commands commands = null;
        try (FileReader reader = new FileReader(this.cmdFile)) {
            commands = gson.fromJson(reader, Commands.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }

    @Override
    public ClientProxyManager loadClientManager() {
        ClientProxyManager manager = null;
        try (FileReader reader = new FileReader(this.clientFile)) {
            manager = gson.fromJson(reader, ClientProxyManager.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        return manager;
    }

    @Override
    public void clearCommands() {
        Commands commands = new Commands();
        saveCommands(commands);
    }

    private void clearGames() {
        Games games = new Games();
        saveGames(games);
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {
        clearCommands();
        clearGames();
        clearProxyManager();
    }

    private void clearProxyManager() {
        ClientProxyManager manager = loadClientManager();
        manager.clear();
        saveClientManager(manager);
    }
}