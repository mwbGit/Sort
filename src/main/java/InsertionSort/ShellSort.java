package InsertionSort;

import supersort.SuperSort;

/**
 * Created by mwb on 16-2-17.
 * 希尔排序的实质就是分组插入排序，该方法又称缩小增量排序，因DL．Shell于1959年提出而得名。
 * 该方法的基本思想是：先将整个待排元素序列分割成若干个子序列（由相隔某个“增量”的元素组成的）
 * 分别进行直接插入排序，然后依次缩减增量再进行排序，待整个序列中的元素基本有序（增量足够小）时，
 * 再对全体元素进行一次直接插入排序。因为直接插入排序在元素基本有序的情况下（接近最好情况），
 * 效率是很高的，因此希尔排序在时间效率上比前两种方法有较大提高。
 */
public class ShellSort extends SuperSort{

    public static void main(String[] args) {
        int [] array={49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        shellSort(array);
        show(array);
    }

    public static void shellSort(int[] a){
       int i,j ,gap;//gap跨度
       for (gap=a.length/2;gap>0;gap/=2){
           for (i=gap;i<a.length;i++){
               if (a[i]<a[i-gap]){//直接插入
                   for (j=i-gap;j>=0&&a[j]>a[j+gap];j-=gap){
                       Swap(a,j,j+gap);
                   }
               }
           }
       }

    }


}
