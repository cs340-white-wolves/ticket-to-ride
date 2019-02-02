package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

public class Player {

    private ID userID;

    public Player(ID userID) {
        setUserID(userID);
    }

    private void setUserID(ID userID) {
        if (userID == null || !userID.isValid()) {
            throw new IllegalArgumentException();
        }

        this.userID = userID;
    }

    public boolean isValid() {
        return userID != null && userID.isValid();
    }
}
