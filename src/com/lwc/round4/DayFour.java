package com.lwc.round4;

import com.sun.xml.internal.fastinfoset.util.PrefixArray;

import java.util.*;

public class DayFour {


    /**
     * 题号：730
     * 难度：困难
     * 时间：20220610
     * 给定一个字符串 s，返回 s 中不同的非空「回文子序列」个数 。
     *
     * 通过从 s 中删除 0 个或多个字符来获得子序列。
     *
     * 如果一个字符序列与它反转后的字符序列一致，那么它是「回文字符序列」。
     *
     * 如果有某个 i , 满足 ai != bi ，则两个序列 a1, a2, ... 和 b1, b2, ... 不同。
     *
     */
    public int countPalindromicSubsequences(String s) {
        int mod = (int)1e9+7;
        int len =s.length();
        //其中dp[i][j]表示的是字符串i到j之间的字符子序列
        int[][] dp = new int[len][len];
        //初始化
        for(int i =0;i<len;i++){
            dp[i][i] =1;
        }
        //遍历整个数组生成答案
        for(int i = len-2;i>=0;i--){
            for(int j=i+1;j<len;j++){
                if(dp[i][j] == 0){
                    char sI=s.charAt(i),sJ=s.charAt(j);
                    //如果两边的字符相等
                    if(sI == sJ){
                        //先从两边出发找到与该字符相同的字符，因为计算子串的话之间会重复
                        int posI=i+1,posJ=j-1;
                        while(posI<j && s.charAt(posI) != sI){
                            posI++;
                        }
                        while(posJ>i&&s.charAt(posJ) != sJ){
                            posJ--;
                        }
                        //如果没有相同的字符
                        if(posI>posJ){
                            dp[i][j] =(2*dp[i+1][j-1]+2)%mod;
                        }else if(posI == posJ){
                            //只有一个相同的字符
                            dp[i][j]=(2*dp[i+1][j-1]+1)%mod;

                        }else{
                            dp[i][j] =(2*dp[i+1][j-1]-dp[posI+1][posJ-1])%mod;
                        }

                    }else{
                        dp[i][j]=(dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1])%mod;
                    }
                    dp[i][j]=(dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1])%mod;
                }
            }
        }
        return dp[0][len-1];
    }
    /**
     * 题号：7
     * 难度：中等
     * 时间：20220610
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     *
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     *
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     */
    public int reverse(int x) {
        long temp =Math.abs(x),ans=0,op=1;
        if(x<0){
            op=-1;
        }
        //有可能会超过整数范围
        while(temp>0){
            ans=ans*10+temp%10;
            temp/=10;
        }
        ans=ans*op;
        if(ans>Integer.MAX_VALUE || ans<Integer.MIN_VALUE){
            ans=0;
        }
        return (int)ans;
    }
    /**
     * 题号：8
     * 难度：中等
     * 时间：20220610
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     *
     * 函数 myAtoi(string s) 的算法如下：
     *
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
     * 返回整数作为最终结果。
     */
    public int myAtoi(String s) {
        long ans = 0,op=1,opCount=0;
        boolean flag =false,operator =false;
        for(int i =0;i<s.length();i++){
            char c =s.charAt(i);
            if(c>='0' && c<='9'){
                flag=true;
                ans =ans*10+op*(c-48);
                if(ans>Integer.MAX_VALUE ){
                    return Integer.MAX_VALUE;
                }
                if(ans<Integer.MIN_VALUE){
                    return Integer.MIN_VALUE;
                }
            }else if((c== '-'||c=='+') && !flag){
                operator=true;
                opCount++;
                if(opCount>1){
                    return (int) ans;
                }
                if(c=='-'){
                    op=-1;
                }

            }else if(c==' ' && !flag && !operator){
                continue;
            }else{
                break;
            }
        }
        return (int)ans;
    }
    /**
     * 题号：9
     * 难度：简单
     * 时间：20220611
     *给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     *
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     *
     * 例如，121 是回文，而 123 不是。

     */
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int temp =x,ans=0;
        while(temp>0){
            ans= ans*10+temp%10;
            temp/=10;
        }
        if(ans == x){
            return true;
        }
        return false;
    }
    /**
     * 题号：11
     * 难度：中等
     * 时间：20220611
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     *
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 返回容器可以储存的最大水量。
     *
     * 说明：你不能倾斜容器。
     */
    public int maxArea(int[] height) {
        return 0;
    }
    /**
     * 题号：926
     * 难度：中等
     * 时间：20220613
     * 如果一个二进制字符串，是以一些 0（可能没有 0）后面跟着一些 1（也可能没有 1）的形式组成的，那么该字符串是 单调递增 的。
     *
     * 给你一个二进制字符串 s，你可以将任何 0 翻转为 1 或者将 1 翻转为 0 。
     *
     * 返回使 s 单调递增的最小翻转次数。
     */
    public int minFlipsMonoIncr(String s) {
        int len = s.length(),countZ=0,countO=0,tailO=0;
        boolean flag =false;
        //将末尾的一可以去除，因为末尾的连续的一就是有序的
        for(int i =len-1;i>=0;i--){
            if(s.charAt(i) == '1'){
                tailO++;
            }else{
                break;
            }
        }
        for(int i =0;i<len-tailO;i++){
            char c =s.charAt(i);
            if(!flag && c=='1'){
                flag=true;
            }
            if(flag){
                if(c == '0'){
                    countZ++;
                }else{
                    countO++;
                }
            }
        }
        return Math.min(countO,countZ);
    }
    //利用动态规划进行解题
    public int minFlipsMonoIncr2(String s) {
        int len =s.length();
        int[][] dp = new int[len][2];
        if(s.charAt(0) == '0'){
            dp[0][1]=1;
        }else{
            dp[0][0]=1;
        }
        for(int i = 1;i<len ;i++){
            char c = s.charAt(i);
            if(c == '0'){
              dp[i][0]=dp[i-1][0];
              dp[i][1]=Math.min(dp[i-1][1]+1,dp[i-1][0]+1);
            }else{
               dp[i][0]=dp[i-1][0]+1;
               dp[i][1]=Math.min(dp[i-1][1],dp[i-1][0]);
            }
        }
        return Math.min(dp[len-1][0],dp[len-1][1]);
    }

    /**
     * 题号：890
     * 难度：中等
     * 时间：20220613
     *你有一个单词列表 words 和一个模式  pattern，你想知道 words 中的哪些单词与模式匹配。
     *
     * 如果存在字母的排列 p ，使得将模式中的每个字母 x 替换为 p(x) 之后，我们就得到了所需的单词，那么单词与模式是匹配的。
     *
     * （回想一下，字母的排列是从字母到字母的双射：每个字母映射到另一个字母，没有两个字母映射到同一个字母。）
     *
     * 返回 words 中与给定模式匹配的单词列表。
     * 你可以按任何顺序返回答案。
     */
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> ans = new ArrayList<>();
        int[] chars = new int[26];
        int[] chars2=new int[26];
        Arrays.fill(chars,-1);
        Arrays.fill(chars2,-1);
        for(String word:words){
            int i=0;
            for(;i<word.length();i++){
                char c =word.charAt(i),p=pattern.charAt(i);
                if(chars[p-'a']==(c-'a') && chars2[c-'a'] == (p-'a')){
                    continue;
                }else if(chars[p-'a'] == -1 && chars2[c-'a'] == -1){
                    //如果这个字母还没有进行映射
                    chars[p-'a']=c-'a';
                    chars2[c-'a']=p-'a';
                }else{
                    break;
                }
            }
            Arrays.fill(chars,-1);
            Arrays.fill(chars2,-1);
            if(i==word.length()){
                ans.add(word);
            }
        }
        return ans;
    }

    /**
     * 题号：1051
     * 难度：简单
     * 时间：20220613
     *学校打算为全体学生拍一张年度纪念照。根据要求，学生需要按照 非递减 的高度顺序排成一行。
     *
     * 排序后的高度情况用整数数组 expected 表示，其中 expected[i] 是预计排在这一行中第 i 位的学生的高度（下标从 0 开始）。
     *
     * 给你一个整数数组 heights ，表示 当前学生站位 的高度情况。heights[i] 是这一行中第 i 位学生的高度（下标从 0 开始）。
     *
     * 返回满足 heights[i] != expected[i] 的 下标数量 。
     */
    public int heightChecker(int[] heights) {
        int[] temp = new int[heights.length];
        int count=0;
        for(int i =0 ;i<heights.length;i++){
            temp[i]=heights[i];
        }
        //给数组排个序，直接用快速排序
        quickSort(temp,0,heights.length-1);
        for(int i =0;i<heights.length;i++){
            if(temp[i] != heights[i]){
                count++;
            }
        }
        return count;
    }
    //快速排序递归算法
    public void quickSort(int[] arr,int begin,int end){
        if(begin >=end ){
            return;
        }
        //使用首位作为基准
        int base = arr[begin];
        int left = begin, right=end;
        while(left<right){
            while(arr[right]>base && right>left){
                right--;
            }
            arr[left] =arr[right];
            while(arr[left]<=base && left<right){
                left++;
            }
            arr[right]=arr[left];
        }
        arr[left]=base;
        quickSort(arr,begin,left-1);
        quickSort(arr,left+1,end);
    }
    //快速排序非递归算法
    public void quickSort(int[] arr){
        Stack<int[]> stack =new Stack<>();
        stack.push(new int[]{0,arr.length-1});
        while(!stack.isEmpty()){
            int[] temp = stack.pop();
            if(temp[0]>=temp[1]){
                continue;
            }
            int left= temp[0],right = temp[1];
            int base = arr[left];
            while(left<right){
                while(arr[right]>base && right>left){
                    right--;
                }
                arr[left] =arr[right];
                while(arr[left]<=base && left<right){
                    left++;
                }
                arr[right]=arr[left];
            }
            arr[left]=base;
            stack.push(new int[]{temp[0],left-1});
            stack.push(new int[]{left+1,temp[1]});
        }
    }

    public static void main(String[] args) {
        DayFour four = new DayFour();
        int[] arr = new int[]{1,1,4,2,1,3};
        four.quickSort(arr);
       for(int i :arr){
           System.out.println(i);
       }

    }
}
/**
 * 题号：497
 * 难度：中等
 * 时间：20220609
 * 给定一个由非重叠的轴对齐矩形的数组 rects ，其中 rects[i] = [ai, bi, xi, yi] 表示 (ai, bi) 是第 i 个矩形的左下角点，(xi, yi) 是第 i 个矩形的右上角点。设计一个算法来随机挑选一个被某一矩形覆盖的整数点。矩形周长上的点也算做是被矩形覆盖。所有满足要求的点必须等概率被返回。
 *
 * 在给定的矩形覆盖的空间内的任何整数点都有可能被返回。
 *
 * 请注意 ，整数点是具有整数坐标的点。
 */
