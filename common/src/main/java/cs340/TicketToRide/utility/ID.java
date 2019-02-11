package cs340.TicketToRide.utility;

import java.util.Objects;
import java.util.UUID;

public class ID {

    private static final int ID_LENGTH = 8;
    private String id;

    public ID(String id) {
        setID(id);
    }

    private void setID(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        ID newID = (ID)o;
        return this.id.equals(newID.toString());
    }

    public boolean isValid() {
        return this.id != null && !this.id.equals("");
    }

    public String toString() {
        return this.id;
    }

    public static ID generateID() {
        String id = UUID.randomUUID().toString().substring(0, ID_LENGTH);
        return new ID(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
