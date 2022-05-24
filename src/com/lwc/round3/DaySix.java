package com.lwc.round3;

import java.util.*;

public class DaySix {


    /**
     * 时间：20220406
     * 难度：中等
     * 题号：
     * 树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
     *
     * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），其中 edges[i} = [ai, bi} 表示树中节点 ai 和 bi 之间存在一条无向边。
     *
     * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
     *
     * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
     *
     * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
     * 分析：可以采用邻接表法先构建整个图，然后通过深度优先遍历计算最高的高度即可
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        int deepest =Integer.MAX_VALUE;
        //构建邻接表，表示图
        List<Integer>[] graph = new List[n];
        for(int i=0;i<n;i++){
            graph[i]=new ArrayList<Integer>();
        }
        //遍历给定的边，无向边要双向都需要增加
        for(int[] temp : edges){
            graph[temp[0]].add(temp[1]);
            graph[temp[1]].add(temp[0]);
        }
      //以每个节点为深度遍历一遍
        for(int i =0;i<n;i++){
            //保存遍历后的节点以防止重复遍历
            List<Integer> visited = new ArrayList<>();
            int temp =dfs(graph,visited,i);
            if(temp < deepest){
                ans.clear();
                deepest=temp;
                ans.add(i);
            }else if(temp == deepest){
                ans.add(i);
            }
        }
        return ans;
    }
    //深度优先遍历进行高度判断
    public int dfs(List<Integer>[] graph,List<Integer> visited,int root){
        int deep=0;
        //先将根节点加入
        visited.add(root);
        List<Integer> nodes = graph[root];
        if(nodes !=null && nodes.size()>0){
            for(int i :nodes){
                if(!visited.contains(i)){
                    visited.add(i);
                    deep =Math.max(dfs(graph,visited,i)+1,deep);
                }
            }
        }
        return deep;
    }
    final int MAX_N=20000;
    //down[i]表示为以零为根节点节点i向上的高度
    int[] up=new int[MAX_N];
    //down[i]表示以零为根节点节点i向下的高度
    int[] down=new int[MAX_N];
    //down[i]表示的是以零为根节点节点i向下的次大值
    int[] down2=new int[MAX_N];
    //采用树型动态规划进行解答
    public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        int deepest =Integer.MAX_VALUE;
        //构建邻接表，表示图
        List<Integer>[] graph = new List[n];
        for(int i=0;i<n;i++){
            graph[i]=new ArrayList<Integer>();
        }  //遍历给定的边，无向边要双向都需要增加
        for(int[] temp : edges){
            graph[temp[0]].add(temp[1]);
            graph[temp[1]].add(temp[0]);
        }
        dfs1(graph,new boolean[n],0);
        dfs2(graph,new boolean[n],0);
        for(int i =0 ;i<n ; i++){
            int tempDeep =Math.max(down[i],up[i]);
            if(deepest>tempDeep){
                deepest=tempDeep;
                ans.clear();
                ans.add(i);
            }else if(tempDeep == deepest){
                ans.add(i);
            }
        }
        return ans;
    }
    //深度优先遍历进行高度判断
    public int dfs1(List<Integer>[] graph,boolean[] visited,int root){
        //先将根节点加入
       visited[root]=true;
        List<Integer> nodes = graph[root];
        if(nodes !=null && nodes.size()>0){
            for(int i :nodes){
                if(!visited[i]){
                    visited[i]=true;
                    int sub =dfs1(graph,visited,i)+1;
                    //获取最大值
                    if(sub>down[root]){
                        down2[root]=down[root];
                        down[root]=sub;
                    }else if(down2[root]<sub){
                        //更新次大值
                        down2[root]=sub;
                    }
                }
            }
        }
        return down[root];
    }
    //计算向上的值
    public void dfs2(List<Integer>[] graph,boolean[] visited,int root){
        visited[root]=true;
        List<Integer> nodes =graph[root];
        if(nodes !=null && nodes.size()>0){
            //遍历子节点
            for(int i :nodes){
                if(!visited[i]){
                    visited[i]=true;
                    //如果最深的节点的子节点包含当前节点,则选择次大节点进行计算
                    if(down[root] == down[i]+1){
                        up[i]=Math.max(down2[root]+1,up[i]);
                    }else{
                        //如果当前节点不是,包含最深节点的路劲时
                        up[i]=Math.max(down[root]+1,up[i]);
                    }
                    up[i]=Math.max(up[root]+1,up[i]);
                    dfs2(graph,visited,i);
                }
            }
        }
    }
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
    /**
     * 题号：796
     * 难度：简单
     * 时间：20220407
     * 给定两个字符串, s 和 goal。如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
     *
     * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。 
     *
     * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 
     */
    public boolean rotateString(String s, String goal) {
        return s.length() == goal.length() && (s + s).contains(goal);
    }
    /**
     * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
     *
     * 树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
     */
    public List<List<Integer>> levelOrder(Node root) {

        List<List<Integer>> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        final int QUEUE_SIZE = 10000;
        //定义一个队列用于保存节点
        Node[] queue=new Node[QUEUE_SIZE];
        //定义队列的首尾,同时定义层次指向的首尾
        int front=0,rear=0;
        if(root.children ==null && root.children.size()>0){
            List<Integer> temp = new ArrayList<>();
            temp.add(root.val);
            ans.add(temp);
            return ans;
        }
        Node tail=root;
        //入队
        queue[rear++]=root;
        List<Integer> temp =null;

        //遍历整个树进行层次遍历
        while(rear != front){
            //出队
            Node node = queue[front++];
            List<Node> children=node.children;
            if(temp ==null){
                temp = new ArrayList<>();
            }
            temp.add(node.val);
            //区分层次
            if(node == tail){

            }

            if(children != null && children.size()>0){
                //子节点入队
                for(Node n:children){
                    queue[rear++]=n;

                }

            }
            if(node == tail){
                ans.add(temp);
                temp=null;
                tail=queue[rear-1];
            }

        }
        //加入最后一层的节点

        return ans;
    }

