package com.lwc.round3;

import java.util.Arrays;
import java.util.Stack;

public class DayTen {

    /**
     * 题号：449
     * 难度：中等
     * 时间：20220511
     * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
     *
     * 设计一个算法来序列化和反序列化 二叉搜索树 。 对序列化/反序列化算法的工作方式没有限制。 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
     *
     * 编码的字符串应尽可能紧凑。
     */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuffer sb = new StringBuffer();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        //对二叉搜索树进行中序遍历
        while(!stack.isEmpty() || p != null){
            //先到达节点的最左边
            while(p != null){
                stack.push(p);
                p=p.left;
            }
            //出栈
            p=stack.pop();
            sb.append(p.val+",");
            p=p.right;
        }

        sb.append(" ");
        //同时进行先序遍历
        p=root;
        while( !stack.isEmpty() || p != null){
            //到达最左边
            while(p != null ){
                sb.append(p.val+",");
                stack.push(p);
                p=p.left;
            }
            //出栈
            p=stack.pop();
            p=p.right;

        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    //跟据给定的字符串创建树
    public TreeNode deserialize(String data) {
        if(data == null ||data.length() == 0 || " ".equals(data)){
            return null;
        }
        //先将该字符串分成两段
        String[] str =data.split(" ");
        String[] mid = (str[0].trim()).split(",");
        String[] pre = (str[1].trim()).split(",");
        //跟据先序遍历和中序遍历的结果进行重构二叉树
        return buildTree(pre,mid,0,pre.length-1,0,mid.length-1);
    }
    public TreeNode buildTree(String[] pre,String[] mid,int pLeft,int pRight,int mLeft,int mRight){
        if( pRight<pLeft ||mLeft>mRight){
            return null;
        }
        int tempmRight=0;
        TreeNode root = new TreeNode(Integer.parseInt(pre[pLeft]));
        //将中序遍历分成左右子串
        for(int i =mLeft ;i<=mRight;i++){
            if(mid[i].equals(pre[pLeft])){
                tempmRight=i-1;
                break;
            }
        }
        int temppRight = tempmRight -mLeft+pLeft+1;
        //同时将先序字符串分成两段
        root.left = buildTree(pre,mid,pLeft+1,temppRight,mLeft,tempmRight);
        root.right=buildTree(pre,mid,temppRight+1,pRight,tempmRight+2,mRight);
        return root;
    }
    /**
     *
     */
    boolean oneEditAway(String first, String second) {
        int len = first.length(),len2 = second.length();
        //如果两个长度之差大于一说明不能进行一次操作
        if(Math.abs(len-len2)>1){
            return false;
        }
        //如果两个长度相同则说明只需要进行替换操作即可
        if(len == len2){
            //此时只需要判断两个字符串之间的不同之处是否大于1即可
            int diff =0;
            for(int i =0 ;i<len ;i++){
                if(first.charAt(i) != second.charAt(i)){
                    diff++;
                }
                if(diff >1){
                    return false;
                }
            }
        }else{
            //如果两个两个字符串之间的长度差距等于1则可能是一个进行删除操作，或者一个进行增加操作
            //删除操作跟增加操作其实等价，这边只进行增加操作，
          int i =0,j=0;
          while(i<len && j<len2){
              if(first.charAt(i) != second.charAt(j)){
                  if(len>len2){
                      i++;
                  }else{
                      j++;
                  }
              }else{
                  i++;
                  j++;
              }
              if(Math.abs(i-j)>1){
                  return false;
              }
          }

        }
        return true;
    }

    /**
     * 题号：691
     * 难度：困难
     * 时间：20220514
     *我们有 n 种不同的贴纸。每个贴纸上都有一个小写的英文单词。
     *
     * 您想要拼写出给定的字符串 target ，方法是从收集的贴纸中切割单个字母并重新排列它们。如果你愿意，你可以多次使用每个贴纸，每个贴纸的数量是无限的。
     *
     * 返回你需要拼出 target 的最小贴纸数量。如果任务不可能，则返回 -1 。
     *
     * 注意：在所有的测试用例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选择的，并且 target 被选择为两个随机单词的连接。
     * 分析：该题自己不会写，看完解析之后的解答如下
     */
    public int minStickers(String[] stickers, String target) {
        int len = target.length();
        //因为题目并不需要字符串的顺序所以只需要跟据是否有该字符串并且进行删除即可
        //而跟据target的字符串长度问len则可以确定长度为len的字符串每个位置选或者不选
        //有两种可能即其子序列有2^len种可能，可以跟据动态规划的算法对其进行处理
        int[] dp =new int[1<<len];
        Arrays.fill(dp,-1);
        //对dp数组进行初始化,可知边界条件为当选中的字符个数为零时对应的需要stickers的个数应带为0
        dp[0]=0;
        int res = dfs(stickers,dp,(1<<len)-1,target);
        return res <= len ? res : -1;

    }
    //其中state是用数字表示0-len-1处某个字符是否选中，即选中为一未选中为0，初始时为2^len-1
    public int dfs(String[] stickres ,int[] dp,int state,String target){
        if(dp[state] <0){
            //res最大是每个stickers都只选一个字符，这里设置初值方便后面取得，除去最大交集字符得最小值
            int res = target.length()+1;
            for(String str : stickres){
                int nState = state;
                //统计sticker字符串字符的个数
                int[] chars = new int[26];
                for(int i =0;i<str.length();i++ ){
                    char c =str.charAt(i);
                    chars[c-'a']++;
                }
                //消除对应位置的字符
                for(int i=0;i<target.length();i++){
                    char c = target.charAt(i);
                    //如果当前子序列在该位置有该字符则进行消除
                    if(((nState >>i) &1 ) ==1 && chars[c-'a']>0){
                        //将对应i的位置变为零,更新状态
                        nState ^=(1<<i);
                        chars[c-'a']--;
                    }
                }
                //选取了字符的才进行深度搜索以避免无限递归
                if(nState<state){
                    //每次选取交集最多的字符的进行操作，从而保证选取的sticker个数是最小的
                    res = Math.min(res,dfs(stickres,dp,nState,target)+1);
                }

            }
            dp[state] =res;
        }
        return dp[state];
    }

    /**
     * 题号：812
     * 难度：简单
     * 时间：20220515
     * 给定包含多个点的集合，从其中取三个点组成三角形，返回能组成的最大三角形的面积。
     */
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double ret = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    ret = Math.max(ret, triangleArea(points[i][0], points[i][1], points[j][0], points[j][1], points[k][0], points[k][1]));
                }
            }
        }
        return ret;
    }

    public double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return 0.5 * Math.abs(x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2);
    }

    /**
     * 难度：中等
     * 时间：20220516
     * 设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
     *
     * 如果指定节点没有对应的“下一个”节点，则返回null。
     *
     * 示例 1:
     * 分析：可以知道左节点的后续节点是根节点，而右孩子节点的后续节点可能是左子树的最右节点
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack();
        TreeNode i =root;
        TreeNode ans = null;
        boolean flag = false;
        while(!stack.isEmpty() || i != null){
            while(i != null){
                stack.push(i);
                i=i.left;
            }
            i=stack.pop();
            if(flag){
                ans = i;
                break;
            }
            if(i.val == p.val){
                flag=true;
            }

            i=i.right;
        }
        return ans;
    }

    /**
     * 题号：953
     * 时间：20220517
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     *
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     */
    public boolean isAlienSorted(String[] words, String order) {
        int[] orders = new int[26];
        //获取其字典序
        for(int i=0;i<order.length();i++){
            orders[order.charAt(i)-'a']=i;
        }
        for(int i =1;i<words.length;i++){
            String str1 =words[i-1];
            String str2 =words[i];
            int j =0;
            while(j<str1.length() && j<str2.length()){
                if(orders[str1.charAt(j)-'a']<orders[str2.charAt(j)-'a']){
                    break;
                }else if(orders[str1.charAt(j)-'a']==orders[str2.charAt(j)-'a']){
                    j++;
                }else{
                    return false;
                }
                //如果前面的字符都相同，且第一个字符串长度要长于第二个
                if(j == str2.length() && str2.length()<str1.length()){
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 题号：688
     * 难度：困难
     * 时间：20220518
     *几乎每一个人都用 乘法表。但是你能在乘法表中快速找到第k小的数字吗？
     *
     * 给定高度m 、宽度n 的一张 m * n的乘法表，以及正整数k，你需要返回表中第k 小的数字。
     * 分析：可知m*n的乘法表其取值范围一定是1-m*n这个范围之中，通过在这个范围中利用二分查找法找到其中的个数
     * 跟据值来判断所处的位置，但是要跟据给定的值来判断其所在的位置例如给定的值为r,则其对应的位置，首先对其进行开方
     * 得到对应的整数值设为j则可以知道j*j,可以直接通过循环来进行计数
     */
    public int findKthNumber(int m, int n, int k) {
        int left=1,right =m*n;
        while(right>left){
            int mid = left +(right-left)/2;
            int count =mid/n *n;
            for(int i = mid/n +1;i<=m;i++){
                count+=(mid/i);
            }
            if(count>=k){
               right = mid;
            }else if(count<k ){
                left=mid+1;
            }
        }
        return left;
    }

    /**
     * 题号：462
     * 难度：中等
     * 时间：20220519
     *给你一个长度为 n 的整数数组 nums ，返回使所有数组元素相等需要的最少移动数。
     *
     * 在一步操作中，你可以使数组中的一个元素加 1 或者减 1 。
     */
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int abs=0,ans =0,len = nums.length;
        //为偶数时
        if(len %2 == 0){
            abs = (nums[len/2]+nums[len/2-1])/2;
        }else{
            //为奇数时
            abs =nums[len/2];
        }
        for(int temp : nums){
            ans +=Math.abs(temp -abs);
        }
        return ans;
    }
    public static void main(String[] args) {
        DayTen ten = new DayTen();
        System.out.println( ten.minMoves2(new int[]{1,10,2,9}));
    }
}
