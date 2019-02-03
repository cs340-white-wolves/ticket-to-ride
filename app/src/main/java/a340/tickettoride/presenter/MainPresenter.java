package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This calls the Service facade and also updates the Views
 */
public class MainPresenter implements IMainPresenter {
    private Username username;
    private Password password;


    public void login(String usr, String pass) {
        try {
            setUsernamePassword(usr, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServiceFacade facade = ServiceFacade.getInstance();
        facade.login(username, password);
    }

    public void register(String usr, String pass) {
        try {
            setUsernamePassword(usr, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServiceFacade facade = ServiceFacade.getInstance();
        facade.register(username, password);
    }

    private void setUsernamePassword(String usr, String pass) throws Exception {
        if (usr == null || pass == null || usr.equals("") || pass.equals("")) {
            throw new IllegalArgumentException();
        }

        Username username = new Username(usr);
        Password password = new Password(pass);

        if (!username.isValid() || !password.isValid()) {
            throw new Exception();
        }
    }
}
