package com.lwc.round4;

import java.util.*;

public class DayTwo {
    private boolean vaild1;

    /**
     * 题号：467
     * 难度：中等
     * 时间：20220525
     * 把字符串 s 看作是 “abcdefghijklmnopqrstuvwxyz” 的无限环绕字符串，所以 s 看起来是这样的：
     *
     * "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd...." . 
     * 现在给定另一个字符串 p 。返回 s 中 唯一 的 p 的 非空子串 的数量 。 
     */
    public int findSubstringInWraproundString(String p) {
        //dp[i]表示以字符i为结尾的最长子串的长度，可以知道长度为k的字符串其子串的个数应当是k
        int[] dp = new int[26];
        int k = 0;
        for (int i = 0; i < p.length(); ++i) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) + 26) % 26 == 1) { // 字符之差为 1 或 -25
                ++k;
            } else {
                k = 1;
            }
            dp[p.charAt(i) - 'a'] = Math.max(dp[p.charAt(i) - 'a'], k);
        }
        return Arrays.stream(dp).sum();

    }

    /**
     *题号：699
     * 难度：困难
     * 时间：20220526
     * 在二维平面上的 x 轴上，放置着一些方块。
     *
     * 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
     *
     * 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。
     *
     * 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
     *
     * 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
     */
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> ans = new ArrayList<>();
        //利用hash表存储最左边边界其中的值表示的是int[0]长度，int[1]整个范围的最高值
        Map<Integer,int[]> left =new HashMap<>();
        int maxHigh=0;
        for(int[] temp :positions){
            //首先表示新方块的范围
            int around= temp[0]+temp[1];
            int hight = temp[1],maxBase =0;
            //获取这个方块范围内的所有值，边界相等的都不算在内
            for (Map.Entry<Integer,int[]> entry :left.entrySet()){
                int leVal =entry.getKey();
                int[] arr= entry.getValue();
                if((leVal<=temp[0]&& leVal+arr[0]>temp[0]) || (leVal>temp[0] && leVal<around)){
                    maxBase=Math.max(maxBase,arr[1]);
                }
            }
            hight+=maxBase;
            maxHigh=Math.max(hight,maxHigh);
            ans.add(maxHigh);
            left.put(temp[0],new int[]{temp[1],hight});

        }
        return ans;
    }

    /**
     * 题号：17.11
     * 难度：中等
     * 时间：20220527
     有个内含单词的超大文本文件，给定任意两个不同的单词，找出在这个文件中这两个单词的最短距离(相隔单词数)。如果寻找过程在这个文件中会重复多次，而每次寻找的单词不同，你能对此优化吗?
     */
    public int findClosest(String[] words, String word1, String word2) {
        int sum =0,ans=100001;
        boolean flag1 =false,flag2=false;
        for(int n =0 ;n<words.length;n++){
            if(words[n].equals(word1)){
                if(flag2){
                    ans = Math.min(ans,sum);
                    sum=0;

                }
                flag1=true;
                flag2=false;
            }
            if(words[n].equals(word2)){
                if(flag1) {
                    ans = Math.min(ans, sum);
                    System.out.println(word2+"出现的地方"+sum);
                    sum = 0;
                }
                flag2=true;
                flag1=false;
            }
            if(flag1 || flag2){
                sum++;
            }
        }
        return ans;
    }

    /**
     * 题号：
     *有效括号字符串为空 ""、"(" + A + ")" 或 A + B ，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。
     *
     * 例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。
     * 如果有效字符串 s 非空，且不存在将其拆分为 s = A + B 的方法，我们称其为原语（primitive），其中 A 和 B 都是非空有效括号字符串。
     *
     * 给出一个非空有效字符串 s，考虑将其进行原语化分解，使得：s = P_1 + P_2 + ... + P_k，其中 P_i 是有效括号字符串原语。
     *
     * 对 s 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回
     */
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int count=0;
        for(int i =0;i<s.length();i++){
            char c = s.charAt(i);
            if(c == '('){
                stack.push(c);
            }
            //如果等于右括号则需要进行判断
            if(c == ')'){
                //如果里面的左括号个数大于1则将括号存入答案
                if(stack.size()>1 || count>0){
                    while(stack.size()>1){
                        sb.append(stack.pop());
                        count++;
                    }
                    sb.append(c);
                    count--;
                }else if(stack.size() == 1){
                    stack.pop();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 题号：468
     * 难度：中等
     * 时间:20220529
     *给定一个字符串 queryIP。如果是有效的 IPv4 地址，返回 "IPv4" ；如果是有效的 IPv6 地址，返回 "IPv6" ；如果不是上述类型的 IP 地址，返回 "Neither" 。
     *
     * 有效的IPv4地址 是 “x1.x2.x3.x4” 形式的IP地址。 其中 0 <= xi <= 255 且 xi 不能包含 前导零。例如: “192.168.1.1” 、 “192.168.1.0” 为有效IPv4地址， “192.168.01.1” 为无效IPv4地址; “192.168.1.00” 、 “192.168@1.1” 为无效IPv4地址。
     *
     * 一个有效的IPv6地址 是一个格式为“x1:x2:x3:x4:x5:x6:x7:x8” 的IP地址，其中:
     *
     * 1 <= xi.length <= 4
     * xi 是一个 十六进制字符串 ，可以包含数字、小写英文字母( 'a' 到 'f' )和大写英文字母( 'A' 到 'F' )。
     * 在 xi 中允许前导零。
     */
    public String validIPAddress(String queryIP) {
        String[] ans =new String[]{"Neither","IPv6","IPv4"};
        if(queryIP.contains(".")){
            String[] strs = queryIP.split("\\.");
            if(queryIP.endsWith(".")||strs.length!=4){
                return ans[0];
            }
            for(String str : strs){
                if(str.length()>1 && str.charAt(0) == '0' || str.length() ==0){
                    return ans[0];
                }
                int val=0;
                for(int i =0 ;i<str.length();i++){
                    char c =str.charAt(i);
                    if(!(c>='0' && c<='9')){
                        return ans[0];
                    }
                    val=val*10+(c-48);
                    if(val>255){
                        return ans[0];
                    }
                }
                if(val>255){
                    return ans[0];
                }
            }
            return ans[2];
        }else{
            //尾巴不好切割
            String[] strs = queryIP.split(":");
            if(queryIP.endsWith(":")||strs.length != 8){
                return ans[0];
            }
            for(String str : strs){
                if(str.length()>4 || str.length() == 0){
                    return ans[0];
                }
                for(int i =0;i<str.length();i++){
                    char c =str.charAt(i);
                    if(!((c<='f'&& c>='a') || (c>='A'&&c<='F') || (c>='0' && c<='9'))){
                        return ans[0];
                    }
                }
            }
            return ans[1];
        }

    }

    /**
     *给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
     *
     * 例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
     * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
     *
     * 返回这些数字之和。题目数据保证答案是一个 32 位 整数。
     */
    int sum =0;
    public int sumRootToLeaf(TreeNode root) {
        if(root.left == null && root.right == null){
            return root.val;
        }
        if(root.left != null){
            dfs(root.left,root.val);
        }
        if(root.right != null){
            dfs(root.right,root.val);
        }
        return sum;
    }
    public void dfs(TreeNode root,int val ){
        int temp =2*val+root.val;
        if(root.left == null && root.right ==null){
            sum+=temp;
        }
        if(root.left != null){
             dfs(root.left,temp);
        }
        if(root.right != null){
            dfs(root.right,temp);
        }
    }

    /**
     * 题号：剑指offer II 114
     * 题号：困难
     * 时间：20220531
     *现有一种使用英语字母的外星文语言，这门语言的字母顺序与英语顺序不同。
     *
     * 给定一个字符串列表 words ，作为这门语言的词典，words 中的字符串已经 按这门新语言的字母顺序进行了排序 。
     *
     * 请你根据该词典还原出此语言中已知的字母顺序，并 按字母递增顺序 排列。若不存在合法字母顺序，返回 "" 。若存在多种可能的合法字母顺序，返回其中 任意一种 顺序即可。
     *
     * 字符串 s 字典顺序小于 字符串 t 有两种情况：
     *
     * 在第一个不同字母处，如果 s 中的字母在这门外星语言的字母顺序中位于 t 中字母之前，那么 s 的字典顺序小于 t 。
     * 如果前面 min(s.length, t.length) 字母都相同，那么 s.length < t.length 时，s 的字典顺序也小于 t 。
     */
    //构建对应的有向图
    Map<Character,List<Character>> charOrder =new HashMap<>();
    //判断节点是否入度为零
    Map<Character,Integer> indegree = new HashMap<>();
    //判断是否有合法的字符序
    boolean vaild =true;
    public String alienOrder(String[] words) {
        StringBuilder sb = new StringBuilder();
        //跟据所给的单词构建图进行遍历
        //对图进行初始化
        for(String word:words){
            int len =word.length();
            for(int i=0;i<len;i++){
                charOrder.putIfAbsent(word.charAt(i),new ArrayList<Character>());
            }
        }
        //遍历所有的字符进行加边，跟据相邻两个单词间的顺序可以直到字符序例如wet,wer 可以确定t->r之间
        //有一条有向边
        for(int i =1;i<words.length && vaild;i++){
            addEdge(words[i-1],words[i]);
        }
        if(!vaild){
            return "";
        }
        //利用广度遍历对图进行拓扑排序
        Queue<Character> queue=new ArrayDeque<>();
        //先将入度为零的进行入队
        for(char c : charOrder.keySet()){
            if(!indegree.containsKey(c)){
                queue.offer(c);
            }
        }
        while(!queue.isEmpty()){
            char c = queue.poll();
            sb.append(c);
            List<Character> list=charOrder.get(c);
            for(char m :list){
              int i = indegree.get(m)-1;
              if(i == 0){
                  queue.offer(m);
              }else{
                  indegree.put(m,i);
              }
            }
        }
        return sb.length() == charOrder.size() ? sb.toString():"";
    }
    public void addEdge(String word1,String word2){
        int len = Math.min(word1.length(),word2.length());
        int index =0;
       while(index<len){
           char before = word1.charAt(index),after =word2.charAt(index);
           //如果两个对应的字符不相同则可以确定一个字符序
           if(before != after){
               //增加对应的边
               charOrder.get(before).add(after);
               //增加after对应的入度
               indegree.put(after,indegree.getOrDefault(after,0)+1);
               break;
           }
           index++;
       }
       if(index == len && word1.length()>word2.length() ){
          vaild=false;
       }
    }

    /**
     * 题号：473
     * 难度：中等
     * 时间：20220601
     *你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。你要用 所有的火柴棍 拼成一个正方形。你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
     *
     * 如果你能使这个正方形，则返回 true ，否则返回 false 。
     */
    public boolean makesquare(int[] matchsticks) {
        int length = matchsticks.length;
        //首先如果可以拼成正方形，则可以确定边长的长度
        int sum =0;
        for(int i : matchsticks){
            sum+=i;
        }
        int len = sum/4;
        //如果边长不能等分则说明不行
        if(len *4 != sum){
            return false;
        }
        //dp[s]表示的是用完s表示的火柴所组成的边的长度当刚好拼满当前的边时则重置为零
        //没拼满时则接着遍历所有的火柴寻找，也就是遍历所有的情况
        int dp[] = new int[1<<length];
        Arrays.fill(dp,-1);
        dp[0]= 0;
        for(int i =1;i<(1<<length);i++){
            for(int j =0 ;j<length;j++){
                //如果第j根火柴没有使用过则跳过
                if(((i>>j)&1) == 0){
                    continue;
                }
                //找到没有使用第j根火柴的状态
                int pre = i ^(1<<j);
                if(dp[pre]>=0 && dp[pre] +matchsticks[j]<=len){
                    //当刚好为零时则表明已经填满了上一边长
                    dp[i] = (dp[pre]+matchsticks[j])%len;
                    break;
                }
            }
        }
        return dp[(1<<length)-1] == 0;
    }
    //回溯解法
    public boolean makesquare2(int[] matchsticks) {
        int length = matchsticks.length;
        //首先如果可以拼成正方形，则可以确定边长的长度
        int sum =0;
        for(int i : matchsticks){
            sum+=i;
        }
        int len = sum/4;
        //如果边长不能等分则说明不行
        if(len *4 != sum){
            return false;
        }
        //将数组进行从大到小进行排序
        Arrays.sort(matchsticks);
        for (int i = 0, j = matchsticks.length - 1; i < j; i++, j--) {
            int temp = matchsticks[i];
            matchsticks[i] = matchsticks[j];
            matchsticks[j] = temp;
        }

        //四条边的值，遍历所有的边累加到四条边中
        int edges[] = new int[4];

        return dfs(edges,len,0,matchsticks);
    }
    public boolean dfs(int[] edges,int len,int index,int[] matchsticks){
        if(index == matchsticks.length){
            return true;
        }
        for(int i = 0;i<4;i++){
            edges[i]+=matchsticks[index];
            if(edges[i]<=len && dfs(edges,len,index+1,matchsticks)){
                return true;
            }
            edges[i]-=matchsticks[index];
        }
        return false;
    }

    /**
     * 题号：450
     * 难度：中等
     * 时间：20220602
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
     *
     * 一般来说，删除节点可分为两个步骤：
     *
     * 首先找到需要删除的节点；
     * 如果找到了，删除它。
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null ){
            return null;
        }
        if(root.val == key){
            if(root.right == null ){
                return root.left;
            }
            if(root.left == null){
                return root.right;
            }
            TreeNode p = root.left;
            while(p.right != null){
                p=p.right;
            }
            p.right=root.right;
            return root.left;
        }else if(root.val <key){
            root.right=deleteNode(root.right,key);
        }else{
            root.left=deleteNode(root.left,key);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode node1 =new TreeNode(1);
        TreeNode node2 =new TreeNode(2);
        TreeNode node3 =new TreeNode(3);
        TreeNode node4 =new TreeNode(4);
        node3.left =node1;
        node2.right=node4;
        node1.right=node2;
        DayTwo to= new DayTwo();
        System.out.println(to.deleteNode(node3,1));
    }
}
