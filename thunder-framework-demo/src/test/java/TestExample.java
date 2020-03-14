import com.runx.example.HelloExample;
import com.runx.example.service.ArticleServiceImpl;
import com.runx.example.service.OkService;
import com.runx.example.service.UserService;
import com.runx.example.service.impl.UserServiceImpl;
import com.runx.framework.AnnotationApplicationContext;
import com.runx.framework.ApplicationContext;
import com.runx.framework.annotation.Autowired;
import com.runx.framework.aop.CglibProxy;
import com.runx.framework.bean.JdkProxyBeanFactory;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class TestExample {

    @Test
    public void test() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        for (int i = 0; i < 100000; i++) {
            HelloExample example = context.getBean(HelloExample.class);
            example.exec();
            ArticleServiceImpl articleService = context.getBean(ArticleServiceImpl.class);
            articleService.publish();
        }

    }

    @Test
    public void constructArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(ArticleServiceImpl.class.getConstructors().length);
        Constructor[] constructors = ArticleServiceImpl.class.getConstructors();
        Arrays.stream(constructors).forEach(item -> {
            System.out.println(item.getName());
            try {
                Parameter[] parameters = item.getParameters();
                for (Parameter parameter : parameters){
                    if (parameter.isAnnotationPresent(Autowired.class)) {
                        System.out.println("需要注入");
                    }
                }
                    item.newInstance("hello");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void articleCase() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        ArticleServiceImpl example = context.getBean(ArticleServiceImpl.class);
        example.publish();
    }

    @Test
    public void postConstruct() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        UserService example = context.getBean(UserServiceImpl.class);
        System.out.println(example.showMyLikes());
        context.close();
    }

    @Test
    public void testAop() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        UserService example = context.getBean(UserServiceImpl.class);
        example = (UserService) new JdkProxyBeanFactory(example).getBean();
        int  result = example.showMyLikes();
        System.out.println(result);
        context.close();
    }

    @Test
    public void testNewVersion() {
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"/tmp");
        AnnotationApplicationContext context = new AnnotationApplicationContext("com.runx.example");
        HelloExample example = context.getBean(HelloExample.class);
        System.out.println(example.getClass().getDeclaredFields().length);
        example.exec();
    }
}
