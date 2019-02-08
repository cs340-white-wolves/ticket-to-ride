package cs340.TicketToRide.communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command implements ICommand {
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;


    public Command(String methodName, Class<?>[] paramTypes, Object[] params) {
        this.methodName = methodName;
        this.parameterTypes = paramTypes;
        this.parameters = params;
    }

    public Object execute(Object target) {
        Object result = null;

        try {
            Method method = Object.class.getMethod(methodName, parameterTypes);
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
            result = e.getTargetException();
        }

        return result;
    }

    public String getMethodName() {
        return methodName;
    }
    public Object[] getParameters() {
        return parameters;
    }

}
