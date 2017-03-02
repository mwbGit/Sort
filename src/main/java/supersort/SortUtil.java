package supersort;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * Java常用排序算法及性能测试集合
 * <p/>
 * 本程序集合涵盖常用排序算法的编写，并在注释中配合极其简单的特例讲解了各种算法的工作原理，以方便理解和吸收；
 * 程序编写过程中吸收了很多维基百科和别人blog上面的例子，并结合自己的思考，选择或改进一个最容易让人理解的写法。
 * 同时包含一个集中式的性能测试和正确性测试方法，方便观测。
 *
 * @author http://blog.csdn.net/sunxing007
 *         转载请注明来自http://blog.csdn.net/sunxing007
 */
public class SortUtil {
    // 被测试的方法集合  
    static String[] methodNames = new String[]{
//            "bubbleSort",
//            "bubbleSortAdvanced",
//            "bubbleSortAdvanced2",
//            "selectSort",
            "insertSort",
            "insertSortAdvanced",
            "insertSortAdvanced2",
            "binaryTreeSort",
            "shellSort",
            "shellSortAdvanced",
            "shellSortAdvanced2",
            "mergeSort",
            "quickSort",
            "heapSort"
    };

    public static void main(String[] args) throws Exception {
        //correctnessTest();
        performanceTest(50000);
    }

    /**
     * 正确性测试<br>
     * 简单地测试一下各个算法的正确性<br>
     * 只是为了方便观测新添加的算法是否基本正确；<br>
     *
     * @throws Exception 主要是反射相关的Exception；<br>
     */
    public static void correctnessTest() throws Exception {
        int len = 10;
        int[] a = new int[len];
        for (int i = 0; i < methodNames.length; i++) {
            for (int j = 0; j < a.length; j++) {
                a[j] = (int) Math.floor(Math.random() * len * 2);
            }
            Method sortMethod = null;
            sortMethod = SortUtil.class.getDeclaredMethod(methodNames[i], a.getClass());
            Object o = sortMethod.invoke(null, a);
            System.out.print(methodNames[i] + " : ");
            if (o == null) {
                System.out.println(Arrays.toString(a));
            } else {
                //兼顾mergeSort，它的排序结果以返回值的形式出现；  
                System.out.println(Arrays.toString((int[]) o));
            }
        }
    }

    /**
     * 性能测试<br>
     * 数组长度用参数len传入，每个方法跑20遍取耗时平均值；<br>
     *
     * @param len 数组长度 建议取10000以上，否则有些算法会显示耗时为0；<br>
     * @throws Exception 主要是反射相关的Exception；<br>
     */
    public static void performanceTest(int len) throws Exception {
        int[] a = new int[len];
        int times = 20;

        System.out.println("Array length: " + a.length);
        for (int i = 0; i < methodNames.length; i++) {
            Method sortMethod = null;
            sortMethod = SortUtil.class.getDeclaredMethod(methodNames[i], a.getClass());
            int totalTime = 0;
            for (int j = 0; j < times; j++) {
                for (int k = 0; k < len; k++) {
                    a[k] = (int) Math.floor(Math.random() * 20000);
                }
                long start = new Date().getTime();
                sortMethod.invoke(null, a);
                long end = new Date().getTime();
                totalTime += (end - start);
            }
            System.out.println(methodNames[i] + " : " + (totalTime / times) + " ms");
            //System.out.println(Arrays.toString(a));  
        }
    }

