package cs340.TicketToRide.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs340.TicketToRide.utility.Username;

public class ChatMessage {
    public static final List<ChatMessage> TEST_CHATS = new ArrayList<>();

    private Username username;
    private String message;

    private static Username nate = new Username("nbarlow");
    private static Username brendon = new Username("bjensen");
    private static Username curt = new Username("cgilbert");
    private static Username reagan = new Username("rweston");

    static {
        TEST_CHATS.add(new ChatMessage(brendon, "Let's get started!"));
        TEST_CHATS.add(new ChatMessage(nate, "Sounds great."));
        TEST_CHATS.add(new ChatMessage(reagan, "+1"));
        TEST_CHATS.add(new ChatMessage(curt, "+1"));
        TEST_CHATS.add(new ChatMessage(brendon, "You know what I like? Really long messages that take a fair bit of screen space so that I can test to make sure that it wraps correctly in the UI."));
        TEST_CHATS.add(new ChatMessage(nate, "Interesting interests you have there :)"));
        TEST_CHATS.add(new ChatMessage(brendon, "Sed eu sodales dui. Integer urna odio, venenatis quis dui at, pharetra commodo nibh. Suspendisse vel arcu laoreet turpis dictum rhoncus id rhoncus elit. Nunc a cursus ligula. Curabitur pulvinar augue nec dignissim tincidunt. Morbi cursus tempor viverra. Sed placerat urna ac accumsan commodo. Nullam quis porta felis. Donec tellus lectus, porta eget congue ut, fermentum vel dui. Suspendisse potenti. Proin sapien lorem, mollis a magna dictum, ullamcorper suscipit sapien.\n"));
        TEST_CHATS.add(new ChatMessage(brendon, "10..."));
        TEST_CHATS.add(new ChatMessage(brendon, "9..."));
        TEST_CHATS.add(new ChatMessage(brendon, "8..."));
        TEST_CHATS.add(new ChatMessage(brendon, "7..."));
        TEST_CHATS.add(new ChatMessage(brendon, "6..."));
        TEST_CHATS.add(new ChatMessage(brendon, "5..."));
        TEST_CHATS.add(new ChatMessage(brendon, "4..."));
        TEST_CHATS.add(new ChatMessage(brendon, "3..."));
        TEST_CHATS.add(new ChatMessage(brendon, "2..."));
        TEST_CHATS.add(new ChatMessage(brendon, "1..."));
        TEST_CHATS.add(new ChatMessage(brendon, "DONE!"));
    }


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
