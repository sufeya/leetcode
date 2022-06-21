package com.lwc.round4;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DayThree {
    /**
     * 题号：829
     * 难度：困难
     * 时间：20220603
     * 给定一个正整数 n，返回 连续正整数满足所有数字之和为 n 的组数 。
     */
    //超时
    int ans =0;
    public int consecutiveNumbersSum(int n) {
        for(int i =1;i<=n/2;i++){
            sum(i,0,n);
        }
        return ans+1;
    }
    public boolean sum(int next,int sum,int n){
        sum+=next;
        if(sum >n){
            return false;
        }
        if(sum == n){
            ans++;
            return true;
        }
        if(sum<n){
            return sum(next+1,sum,n);
        }
        return false;
    }
    /**
     *不妨设起始数为a项数为k则可以知道等差数列求和为（2*a+k-1）*k=2*n
     * 需要进行枚举的话则必须直到k的取值范围2*n/k =2*a+k-1>=k+1(因为a>=1，k>=1)
     * 可以知道2*n>k*(k+1),枚举符合要求的数进行筛选，枚举k的值就要求出对应的a的值
     * 如果a的值符合要求则说明存在2*a=(2*n/k-k+1)
     */
    public int consecutiveNumbersSum2(int n) {
        int bound = 2*n;

      for(int k=1;k*(k+1)<=bound;k++){
            if(bound%k != 0){
                continue;
            }
            if((bound/k -k+1)%2 == 0){
                ans++;
            }
      }
        return ans;
    }

    /**
     * 题号：929
     * 难度：简单
     * 时间：20220604
     *每个 有效电子邮件地址 都由一个 本地名 和一个 域名 组成，以 '@' 符号分隔。除小写字母之外，电子邮件地址还可以含有一个或多个 '.' 或 '+' 。
     *
     * 例如，在 alice@leetcode.com中， alice 是 本地名 ，而 leetcode.com 是 域名 。
     * 如果在电子邮件地址的 本地名 部分中的某些字符之间添加句点（'.'），则发往那里的邮件将会转发到本地名中没有点的同一地址。请注意，此规则 不适用于域名 。
     *
     * 例如，"alice.z@leetcode.com” 和 “alicez@leetcode.com” 会转发到同一电子邮件地址。
     * 如果在 本地名 中添加加号（'+'），则会忽略第一个加号后面的所有内容。这允许过滤某些电子邮件。同样，此规则 不适用于域名 。
     *
     * 例如 m.y+name@email.com 将转发到 my@email.com。
     * 可以同时使用这两个规则。
     *
     * 给你一个字符串数组 emails，我们会向每个 emails[i] 发送一封电子邮件。返回实际收到邮件的不同地址数目。
     */
    public int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet();
        for(String email:emails){
            String[] temps = email.split("@");
            StringBuffer sb = new StringBuffer();
            String endAddress;
            if(temps[0].contains("+")){
               String[] address =temps[0].split("\\+",2);
                endAddress= address[0].replace(".","");
            }else{
               endAddress= temps[0].replace(".","");
            }
            sb.append(endAddress).append("@").append(temps[1]);
            set.add(sb.toString());
        }
        return set.size();
    }

    /**
     * 题号：478
     * 难度：中等
     * 时间：20220605
     *给定圆的半径和圆心的位置，实现函数 randPoint ，在圆中产生均匀随机点。
     *
     * 实现 Solution 类:
     *
     * Solution(double radius, double x_center, double y_center) 用圆的半径 radius 和圆心的位置 (x_center, y_center) 初始化对象
     * randPoint() 返回圆内的一个随机点。圆周上的一点被认为在圆内。答案作为数组返回 [x, y] 。
     */

    class Solution {
        public double r;
        public double x;
        public double y;
        public Solution(double radius, double x_center, double y_center) {
            this.r=radius;
            this.x=x_center;
            this.y=y_center;
        }

        public double[] randPoint() {
            Random random= new Random();
            while (true) {
                double x = random.nextDouble() * (2 * r) - r;
                double y = random.nextDouble() * (2 * r) - r;
                if (x * x + y * y <= r * r) {
                    return new double[]{this.x + x, this.y + y};
                }
            }

        }
    }

    /**
     * 题号：729
     * 难度：中等
     * 时间：20220606
     *实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
     *
     * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
     *
     * 日程可以用一对整数 start 和 end 表示，这里的时间是半开区间，即 [start, end), 实数 x 的范围为，  start <= x < end 。
     *
     * 实现 MyCalendar 类：
     *
     * MyCalendar() 初始化日历对象。
     * boolean book(int start, int end) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。否则，返回 false 并且不要将该日程安排添加到日历中。
     * 分析：利用线段树进行求解，且进行动态开点，在查询的时候进行开点，并且在更新的时候对节点进行对节点进行更新，
     * 在更新的时候使用懒标记进行更新，在查询或者更新的时候判断则将懒标记进行下传到子节点进行更新即可，而不用将
     * 懒标记下传到下属的所有的节点进行更新大大节约时间
     */
    class Node{
        //val 表示下属节点数，lNode表示左节点对应的下标，rNode表示右节点对应的下标，lazy表示的是懒标记
        int val=0,lNode=0,rNode=0,lazy=0;
        int start=0,end=0;
        public Node(int start,int end){
            this.start=start;
            this.end=end;
        }
        public Node(){

        }
    }
    int N = (int)1e9, M = 120010;
    //开辟列表空间用于存放节点,尽量开辟最大空间
    Node[] tree = new Node[M];
    //索引用于记录创建节点的位置
    int index =2;
    //查询区间是否有节点判断能不能进行插入,root表示查询节点的根节点下标，rl，rr表示的是根节点表示的范围
    //左界rl,右界rr,end，start表示的是要插入的左右界
    public int qurey(int root,int rl,int rr,int start,int end){
        //如果要插入的区间覆盖了当前的节点的范围则直接返回值
        if(start<=rl && rr<=end){
            return tree[root].val;
        }
        //在查询的过程中对节点进行创建
        createNode(root,rl,rr);
       int lazy =tree[root].lazy;
        int mid = rl+(rr-rl>>1);
        //将懒标记下推
        if(lazy==1){
            pushDown(root,mid-rl+1,rr-mid+1);
        }

        int ans =0;
        //题目的区间是左闭右开区间
        if(start<= mid){
            ans = qurey(tree[root].lNode,rl,mid,start,end);
        }
        if(end>mid){
            ans +=qurey(tree[root].rNode,mid+1,rr,start,end);
        }
        return ans;
    }
    //对线段树进行更新操作
    public void update(int root ,int rl,int rr,int start,int end ,int val){
        if(start<=rl && rr<=end){
            tree[root].lazy=val;
            tree[root].val=((rr-rl)+1)*val;
            return;
        }
        //在更新的过程中同时需要创建节点
        createNode(root,rl,rr);
        //如果父节点有懒标记则将懒标记向下推
        int lazy =tree[root].lazy;
        int mid = rl+(rr-rl>>1);
        if( lazy == 1){
            pushDown(root,mid-rl+1,rr-mid+1);
        }
        if(start<=mid){
            update(tree[root].lNode,rl,mid,start,end,val);
        }
        if(mid<end){
            update(tree[root].rNode,mid+1,rr,start,end,val);
        }

        pushUp(root);
    }
    //将懒标记向下推
    public void pushDown(int root,int lLen,int rLen){
        tree[tree[root].lNode].val=lLen;
        tree[tree[root].rNode].val=rLen;
        tree[tree[root].lNode].lazy = tree[root].lazy;
        tree[tree[root].rNode].lazy = tree[root].lazy;
        tree[root].lazy=0;
    }
    //将子节点更新的结果进行上推
    public void pushUp(int root){
        tree[root].val = tree[tree[root].rNode].val+tree[tree[root].lNode].val;
    }
    //创建线段树节点
    public void createNode(int root,int start,int end){
        if(tree[root] == null){
            tree[root] = new Node(start,end);
        }
        int mid = start+(end-start>>1);
        if(tree[root].lNode == 0){
            tree[root].lNode=index++;
            tree[tree[root].lNode] = new Node(start,mid);
        }
        if(tree[root].rNode == 0){
            tree[root].rNode =index++;
            tree[tree[root].rNode] = new Node(mid+1,end);
        }
    }
    public boolean book(int start ,int end ){
        if(qurey(1,1,N+1,start+1,end) >0){
            return false;
        }
        update(1,1,N+1,start+1,end,1);
        return true;
    }



    /**
     * 题号：875
     * 难度：中等
     * 时间：20220607
     *珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
     *
     * 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。  
     *
     * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     *
     * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
     *
     */
    public int minEatingSpeed(int[] piles, int h) {
        //通过二分查找对速度进行查找，可知最大的速度阈值为最大香蕉的个数，但是这个最小值
        //最小值应当为所有香蕉累加的速度
        long right = 0;
        long left=0;
        for(int i : piles){
            right= Math.max(i,right);
            left+=i;
        }
        left= (left/h)*h<left ?(left/h)+1:(left/h);
        while(left<right){
            long mid = left +(right-left>>1);
            //计算在该速度下真正的时间
            int time=0;
            for(int i:piles){
                time+=(i/mid)*mid <i ?(i/mid)+1:(i/mid);
            }
            if(time>h){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        return (int)right;
    }

    /**
     * 题号：1037
     * 难度：简单
     * 时间：20220608
     *给定一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点，如果这些点构成一个 回旋镖 则返回 true 。
     *
     * 回旋镖 定义为一组三个点，这些点 各不相同 且 不在一条直线上 。
     */
    public boolean isBoomerang(int[][] points) {
        //向量叉积，判断是否为零，为零则共线
        int[] v1 = {points[1][0] - points[0][0], points[1][1] - points[0][1]};
        int[] v2 = {points[2][0] - points[0][0], points[2][1] - points[0][1]};
        return v1[0] * v2[1] - v1[1] * v2[0] != 0;
    }


    public static void main(String[] args) {
        DayThree three = new DayThree();
        MyCalendarThree mc= new MyCalendarThree();
        int[][] temp = new int[][]{{47,50},{1,10},{27,36},{40,47},{20,27},{15,23},{10,18},{27,36},{17,25},{8,17},{24,33},{23,28},{21,27},{47,50},{14,21},{26,32},{16,21},{2,7},{24,33},{6,13},{44,50},{33,39},{30,36},{6,15},{21,27},{49,50},{38,45},{4,12},{46,50},{13,21}};
        for(int[] i :temp){
            System.out.println(mc.book(i[0],i[1]));
        }
//23 26

    }
}
/**
 * 题号：732
 * 难度：困难
 * 时间：20220606
 *当 k 个日程安排有一些时间上的交叉时（例如 k 个日程安排都在同一时间内），就会产生 k 次预订。
 *
 * 给你一些日程安排 [start, end) ，请你在每个日程安排添加后，返回一个整数 k ，表示所有先前日程安排会产生的最大 k 次预订。
 *
 * 实现一个 MyCalendarThree 类来存放你的日程安排，你可以一直添加新的日程安排。
 *
 * MyCalendarThree() 初始化对象。
 * int book(int start, int end) 返回一个整数 k ，表示日历中存在的 k 次预订的最大值。
 *
 */
class MyCalendarThree {
    int max;
    Node node;
    int N = (int)1e9+1;
    class Node {
        int count =0,lazy=0,left=0,right=0;
        Node rNode,lNode;
        public Node(int left,int right){
            this.left=left;
            this.right=right;
        }
        public Node(){

        }
    }
    public void update(Node root,int start,int end){
        int rr=root.right,rl=root.left;
        if(start<=rl && rr<=end){
            root.count++;
            root.lazy+=1;
            return;
        }
        int mid = rl+(rr-rl>>1);
        createNode(root,rl,rr,mid);

        pushDown(root);

        if(mid>=start){
            update(root.lNode,start,end);
        }
        if(end>mid){
            update(root.rNode,start,end);
        }
        pushUp(root,start,end);
    }
    public int query(Node root,int start ,int end){
        int rr=root.right,rl=root.left;
        if(start<=rl && rr<=end){
            return root.count;
        }
        int mid = rl +(rr-rl>>1);
        createNode(root,rl,rr,mid);

        pushDown(root);

        int count =0;
        if(mid>=start){
            count= query(root.lNode,start,end);
        }
        if(mid<end){
            count = Math.max(count,query(root.rNode,start,end));
        }
        return count;
    }
    public void createNode(Node root,int left,int right,int mid){
        if(root == null){
            root = new Node(left,right);
        }
        if(root.lNode == null){
            root.lNode= new Node(left,mid);
        }
        if(root.rNode==null){
            root.rNode=new Node(mid+1,right);
        }
    }
    //将懒标记下推
    public void pushDown(Node root){
        root.lNode.count+=root.lazy;
        root.lNode.lazy+=root.lazy;
        root.rNode.lazy+=root.lazy;
        root.rNode.count+=root.lazy;
        root.lazy=0;
    }
    public void pushUp(Node root,int start ,int end){
        root.count=Math.max(root.lNode.count,root.rNode.count);
    }
    public int book(int start ,int end){
        update(node,start,end-1);
        return node.count;
    }
    public MyCalendarThree(){
        max=0;
        node= new Node(0,N);
    }
}

