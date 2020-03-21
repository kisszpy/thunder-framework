import com.runx.MyArray;
import org.junit.Test;

/**
 * @author: kisszpy
 * @date: 2020/3/17
 */
public class MyArrayTest {

    @Test
    public void arrayAddLast() {
        MyArray<Integer> array = new MyArray<>();
        for (int i = 0; i < 100; i++) {
            array.addLast(i + 1);
        }
        System.out.println(array);
    }

    @Test
    public void arrayAddFirst() {
        MyArray<Integer> array = new MyArray<>();
        for (int i = 0; i < 10; i++) {
            array.addFirst(i + 1);
        }
        System.out.println(array);
    }
    @Test
    public void remove() {
        MyArray<Integer> array = new MyArray<>();
        for (int i = 0; i < 10; i++) {
            array.addFirst(i + 1);
        }
        array.remove(3);
        System.out.println(array);
    }

    @Test
    public void getIndex() {
        MyArray<Integer> array = new MyArray<>();
        for (int i = 0; i < 10; i++) {
            array.addFirst(i + 1);
        }
        Integer value = array.get(2);
        array.set(2,999);
        System.out.println(array);
    }
}
