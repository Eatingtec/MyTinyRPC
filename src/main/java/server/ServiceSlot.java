package server;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Greeting on 2017/3/28.
 */
public class ServiceSlot {
    private Object object;
    private Map<String,Method> methods;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Map<String, Method> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, Method> methods) {
        this.methods = methods;
    }
}
