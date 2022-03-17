package com.lwc.round1;

import java.util.Arrays;

public class DayTen {
    public static void main(String[] args) {
        DayTen ten=new DayTen();
        System.out.println(ten.maxPower("cc"));
;    }
    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * 示例 2：
     *
     * 输入：s = "cbbd"
     * 输出："bb"
     * 示例 3：
     *
     * 输入：s = "a"
     * 输出："a"
     * 示例 4：
     *
     * 输入：s = "ac"
     * 输出："a"
     * 分析：该题求的是最长回文子串则应当采用动态规划算法进行求解:第一步应当确定状态转移方程，第二步确定初始状态
     * 第三步确定边界条件进行分析
     * 解题：题目要求得到最长回文子串，状态转移方程可以是找f[i]=Max(f[f[i-1]]+1,f[i]),初始状态是每个对应位置上的字符都是一
     * 还需要创建一个判别回文子串的方法
     */
    public String longestPalindrome(String s) {
        if(s.length()==1){
            return s;
        }
        //状态数组判断从i到j所表示的字符串是否是回文字符串,且注意只有i>j可以进行使用因为上半部分的数组跟下半部分的数组是
        //一样的
        boolean[][] bp=new boolean[s.length()][s.length()];
        //对数组进行初始化，其中对角线上的均为回文字符串
        int i=0;
        while(i<s.length()){
            bp[i][i]=true;
            i++;
        }
        //状态转移方程，跟据回文字符串的特性可以如果bp[i+1][j-1]是回文字符串且当j位置上的字符等于i位置上的字符时那么bp[i][j]必为
        //回文字符
        for(i=0;i<s.length();i++){
            for(int j=0 ;j<i;j++){
                if(i>0 && j<s.length()-1 && bp[i-1][j+1] && s.charAt(i)==s.charAt(j) ){
                    bp[i][j]=true;
                }else{
                    if(isBack(s.substring(j,i+1))){
                        bp[i][j]=true;
                    }else{
                        bp[i][j]=false;
                    }
                }
            }
        }
        int strLenght=0;
        String res ="";
        for(i=0;i<s.length();i++){
            for(int j=0 ;j<=i;j++){
                if(bp[i][j]){
                    strLenght=Math.max(strLenght,i-j+1);
                    if(strLenght==(i-j+1)){
                        res=s.substring(j,i+1);
                    }
                }
            }
        }
        return res;
    }
    //判别该字符串是否是回文子串
    public boolean isBack(String str){
        int i =0 ,j =str.length()-1;
        //指针一个从头一个在尾对字符串的字符进行比较
        while(i<j){
            //如果i和j位置上的字符不相同时，则说明该字符串不是回文字符
            if(str.charAt(i) != str.charAt(j)){
               return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /**
     * 题号：1005
     * 难度：简单
     * 时间：20211203
     * 给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
     *
     * 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
     * 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
     *
     * 以这种方式修改数组后，返回数组 可能的最大和 。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [4,2,3], k = 1
     * 输出：5
     * 解释：选择下标 1 ，nums 变为 [4,-2,3] 。
     * 示例 2：
     *
     * 输入：nums = [3,-1,0,2], k = 3
     * 输出：6
     * 解释：选择下标 (1, 2, 2) ，nums 变为 [3,1,0,2] 。
     * 示例 3：
     *
     * 输入：nums = [2,-3,-1,5,-4], k = 2
     * 输出：13
     * 解释：选择下标 (1, 4) ，nums 变为 [2,3,-1,5,4] 。
     * 分析：该题实际上是将最小的转化为其相反数然后进行相加即可，也就是将整个数组进行排序每次选择第k小的数进行转化即可
     * @param nums
     * @param k
     * @return
     */
    public int largestSumAfterKNegations(int[] nums, int k) {
        //对nums进行排序
        Arrays.sort(nums);
        //将前几个数进行转化，每次转化之后都再次进行排序
        for(int i =0 ;i<k ;i++){
            nums[0]= -nums[0];
            Arrays.sort(nums);
        }
        //定义结果对其进行累加
        int res =0;
        //再进行累加即可
        for(int i = 0;i<nums.length;i++){
            res =res+ nums[i];
        }
        return res;
    }
    /**
     * 题号：1446
     * 难度：简单
     * 时间：20211203
     * 给你一个字符串 s ，字符串的「能量」定义为：只包含一种字符的最长非空子字符串的长度。
     *
     * 请你返回字符串的能量。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "leetcode"
     * 输出：2
     * 解释：子字符串 "ee" 长度为 2 ，只包含字符 'e' 。
     * 示例 2：
     *
     * 输入：s = "abbcccddddeeeeedcba"
     * 输出：5
     * 解释：子字符串 "eeeee" 长度为 5 ，只包含字符 'e' 。
     * 示例 3：
     *
     * 输入：s = "triplepillooooow"
     * 输出：5
     * 示例 4：
     *
     * 输入：s = "hooraaaaaaaaaaay"
     * 输出：11
     * 示例 5：
     *
     * 输入：s = "tourist"
     * 输出：1
     * 分析：可以设置一个双指针对其进行循环，如果两指针指向的字符都是一样的则右指针递增，如果不一样则左指针指向右指针进行迭代
     * 同时设置一个res值用于获取其中相差最大的值
     * 解题：
     */
    public int maxPower(String s) {
        if(s.length() == 1){
            return 1;
        }
        //保存最大的长度的结果值
        int res =0;
        //设置两个指针分别指向两个字符
        int left =0,i=0;
        for( ;i <s.length() ;i++){
            if(s.charAt(left) != s.charAt(i)){
                res =Math.max(res,i-left);
                left=i;
            }
        }
        res = Math.max(res,i-left);
        return res;
    }
}
