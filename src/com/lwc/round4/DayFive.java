package com.lwc.round4;

import java.util.*;

/**
 * @author liu
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 11:24
 */
public class DayFive {

    /**
     * 题号：719
     * 难度：困难
     * 时间：20220615
     * 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
     *
     * 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。返回 所有数对距离中 第 k 小的数对距离。
     */
    public int smallestDistancePair(int[] nums, int k) {
        //先对数组进行排序
        Arrays.sort(nums);
        int len = nums.length,right=nums[len-1]-nums[0],left=0;
        while(right>left){
            int mid = left+(right-left>>1);
            int count =0;
            //双指针
            for(int i =0,j=0;j<len;j++){
                //找出小于等于mid的所有数对
                while(nums[j]-nums[i]>mid){
                    i++;
                }
                count+=j-i;
            }
            if(count<k){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        return right;
    }
    /**
     * 题号：532
     * 难度：中等
     * 时间：20220616
     * 给定一个整数数组和一个整数 k，你需要在数组里找到 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
     *
     * 这里将 k-diff 数对定义为一个整数对 (nums[i], nums[j])，并满足下述全部条件：
     *
     * 0 <= i < j < nums.length
     * |nums[i] - nums[j]| == k
     * 注意，|val| 表示 val 的绝对值。
     */
    public int findPairs(int[] nums, int k) {

        int len = nums.length,count=0;
        //先对数组进行排序
        Arrays.sort(nums);
        for(int i=0,j=0;j<len;j++){
            while(nums[j]-nums[i]>=k&&i<j){
                if((nums[j]-nums[i] == k) &&(i-1<0 ||nums[i] != nums[i-1]) ){
                    count++;
                }
                i++;
            }
        }
        return count;
    }
    /**
     * 题号：42
     * 难度：困难
     * 时间：20220616
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     */
    public int trap(int[] height) {
        int len = height.length,ans =0;
        int[] right = new int[len],left = new int[len];
        left[0]=height[0];
        right[len-1]=height[len-1];
        for(int i =1;i<len ;i++){
            left[i]=Math.max(left[i-1],height[i]);
        }
        for(int j =len-2;j>=0;j--){
            right[j]=Math.max(right[j+1],height[j]);
        }
        for(int i=0;i<len;i++){
            int temp = Math.min(right[i],left[i]);
            ans+=(temp-height[i]);
        }
        return ans;
    }
    /**
     * 题号：1089
     * 难度：简单
     * 时间：20220617
     *给你一个长度固定的整数数组 arr，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
     *
     * 注意：请不要在超过该数组长度的位置写入元素。
     *
     * 要求：请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
     */
    public void duplicateZeros(int[] arr) {
        Queue<Integer> queue =new ArrayDeque<>();
        int i =0,len=arr.length;
        while(i<len){
            if(!queue.isEmpty()){
                queue.offer(arr[i]);
                arr[i]=queue.poll();
            }

            if(i<len && arr[i] == 0 && i+1<len){
                queue.offer(arr[i+1]);
                arr[i+1]=0;
                i+=2;
            }else{
                i++;
            }
        }
    }

    /**
     * 题号：508
     * 难度：中等
     * 时间：20220619
     * 给你一个二叉树的根结点 root ，请返回出现次数最多的子树元素和。如果有多个元素出现的次数相同，返回所有出现次数最多的子树元素和（不限顺序）。
     *
     * 一个结点的 「子树元素和」 定义为以该结点为根的二叉树上所有结点的元素之和（包括结点本身）。
     */
    Map<Integer,Integer> map = new HashMap<>();
    public int[] findFrequentTreeSum(TreeNode root) {
        dfs(root);
        int max=0;
        List<Integer>ans = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry :map.entrySet()){
            int temp = entry.getValue();
            if(temp>max){
                max=temp;
                ans.clear();
                ans.add(entry.getKey());
            }else if(temp == max){
                ans.add(entry.getKey());
            }
        }
       int[] temp= new int[ans.size()];
        for(int i =0 ;i<ans.size();i++){
            temp[i] = ans.get(i);
        }
        return temp ;
    }
    public int dfs(TreeNode root ){
        int sum= root.val;
        if(root.left != null){

            sum+=dfs(root.left);
        }
        if(root.right != null ){
            sum+= dfs(root.right);
        }
        map.put(sum,map.getOrDefault(sum,0)+1);
        return sum;
    }

    /**
     * 题号：029
     * 难度：中等
     * 时间：20220619
     * 给定循环单调非递减列表中的一个点，写一个函数向这个列表中插入一个新元素 insertVal ，使这个列表仍然是循环升序的。
     *
     * 给定的可以是这个列表中任意一个顶点的指针，并不一定是这个列表中最小元素的指针。
     *
     * 如果有多个满足条件的插入位置，可以选择任意一个位置插入新的值，插入后整个列表仍然保持有序。
     *
     * 如果列表为空（给定的节点是 null），需要创建一个循环有序列表并返回这个节点。否则。请返回原先给定的节点。
     */
    public Node insert(Node head, int insertVal) {
        if( head == null){
            head = new Node(insertVal);
            head.next=head;
            return head;
        }
        Node pre =head,next = head.next;
        Node inserNode = new Node(insertVal);
        pre=head;
        next=pre.next;
        do{
            //插两节点之间的情况
            if(insertVal>=pre.val && insertVal<=next.val){
                break;
            }
            if(pre.val>next.val &&(insertVal<= pre.val || insertVal<=next.val)){
               break;
            }
            pre=next;
            next=next.next;
        }while((pre != head));
        inserNode.next=next;
        pre.next=inserNode;
        return  head;
    }

    /**
     * 题号：1108
     * 难度：简单
     * 时间：20220621
     * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
     *
     * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
     */
    public String defangIPaddr(String address) {
        return address.replace(".","[.]");
    }

    public static void main(String[] args) {
        /*DayFive five=new DayFive();
        System.out.println(five.defangIPaddr("1.1.1.1"));*/
        RangeModule rangeModule = new RangeModule();
        /*rangeModule.addRange(10,20);
        rangeModule.removeRange(14,16);
        System.out.println(rangeModule.queryRange(10,14));
        System.out.println(rangeModule.queryRange(13,15));
        System.out.println(rangeModule.queryRange(16,17));*/
        rangeModule.addRange(10,180);//
        rangeModule.addRange(150,200);//191
        rangeModule.addRange(250,500);//251
        System.out.println(rangeModule.queryRange(50,100));
        System.out.println(rangeModule.queryRange(180,300));
        System.out.println(rangeModule.queryRange(600,1000));
        rangeModule.removeRange(50,150);
        System.out.println(rangeModule.queryRange(50,100));
    }
}
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }
}
/**
 * 题号：715
 * 难度：困难
 * 时间：
 * Range模块是跟踪数字范围的模块。设计一个数据结构来跟踪表示为 半开区间 的范围并查询它们。
 *
 * 半开区间 [left, right) 表示所有 left <= x < right 的实数 x 。
 *
 * 实现 RangeModule 类:
 *
 * RangeModule() 初始化数据结构的对象。
 * void addRange(int left, int right) 添加 半开区间 [left, right)，跟踪该区间中的每个实数。添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 [left, right) 中尚未跟踪的任何数字到该区间中。
 * boolean queryRange(int left, int right) 只有在当前正在跟踪区间 [left, right) 中的每一个实数时，才返回 true ，否则返回 false 。
 * void removeRange(int left, int right) 停止跟踪 半开区间 [left, right) 中当前正在跟踪的每个实数。
 */
