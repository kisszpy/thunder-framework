import com.runx.ds.Array;
import org.junit.Test;

/**
 * @author: kisszpy
 * @date: 2020/3/14
 */
public class ArrayTest {

    @Test
    public void add() {
        Array array = new Array();
        // array.addLast(20);
        array.add(0,1);
        array.add(1,2);
        array.add(2,3);
        array.add(1,5);
        System.out.println(array);
    }

    @Test
    public void testGrow() {
        int num = 90;
        Array array = new Array();
        for (int i = 0; i < num; i++) {
            array.add(i,i+1);
        }
        System.out.println(array);
    }

    @Test
    public void addFirst() {
        Array array = new Array();
        array.addFirst(3);
        array.addFirst(2);
        array.addFirst(1);
        System.out.println(array);
    }

    @Test
    public void addLast() {
        Array array = new Array();
        array.addLast(1);
        array.addLast(2);
        array.addLast(3);
        System.out.println(array);
    }

    @Test
    public void testRemove() {
        int num = 100000;
        Array array = new Array(num*2);
        for (int i = 0;i<num;i++) {
            array.addLast(i);
        }

        for (int i = 0;i<num;i++) {
            array.remove(0);
        }
        System.out.println(array);
    }
}