    /**
     * 最原始的冒泡交换排序;<br>
     * 两层遍历，外层控制扫描的次数，内层控制比较的次数；<br>
     * 外层每扫描一次，就有一个最大的元素沉底；所以内层的比较次数将逐渐减小；<br>
     * 时间复杂度: 平均:O(n^2)，最好:O(n);最坏:O(n^2);
     * 空间复杂度: O(1);
     */
    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * 改进的冒泡法<br>
     * 改进之处在于：设一个标志位，如果某趟跑下来，没有发生交换，说明已经排好了；<br>
     */
    public static void bubbleSortAdvanced(int[] a) {
        int k = a.length - 1;
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < k; i++) {
                if (a[i] > a[i + 1]) {
                    int tmp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = tmp;
                    //有交换则继续保持标志位；  
                    flag = true;
                }
            }
            k--;
        }
    }

    /**
     * 改进的冒泡法2<br>
     * 改进之处在于吸收上面的思想(没有交换意味着已经有序)，如果局部的已经是有序的，则后续的比较就不需要再比较他们了。<br>
     * 比如：3142 5678，假如刚刚做完了2和4交换之后，发现这趟比较后续再也没有发生交换，则后续的比较只需要比到4即可；<br>
     * 该算法就是用一个标志位记录某趟最后发生比较的地点；<br>
     */
    public static void bubbleSortAdvanced2(int[] a) {
        int flag = a.length - 1;
        int k;
        while (flag > 0) {
            k = flag;
            flag = 0;
            for (int i = 0; i < k; i++) {
                if (a[i] > a[i + 1]) {
                    int tmp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = tmp;
                    //有交换则记录该趟最后发生比较的地点；  
                    flag = i + 1;
                }
            }
        }
    }

    /**
     * 插入排序
     * <p/>
     * 关于插入排序，这里有几个约定，从而可以快速理解算法：<br>
     * i: 无序表遍历下标；i<n-1；<br>
     * j: 有序表遍历下表；0<=j<i;<br>
     * a[i]:表示当前被拿出来做插入排序的无序表头元素；<br>
     * a[j]:有序表中的任意元素；<br>
     * <br>
     * 算法关键点：把数组分割为a[0~i-1]有序表，a[i~n-1]无序表；每次从无序表头部取一个，<br>
     * 把它插入到有序表适当的位置，直到无序表为空；<br>
     * 初始时，a[0]为有序表，a[1~n-1]为无序表；<br>
     * <p/>
     * 时间复杂度: 平均:O(n^2)，最好:O(n);最坏:O(n^2);
     * 空间复杂度: O(1);
     */
    public static void insertSort(int[] a) {
        //从无序表头开始遍历；  
        for (int i = 1; i < a.length; i++) {
            int j;
            //拿a[i]和有序表元素依次比较，找到一个恰当的位置；  
            for (j = i - 1; j >= 0; j--) {
                if (a[j] < a[i]) {
                    break;
                }
            }
            //如果找到恰当的位置，则从该位置开始，把元素朝后移动一格，为插入的元素腾出空间；  
            if (j != (i - 1)) {
                int tmp = a[i];
                int k;
                for (k = i - 1; k > j; k--) {
                    a[k + 1] = a[k];
                }
                a[k + 1] = tmp;
            }
        }
    }

    /**
     * 改进的插入排序1
     * 改进的关键在于：首先拿无序表头元素a[i]和有序表尾a[i-1]比较，
     * 如果a[i]<a[i-1],说明需要调整；调整的过程为：
     * 从有序表尾开始，把有序表里面比a[i]大的元素都朝后移动，直到找到恰当的位置；
     */
    public static void insertSortAdvanced(int[] a) {
        //遍历无序表；  
        for (int i = 1; i < a.length; i++) {
            //如果无序表头元素小于有序表尾，说明需要调整；  
            if (a[i] < a[i - 1]) {
                int tmp = a[i];
                int j;
                //从有序表尾朝前搜索并比较，并把大于a[i]的元素朝后移动以腾出空间；  
                for (j = i - 1; j >= 0 && a[j] > tmp; j--) {
                    a[j + 1] = a[j];
                }
                a[j + 1] = tmp;
            }
        }
    }

    /**
     * 改进的插入排序2
     * 总体思想和上面相似，拿无序表头元素从有序表尾元素开始朝前比较，
     * 如果a[i]比a[i-1]小，则把a[i]从有序表尾用冒泡交换的方式朝前移动，直到到达恰当的位置；
     */
    public static void insertSortAdvanced2(int[] a) {
        //遍历无序表  
        for (int i = 1; i < a.length; i++) {
            //拿a[i]从有序表尾开始冒泡；  
            for (int j = i; j > 0 && a[j - 1] > a[j]; j--) {//a[j+1]就是a[i]
                int tmp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = tmp;
            }
        }
    }

    /**
     * 快速排序<br>
     * 算法的思想在于分而治之:先找一个元素(一般来说都是数组头元素),把比它大的都放到右边,把比它小的都放到左边;<br>
     * 然后再按照这样的思想去处理两个子数组; 下面说的子数组头元素通指用来划分数组的元素;<br>
     * <br>
     * 下面程序关键点就在于!forward, low0++, high0--这些运算; 这三个运算使得a[low0],a[high0]里面总有一个指向子数组头元素; <br>
     * 可以用极端的情况来方便理解这三个值的运作: <br>
     * 假如我的数列为0123456789, 初始时forward=false,0作为子数组划分依据,很显然第一轮的时候不会发生任何交换,low0一直指向0,<br>
     * high0逐渐下降直到它指向0为止; 同理可思考9876543210这个例子;<br>
     * <br>
     * 时间复杂度: 平均:O(nlogn)，最好:O(nlogn);最坏:O(n^2);
     * 空间复杂度: O(logn);要为递归栈提供空间
     *
     * @param a    待排序数组<br>
     * @param low  子数组开始的下标;<br>
     * @param high 子数组结束的下标;<br>
     */
    public static void quickSort(int[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int low0 = low;
        int high0 = high;
        boolean forward = false;
        while (low0 != high0) {
            if (a[low0] > a[high0]) {
                int tmp = a[low0];
                a[low0] = a[high0];
                a[high0] = tmp;
                forward = !forward;
            }
            if (forward) {
                low0++;
            } else {
                high0--;
            }
        }
        low0--;
        high0++;
        quickSort(a, low, low0);
        quickSort(a, high0, high);
    }

    /**
     * 快速排序的简单调用形式<br>
     * 方便测试和调用<br>
     *
     * @param a
     */
    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    /**
     * 归并排序<br> 
     * 所谓归并，就是合并两个有序数组；归并排序也用了分而治之的思想，把一个数组分为若干个子数组；<br> 
     * 当子数组的长度为1的时候，则子数组是有序的，于是就可以两两归并了；<br> 
     * <br> 
     * 由于归并排序需要分配空间来转储归并的结果，为了算法上的方便，归并算法的结果以返回值的形式出现；<br> 
     */

    /**
     * 合并两个有序数组
     *
     * @param a 有序数组1
     * @param b 有序数组2
     * @return 合并之后的有序数组；
     */
    public static int[] merge(int[] a, int[] b) {
        int result[] = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                result[k++] = a[i];
                i++;
            } else {
                result[k++] = b[j];
                j++;
            }
        }
        while (i < a.length) {
            result[k++] = a[i++];
        }
        while (j < b.length) {
            result[k++] = b[j++];
        }
        return result;
    }

    /**
     * 归并排序<br>
     * 把数组从中间一分为二，并对左右两部分递归调用，直到数组长度为1的时候，开始两两归并；<br>
     * 时间复杂度: 平均:O(nlogn)，最好:O(nlogn);最坏:O(nlogn);
     * 空间复杂度: O(n);要为归并的结果分配空间
     *
     * @return 有序数组；
     */
    public static int[] mergeSort(int[] a) {
        if (a.length == 1) {
            return a;
        }
        int mid = a.length / 2;
        int[] leftPart = new int[mid];
        int[] rightPart = new int[a.length - mid];
        System.arraycopy(a, 0, leftPart, 0, leftPart.length);
        System.arraycopy(a, mid, rightPart, 0, rightPart.length);
        leftPart = mergeSort(leftPart);
        rightPart = mergeSort(rightPart);
        return merge(leftPart, rightPart);
    }

    /**
     * 选择排序<br>
     * 和插入排序类似，它也把数组分割为有序区和无序区，所不同的是：<br>
     * 插入排序是拿无序区的首元素插入到有序区适当的位置，而<br>
     * 选择排序是从无序区中挑选最小的放到有序区最后；<br>
     * <br>
     * 两层循环，外层控制有序区的队尾，内层用来查找无序区最小元素；<br>
     * <p/>
     * 时间复杂度: 平均:O(n^2)，最好:O(n);最坏:O(n^2);
     * 空间复杂度: O(1);
     *
     * @param a
     */
    public static void selectSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }

    /**
     * 希尔排序<br>
     * 其思想是把数组按等步长(/间距)划分为多个子序列，对各个子序列做普通的插入排序，<br>逐次降低步长，直到为1的时候最后再做一次普通的插入排序；
     * 用一个极端的例子作比方，我有数列如下：<br>
     * [1,2,3,4,5,6,7,8,9,10];<br>
     * 初始的时候，步长gap=5；则划分的子数组为[1,6], [2,7], [3,8], [4,9], [5,10];<br>对他们分别排序(当然由于本数组特殊，所以结果是不变的)；<br>
     * 然后gap=2=5/2; 子数组为[1,3,5,7,9], [2,4,6,8,10]; <br>
     * 最后gap=1=2/2; 做一次全局排序；<br>
     * <br>
     * 希尔排序克服了插入/冒泡排序的弱点(一次只能把元素移动一个相邻的位置), <br>依靠大步长，可以把元素尽快移动到目标位置(或附近);<br>
     * 希尔排序实际上是插入排序的变种。它适用于：当数组总体有序，个别需要调整的情况；这时候利用插入排序的优势，可以达到O(n)的效率；<br>
     * 影响希尔算法的一个重要的因素是步长选择，一个好步长的优点是：后面的短步长排序不会破坏前面的长步长排序；<br>
     * 怎么理解这种破坏呢？前面的长步长把一个较小的数移到了左面，但是在缩小步长之后有可能又被交换到了右面 (因为它被分到了一个有很多比它更小的组)；<br>
     * 关于步长，可以查看http://zh.wikipedia.org上面关于希尔排序的页面；<br>
     * 下面的程序是希尔排序最基础的写法，适合用来理解希尔排序思想；<br>
     * <p/>
     * 时间复杂度： 受步长影响较大，n/2步长的平均复杂度为n(logn)^2;
     */
    public static void shellSort(int[] a) {
        // 控制间距；间距逐渐减小，直到为1；  
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            // 扫描每个子数组  
            for (int i = 0; i < gap; i++) {
                // 对每个字数组，扫描无序区；注意增量；  
                // a[i]是初始有序区；  
                for (int j = i + gap; j < a.length; j += gap) {
                    // 无序区首元素小于有序区尾元素，说明需要调整  
                    if (a[j] < a[j - gap]) {
                        int tmp = a[j];
                        int k = j - gap;
                        //从有序区尾向前搜索查找适当的位置；  
                        while (k >= 0 && a[k] > tmp) {
                            a[k + gap] = a[k];
                            k -= gap;
                        }
                        a[k + gap] = tmp;
                    }
                }
            }
        }
    }

    /**
     * 改进的希尔排序<br>
     * 改进之处在于：上面的写法用一个for循环来区别对待每个字数组；而实际上是不必要的；<br>
     * a[0,1,...gap-1]作为所有子数组的有序区，a[gap,...n-1]作为所有字数组的无序区；<br>
     * <br>
     * 该改进在时间效率上没有改进；只是让程序看起来更简洁；<br>
     *
     * @param a
     */
    public static void shellSortAdvanced(int[] a) {
        // 控制步长  
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            // 从无序区开始处理，把多个子数组放在一起处理；  
            for (int j = gap; j < a.length; j++) {
                // 下面的逻辑和上面是一样的；  
                if (a[j] < a[j - gap]) {
                    int tmp = a[j];
                    int k = j - gap;
                    while (k >= 0 && a[k] > tmp) {
                        a[k + gap] = a[k];
                        k -= gap;
                    }
                    a[k + gap] = tmp;
                }
            }
        }
    }

    /**
     * 改进的希尔排序2<br>
     * 在吸收shellSortAdvanced思想的基础上，采用insertAdvanced2的做法；<br>即无序区首元素通过朝前冒泡的形式移动的适当的位置；<br>
     *
     * @param a
     */
    public static void shellSortAdvanced2(int[] a) {
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                if (a[i] < a[i - gap]) {
                    for (int j = i - gap; j >= 0 && a[j + gap] > a[j]; j -= gap) {
                        int tmp = a[j];
                        a[j] = a[j + gap];
                        a[j + gap] = tmp;
                    }
                }
            }

        }
    }

    /**
     * 堆排序<br>
     * 堆的定义：堆是一个完全，或近似完全的二叉树，堆顶元素的值大于左右孩子的值，左右孩子也需要满足这个条件；<br>
     * 按照堆的定义，堆可以是大顶堆(maxHeap)，或小顶堆(minHeap)；<br>
     * 一般用数组即可模拟二叉树，对于任意元素i，左孩子为2*i+1,右孩子为2*i+2;父节点为(i-1)/2;
     * <p/>
     * 时间复杂度: 平均:O(nlogn);
     * 空间复杂度: O(1);
     *
     * @param a
     */
    public static void heapSort(int[] a) {

        // 先从最后一个非叶子节点往上调整，使满足堆结构；  
        for (int i = (a.length - 2) / 2; i >= 0; i--) {
            maxHeapAdjust(a, i, a.length);
        }
        // 每次拿最后一个节点和第一个交换，然后调整堆；直到堆顶；  
        for (int i = a.length - 1; i > 0; i--) {
            int tmp = a[i];
            a[i] = a[0];
            a[0] = tmp;
            maxHeapAdjust(a, 0, i);
        }
    }

    /**
     * 调整堆<br>
     * 把以i为跟节点的二叉树调整为堆；<br>
     * 可以这么来思考这个过程：这个完全二叉树就像一个金字塔，塔顶的小元素沿着树结构，往下沉降；<br>
     * 调整的结果是最大的元素在金字塔顶，然后把它从堆中删除(把它交换到堆尾，然后堆收缩一格)；<br>
     * 堆排序快的原因就是根据二叉树的特点，一个节点要沉降到合适的位置，只需要logn步；同时前期调整的结果(大小顺序)会被记录下来，从而加快后续的调整；<br>
     *
     * @param a   待排数组
     * @param i   堆顶
     * @param len 堆长度
     */
    public static void maxHeapAdjust(int[] a, int i, int len) {
        int tmp = a[i];
        // j是左孩子节点  
        int j = i * 2 + 1;
        //  
        while (j < len) {
            // 从左右孩子中挑选大的  
            // j+1是右孩子节点  
            if ((j + 1) < len && a[j + 1] > a[j]) {
                j++;
            }
            // 找到恰当的位置就不再找  
            if (a[j] < tmp) {
                break;
            }
            // 否则把较大者沿着树往上移动；  
            a[i] = a[j];
            // i指向刚才的较大的孩子；  
            i = j;
            // j指向新的左孩子节点；  
            j = 2 * i + 1;
        }
        // 把要调整的节点值下沉到适当的位置；  
        a[i] = tmp;
    }

    /**
     * 二叉树排序<br>
     * 二叉树的定义是嵌套的：<br>节点的值大于左叶子节点的值，小于右叶子节点的值；叶子节点同样满足这个要求；<br>
     * 二叉树的构造过程就是排序的过程：<br>
     * 先构造跟节点，然后调用add方法添加后续节点为跟节点的子孙节点；这个过程也是嵌套的;<br>
     * <br>
     * 中序遍历二叉树即得到有序结果；<br>
     * 二叉树排序用法特殊，使用情形要视情况而定；<br>
     * <p/>
     * 时间复杂度: 平均:O(nlogn);
     * 空间复杂度: O(n);
     *
     * @param a
     */
    public static void binaryTreeSort(int[] a) {
        // 构造一个二叉树节点内部类来实现二叉树排序算法；  
        class BinaryNode {
            int value;
            BinaryNode left;
            BinaryNode right;

            public BinaryNode(int value) {
                this.value = value;
                this.left = null;
                this.right = null;
            }

            public void add(int value) {
                if (value > this.value) {
                    if (this.right != null) {
                        this.right.add(value);
                    } else {
                        this.right = new BinaryNode(value);
                    }
                } else {
                    if (this.left != null) {
                        this.left.add(value);
                    } else {
                        this.left = new BinaryNode(value);
                    }
                }
            }

            /**
             * 按中序遍历二叉树，就是有序的。 
             */
            public void iterate() {
                if (this.left != null) {
                    this.left.iterate();
                }
                // 在测试的时候要把输出关掉，以免影响性能；  
                // System.out.print(value + ", ");  
                if (this.right != null) {
                    this.right.iterate();
                }
            }
        }

        BinaryNode root = new BinaryNode(a[0]);
        for (int i = 1; i < a.length; i++) {
            root.add(a[i]);
        }
        root.iterate();
    }
}  