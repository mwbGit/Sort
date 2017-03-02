package supersort;

/**
 * Created by mwb on 16-2-17.
 */
public class Test extends SuperSort {
    public static void main(String[] args) {
        Test a = new Test();
        int[] array = new int[]{9, 7, 8, 0, 5, 4, 3, 2, 1, 1111};
        a.bubblesort(array);
        show(array);
    }

    public static void bubblesort(int a[]) {
        int i, j, temp, len = a.length;
        int grap;

        for (grap = len / 2; grap > 0; grap /= 2) {
            for (i = grap; i < len; i++) {
                for (j = i - grap; j >= 0 && a[j] > a[j + grap]; j -= grap) {
                    Swap(a, j, j + grap);
                }
            }
        }
    }


}
