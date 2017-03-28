package common;

import java.util.List;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCRequestMessage {
    private String id;
    private String interfaceName;
    private String methodName;
    private List<Object> parameters;

    @Override
    public String toString() {
        return "RPCRequestMessage{" +
                "id='" + id + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + parameters +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
