package com.lwc.round4;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * @author liuweichun
 * @date 2022/7/22 15:27
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayNine {

     /**
     * 题号：757
     * 难度：困难
     * 时间：20220722
     * 一个整数区间 [a, b]  ( a < b ) 代表着从 a 到 b 的所有连续整数，包括 a 和 b。
     *
     * 给你一组整数区间intervals，请找到一个最小的集合 S，使得 S 里的元素与区间intervals中的每一个整数区间都至少有2个元素相交。
     *
     * 输出这个最小集合S的大小。
     * 这题不会完全没有思路看的大佬的
     * 自己的想法是对每个点集取交集最后答案就是没有交集的集合个数
     */
    public int intersectionSizeTwo(int[][] intervals) {
        //先对区间进行排序，以右边界从小到达进行排列，
        //保证添加到集合中的值是从小到大，同时在右边界相等时保证左边界是由大到小，可以保证不会重复加入节点
        Arrays.sort(intervals,(a , b)-> a[1] != b[1] ?a[1]-b[1] :b[0]-a[0]);
        //取最大的值与次大的值
        int first=-1,second = -1,ans = 0;
        for(int[] temp : intervals){
            //则只需要加入最右端的一个点即可
            if(temp[0]>first){
                //如果集合中的最大值都小于其左边界则
                ans+=2;
                first=temp[1];
                second=temp[1]-1;

            }else if(temp[0]>second){
                ans++;
                second=first;
                first=temp[1];
            }
        }
        return ans;
    }
    //取两个数集之间的交集
    public int[] getUnion(int[] first,int[] second){
        //两个数之间没有交集,返回空集
        if(first[0]>second[1] || first[1]<second[0]){
            return null;
        }
        int left = Math.max(first[0],second[0]),right = Math.min(first[1],second[1]);
        return new int[]{left,right};
    }
    /**
     * 题号：115
     * 难度：中等
     * 时间：20220724
     * 给定一个长度为 n 的整数数组 nums ，其中 nums 是范围为 [1，n] 的整数的排列。还提供了一个 2D 整数数组 sequences ，其中 sequences[i] 是 nums 的子序列。
     * 检查 nums 是否是唯一的最短 超序列 。最短 超序列 是 长度最短 的序列，并且所有序列 sequences[i] 都是它的子序列。对于给定的数组 sequences ，可能存在多个有效的 超序列 。
     *
     * 例如，对于 sequences = [[1,2],[1,3]] ，有两个最短的 超序列 ，[1,2,3] 和 [1,3,2] 。
     * 而对于 sequences = [[1,2],[1,3],[1,2,3]] ，唯一可能的最短 超序列 是 [1,2,3] 。[1,2,3,4] 是可能的超序列，但不是最短的。
     * 如果 nums 是序列的唯一最短 超序列 ，则返回 true ，否则返回 false 。
     * 子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺序的序列。
     */
    List<Integer>[] graph;
    int n;
    Map<Integer,Integer> degree;
    public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
        n= nums.length;
        //跟据序列构建一个图
        graph = new List[n+1];
       for(int i = 1;i<n+1;i++){
           graph[i] = new ArrayList<>();
       }
        //对应每个节点的入度
        degree = new HashMap<>();
        for(int[] sequence :sequences){
            buildGraph(sequence);
        }
        //对该图进行拓扑排序
        //先将所有的入度为零的节点进队
        Queue<Integer> queue = new ArrayDeque<>();
        for(int i =1;i<=n;i++){
            if(!degree.containsKey(i)){
                queue.offer(i);
            }
        }
        int orderSize = 0,k=0;
        List<List<Integer>> order = new ArrayList();
        //广度优先遍历进行拓扑排序
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            while(size>0){
                int  i = queue.poll();
                temp.add(i);
                for(int j : graph[i]){
                    int d = degree.get(j)-1;
                    if(d == 0){
                        queue.offer(j);
                    }
                    degree.put(j,d);
                }
                size--;
            }
            order.add(temp);
            orderSize=Math.max(orderSize,temp.size());
            if(orderSize>1 || temp.get(0) != nums[k]){
                return false;
            }
            k++;
        }
        return true;
    }
    public void buildGraph(int[] sequence){
        for(int i =1 ;i<sequence.length;i++){
            int first = sequence[i-1],second = sequence[i];
            graph[first].add(second);
            degree.put(second,degree.getOrDefault(second,0)+1);

        }
    }

    /**
     * 题号：1184
     * 难度：简单
     * 时间：20220724
     * 环形公交路线上有 n 个站，按次序从 0 到 n - 1 进行编号。我们已知每一对相邻公交站之间的距离，distance[i] 表示编号为 i 的车站和编号为 (i + 1) % n 的车站之间的距离。
     *
     * 环线上的公交车都可以按顺时针和逆时针的方向行驶。
     *
     * 返回乘客从出发点 start 到目的地 destination 之间的最短距离。
     */
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        //顺时针进行遍历
        int n = start,sLen=0,nLen=0,len = distance.length;
      while(n != destination){
          sLen+=distance[n];
          n=(n+1)%len;
      }
      n = start;
      while(n!=destination){
          n=(n+len-1)%len;
          nLen+=distance[n];
      }
        return Math.min(sLen,nLen);
    }

    /**
     * 题号：592
     * 难度：中等
     * 时间：20220727
     * 给定一个表示分数加减运算的字符串 expression ，你需要返回一个字符串形式的计算结果。 
     *
     * 这个结果应该是不可约分的分数，即最简分数。 如果最终结果是一个整数，例如 2，你需要将它转换成分数形式，其分母为 1。所以在上述例子中, 2 应该被转换为 2/1。
     */
    public String fractionAddition(String expression) {
        return null;
    }

    //找到两个数的质因数
    public Map<Integer,Integer> findOutPrime(int i){
        Map<Integer,Integer> map = new HashMap<>();
        for(int j = 2;j<=Math.sqrt(i) && i>1;j++){
            int k =j,count = 1,base=j;
            if(i%j==0){
                while(isPrime(j) && i%(k*base)== 0){
                    k=base*k;
                    count++;
                }
                i= i/k;
                map.put(base,count);
            }
        }
        if(i>1){
            map.put(i,1);
        }
        return map;
    }
    public boolean isPrime(int i){
        for(int j = 2 ;j<Math.sqrt(i);j++){
            if(j%i == 0){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        DayNine nine = new DayNine();
        Skiplist skiplist = new Skiplist();
        skiplist.add(9628);
       Map map = nine.findOutPrime(6);
    }
}
/**
 * 题号：1206
 * 难度：困难
 * 时间：20220726
 * 不使用任何库函数，设计一个 跳表 。
 *
 * 跳表 是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。跳表相比于树堆与红黑树，其功能与性能相当，并且跳表的代码长度相较下更短，其设计思想与链表相似。
 *
 * 例如，一个跳表包含 [30, 40, 50, 60, 70, 90] ，然后增加 80、45 到跳表中，以下图的方式操作：
 *
 *
 * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
 *
 * 跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操作的时间复杂度不超过 O(n)。跳表的每一个操作的平均时间复杂度是 O(log(n))，空间复杂度是 O(n)。
 *
 * 了解更多 : https://en.wikipedia.org/wiki/Skip_list
 *
 * 在本题中，你的设计应该要包含这些函数：
 *
 * bool search(int target) : 返回target是否存在于跳表中。
 * void add(int num): 插入一个元素到跳表。
 * bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
 * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
 */
class Skiplist {
    //查询开始节点
    SkipNode begin;
    //跳表节点
    class SkipNode{
        int val;
        SkipNode down,right;
        public SkipNode(int val ,SkipNode down,SkipNode right){
            this.val  = val;
            this.down = down;
            this.right = right;
        }
    }
    //设置调表概率
    double SKIPLIST_P = 0.5;
    int MAX_LEVEL = 16;
    //产生一个随机的层数当level为1时表示只在原始层创建节点
    //当level等2时表示创建一层索引，可以知道有1/2的概率生成1 1/4的概率生成2以此类推有1/2^生成k
    //在生成k时分别创建1....k-1层索引，这样就保证了 1/2 的概率生成第一层索引，1/4的概率生成第二层索引
    //因为生成1为1/2除一以外都将生成第一层索引故可以保证一层索引为原始节点的1/2其他层次类推
    public int randomLevel(){
        int level = 1;
        while( Math.random()<SKIPLIST_P && level<MAX_LEVEL){
            level+=1;
        }
        return level;
    }
    int currentLevel = 1;
    public Skiplist() {
        begin = new SkipNode(-1,null,null);
    }

    public boolean search(int target) {
        SkipNode node = begin;
        while(node != null){
            if(node.val == target){
                return true;
            }else if(node.val<target){
                if(node.right != null && node.right.val <=target){
                    node=node.right;
                }else if(node.right == null || (node.right != null && node.right.val> target)){
                    node = node.down;
                }
            }else{
                return false;
            }
            System.out.println("查询节点的值"+node.val);
        }
        return false;
    }

    //插入跳表
    public void add(int num) {
        int level = randomLevel();
        //如果leve == 1 则说明不需要插入索引直接查表即可
        if(level == 1){
            SkipNode node = begin;
            while(node != null){
                //如果下索引跟右指针为空或者右指针的值要大于当前节点时直接插入
                if(node.down == null &&(node.right == null || node.right.val>num  )){
                    SkipNode temp = new SkipNode(num,node.down,node.right);
                    node.right = temp;
                   break;
                }else if(node.right == null || (node.right != null && node.right.val > num)){
                    //将需要更新的左索引加入到栈中后续进行更新索引
                    node = node.down;
                } else if(node.right.val<=num) {
                    node = node.right;
                }
                System.out.println("增加节点的值"+node.val);
            }
        }else{
            //如果level != 1 则需要插入对应的索引，同时注意当需要插入的索引值大于当前层时需要进行更新
            Stack<SkipNode> stack = new Stack<>();
            int tempLevel = level;
            while(tempLevel>currentLevel){
                SkipNode node = new SkipNode(begin.val,begin,null);
                begin= node;
                tempLevel--;
            }
            //更新当前层次
            currentLevel = level;
            SkipNode node = begin;
            SkipNode basesNode= null;
            while(node != null){
                //如果下索引跟右指针为空或者右指针的值要大于当前节点时直接插入
                if(node.down == null &&(node.right == null || node.right.val>num  )){
                    SkipNode temp = new SkipNode(num,node.down,node.right);
                    basesNode = temp;
                    node.right = temp;
                    break;
                }else if(node.right == null || (node.right != null && node.right.val > num)){
                    stack.push(node);
                    //将需要更新的左索引加入到栈中后续进行更新索引
                    node = node.down;
                } else if(node.right.val<=num) {
                    node = node.right;
                }
                System.out.println("增加节点的值"+node.val);
            }
            //更新索引
            while(!stack.isEmpty()){
                SkipNode tempNode = stack.pop();
                SkipNode newIndex = new SkipNode(num,basesNode,tempNode.right);
                tempNode.right = newIndex;
                basesNode = newIndex;
            }
        }
    }
    //删除跳表
    public boolean erase(int num) {
        //找不到节点则直接删除
        if(!search(num)){
            return false;
        }
        SkipNode temp = begin;
        while(temp != null){

            if(temp.right == null){
                temp=temp.down;
            }else if(temp.right.val == num){
                //删除右节点
                temp.right = temp.right.right;
                //接着往下进行遍历删除
                temp=temp.down;
            }else if(temp.right.val > num){
                temp=temp.down;
            }else if(temp.right.val < num){
                //小于的话就向右走
                temp = temp.right;
            }
            System.out.println("删除节点的值"+temp.val);
        }
        return true;
    }
}
//上述方法超时了，现在使用新的方法进行是实现,也就是力扣上的实现方法
class Skiplist2 {
    private Random random;
    private Double SKIP_P = 0.25;
    private int MAX_LEVEL = 32;
    private SkipNode head;
    private int level;

    public Skiplist2(){
        this.head = new SkipNode(-1,32);
        random = new Random();
        this.level = 0;
    }
    public boolean search(int target) {
        SkipNode cur = this.head;
        //先找到水平方向找到大于或等于target的层，然后向下进行查找
        for(int i = level-1;i>=0;i--){
            while(cur.forwords[i] != null && cur.forwords[i].val<target){
                cur=cur.forwords[i];
            }
        }
        cur = cur.forwords[0];
        //如果找不到目标值则直接返回false
        if(cur == null || cur.val != target){
            return  false;
        }
        return true;
    }
    //插入跳表
    public void add(int num) {
        SkipNode cur = this.head;
        SkipNode[] updateNode = new SkipNode[MAX_LEVEL];
        Arrays.fill(updateNode,head);
        for(int i = level-1;i>=0;i--){
            while(cur.forwords[i] != null && cur.forwords[i].val < num){
                cur=cur.forwords[i];
            }
            //记录需要更新索引的前向索引
            updateNode[i] = cur;
        }

        int newLevel = randomLevel();
        //更新最高层
        level = Math.max(newLevel,level);
        SkipNode newNode = new SkipNode(num,newLevel);
        //插入新的索引
        for(int i =0 ;i<newLevel;i++){
            newNode.forwords[i] = updateNode[i].forwords[i];
            updateNode[i].forwords[i] = newNode;
        }
    }
    //删除跳表
    public boolean erase(int num) {
        SkipNode cur = this.head;
        SkipNode[] updateNodes = new SkipNode[MAX_LEVEL];
        Arrays.fill(updateNodes,head);
        for(int i = level -1 ;i>=0;i--){
            while(cur.forwords[i] != null && cur.forwords[i].val <num){
                cur = cur.forwords[i];
            }
            updateNodes[i] = cur;
        }
        cur = cur.forwords[0];
        if(cur == null || cur.val != num){
            return false;
        }
        for(int i = 0;i< level ;i++){
            //如果当前没有建索引则直接退出即可
            if(cur != updateNodes[i].forwords[i]){
                break;
            }
            updateNodes[i].forwords[i]=updateNodes[i].forwords[i].forwords[i];
        }
        //更新最高层
        while(level>1 && this.head.forwords[level-1] == null){
            level--;
        }
        return true;
    }
    private int randomLevel(){
        int lev =1;
        while(random.nextDouble() <SKIP_P && lev<MAX_LEVEL){
            lev++;
        }
        return lev;
    }
    class SkipNode{
        private int val;
        private SkipNode[] forwords;
        public SkipNode(int val , int level){
            this.val = val;
            //跟据所传进来的层数创建前向指针
            this.forwords = new SkipNode[level];
        }
    }
}
/**
 * 完全二叉树 是每一层（除最后一层外）都是完全填充（即，节点数达到最大）的，并且所有的节点都尽可能地集中在左侧。
 *
 * 设计一种算法，将一个新节点插入到一个完整的二叉树中，并在插入后保持其完整。
 *
 * 实现 CBTInserter 类:
 *
 * CBTInserter(TreeNode root) 使用头节点为 root 的给定树初始化该数据结构；
 * CBTInserter.insert(int v)  向树中插入一个值为 Node.val == val的新节点 TreeNode。使树保持完全二叉树的状态，并返回插入节点 TreeNode 的父节点的值；
 * CBTInserter.get_root() 将返回树的头节点。
 */
class CBTInserter {
    List<TreeNode> nodeList;
    TreeNode root;
    public CBTInserter(TreeNode root) {
        nodeList = new ArrayList<>();
        this.root = root;
        //层次遍历将节点放入数组中
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size>0){
                TreeNode node = queue.poll();
                nodeList.add(node);
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
                size--;
            }
        }
    }

    public int insert(int val) {
        //2*i+1 = n-1 --> i=n/2 -1   2*i+2 = n-1 --> i = n/2 -3/2 == n/2 -1
        //获取最后一个节点的父节点
        int lastNodeParent = nodeList.size()/2 -1;
        TreeNode node = null;
        if(lastNodeParent>=0){
            node = nodeList.get(lastNodeParent);
        }
        TreeNode rightCh = new TreeNode(val);
        nodeList.add(rightCh);
        //只有一个根节点时
        if(node != null && node.right == null ){
            node . right = rightCh;
            return node.val;
        }else{
            TreeNode temp = nodeList.get(lastNodeParent+1);
            temp.left=rightCh;
            return temp.val;
        }
    }

    public TreeNode get_root() {
        return root;
    }
}