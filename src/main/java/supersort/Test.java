package supersort;

/**
 * Created by mwb on 16-2-17.
 */
public class Test extends SuperSort {
    public static void main(String[] args) {
        Test a=new Test();
        int [] array=new int[]{9,7,8,0,5,4,3,2,1};
        a.bubblesort(array,array.length);
        show(array);
    }
    public void bubblesort(int []a,int len){
        int i,j;
        int nMindex;
        for (i=0;i<len;i++){
            nMindex=i;
            for (j=i+1;j<len;j++){
                if (a[nMindex]>a[j]){
                    nMindex=j;
                }
            }
            Swap(a,nMindex,i);
        }

    }
}
