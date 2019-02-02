package cs340.TicketToRide.communication;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cs340.TicketToRide.IServer;

public class ServerCommand implements IServerCommand {
    private String methodName;
    private Object[] parameters = null;//Only used on the server side
    private Class<?>[] parameterTypes; //Only used on server side.


    public ServerCommand(String methodName, Class<?>[] paramTypes, Object[] params) {
        this.methodName = methodName;
        this.parameterTypes = paramTypes;
        this.parameters = params;
    }
    //Queries

    public String getMethodName() {
        return methodName;
    }

    public Object execute(IServer target) {
        Object result = null;

        try {
            Method method = IServer.class.getMethod(methodName, parameterTypes);
            result = method.invoke(target, parameters);
        } catch (NoSuchMethodException | SecurityException e) {
            System.out.println("ERROR: Could not find the method " + methodName + ", or, there was a security error");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Illegal accesss while trying to execute the method " + methodName);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Illegal argument while trying to find the method " + methodName);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Illegal accesss while trying to execute the method " + methodName);
            e.printStackTrace();
        }

        return result;
    }
    public Object[] getParameters() {
        return parameters;
    }

    //Commands

}