    /**
     * 时间：20220409
     * 难度：困难
     * 题号：780
     * 给定四个整数 sx , sy ，tx 和 ty，如果通过一系列的转换可以从起点 (sx, sy) 到达终点 (tx, ty)，则返回 true，否则返回 false。
     *
     * 从点 (x, y) 可以转换到 (x, x+y)  或者 (x+y, y)。
     * 分析：该题可以转化为从sx到tx需要多少个sx和多少个sy,同理sy到ty一样的操作即可
     * 这题我不会写，看的解析写的
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        //需要反向进行操作
       while(tx>sx && ty>sy){
           if(tx>ty){
               tx%=ty;
           }else{
               ty%=tx;
           }
       }
       //如果其中某个是小于目标
       if(tx<sx || ty<sy){
           return false;
       }
       return tx==sx ? (ty-sy)%tx ==0 : (tx-sx)%ty==0;
    }

    public int uniqueMorseRepresentations(String[] words) {
        Map<String,Integer> map =new HashMap<>();
        String[] strs = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        for(String str :words){
            StringBuffer sb = new StringBuffer();
            for(int i =0 ;i<str.length();i++){
                sb.append(strs[str.charAt(i)-'a']);
            }
            String temp =sb.toString();
            map.put(temp,map.getOrDefault(temp,0)+1);
        }
        return map.size();
    }

    /**
     * 题号：357
     * 难度：中等
     * 时间：20220411
     * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10n 。
     *分析：该题可以通过回溯法枚举各个长度段的数字进行累加从而获得答案
     */
    public int countNumbersWithUniqueDigits(int n) {
        int ans =0;
        //如果n等于0是说明只有一个数符合要求
        if(n == 0){
            return 1;
        }
        //如果n等于1时有0-9共十个，这样可以确保第一层枚举的首位肯定不为0且可以
        //避免漏答案

        return backTracking(0,new HashSet<>(),n);
    }
    public int backTracking(int level, Set<Integer> set,int len){
        //count对枚举的数量进行累加
        int count = 1;
        //如果当前的层次数小于长度，则可以进行下层遍历
        if(level<len){
            int i =1;
            //如果不是首位数的话那么就可以取零
            if(level != 0){
                i=0;
            }
            for(;i<10;i++){
                //如果set中包含了i则进行剪枝，从而减少次数
                if(set.contains(i)){
                    continue;
                }
                set.add(i);
                count+=backTracking(level+1,set,len);
                //移除i进行状态回溯
                set.remove(i);
            }
        }
        return count;
    }
    //采用动态规划进行解题
    public int countNumbersWithUniqueDigits2(int n) {
        int[][] dp= new int[n][n];

        return 0;
    }

    /**
     * 题号：806
     * 难度：简单
     * 时间：20220412
     * 我们要把给定的字符串 S 从左到右写到每一行上，每一行的最大宽度为100个单位，如果我们在写某个字母的时候会使这行超过了100 个单位，那么我们应该把这个字母写到下一行。我们给定了一个数组 widths ，这个数组 widths[0] 代表 'a' 需要的单位， widths[1] 代表 'b' 需要的单位，...， widths[25] 代表 'z' 需要的单位。
     *
     * 现在回答两个问题：至少多少行能放下S，以及最后一行使用的宽度是多少个单位？将你的答案作为长度为2的整数列表返回
     *
     */
    public int[] numberOfLines(int[] widths, String s) {
        int lineWeight =0;
        int lines =0;
        for(int i = 0;i<s.length();i++){
            char c = s.charAt(i);
            if(100-lineWeight>=widths[c-'a']){
                lineWeight+=widths[c-'a'];
            }else{
                lineWeight=widths[c-'a'];
                lines++;
            }
        }
        return new int[]{lines,lineWeight};
    }

    /**
     *将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     */
    public String convert(String s, int numRows) {
        return null;
    }
        public static void main(String[] args) {
        DaySix six =new DaySix();

        System.out.println(six.backTracking(0,new HashSet<>(),3));
    }
}
