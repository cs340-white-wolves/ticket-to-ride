package cs340.TicketToRide.communication;

public class Response {
    private Object object;
    private String className;

    public Response(Object object, String className) {
        setObject(object);
        setClassName(className);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
