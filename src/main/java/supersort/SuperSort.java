package supersort;

/**
 * Created by mwb on 16-2-17.
 */
public class SuperSort {
    public static void show(int []array){
        System.out.println("排序后数组:");
        for (int i:array){
            System.out.print(i+" ");
        }
    }
    public static void Show(int []array){
        for (int i:array){
            System.out.print(i+" ");
        }
        System.out.println();
    }
    public static void Swap(int array[],int a,int b){
        if (a==b){
            return;
        }
        int temp;
        temp=array[a];
        array[a]=array[b];
        array[b]=temp;
    }

}
