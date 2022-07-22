package com.lwc.round4;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/7/7 16:34
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DaySeven {
    /**
     * 在英语中，我们有一个叫做 词根(root) 的概念，可以词根后面添加其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。例如，词根an，跟随着单词 other(其他)，可以形成新的单词 another(另一个)。
     *
     * 现在，给定一个由许多词根组成的词典 dictionary 和一个用空格分隔单词形成的句子 sentence。你需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，则用最短的词根替换它。
     *
     * 你需要输出替换之后的句子。
     */
    class Node{
        Node[] root = new Node[26];
        boolean flag= false;
    }
    Node[] root =new Node[26];
    public String replaceWords(List<String> dictionary, String sentence) {
        //构建字典树
        for(String str : dictionary){
            buildTree(str);
        }
        StringBuilder sb = new StringBuilder();
        String[] words = sentence.split(" ");
      for(String str : words){
          Node[] bRoot = root;
          boolean isPrex = false;
          StringBuilder prex = new StringBuilder();
          int contain = 0,len =str.length();
          for(int i = 0; i<len;i++){
              char c = str.charAt(i);
              if(!isPrex){
                  Node node =bRoot[c-'a'];
                  if( node !=null){
                      prex.append(c);
                      isPrex = node.flag;
                      bRoot = node.root;
                      contain =1;
                  }else{
                      sb.append(str+" ");
                      break;
                  }
              }else{
                  if(contain == 1){
                      sb.append(prex.toString()+" ");
                      break;
                  }
              }
              if(i == len-1){
                  sb.append(str+" ");
              }
          }
      }
     return sb.toString().trim();
    }
    public void buildTree(String str){
        Node[] bRoot = root;
        for(int i =0;i<str.length();i++){
            char c = str.charAt(i);
            if(bRoot[c-'a'] == null){
                bRoot[c-'a'] =new Node();
            }
            if(i == str.length()-1){
                bRoot[c-'a'].flag=true;
            }
            bRoot=bRoot[c-'a'].root;
        }
    }

    /**
     *题号：1217
     * 难度：简单
     * 时间：20220708
     *有 n 个筹码。第 i 个筹码的位置是 position[i] 。
     *
     * 我们需要把所有筹码移到同一个位置。在一步中，我们可以将第 i 个筹码的位置从 position[i] 改变为:
     *
     * position[i] + 2 或 position[i] - 2 ，此时 cost = 0
     * position[i] + 1 或 position[i] - 1 ，此时 cost = 1
     * 返回将所有筹码移动到同一位置上所需要的 最小代价 。
     */
    public int minCostToMoveChips(int[] position) {
        int len = position.length,count =0;
        for(int i=0;i<len;i++){
            if(position[i]%2 == 0){
                count++;
            }
        }
        return Math.min(len-count,count);
    }

    /**
     * 题号：873
     * 难度：中等
     * 时间：20220709
     *如果序列 X_1, X_2, ..., X_n 满足下列条件，就说它是 斐波那契式 的：
     *
     * n >= 3
     * 对于所有 i + 2 <= n，都有 X_i + X_{i+1} = X_{i+2}
     * 给定一个严格递增的正整数数组形成序列 arr ，找到 arr 中最长的斐波那契式的子序列的长度。如果一个不存在，返回  0 。
     *
     * （回想一下，子序列是从原序列 arr 中派生出来的，它从 arr 中删掉任意数量的元素（也可以不删），而不改变其余元素的顺序。例如， [3, 5, 8] 是 [3, 4, 5, 6, 7, 8] 的一个子序列）
     * 解题：这题不会写不知道该如何确定状态转移方程，状态转移方程应该先确定之前的状态然后确定现在的状态
     */
    public int lenLongestFibSubseq(int[] arr) {
        int len = arr.length,ans =0;
        //其中dp[i][j]表示的是以i和j为末尾两个元素的子序列
        //可以知道其状态转移方程为dp[i][j]= dp[j][k]+1或3,其中k为数组中arr[k]+arr[j]=arr[i]的坐标
        int[][] dp = new int[len][len];
        for(int i = 2;i<len;i++){
            for(int j =i-1;j>=0;j--){
                if(arr[i]>=2*arr[j]){
                    continue;
                }
                int temp = arr[i]-arr[j];
                //找到temp在arr中的位置
                int index = findIndex(temp,arr,0,j);
                if(index == -1){
                    continue;
                }
                if(dp[j][index]>=3){
                    dp[i][j] = dp[j][index]+1;
                }else{
                    dp[i][j]=3;
                }
                ans= Math.max(dp[i][j],ans);
            }
        }
        return ans;
    }
    //二分查找
    public int findIndex(int val ,int[] arr,int begin,int end){
        int right = end ,left = begin;
       while(right>=left){
           int mid = left +(right-left>>1);
           if(arr[mid]>val){
               right=mid-1;
           }else if(arr[mid]<val){
               left=mid+1;
           }else {
               return mid;
           }
       }
        return -1;
    }

    /**
     *题号：741
     * 难度：困难
     * 时间：20220710
     * 一个N x N的网格(grid) 代表了一块樱桃地，每个格子由以下三种数字的一种来表示：
     *
     * 0 表示这个格子是空的，所以你可以穿过它。
     * 1 表示这个格子里装着一个樱桃，你可以摘到樱桃然后穿过它。
     * -1 表示这个格子里有荆棘，挡着你的路。
     * 你的任务是在遵守下列规则的情况下，尽可能的摘到最多樱桃：
     *
     * 从位置 (0, 0) 出发，最后到达 (N-1, N-1) ，只能向下或向右走，并且只能穿越有效的格子（即只可以穿过值为0或者1的格子）；
     * 当到达 (N-1, N-1) 后，你要继续走，直到返回到 (0, 0) ，只能向上或向左走，并且只能穿越有效的格子；
     * 当你经过一个格子且这个格子包含一个樱桃时，你将摘到樱桃并且这个格子会变成空的（值变为0）；
     * 如果在 (0, 0) 和 (N-1, N-1) 之间不存在一条可经过的路径，则没有任何一个樱桃能被摘到。
     */
    int[][] tempGrid;
    int len=0,ans =0;
    public int cherryPickup(int[][] grid) {
        len=grid.length;
        tempGrid = grid;
        if(len == 1){
            return grid[0][0];
        }
        dfs(0,0,0,0);
        return ans;
    }
    //使用深度优先遍历进行累加
    public void dfs(int x,int y,int step,int count){
        if(step == 4*(len-1)){
            ans = Math.max(count,ans);
            return;
        }
        //大于2倍的n说明在往回走，这时只能往左和往上
        if(step>=2*(len-1)){
            if(x-1>=0 && tempGrid[x-1][y] != -1){
                if(tempGrid[x-1][y]==1){
                    tempGrid[x-1][y]=0;
                    dfs(x-1,y,step+1,count+1);
                    tempGrid[x-1][y]=1;
                }else{
                    dfs(x-1,y,step+1,count);
                }

            }
            if(y-1>=0 && tempGrid[x][y-1] != -1){
                if(tempGrid[x][y-1] ==1){
                    tempGrid[x][y-1]=0;
                    dfs(x,y-1,step+1,count+1);
                    tempGrid[x][y-1]=1;
                }else{
                    dfs(x,y-1,step+1,count);
                }

            }
        }else{
            //只能往下和往右
            if(x+1<len && tempGrid[x+1][y] != -1){
                if(tempGrid[x+1][y] == 1){
                    tempGrid[x+1][y]=0;
                    dfs(x+1,y,step+1,count+1);
                    tempGrid[x+1][y]=1;
                }else{
                    dfs(x+1,y,step+1,count);
                }

            }
            if(y+1<len && tempGrid[x][y+1] != -1){
                if(tempGrid[x][y+1] == 1){
                    tempGrid[x][y+1]=0;
                    dfs(x,y+1,step+1,count+1);
                    tempGrid[x][y+1]=1;
                }else{
                    dfs(x,y+1,step+1,count);
                }

            }
        }
    }
    //上面深度优先遍历时间长超时了所以下面使用动态规划进行求解
    public int cherryPickup2(int[][] grid) {
        int len = grid.length;
        //其中dp[i][j][k]表示的是步数为i时A横坐标为j,b横坐标为k时的能采到樱桃的最大值
        //因为步数固定所以知道横坐标此时一定知道总左边的值
        int[][][] dp =new int[2*len-1][len][len];

        //初始化
        for(int j =0;j<2*len-1;j++){
            for(int i = 0;i<len ;i++){
                Arrays.fill(dp[j][i],Integer.MIN_VALUE);
            }
        }
        dp[0][0][0] =grid[0][0];
        for(int i =1;i<2*len-1;i++){
            //当步数超过len-1时横坐标的范围会发生变化从（i-len+1,len-1）,当没有超过n-1步时
            //其范围是（0，i）
            for(int j = Math.max(i-len+1,0);j<=Math.min(i,len-1);j++){
                int y1 = i-j;
                //如果当前a的位置为障碍物则不能到达该位置
                if(grid[j][y1] == -1){
                    continue;
                }
                //为了确保b与a不会重合所以这里不能使得b的行数小于a的行数
                for(int k = j;k<=Math.min(i,len-1);k++){
                    int y2=i-k;
                    //如果当前b的位置为障碍物则不能到达该位置
                    if(grid[k][y2] == -1){
                        continue;
                    }
                   //a和b都向右移动达到当前位置,移动的横坐标不变而步数少一说明列数加一
                    int res = dp[i-1][j][k];
                    if(j>0){
                        //b向右移动a向下移动，到达当前位置
                        res = Math.max(dp[i-1][j-1][k],res);
                    }
                    if(k>0){
                        //a向右移动，b向下移动到达当前位置
                        res = Math.max(dp[i-1][j][k-1],res);
                    }
                    if(k>0 && j>0){
                        //二者均向下移动
                        res = Math.max(dp[i-1][j-1][k-1],res);
                    }
                    //如果j和k不在同一位置则可以将当前位置的值加入获取一个樱桃
                    //这里两个之和体现于在每一次不用位置时都是将上一次两个所能摘到樱桃数
                    //的最大值加二，这样就将两条路径上所有的樱桃都累加起来了
                    if(j != k && grid[k][y2] == 1){
                        res+=1;
                    }
                    if(grid[j][y1] == 1){
                        res +=1;
                    }
                    dp[i][j][k] = res;
                }
            }
        }
        return Math.max(dp[2*len-2][len-1][len-1],0);
    }

    /**
     * 题号：1252
     * 难度：简单
     * 时间：20220712
     *给你一个 m x n 的矩阵，最开始的时候，每个单元格中的值都是 0。
     *
     * 另有一个二维索引数组 indices，indices[i] = [ri, ci] 指向矩阵中的某个位置，其中 ri 和 ci 分别表示指定的行和列（从 0 开始编号）。
     *
     * 对 indices[i] 所指向的每个位置，应同时执行下述增量操作：
     *
     * ri 行上的所有单元格，加 1 。
     * ci 列上的所有单元格，加 1 。
     * 给你 m、n 和 indices 。请你在执行完所有 indices 指定的增量操作后，返回矩阵中 奇数值单元格 的数目。
     */
    public int oddCells(int m, int n, int[][] indices) {
        int[] countM= new int[m],countN = new int[n];
        int ans = 0;
        for(int i = 0;i<indices.length ;i++){
            countM[indices[i][0]]++;
            countN[indices[i][1]]++;

        }
       for(int i = 0;i<n;i++){
           for(int j = 0 ;j<m;j++){
               if((countM[j]+countN[i])%2!=0){
                   ans++;
               }
           }
       }
        return ans;
    }



    /**
     * 题号：735
     * 难度：中等
     * 时间：20220713
     *给定一个整数数组 asteroids，表示在同一行的行星。
     *
     * 对于数组中的每一个元素，其绝对值表示行星的大小，正负表示行星的移动方向（正表示向右移动，负表示向左移动）。每一颗行星以相同的速度移动。
     *
     * 找出碰撞后剩下的所有行星。碰撞规则：两个行星相互碰撞，较小的行星会爆炸。如果两颗行星大小相同，则两颗行星都会爆炸。两颗移动方向相同的行星，永远不会发生碰撞。
     */
    public int[] asteroidCollision(int[] asteroids) {
        int len = asteroids.length;
        Stack<Integer> stack= new Stack<>();
        for(int i =0;i<len ;i++){
            if(stack.isEmpty()){
                stack.push(asteroids[i]);
            }else{
                while(!stack.isEmpty()){
                    int temp = stack.lastElement();
                    if(asteroids[i]<0 && temp>0){
                        if(Math.abs(asteroids[i])>temp){
                            stack.pop();
                            //如果都撞干净了就需要加上当前的
                            if(stack.isEmpty()){
                                stack.push(asteroids[i]);
                                break;
                            }
                        }else if(Math.abs(asteroids[i]) == temp){
                            stack.pop();
                            break;
                        }else {
                            break;
                        }
                    }else{
                        stack.push(asteroids[i]);
                        break;
                    }
                }

            }
        }
        int[] ans = new int[stack.size()];
        for(int i = ans.length-1;i>=0;i--){
            ans[i]=stack.pop();
        }
        return ans;
    }


    public static void main(String[] args) {
        DaySeven seven = new DaySeven();
        WordFilter2 wordFilter = new WordFilter2(new String[]{"kxxgfp"});
        System.out.println( wordFilter.f("fs", "fs"));

    }
}
/***
 * 题号：676
 * 难度：中等
 * 时间：20220712
 * 设计一个使用单词列表进行初始化的数据结构，单词列表中的单词 互不相同 。 如果给出一个单词，请判定能否只将这个单词中一个字母换成另一个字母，使得所形成的新单词存在于你构建的字典中。
 *
 * 实现 MagicDictionary 类：
 *
 * MagicDictionary() 初始化对象
 * void buildDict(String[] dictionary) 使用字符串数组 dictionary 设定该数据结构，dictionary 中的字符串互不相同
 * bool search(String searchWord) 给定一个字符串 searchWord ，判定能否只将字符串中 一个 字母换成另一个字母，使得所形成的新字符串能够与字典中的任一字符串匹配。如果可以，返回 true ；否则，返回 false 。
 */
