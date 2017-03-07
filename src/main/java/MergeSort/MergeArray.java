package MergeSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-18.
 * 归并排序是建立在归并操作上的一种有效的排序算法。
 * 该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 * 首先考虑下如何将将二个有序数列合并。这个非常简单，只要从比较二个数列的第一个数，
 * 谁小就先取谁，取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，
 * 那直接将另一个数列的数据依次取出即可。
 */
public class MergeArray extends SuperSort {

    public static void main(String[] args) {
        int[] array = new int[]{9, 7, 8, 0, 5, 4, 3, 2, 1};

        mergeSort(array, 0, array.length - 1);
        show(array);
    }

    public static void mergeArray(int[] array, int left, int middle, int right) {
        int i = left, j = middle + 1, k = 0;
        int[] temp = new int[array.length];

        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (i = 0; i < k; i++) {
            array[left + i] = temp[i];
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            mergeArray(array, left, mid, right);
        }
    }
}
