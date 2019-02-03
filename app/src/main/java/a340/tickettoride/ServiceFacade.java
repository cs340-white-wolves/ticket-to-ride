package a340.tickettoride;

import cs340.TicketToRide.IServer;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This class will call the server proxy but it will also update the client model
 */
public class ServiceFacade {
    private static ServiceFacade singleton;

    private ServiceFacade() {
    }

    public static ServiceFacade getInstance() {
        if (singleton == null) {
            singleton = new ServiceFacade();
        }
        return singleton;
    }

    public void login(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }


    }

    public void register(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

    }
}