class MagicDictionary {
    class Node{
        Node[] c = new Node[26];
        boolean end = false;
    }
    Node[] root;
    public MagicDictionary() {
        root= new Node[26];
    }

    public void buildDict(String[] dictionary) {
        for(String str: dictionary){
            Node[] bRoot = root;
            for(int i =0;i<str.length();i++){
                char c = str.charAt(i);
                if(bRoot[c-'a'] == null){
                    bRoot[c-'a'] = new Node();
                }
                if(i == str.length()-1){
                    bRoot[c-'a'].end=true;
                }
                bRoot=bRoot[c-'a'].c;
            }

        }
    }

    public boolean search(String searchWord) {
        int count = 0,flag=0;
        Queue<Node> queue = new ArrayDeque<>();
        Node[] bRoot =root;
        for(int i=0;i<searchWord.length();i++ ){
            char c = searchWord.charAt(i);
            if(bRoot[c-'a'] == null){
                flag = i;
                if(count == 0){
                    count++;
                    for(int j =0;j<26;j++){
                        if(bRoot[j] !=null){
                            queue.offer(bRoot[j]);
                        }
                    }
                    if(!queue.isEmpty()){
                        bRoot=queue.poll().c;
                    }else{
                        return false;
                    }
                }else{
                    //如果队列中没有备选项
                    if(queue.isEmpty()){
                        return false;
                    }else{
                        bRoot=queue.poll().c;
                        i=flag+1;
                    }
                }
            }else{
                //如果建的树过短,或者要搜寻的字符串过长
                if((bRoot[c-'a'].end && i<searchWord.length()-1)||(i == searchWord.length()-1 && !bRoot[c-'a'].end)){
                    return false;
                }
                bRoot=bRoot[c-'a'].c;
                //如果提前结束

            }
        }
        //如果要搜寻的字符串提前结束则说明没找打
        if(count==0){
            return false;
        }
        return true;
    }
}
/***
 * 题号：745
 * 难度：困难
 * 时间：20220714
 * 设计一个包含一些单词的特殊词典，并能够通过前缀和后缀来检索单词。
 *
 * 实现 WordFilter 类：
 *
 * WordFilter(string[] words) 使用词典中的单词 words 初始化对象。
 * f(string pref, string suff) 返回词典中具有前缀 prefix 和后缀 suff 的单词的下标。如果存在不止一个满足要求的下标，返回其中 最大的下标 。如果不存在这样的单词，返回 -1 。
 */
