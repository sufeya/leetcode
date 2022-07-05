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

    /**
     * 题号：513
     * 难度：中等
     * 时间：20220622
     * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
     *
     * 假设二叉树中至少有一个节点。
     */
    int MAX_SIZE = (int)1e4+1;
    public int findBottomLeftValue(TreeNode root) {
        //直接使用层次遍历既可以找到最后那一层最左边的节点值
        TreeNode[] queue = new TreeNode[MAX_SIZE];
        int top=0,bottom = 0;
        queue[bottom++]= root;
        TreeNode begin = root,end;
        int size =0,preSize=0;
        while(top != bottom){
            TreeNode p = queue[top++];
            size++;
            end = p;
            if(p.left != null ){
                queue[bottom++]=p.left;
            }
            if(p.right != null){
                queue[bottom++] = p.right;
            }
            if(end == begin){
                begin = queue[bottom-1];
                preSize = size;
                size=0;
            }
        }
        return queue[bottom-preSize].val;
    }

    /**
     * 题号：30
     * 难度：困难
     * 时间：20220623
     *给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
     *
     * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
     * 可以利用回溯法穷举范围内所有可能形成的结果然后去跟在窗口中的字符串进行匹配如果
     */
    List<Integer> ans =new ArrayList<>();
    public List<Integer> findSubstring(String s, String[] words) {
        //int[] used =new int[words.length];
        int len = words[0].length()*words.length,m = words[0].length();
        Map<String,Integer> map = new HashMap<>();
        for(String word:words){
            map.put(word,map.getOrDefault(word,0)+1);
        }
        out:for(int i =0;i+len<=s.length();i++){
           String temp = s.substring(i,i+len);
           Map<String ,Integer> cur = new HashMap<>();
           for(int j =0;j+m<=temp.length();j+=m){
               String temp2 = temp.substring(j,j+m);
               //如果有word中不包含字符则直接剪枝,跳到最外层循环
                if(!map.containsKey(temp2)){
                    continue  out;
                }
                cur.put(temp2,cur.getOrDefault(temp2,0)+1);
           }
           if(cur.equals(map)){
               ans.add(i);
           }
        }
        return ans;
    }
    public void dfs(String[] words,int[] used,StringBuffer sb,int deep,int len,String str,int index){
        if(deep == len){
            if(str.equals(sb.toString()) && !ans.contains(index)){
                ans.add(index);
            }
            return;
        }
        for(int i =0 ;i<words.length;i++){
            if(used[i] == 1){
                continue;
            }
            sb.append(words[i]);
            used[i]=1;
            dfs(words,used,sb,deep+1,len,str,index);
            used[i]=0;
            sb=sb.delete(sb.length()-words[i].length(),sb.length());
        }
    }

    /**
     *  题号：515
     *  难度：中等
     *  时间：20220624
     *给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
     */
    public List<Integer> largestValues(TreeNode root) {

        Queue<TreeNode> queue = new ArrayDeque<>();
        List<Integer> ans =new ArrayList<>();
        if(root == null){
            return ans;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            int max =Integer.MIN_VALUE;
            while(size>0){
               TreeNode  p = queue.poll();
               max = Math.max(p.val,max);
               size--;
               if(p.right != null){
                   queue.offer(p.right);
               }
               if(p.left != null){
                   queue.offer(p.left);
               }
            }
            ans.add(max);
        }
        return ans;
    }

    /**
     * 题号：offer 91
     * 难度：中等
     * 时间：20220625
     * 假如有一排房子，共 n 个，每个房子可以被粉刷成红色、蓝色或者绿色这三种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
     *
     * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。每个房子粉刷成不同颜色的花费是以一个 n x 3 的正整数矩阵 costs 来表示的。
     *
     * 例如，costs[0][0] 表示第 0 号房子粉刷成红色的成本花费；costs[1][2] 表示第 1 号房子粉刷成绿色的花费，以此类推。
     *
     * 请计算出粉刷完所有房子最少的花费成本。
     */
    public int minCost(int[][] costs) {
        int len = costs.length;
        //其中dp[i][0]表示的是第i个房子粉刷成红色前0-i个房子的最小费用
        int red =costs[0][0],bule=costs[0][1],green=costs[0][2];
        for(int i=1;i<len ;i++){
            int temp = red,temp2=green,temp3=bule;
            red=Math.min(temp3,temp2)+costs[i][0];
            bule=Math.min(temp,temp2)+costs[i][1];
            green=Math.min(temp,temp3)+costs[i][2];
        }
        return Math.min(Math.min(red,bule),green);
    }
    public static void main(String[] args) {
        DayFive five=new DayFive();
        System.out.println( five.minCost(new int[][]{{17,2,17},{16,16,5},{14,3,19}}));
        //System.out.println(five.findSubstring( "wordgoodgoodgoodbestword",new String[]{"word","good","best","good"}) );
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