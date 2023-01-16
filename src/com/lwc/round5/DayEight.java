package com.lwc.round5;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/11/22 9:43
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayEight {
    /**
     * 难度：中等
     * 时间：20221208
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合
     */
    static Set<String> set = new HashSet<>();
    static List<String> ans = new LinkedList<>();
    public List<String> generateParenthesis(int n) {
        generate("",n,n);
        return ans;
    }
    public void generate(String sb,int left,int right){
        if(left == 0 || right == 0){
            while(left>0){
               sb = sb+"(";
                left--;
            }
            while(right>0){
               sb= sb+")";
                right--;
            }
            if(undefineStr(sb.toString())){
                ans.add(sb.toString());
            }
            return;
        }
        if(set.contains(sb.toString())){
            return;
        }
        if(!undefineStr(sb.toString())){
            set.add(sb.toString());
            return;
        }
        generate(sb+")",left,right-1);
        generate((sb+"("),left-1,right);
    }
    //判断括号是否合法
    public boolean undefineStr(String str){
        Stack<Character> stack = new Stack();
        int len = str.length();
        for(int i = 0;i<len ;i++){
            char c = str.charAt(i);
            if(c == ')'){
                if(!stack.isEmpty() && stack.peek() == '('){
                    stack.pop();
                }else{
                    return false;
                }
            }else{
                stack.push(c);
            }
        }
        return true;
    }
    //这题也可以利用动态规划法进行解题
    ArrayList<String>[] dp =  new ArrayList[100];
    public List<String> generateParenthesis2(int n) {
        return generateBuket(n);
    }
    public ArrayList generateBuket(int n ){
        if(dp[n] != null){
            return dp[n];
        }
        ArrayList<String> ans = new ArrayList<>();
        if(n == 0){
            ans.add("");
        }else{
            for(int i = 0;i < n ;i++){
                for(String left : dp[i]){
                    for(String right:dp[n-i-1]){
                        ans.add("("+left+")"+right);
                    }
                }
            }
        }
        dp[n] = ans;
        return ans;
    }
    /**
     * 题号：808
     * 难度：中等
     * 时间：20221120
     * 有 A 和 B 两种类型 的汤。一开始每种类型的汤有 n 毫升。有四种分配操作：
     * <p>
     * 提供 100ml 的 汤A 和 0ml 的 汤B 。
     * 提供 75ml 的 汤A 和 25ml 的 汤B 。
     * 提供 50ml 的 汤A 和 50ml 的 汤B 。
     * 提供 25ml 的 汤A 和 75ml 的 汤B 。
     * 当我们把汤分配给某人之后，汤就没有了。每个回合，我们将从四种概率同为 0.25 的操作中进行分配选择。如果汤的剩余量不足以完成某次操作，我们将尽可能分配。当两种类型的汤都分配完时，停止操作。
     * <p>
     * 注意 不存在先分配 100 ml 汤B 的操作。
     * <p>
     * 需要返回的值： 汤A 先分配完的概率 +  汤A和汤B 同时分配完的概率 / 2。返回值在正确答案 10-5 的范围内将被认为是正确的。
     * 这道题我真的是一点都没看明白刚开是，特别是这个概率怎么算的一点都不明白，我以为是直接计算全概率事件，后面发现
     * 好像并不是这样子，看完解析后得到的理解如下该题显然是用动态规划进行计算，使用动态归还自底向下进行计算
     * 可以保留之前的状态从而不用重复计算一下是状态方程dp[i][j]表示的是当a汤剩下i ml和b汤剩下j ml时的概率
     * 因为这两中汤只有四种分法 （100，0）（75，25），（50，50），（25，75），可以知道都时2的整数，当剩下的不满25时我们可以
     * 当成25直接分，所以所有值均除以25得一下状态（4，0），（3，1），（2，2），（1，3），这样就大大缩小计算
     * dp[i][j] = (dp[i-4][j]+dp[i-3][j-1]+dp[i-2][j-2]+dp[i-1][j-3])*0.25,因为这四种分法得该路都是0.25,所以
     * 上一次是dp[i-4][j]得概率就是0.25，跟据全概率公式p(x) = p(x|b)*p(b)+p(x|c)*p(c)+p(x|d)*p(d)+p(x|e)*p(e)
     * 其中p(x|b)表示的是在条件b下x发生的概率。解释如下x表示的是分完之后a剩下的汤为(i,j)的概率，b表示的是分（100，0）的概率
     * 其他的解释一致
     */
    public double soupServings(int n) {
        //通过计算当n的值大于4475时概率接近1，同时差值满足精度范围
        if (n >= 4475) {
            return 1.0;
        }
        //不满25当25算
        n = (int) Math.ceil(n * 1.0 / 25.0);
        double[][] dp = new double[n + 1][n + 1];
        //考虑初始条件,当a汤为0 b汤大于0时，a分完的概率就是1，同时分完的概率就是零所以这时的概率就是1
        Arrays.fill(dp[0], 1.0);
        //当都为零时，a先分完的概率0，同时分完的概率是1 所以dp[0][0] = 0.5
        dp[0][0] = 0.5;
        //当a大于零，b为零时 a先分完是0，同时分完的概率是0
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = (dp[Math.max(i - 4, 0)][j] + dp[Math.max(0, i - 3)][Math.max(0, j - 1)] + dp[Math.max(0, i - 2)][Math.max(0, j - 2)] + dp[Math.max(0, i - 1)][Math.max(0, j - 3)]) * 0.25;
            }
        }
        return dp[n - 1][n - 1];
    }

    //自顶向下记忆化搜索
    double[][] meme;

    public double soupServings2(int n) {
        if (n >= 4475) {
            return 1.0;
        }
        //不满25当25算
        n = (int) Math.ceil(n * 1.0 / 25.0);
        meme = new double[n + 1][n + 1];

        return  dfs(n, n);
    }

    public double dfs(int a, int b) {
        if (a == 0 && b > 0) {
            return 1;
        }
        if (a > 0 && b == 0) {
            return 0;
        }
        if (a == 0 && b == 0) {
            return 0.5;
        }
        if (meme[a][b] == 0) {
            meme[a][b] = (dfs(Math.max(a - 4, 0), b) + dfs(Math.max(a - 3, 0), Math.max(b - 1, 0)) + dfs(Math.max(a - 2, 0), Math.max(b - 2, 0)) + dfs(Math.max(a - 1, 0), Math.max(b - 3, 0))) * 0.25;
        }
        return meme[a][b];
    }
    /**
     * 题号：33
     * 难度：中等
     * 时间：20221220
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     *
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     *
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     *
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     */
    public int search(int[] nums, int target) {
        if(nums.length == 1){
            return nums[0] == target ? 0:-1;
        }
        int k = -1;
        //先找到反转的位置
        for(int i =1;i<nums.length;i++){
            if(nums[i]<nums[i-1]){
                k = i;
                break;
            }
        }
        //分别对两边进行二分查找
        int left1 = 0,right1 = k-1,left = k ,right = nums.length-1;
        if(k<0){
            left = 0;
        }
        while(left1<=right1 && left1>0){
            int mid = (right1-left1>>1)+left1;
            if(nums[mid]>=target){
                right1 = mid-1;
            }else{
                left1 = mid+1;
            }
        }
        if(nums[left1] == target){
            return left1;
        }
        while(left<=right && left>0){
            int mid = (right-left>>1)+left;
            if(nums[mid]>=target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        if(left>=nums.length){
            return -1;
        }
        return nums[left] == target ? left:-1;
    }
    /**
     * 题号：878
     * 难度：困难
     * 时间：20221123
     *一个正整数如果能被 a 或 b 整除，那么它是神奇的。
     *
     * 给定三个整数 n , a , b ，返回第 n 个神奇的数字。因为答案可能很大，所以返回答案 对 109 + 7 取模 后的值。
     */
    public int nthMagicalNumber(int n, int a, int b) {
        int mod = (int)1e9+7;
       long right = (long)n*Math.min(a,b);
       long left =Math.min(a,b);
       int maxDiff = count(a,b),minMul = a*b/maxDiff;
        //通过二分查找找到对应的第n个数
        while(right>=left){
           long mid  = (right-left)/2+left;
           long count = mid/a +mid/b - mid/minMul;
           if(count<n){
               left = mid+1;
           }else{
               right = mid-1;
           }
        }
        //这是因为二分查找
        return (int)((right+1)%mod);
    }
    //求最大公约数
    public int count(int a ,int b){
        //两数的乘积为最大公约数与最小公倍数之积，通过辗转相除法求的最大公约数
        int i = Math.max(a,b) ,j = i == a?b:a;
        while(i%j != 0){
            int temp = j;
            j = i%j;
            i=temp;
        }
      return j;
    }
    static final int MOD = 1000000007;
    public int nthMagicalNumber2(int n, int a, int b) {
        long l = Math.min(a, b);
        long r = (long) n * Math.min(a, b);
        int c = lcm(a, b);
        while (l <= r) {
            long mid = (l + r) / 2;
            long cnt = mid / a + mid / b - mid / c;
            if (cnt >= n) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        //39999999999999
        //39999999999999
        return (int) ((r + 1) % MOD);
    }

    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }


    public static void main(String[] args) {
        DayEight eight = new DayEight();
        System.out.println(eight.search(new int[]{1,3},0));
        System.out.println(ans.toString());
    }
}