class WordFilter {
    class Node{
        Node[] c = new Node[26];
        List<Integer> list = new ArrayList<>();
    }

    Node[] pref = new Node[26];
    Node[] suff = new Node[26];
    public WordFilter(String[] words) {
        for(int k =0;k<words.length;k++){
            Node[] preRoot = pref;
            Node[] suffRoot = suff;
            String str = words[k];
            int len = str.length();
            for(int i =0,j=len-1;i<len && j>=0;i++,j--){
                char cP= str.charAt(i),cs = str.charAt(j);

                if(preRoot[cP-'a'] == null){
                    preRoot[cP-'a'] = new Node();
                }

                if(suffRoot[cs-'a'] == null){
                    suffRoot[cs-'a'] = new Node();
                }

                preRoot[cP-'a'].list.add(k);
                suffRoot[cs-'a'].list.add(k);

                preRoot = preRoot[cP-'a'].c;
                suffRoot = suffRoot[cs-'a'].c;

            }
        }
    }

    public int f(String pref, String suff) {
        Node[] preNode = this.pref;
        Node[] suffNode = this.suff;
        List<Integer> maxSuff=null;
        for(int i =suff.length()-1 ;i>=0;i--){
            char c = suff.charAt(i);
            if(suffNode[c-'a'] == null){
                return -1;
            }else{
                if(i == 0){
                    maxSuff = suffNode[c-'a'].list;
                }
                suffNode=suffNode[c-'a'].c;
            }
        }
        List<Integer> maxPre =null;
        for(int j =0;j<pref.length();j++){
            char c = pref.charAt(j);
            if(preNode[c-'a'] == null){
                return -1;
            }else{
                if(j == pref.length()-1){
                    maxPre = preNode[c-'a'].list;
                }
                preNode = preNode[c-'a'].c;
            }
        }
        //双指针遍历
        for(int i = maxSuff.size()-1,j = maxPre.size()-1;i>=0&&j>=0;){
            if(maxPre.get(j)>maxSuff.get(i)){
                j--;
            }else if(maxSuff.get(i)>maxPre.get(j)){
                i--;
            }else{
                return maxPre.get(i);
            }
        }
        return -1;
    }
}
class WordFilter2 {
    Trie trie;
    String weightKey;

