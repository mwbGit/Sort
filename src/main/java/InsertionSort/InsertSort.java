package InsertionSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-17.
 * 直接插入排序(Insertion Sort)的基本思想是：每次将一个待排序的记录，
 * 按其关键字大小插入到前面已经排好序的子序列中的适当位置，直到全部记录插入完成为止。
 * 设数组为a[0…n-1]。
 * 1.初始时，a[0]自成1个有序区，无序区为a[1..n-1]。令i=1
 * 2.将a[i]并入当前的有序区a[0…i-1]中形成a[0…i]的有序区间。
 * 3.i++并重复第二步直到i==n-1。排序完成。
 */
public class InsertSort extends SuperSort {
    public static void main(String[] args) {
        int[] array = {11, 2, 222, 4, 55, 2221, 0, 7};
        Insertsort(array, array.length);
        show(array);
    }

    public static void Insertsort(int[] a, int length) {
       int i,j;
        for (i=1;i<length;i++){//i之前都是拍好序 循环..i++ 排序
        //    System.out.println("当前要排序的值="+a[i]+"(i="+i+")");
            for (j=i-1;j>=0&&a[j]>a[j+1];j--){//初始基准数为有序最后一位,即j-1(i-1);j与前一个数比较小于则交换位置,循环..j--;
                Swap(a,j+1,j);
        //      System.out.println("交换后为:");
        //      Show(a);
            }
        }
    }

}