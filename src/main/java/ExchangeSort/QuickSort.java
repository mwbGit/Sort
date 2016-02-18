package ExchangeSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-18.
 * 该方法的基本思想是：
 * 1．先从数列中取出一个数作为基准数。
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左右区间重复第二步，直到各区间只有一个数。虽然快速排序称为分治法，
 * 但分治法这三个字显然无法很好的概括快速排序的全部步骤。因此我的对快速排序作了进一步的说明：
 * 挖坑填数+分治法：
 */
public class QuickSort extends SuperSort {
    public static void main(String[] args) {
        QuickSort a = new QuickSort();
        int[] array = new int[]{9, 7, 8, 0, 5, 4, 3, 2, 1};
       a.quick_sort(array, 0, array.length - 1);
        show(array);
    }

    void quick_sort(int s[], int l, int r) {
        if (l < r) {
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && x < s[j]) {
                    j--;
                }
                if (i < j) {
                    s[i++] = s[j];
                }
                while ((j > i && x > s[i])) {
                    i++;
                }
                if ((i < j)) {
                    s[j--] = s[i];
                }
            }
            s[i] = x;
            quick_sort(s, l, i - 1);
            quick_sort(s, i + 1, r);
        }
    }



}
