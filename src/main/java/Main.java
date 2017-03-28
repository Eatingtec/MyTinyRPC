import java.lang.reflect.Method;

/**
 * Created by Greeting on 2017/3/26.
 */
public class Main {
    public static void main(String[] args) {
        Method[] methods = A.class.getMethods();
        for(Method method : methods){
            System.out.println(method.getName());
            for(Class c : method.getParameterTypes()){
                System.out.println(c.getName());
            }
        }

    }
}


interface A{
    void method1();
    void method2();
    void method3(int a,double b);
    void method4(double b,int a);

}