class RangeModule {
    class Node{
        Node leftNode,rightNode;
        int end ,begin,lazy,count;
        public Node(int begin,int end){
            lazy=0;
            this.begin=begin;
            this.end=end;
        }
    }
    int MAX= (int)1e9;
    Node root;
    public RangeModule() {
        root=new Node(1,MAX);
    }

    public void addRange(int left, int right) {
        if(query(root,left,right-1) == right-left+1 ){
            return;
        }
        update(left,right-1,root);
    }
    public void update(int begin ,int end ,Node root){
        int nr=root.end,nl=root.begin;
        //如果左界跟右界覆盖了该根节点则直接更新该根节点即可
        if(begin<=nl && end>=nr){
            root.lazy=1;
            root.count=root.end-root.begin+1;
            return;
        }
        createNode(root,nl,nr);
        int mid =nl +(nr-nl>>1);
        //如果当前节点节点存在懒标记则将懒标记下移
        if(root.lazy == 1){
            pushDown(root);
        }

        if(begin<=mid){
            update(begin,end,root.leftNode);
        }
        if(end>mid){
            update(begin, end, root.rightNode);
        }
        pushUp(root);
    }
    //删除对应节点
    public void delete(Node root,int begin,int end){
        int nl = root.begin,nr = root.end;
        //如果刚好覆盖了对应的节点则直接删除该节点
        if(nl>=begin && nr<=end){
            root.count=0;
            root.lazy=1;
            return;
        }
        createNode(root,nl,nr);
        int mid = nl +(nr-nl>>1);
        if(root.lazy == 1){
            pushDown(root);
        }
        if(mid>=begin){
            delete(root.leftNode,begin,end);
        }
        if(mid<end){
            delete(root.rightNode,begin,end);
        }
        pushUp(root);
    }
    public int query(Node root ,int begin,int end){
        int nl = root.begin,nr = root.end;
        if(nl>=begin && nr<=end){
            return root.count;
        }
        createNode(root,nl,nr);
        //查询的时候也要将懒标记给下推
        int mid = nl +(nr-nl>>1),count=0;
        if(root.lazy == 1){
            pushDown(root);
        }

        if(begin<=mid){
            count=query(root.leftNode,begin,end);
        }
        if(mid<end){
            count+=query(root.rightNode,begin,end);
        }
        return count;
    }
    public void pushDown(Node root){
        root.leftNode.lazy=1;
        root.rightNode.lazy=1;
        root.lazy=0;
        root.leftNode.count=(root.count == 0 ? 0:root.leftNode.end-root.leftNode.begin+1);
        root.rightNode.count=(root.count == 0 ? 0:root.rightNode.end-root.rightNode.begin+1);
    }
    public void pushUp(Node root){
        root.count=root.leftNode.count+root.rightNode.count;
    }

    public void createNode(Node root,int begin,int end){
        if(root == null){
            root = new Node(begin,end);
        }
        int mid = begin+(end-begin>>1);
        if(root.leftNode == null){
            root.leftNode = new Node(begin,mid);
        }
        if(root.rightNode == null){
            root.rightNode = new Node(mid+1,end);
        }
    }
    public boolean queryRange(int left, int right) {
        int ans = query(root,left,right-1);
        return ans == right-left;
    }

    public void removeRange(int left, int right) {
        if(query(root,left,right-1) == 0){
            return;
        }
        delete(root,left,right-1);
    }
}