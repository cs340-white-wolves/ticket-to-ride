package cs340.TicketToRide.model.game;

import java.util.Objects;

import cs340.TicketToRide.utility.Username;

public class ChatMessage {
    private Username username;
    private String message;

    public ChatMessage(Username username, String message) {
        this.username = username;
        this.message = message;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, message);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
