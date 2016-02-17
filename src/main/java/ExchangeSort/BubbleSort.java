package ExchangeSort;

import supersort.SuperSort;
/**
 * Created by mwb on 16-2-17.
 * 冒泡排序是非常容易理解和实现，，以从小到大排序举例：
 * 设数组长度为N。
 * 1．比较相邻的前后二个数据，如果前面数据大于后面的数据，就将二个数据交换。
 * 2．这样对数组的第0个数据到N-1个数据进行一次遍历后，最大的一个数据就“沉”到数组第N-1个位置。
 * 3．N=N-1，如果N不为0就重复前面二步，否则排序完成。
 */
public class BubbleSort extends SuperSort {
    public static void main(String[] args) {
        int [] array={11,2,222,4,55,2221,0,7};
        BubbleSort(array, array.length);
        show(array);
    }
    public static  void BubbleSort(int []array ,int len){
        int i;//下标
        int k;//前k个元素需要排序 后面已排好
        int flag=len;//条件控制变量
        while(flag>0){
            k=flag;
            flag=0;//至0
            for (i=1;i<k;i++){//i=1开始 与前一个i=0 比较
                if (array[i-1]>array[i]){//前一个数大于当前数就交换位置 使最后一个数最大
                    Swap(array,i-1,i);//交换
                    flag=i;
                }//如果没有进入if 说明k之前已经排好序 无需交换 结束while
            }
        }

    }

}