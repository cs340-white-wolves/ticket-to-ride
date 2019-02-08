package cs340.TicketToRide.communication;

import com.google.gson.Gson;

public class Response {
    private String jsonString;
    private String className;
    private transient Gson gson = new Gson();

    public Response(Object object, String className) {
        setJsonString(object);
        setClassName(className);
    }

    public Object getResultObject() {
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
        if (object == null) {
            throw new IllegalArgumentException();
        }
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
}
