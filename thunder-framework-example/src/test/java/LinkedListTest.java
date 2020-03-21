import com.runx.ds.LinkedList;
import org.junit.Test;

/**
 * @author: kisszpy
 * @date: 2020/3/15
 */
public class LinkedListTest {

    @Test
    public void testAddLast() {
        LinkedList list = new LinkedList();
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        System.out.println(list.size());
    }

    @Test
    public void testAddHead() {
        LinkedList list = new LinkedList();
        for (int i = 0;i<10;i++) {
            list.addLast(i);
        }
        System.out.println(list.get(200));
        System.out.println(list.toString());
    }
}
