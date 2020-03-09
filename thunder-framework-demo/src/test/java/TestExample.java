import com.runx.example.HelloExample;
import com.runx.framework.ApplicationContext;
import org.junit.Test;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class TestExample {

    @Test
    public void test() {
        ApplicationContext  context = new ApplicationContext("com.runx.example");
        HelloExample example = context.getBean(HelloExample.class);
        example.exec();
    }

}