class Solution {
    /**
     * 不可以直接随机选择一个矩形然后再选择里面的点
     * 因为每个矩形里面的点数都不一样如果直接等概率直接选择矩形的话
     * 那这样里面的每个点的概率就不一样了
     */
    int[][] point;
    //面积前缀和
    int[] sum ;
    Random random;
    public Solution(int[][] rects) {
        random= new Random();
        sum =new int[rects.length];
        point=rects;
        sum[0]=(rects[0][2]-rects[0][0]+1)*(rects[0][3]-rects[0][1]+1);
        for(int i =1;i< rects.length;i++){
            sum[i]=sum[i-1]+(rects[i][2]-rects[i][0]+1)*(rects[i][3]-rects[i][1]+1);
        }
    }

    public int[] pick() {
        int val = random.nextInt(sum[point.length-1])+1;
        //利用二分查找找到val所在的位置
        int left =0,right=point.length-1;
        while(right>left){
            int mid = left+(right-left>>1);
            if(sum[mid]<val){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        //找到对应矩形面积并且返回矩形中的任意一点
        int lenX=point[right][2]-point[right][0]+1,lenY=point[right][3]-point[right][1]+1;
        return new int[]{point[right][0]+random.nextInt(lenX),point[right][1]+random.nextInt(lenY)};
    }

}