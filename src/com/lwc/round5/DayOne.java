package com.lwc.round5;

import javafx.util.Pair;
import java.util.*;

/**
 * @author liuweichun
 * @date 2022/8/16 21:23
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayOne {

    public static void main(String[] args) {

        DayOne one = new DayOne();
        one.specialArray(new int[]{3,5});
    }
    /**
     * 题号：652
     * 难度：中等
     * 时间：20220905
     * 给定一棵二叉树 root，返回所有重复的子树。
     *
     * 对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
     *
     * 如果两棵树具有相同的结构和相同的结点值，则它们是重复的。
     */
    HashMap<Integer,TreeNode> map;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        map = new HashMap<>();
        List<TreeNode> ans = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()){
           TreeNode node = queue.poll();
           if(!map.containsKey(node.val)){
               map.put(node.val,node);
           }else{
               TreeNode node1 = map.get(node.val);
                boolean res = findDfs(node1,node);
                if(res){
                    System.out.println();
                    //这里可能会重复添加
                    ans.add(node1);
                }
           }
           if(node.left != null){
               queue.offer(node.left);
           }
           if(node.right != null){
               queue.offer(node.right);
           }
        }
        return null;
    }
    //深度优先遍历树
    public boolean findDfs(TreeNode root,TreeNode cRoot){
        //如果当前节点相同则比较其子节点
        if(root.val == cRoot.val){
            boolean resRight = false,resLeft = false;
            if(root.left != null && cRoot.left != null){
                resLeft = findDfs(root.left,cRoot.left);
            }else if(root.left == null && cRoot.left == null){
                resLeft=true;
            }
            if(root.right != null && cRoot.right != null){
                resRight = findDfs(root.right,cRoot.right);
            }else if(root.right == null && cRoot.right == null){
                resRight=true;
            }
            //左右均相同则返回
            return resLeft && resRight;
        }
        return false;
    }
    //上诉自己方法有问题所以换过一种新的方法对左右子树进行序列化然后判断是否有重复的
    HashMap<String,TreeNode> sMap = new HashMap<>();
    Set<TreeNode> set = new HashSet<>();
    public List<TreeNode> findDuplicateSubtrees2(TreeNode root) {
        dfs(root);
        return new ArrayList<>(set);
    }
    public String dfs(TreeNode root){
        if(root == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(root.val).append("(");
        sb.append(dfs(root.left)).append(")").append("(");
        sb.append(dfs(root.right)).append(")");
        String se = sb.toString();
        if(sMap.containsKey(se)){
            set.add(sMap.get(se));
        }else{
            sMap.put(se,root);
        }
        return se;
    }
    //三元组解法,将一个子树看成根节点的值与左右子树的序号的三元组，其中序号是指这个唯一子树出现的顺序
    int index =0;
    Map<String, Pair<TreeNode,Integer>> pMap = new HashMap<>();
    public int dfsT(TreeNode root){
        if(root == null){
            return 0;
        }
      int[] hash = new int[]{root.val,dfsT(root.left),dfsT(root.right)};
        String key = Arrays.toString(hash);
        if(sMap.containsKey(key)){
            Pair<TreeNode,Integer> pair = pMap.get(key);
            set.add(pair.getKey());
            return pair.getValue();
        }else{
            Pair<TreeNode,Integer> pair = new Pair(root,index++);
            pMap.put(key,pair);
            return pair.getValue();
        }
    }
    /**
     * 题号：828
     * 难度：困难
     * 时间：20220906
     * 我们定义了一个函数 countUniqueChars(s) 来统计字符串 s 中的唯一字符，并返回唯一字符的个数。
     *
     * 例如：s = "LEETCODE" ，则其中 "L", "T","C","O","D" 都是唯一字符，因为它们只出现一次，所以 countUniqueChars(s) = 5 。
     *
     * 本题将会给你一个字符串 s ，我们需要返回 countUniqueChars(t) 的总和，其中 t 是 s 的子字符串。输入用例保证返回值为 32 位整数。
     *
     * 注意，某些子字符串可能是重复的，但你统计时也必须算上这些重复的子字符串（也就是说，你必须统计 s 的所有子字符串中的唯一字符）。
     * 查找字符在字符串中的贡献值
     */
    public int uniqueLetterString(String s) {
        //其中right[i]表示字符s[i]的是右边界同一字符出现的下标，left[i]表示的是字符s[i]左边界出现相同字符的下标位置
        //其目的是为了确定s[i]字符有多少个唯一包含的子串数量，这样就可以确定s[i]计算了多少回
        int len = s.length(),right[] = new int[len],left[] = new int[len],pre[]=new int[26];
        Arrays.fill(pre,-1);
        for(int i = 0;i<len;i++){
            char c = s.charAt(i);
            left[i]=pre[c-'A'];
            pre[c-'A']=i;
        }
        Arrays.fill(pre,len);
        for(int i = len-1;i>-1;i--){
            char c = s.charAt(i);
            right[i]=pre[c-'A'];
            pre[c-'A']=i;
        }
        int ans = 0;
        //计算每个子串的数量
        for(int i =0;i<len ;i++){
            ans += (i-left[i])*(right[i]-i);
        }
        return ans;
    }
    /**
     * 题号：1592
     * 难度：简单
     * 时间：20220907
     * 给你一个字符串 text ，该字符串由若干被空格包围的单词组成。每个单词由一个或者多个小写英文字母组成，并且两个单词之间至少存在一个空格。题目测试用例保证 text 至少包含一个单词 。
     *
     * 请你重新排列空格，使每对相邻单词之间的空格数目都 相等 ，并尽可能 最大化 该数目。如果不能重新平均分配所有空格，请 将多余的空格放置在字符串末尾 ，这也意味着返回的字符串应当与原 text 字符串的长度相等。
     *
     * 返回 重新排列空格后的字符串 。
     */
    public String reorderSpaces(String text) {
        List<String> strs = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        text = text;
        int count = 0;
        for(int i = 0;i<text.length();i++){
            char c = text.charAt(i);
            if(c == ' '){
                count++;
                if(sb.length()>0){
                    strs.add(sb.toString());
                    sb.delete(0,sb.length());
                }
            }else{
                sb.append(c);
            }
        }
        if(sb.length()>0){
            strs.add(sb.toString());
            sb.delete(0,sb.length());
        }
        int sLen = strs.size(),size = (sLen-1==0?0:count/(sLen-1));

        StringBuffer ans = new StringBuffer();
        for(int i = 0;i<sLen;i++){
            ans.append(strs.get(i));
            for(int j = 0;j<(i ==sLen-1 ? count-size*(sLen-1):size);j++){
                ans.append(" ");
            }
        }
        return ans.toString();
    }

    /**
     * 题号：669
     * 难度：中等
     * 时间：20220910
     * 给你二叉搜索树的根节点 root ，同时给定最小边界low 和最大边界 high。通过修剪二叉搜索树，使得所有节点的值在[low, high]中。修剪树 不应该 改变保留在树中的元素的相对结构 (即，如果没有被移除，原有的父代子代关系都应当保留)。 可以证明，存在 唯一的答案 。
     *
     * 所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if(root == null){
            return null;
        }
        //如果root的值大于边界则减掉右子树
        if(root.val>high){
            root=trimBST(root.left,low,high);
        }else if(root.val<low){
            //小于最小值则剪掉根节点及左边界
            root=trimBST(root.right,low,high);
        }else{
            root.left=trimBST(root.left,low,high);
            root.right=trimBST(root.right,low,high);
        }
        return root;
    }
    /**
     * 题号：1608
     * 难度：简单
     * 时间：20220912
     * 给你一个非负整数数组 nums 。如果存在一个数 x ，使得 nums 中恰好有 x 个元素 大于或者等于 x ，那么就称 nums 是一个 特殊数组 ，而 x 是该数组的 特征值 。
     *
     * 注意： x 不必 是 nums 的中的元素。
     *
     * 如果数组 nums 是一个 特殊数组 ，请返回它的特征值 x 。否则，返回 -1 。可以证明的是，如果 nums 是特殊数组，那么其特征值 x 是 唯一的 。
     */
    public int specialArray(int[] nums) {
        //排序
        int len = nums.length;
        Arrays.sort(nums);
        //倒序
        for(int i=0,j=len-1;i<j;i++,j--){
            int temp = nums[i];
            nums[i]=nums[j];
            nums[j]=temp;
        }
        for(int i =1;i<len;i++){
            if(nums[i-1]>=i && (i == len||nums[i]<i )){
                return i;
            }
        }
        return -1;
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
/**
 * 题号：1656
 * 难度：简单
 * 时间：20220816
 * 有 n 个 (id, value) 对，其中 id 是 1 到 n 之间的一个整数，value 是一个字符串。不存在 id 相同的两个 (id, value) 对。
 *
 * 设计一个流，以 任意 顺序获取 n 个 (id, value) 对，并在多次调用时 按 id 递增的顺序 返回一些值。
 *
 * 实现 OrderedStream 类：
 *
 * OrderedStream(int n) 构造一个能接收 n 个值的流，并将当前指针 ptr 设为 1 。
 * String[] insert(int id, String value) 向流中存储新的 (id, value) 对。存储后：
 * 如果流存储有 id = ptr 的 (id, value) 对，则找出从 id = ptr 开始的 最长 id 连续递增序列 ，并 按顺序 返回与这些 id 关联的值的列表。然后，将 ptr 更新为最后那个  id + 1 。
 * 否则，返回一个空列表。
 */
class OrderedStream {
    int ptr,n;
    String[] strs;

    public OrderedStream(int n) {
        this.n = n;
        ptr=1;
        strs = new String[n];
    }

    public List<String> insert(int idKey, String value) {
        List<String> ans = new ArrayList<>();
        if(idKey == ptr){
            strs[idKey-1] = value;
            while( ptr != n+1 && strs[ptr-1] != null ){
                ans.add(strs[ptr-1]);
                ptr++;
            }
            return ans;
        }
        strs[idKey-1] = value;
        return ans;
    }
}