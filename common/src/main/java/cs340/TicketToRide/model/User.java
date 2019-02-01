package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

public class User {

    private ID userID;

    public User(ID userID) {
        setUserID(userID);
    }

    private void setUserID(ID userID) {
        if (userID == null || !userID.isValid()) {
            throw new IllegalArgumentException();
        }

        this.userID = userID;
    }

    // todo: update later
    public boolean isValid() {
        return true;
    }

    public ID getUserID() {
        return this.userID;
    }
}
