package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

public class User {

    private ID userID;

    public User() {
        userID = ID.generateID();
    }

    // todo: update later
    public boolean isValid() {
        return true;
    }

    public ID getUserID() {
        return this.userID;
    }
}
