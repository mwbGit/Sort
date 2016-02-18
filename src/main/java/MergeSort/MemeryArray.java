package MergeSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-18.
 *  归并排序是建立在归并操作上的一种有效的排序算法。
 *  该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 *  首先考虑下如何将将二个有序数列合并。这个非常简单，只要从比较二个数列的第一个数，
 *  谁小就先取谁，取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，
 *  那直接将另一个数列的数据依次取出即可。
 */
public class MemeryArray extends SuperSort{

    public static void main(String[] args) {
        MemeryArray a=new MemeryArray();
        int [] array=new int[]{9,7,8,0,5,4,3,2,1};

        a.mergesort(array, 0, array.length - 1);
        show(array);
    }
    //将有二个有序数列a[first...mid]和a[mid...last]合并。
    void mergearray(int a[], int first, int mid, int last)
    {
        int []temp=new int[a.length];
        int i = first, j = mid + 1;
        int m = mid,   n = last;
        int k = 0;

        while (i <= m && j <= n)
        {//谁小谁放中间数组中
            if (a[i] <= a[j])
                temp[k++] = a[i++];
            else
                temp[k++] = a[j++];
        }

        while (i <= m)
            temp[k++] = a[i++];

        while (j <= n)
            temp[k++] = a[j++];

        for (i = 0; i < k; i++)
            a[first + i] = temp[i];
    }
    void mergesort(int a[], int first, int last)
    {
        if (first < last)
        {
            int mid = (first + last) / 2;
            mergesort(a, first, mid);    //左边有序
            mergesort(a, mid + 1, last); //右边有序
            mergearray(a, first, mid, last); //再将二个有序数列合并
        }
    }


}
