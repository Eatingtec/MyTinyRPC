import mytest.MyTest;
import mytest.MyTestImpl;
import server.RPCServerInterfaceConfig;
import server.RPCServerManager;

/**
 * Created by Greeting on 2017/3/28.
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        RPCServerManager rpcServerManager = RPCServerManager.getInstance();
        RPCServerInterfaceConfig config = new RPCServerInterfaceConfig();
        config.setInterface(MyTest.class);
        config.setImplement(new MyTestImpl());
        rpcServerManager.add(config);
        rpcServerManager.start();

    }
}
