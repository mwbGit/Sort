package supersort;

import java.util.*;

/**
 * Created by mwb on 16-2-17.
 */
public class Test extends SuperSort {
    public static void main(String[] args) {
        int[] array = new int[]{9, 7, 8, 0, 5, 4, 3, 2, 1, 1111};
        Random random = new Random();
        System.out.println(random.nextInt(1000));
        int a = (int) (Math.random() * 100);
        System.out.println(a);
        show(array);

        List<Integer> list = Arrays.asList(2, 3, 6, 1);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list);
    }

}
