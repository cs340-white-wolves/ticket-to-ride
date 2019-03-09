package a340.tickettoride.presenter;
import java.lang.reflect.Method;

import a340.tickettoride.model.ClientModel;

public class TestCommand {

    private String name;
    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] parameters;

    public TestCommand(String name, String className, String methodName, Class<?>[] paramTypes, Object[] parameters) {
        this.name = name;
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.parameters = parameters;
    }



    public void execute(ClientModel model) throws ReflectiveOperationException {
            Class<?> targetClass = Class.forName(this.className);
            Method targetMethod = targetClass.getMethod(this.methodName, this.paramTypes);
            targetMethod.invoke(model, this.parameters);
    }

    public String toString() {
        return this.name;
    }
}
