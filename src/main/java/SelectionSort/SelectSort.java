package SelectionSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-17.
 * 直接选择排序和直接插入排序类似，都将数据分为有序区和无序区，
 * 所不同的是直接播放排序是将无序区的第一个元素直接插入到有序区以形成一个更大的有序区，
 * 而直接选择排序是从无序区选一个最小的元素直接放到有序区的最后。
 */
public class SelectSort extends SuperSort {

    public static void main(String[] args) {
        int[] array = {11, 2, 222, 4, 55, 2221, 0, 7};
        SelectSort(array, array.length);
        show(array);
    }

    private static void SelectSort(int[] a, int n) {
        int i, j, nMinIndex;
        for (i = 0; i < n; i++) {
            nMinIndex = i;
            for (j = i + 1; j < n; j++) {//无序中遍历
                if (a[nMinIndex] > a[j]) {//找到最小
                    nMinIndex = j;
                }
            }
            Swap(a, i, nMinIndex);
        }
    }
}
