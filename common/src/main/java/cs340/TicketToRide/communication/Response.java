package cs340.TicketToRide.communication;

import com.google.gson.Gson;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.exception.NotUniqueException;

public class Response {
    private String jsonString;
    private String className;
    private String errMessage;
    private transient Gson gson = new Gson();

    public Response(Object object) {
        if (object == null) {
            object = "";
        }

        setJsonString(object);
        setClassName(object.getClass().getName());
    }

    public Response(Exception exception) {
        className = exception.getClass().getName();
        errMessage = exception.getMessage();
    }

    public Object getResultObject() {

        if (errMessage != null) {
            return new Exception(errMessage);
        }

        Class<?> clazz = null;
        if (gson == null) {
            gson = new Gson();
        }
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (clazz == null) {
            return null;
        }

        return gson.fromJson(jsonString, clazz);
    }

    public void setJsonString(Object object) {
        gson = new Gson();
        this.jsonString = gson.toJson(object);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if (className == null || className.equals("")) {
            throw new IllegalArgumentException();
        }
        this.className = className;
    }

    public boolean isError() {
        return errMessage != null && !errMessage.equals("");
    }

    public RuntimeException getException() {
        if (className.equals(AuthenticationException.class.getName())) {
            return new AuthenticationException(errMessage);
        }
        if (className.equals(GameFullException.class.getName())) {
            return new GameFullException(errMessage);
        }
        if (className.equals(NotUniqueException.class.getName())) {
            return new NotUniqueException(errMessage);
        }

        return new RuntimeException(errMessage);
    }
}
