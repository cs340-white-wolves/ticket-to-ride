package a340.tickettoride.presenter;
import java.lang.reflect.Method;

public class TestCommands {

    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] parameters;

    public TestCommand(String className, String methodName, Class<?>[] paramTypes, Object[] parameters) {
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.parameters = parameters;
    }



    public Object execute(IStringProcessor stringProcessor) throws ReflectiveOperationException {

            Class<?> targetClass = Class.forName(this.className);
            Method targetMethod = targetClass.getMethod(this.methodName, convertParamTypes(this.paramTypes));
            return targetMethod.invoke(stringProcessor, this.parameters);
    }
}
