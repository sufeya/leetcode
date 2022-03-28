package com.lwc.round3;

public class DayFour {
    public static void main(String[] args) {
        DayFour four =new DayFour();
        four.hasAlternatingBits(10);
    }
    /**
     * 题号：2028
     * 难度：中等
     * 时间：20220327
     * 现有一份 n + m 次投掷单个 六面 骰子的观测数据，骰子的每个面从 1 到 6 编号。观测数据中缺失了 n 份，你手上只拿到剩余 m 次投掷的数据。幸好你有之前计算过的这 n + m 次投掷数据的 平均值 。
     *
     * 给你一个长度为 m 的整数数组 rolls ，其中 rolls[i] 是第 i 次观测的值。同时给你两个整数 mean 和 n 。
     *
     * 返回一个长度为 n 的数组，包含所有缺失的观测数据，且满足这 n + m 次投掷的 平均值 是 mean 。如果存在多组符合要求的答案，只需要返回其中任意一组即可。如果不存在答案，返回一个空数组。
     *
     * k 个数字的 平均值 为这些数字求和后再除以 k 。
     *
     * 注意 mean 是一个整数，所以 n + m 次投掷的总和需要被 n + m 整除
     * 分析：该题可以通过回溯法进行穷举所有的可能性,回溯法需要进行重新学习
     */
    static boolean flag = true;
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int tempSum = 0;
        int sum = mean*(rolls.length+n);
        for(int i =0 ;i<rolls.length;i++){
            tempSum+=rolls[i];
        }
        int[] ans = new int[n];
        rollBack(sum,n,tempSum,ans,0);
        return ans;
    }
    public int rollBack(int sum,int m,int tempSum,int[] ans,int time){
        if(time<m && flag){
            //time表示的是找到数据的个数,ans表示的是所找到的结果
            for(int i =1;i<7;i++){
                tempSum+=i;
                ans[time++]=i;
                //如果tempSum大于sum的话则进行剪枝
                if(tempSum<=sum){
                    if(rollBack(sum,m,tempSum,ans,time) == sum){
                        flag = false;
                        return tempSum;
                    }
                }
                //重置状态
                ans[--time]=0;
                tempSum-=i;
            }
        }
      return tempSum;
    }
    public int[] missingRolls2(int[] rolls, int mean, int n) {
        int sum = mean *(rolls.length +n);
        //减去原来有的数据
        for(int i :rolls){
            sum-=i;
        }
        //因为取值在1到6之间所以对应的数据就在n到6n之间
        if(sum<n || sum> 6*n){
            return new int[0];
        }
        //将整个数分成n份，在后在加上剩余的部分就可以
        int base = sum/n,remain=sum%n;
        int[] ans = new int[n];
        for(int i = 0;i<n;i++){
            //要加上remain份1才可以与之前的数相同
            ans[i] = base +(remain>i?1:0);
        }
        return ans;
    }
    /**
     *  题号：693
     *  难度：简单
     *  时间：20220328
     * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
     */
    public boolean hasAlternatingBits(int n) {
        int temp = n;
        int j =(temp&1),i=((temp>>1)&1);
        temp = (temp>>1);
        while(temp>0){
            //取最后一个数字
           if(i == j){
               return false;
           }else{
               j=i;
               i=((temp>>1)&1);
               temp = (temp>>1);
           }
        }
        if(i != j ){
            return true;
        }else{
            return false;
        }
    }
}
