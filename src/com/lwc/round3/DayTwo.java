package com.lwc.round3;


import com.lwc.round3.DayTwo;

import java.util.*;

public class DayTwo {
    public static void main(String[] args) {
        DayTwo two =new DayTwo();
        System.out.println(two.winnerOfGame( "BBBAAAABB"));
    }
    /**
     * 题号：606
     * 难度：简单
     * 时间：20220319
     * 你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。
     *
     * 空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
     */
    StringBuilder res = new StringBuilder();
    public String tree2str(TreeNode root) {
        preTree(root);
        return res.toString();
    }
    public void preTree(TreeNode root){
        if(root != null){
            //遍历根节点
            res.append(root.val);
            if(root.left != null ){
                res.append("(");
                preTree(root.left);
                res.append(")");
            }else if(root.right !=null && root.left == null){
                res.append("()");
            }
            if(root.right != null){
                res.append("(");
                preTree(root.right);
                res.append(")");
            }
        }
    }
    /**
     * 题号：2038
     * 难度：中等
     * 时间：20220322
     * 总共有 n 个颜色片段排成一列，每个颜色片段要么是 'A' 要么是 'B' 。给你一个长度为 n 的字符串 colors ，其中 colors[i] 表示第 i 个颜色片段的颜色。
     *
     * Alice 和 Bob 在玩一个游戏，他们 轮流 从这个字符串中删除颜色。Alice 先手 。
     *
     * 如果一个颜色片段为 'A' 且 相邻两个颜色 都是颜色 'A' ，那么 Alice 可以删除该颜色片段。Alice 不可以 删除任何颜色 'B' 片段。
     * 如果一个颜色片段为 'B' 且 相邻两个颜色 都是颜色 'B' ，那么 Bob 可以删除该颜色片段。Bob 不可以 删除任何颜色 'A' 片段。
     * Alice 和 Bob 不能 从字符串两端删除颜色片段。
     * 如果其中一人无法继续操作，则该玩家 输 掉游戏且另一玩家 获胜 。
     * 假设 Alice 和 Bob 都采用最优策略，如果 Alice 获胜，请返回 true，否则 Bob 获胜，返回 false。
     *
     */
    public boolean winnerOfGame(String colors) {
        boolean ans = false;
        int len = colors.length();
        //先对整个字符串数组进行预处理，判断连续字符的长度
        char[] chars = colors.toCharArray();
        Map<Character,Integer>[] lengths= new HashMap[len];
        int temp=1;
        for(int i = 0 ;i< len;i++){
            if(i<len -1 && chars[i] == chars[i+1]){
                temp++;
            }else if(i<len -1 && chars[i] != chars[i+1]){
                Map<Character,Integer> map = new HashMap<>();
                map.put(chars[i],temp);
                lengths[i]=map;
                temp = 1;
            }else if(i == len -1){
                //到达最后一个要进行特殊处理
                if(i-1>0 && chars[i] == chars[i-1]){
                    Map<Character,Integer> map = new HashMap<>();
                    map.put(chars[i],temp);
                    lengths[i]=map;
                }else{
                    Map<Character,Integer> map = new HashMap<>();
                    map.put(chars[i],1);
                    lengths[i]=map;
                }
            }
        }
        //记录timeA和timeB的次数
        int timeA =0,timeB=0;
        for(int i = 0 ;i< len;i++){
            if(lengths[i] != null){
                int tempA = lengths[i].getOrDefault('A',0)-2;
                int tempB = lengths[i].getOrDefault('B',0)-2;
                if(tempA>0){
                    timeA += tempA;
                }
                if(tempB > 0){
                    timeB += tempB;
                }
            }
        }
        //如果timeA比timeB仅多一次则a赢，多两次以上则a赢，a比b少则b赢
        if(timeA<=timeB){
            ans = false;
        }else{
            ans =true;
        }
        return ans;
    }

