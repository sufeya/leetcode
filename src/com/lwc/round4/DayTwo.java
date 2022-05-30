package com.lwc.round4;

import java.util.*;

public class DayTwo {
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
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
        TreeNode node1= new TreeNode(1);
        TreeNode node2= new TreeNode(0);
        TreeNode node3= new TreeNode(0);
        TreeNode node4= new TreeNode(1);
        TreeNode node5= new TreeNode(1);
        TreeNode node6= new TreeNode(0);
        TreeNode node7= new TreeNode(1);
        node1.left =node2;
        node2.left=node3;
        node2.right=node4;
        node1.right=node5;
        node5.left=node6;
        node5.right=node7;
        DayTwo to= new DayTwo();
        System.out.println(to.sumRootToLeaf(node1));
    }
}
