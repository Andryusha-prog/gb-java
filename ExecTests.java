import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExecTests {
    public static void main(String[] args) {

        start(testClass1.class);
    }

    static void start(Class testClass) {

        Map<Integer, Method> mapMethod = new TreeMap<>();
        Method[] methods = testClass.getDeclaredMethods();
        checkSuite(methods);

        startSuite(methods, BeforeSuite.class);
//----------------------------------------------------------------------------------------------------------------------
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getAnnotation(Test.class) != null) {
                mapMethod.put(methods[i].getAnnotation(Test.class).value(), methods[i]);
            }
        }
        for (Map.Entry<Integer, Method> entry : mapMethod.entrySet()) {
            try {
                entry.getValue().invoke(entry.getValue().getName());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
//----------------------------------------------------------------------------------------------------------------------
        startSuite(methods, AfterSuite.class);

    }

    static void startSuite(Method[] methods, Class suite) {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getAnnotation(suite) != null) {
                try {
                    methods[i].invoke(methods[i].getName());
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void checkSuite(Method[] methods) {
        int numberBefore = 0;
        int numberAfter = 0;
        for (Method o : methods) {
            if (o.getAnnotation(BeforeSuite.class) != null) {
                numberBefore++;
            }
            if (o.getAnnotation(AfterSuite.class) != null) {
                numberAfter++;
            }
        }
        if (numberAfter != 1 || numberBefore != 1) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.out.println("Аннотации BeforeSuite и AfterSuite не в единственном экземпляре");
                e.printStackTrace();
            }
        }

    }
}