    /**
     * 题号：2039
     * 难度：中等
     * 时间：20220320
     * 给你一个有 n 个服务器的计算机网络，服务器编号为 0 到 n - 1 。同时给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示服务器 ui 和 vi 之间有一条信息线路，在 一秒 内它们之间可以传输 任意 数目的信息。再给你一个长度为 n 且下标从 0 开始的整数数组 patience 。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/the-time-when-the-network-becomes-idle
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 分析：首先算出每个到每个节点的最短路径，然后跟据最短路径在跟据发送时间计算每个节点的
     * 发送所有信息的时间，取最大值即可
     * 解题：
     */
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int ans =0;
        //跟据给定的边构造图
        List<Integer>[] maps= new List[patience.length];
        for(int i =0 ;i< patience.length;i++){
            maps[i] = new ArrayList<>();
        }
        for(int[] temp : edges){
            maps[temp[1]].add(temp[0]);
            maps[temp[0]].add(temp[1]);
        }
        int[] dis = dijkstra(maps);
        //遍历最短距离，跟重发时间选择最大的时间即可
        for(int i = 1;i<patience.length ;i++){
            int temp =2*dis[i];
            int temp2 = 0;
            if(temp / patience[i] == 0){
                temp2= temp+1;
            }else{
                //计算所有能过发送消息的次数
                int time = (temp/patience[i])*patience[i] -temp == 0 ?  temp/patience[i] : temp/patience[i]+1;
                temp2= (time-1)*patience[i]+temp+1;
            }
            ans = Math.max(ans ,temp2);
        }
        return ans;
    }
    //利用dijstra算法求出单元最短路径,返回最短距离的数组
    public int[] dijkstra(List<Integer>[] map){
        boolean[] visited =new boolean[map.length];
        //定义一个队列用于存放遍历节点
        Deque<Integer> deque =new ArrayDeque<>();
        int[] dis =new int[map.length];
        //初始时到其他节点的距离均为空
        Arrays.fill(dis,Integer.MAX_VALUE);
        //0到零的距离为零
        dis[0]=0;
        //将源点入队
        deque.add(0);
        while( !deque.isEmpty() ){
            //出队
            int i = deque.poll();
            //更新最短距离
            int lenght = dis[i];
            //遍历节点i的所有子节点
            for(int j = 0 ;j<map[i].size() ;j++){
                int k =map[i].get(j);
                if(!visited[k]){
                    //如果i是源点时则直接更新节点距离
                    if(i == 0 ){
                        dis[k]=1;
                    }else{
                        //如果i不是源点时则比较之前的距离，与直接距离进行更新最短路径
                        dis[k]=Math.min(dis[k],lenght+1);
                    }
                    //将该节点进行入队
                    deque.add(k);
                }
            }
            visited[i] = true;
        }
        return dis;
    }
    /**
     * 给定一个二叉搜索树 root 和一个目标结果 k，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
     */
    public boolean findTarget(TreeNode root, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        minTrace(root,map);
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            int i = k - entry.getKey();
            if(i == entry.getKey()){
                if(entry.getValue()>=2){
                    return true;
                }else{
                    continue;
                }
            }else{
                if(map.containsKey(i)){
                    return true;
                }else{
                    continue;
                }
            }
        }
        return false;
    }
    public void minTrace(TreeNode root ,Map<Integer,Integer> map){
        if(root !=null){
            map.put(root.val,map.getOrDefault(root.val,0)+1);
            minTrace(root.left,map);
            minTrace(root.right,map);
        }
    }

}
/**
 * 题号：2043
 * 难度：中等
 * 时间：20220318
 * 你的任务是为一个很受欢迎的银行设计一款程序，以自动化执行所有传入的交易（转账，存款和取款）。银行共有 n 个账户，编号从 1 到 n 。每个账号的初始余额存储在一个下标从 0 开始的整数数组 balance 中，其中第 (i + 1) 个账户的初始余额是 balance[i] 。
 *
 * 请你执行所有 有效的 交易。如果满足下面全部条件，则交易 有效 ：
 *
 * 指定的账户数量在 1 和 n 之间，且
 * 取款或者转账需要的钱的总数 小于或者等于 账户余额。
 * 实现 Bank 类：
 */
class Bank {
    long[] accounts;
    int len;
    public Bank(long[] balance) {
        accounts = balance;
        len = balance.length;
    }

    public boolean transfer(int account1, int account2, long money) {
        if(account1 > len+1 || account2>len+1){
            return false;
        }
        long moneyAccount1 = accounts[account1-1];
        if(moneyAccount1<money){
            return false;
        }
        accounts[account1-1] = moneyAccount1 - money;
        accounts[account2-1] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if(account >len+1){
            return false;
        }
        accounts[account-1] +=money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if(account>len+1 || accounts[account-1]<money){
            return false;
        }
        accounts[account-1]-=money;
        return true;
    }
}

class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
}