    public WordFilter2(String[] words) {
        trie = new Trie();
        weightKey = "##";
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            Trie cur = trie;
            int m = word.length();
            for (int j = 0; j < m; j++) {
                Trie tmp = cur;
                for (int k = j; k < m; k++) {
                    String key = new StringBuilder().append(word.charAt(k)).append('#').toString();
                    if (!tmp.children.containsKey(key)) {
                        tmp.children.put(key, new Trie());
                    }
                    tmp = tmp.children.get(key);
                    tmp.weight.put(weightKey, i);
                }
                tmp = cur;
                for (int k = j; k < m; k++) {
                    String key = new StringBuilder().append('#').append(word.charAt(m - k - 1)).toString();
                    if (!tmp.children.containsKey(key)) {
                        tmp.children.put(key, new Trie());
                    }
                    tmp = tmp.children.get(key);
                    tmp.weight.put(weightKey, i);
                }
                String key = new StringBuilder().append(word.charAt(j)).append(word.charAt(m - j - 1)).toString();
                if (!cur.children.containsKey(key)) {
                    cur.children.put(key, new Trie());
                }
                cur = cur.children.get(key);
                cur.weight.put(weightKey, i);
            }
        }
    }

    public int f(String pref, String suff) {
        Trie cur = trie;
        int m = Math.max(pref.length(), suff.length());
        for (int i = 0; i < m; i++) {
            char c1 = i < pref.length() ? pref.charAt(i) : '#';
            char c2 = i < suff.length() ? suff.charAt(suff.length() - 1 - i) : '#';
            String key = new StringBuilder().append(c1).append(c2).toString();
            if (!cur.children.containsKey(key)) {
                return -1;
            }
            cur = cur.children.get(key);
        }
        return cur.weight.get(weightKey);
    }
}

class Trie {
    Map<String, Trie> children;
    Map<String, Integer> weight;

    public Trie() {
        children = new HashMap<String, Trie>();
        weight = new HashMap<String, Integer>();
    }
}

