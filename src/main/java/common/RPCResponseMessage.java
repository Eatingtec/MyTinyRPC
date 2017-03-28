package common;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCResponseMessage {
    private String requestId;
    private boolean isWrong;
    private boolean isExceptional;
    private String exceptionInfo;
    private Object result;

    @Override
    public String toString() {
        return "RPCResponseMessage{" +
                "requestId='" + requestId + '\'' +
                ", isWrong=" + isWrong +
                ", isExceptional=" + isExceptional +
                ", exceptionInfo='" + exceptionInfo + '\'' +
                ", result=" + result +
                '}';
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isWrong() {
        return isWrong;
    }

    public void setWrong(boolean wrong) {
        isWrong = wrong;
    }

    public boolean isExceptional() {
        return isExceptional;
    }

    public void setExceptional(boolean exceptional) {
        isExceptional = exceptional;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
