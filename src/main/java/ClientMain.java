import client.RPCClientManager;
import client.RPCInterfaceConfig;
import mytest.MyTest;

/**
 * Created by Greeting on 2017/3/28.
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        RPCClientManager rpcClientManager = RPCClientManager.getInstance();
        RPCInterfaceConfig config = new RPCInterfaceConfig();
        config.setName("MyTest").setInterface(MyTest.class);
        rpcClientManager.add(config);
        MyTest myTest = (MyTest)rpcClientManager.get("MyTest");
        //Thread.sleep(2000);

        String res = myTest.testMethod();
        System.out.println(res);
    }
}
