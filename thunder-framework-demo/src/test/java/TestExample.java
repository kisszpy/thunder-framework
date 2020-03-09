import com.runx.example.HelloExample;
import com.runx.example.service.ArticleServiceImpl;
import com.runx.framework.ApplicationContext;
import com.runx.framework.annotation.Autowired;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class TestExample {

    @Test
    public void test() {
        ApplicationContext context = new ApplicationContext("com.runx.example");
        HelloExample example = context.getBean(HelloExample.class);
        example.exec();
        ArticleServiceImpl articleService = context.getBean(ArticleServiceImpl.class);
        articleService.publish();
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


}
