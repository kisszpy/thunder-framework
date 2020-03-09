import com.runx.example.HelloExample;
import com.runx.example.dao.UserDao;
import com.runx.example.service.UserService;
import com.runx.example.service.impl.UserServiceImpl;
import com.runx.framework.aop.CglibProxy;
import com.runx.framework.aop.JdkProxy;
import com.runx.framework.ApplicationContext;
import com.runx.framework.bean.CglibProxyBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Slf4j
public class FrameworkTester {

    @Test
    public void testAop() {
        UserService userService = new UserServiceImpl();
        Object object = new JdkProxy(userService).getInstance();
        System.out.println(object);

    }

    @Test
    public void testClazz() throws NoSuchFieldException {
        HelloExample.class.getDeclaredField("userService");
    }

    @Test
    public void testScanner() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        UserService service = context.getBean(UserService.class);
        service.say();
//        UserService userService = context.getBean1("userServiceImpl");
//        userService.say();
    }

    @Test
    public void testCglib() throws NoSuchFieldException {
        HelloExample e = new HelloExample();
        HelloExample example = (HelloExample) new CglibProxy(e).getInstance();
    }

    @Test
    public void testLog() {
        log.info("hello");


    }

    @Test
    public void testInject() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        UserDao example = context.getBean(UserDao.class);
        System.out.println(example);
    }
}
