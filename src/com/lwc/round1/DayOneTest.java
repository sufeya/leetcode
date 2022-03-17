package com.lwc.round1;

import java.util.HashMap;
import java.util.Map;

public class DayOneTest {
    public static void main(String[] args) {
        int [][] nums={{0,0},{1,0},{2,0}};
        numberOfBoomerangs(nums);

    }

    /**
     * 二分法查找
     * @param nums
     * @param target
     * @return
     */
    public static int   search(int[] nums, int target) {
        return splite(target,nums,0,nums.length-1);
    }
    public static int splite(int target,int[] nums,int low ,int high){
        if(low >high ){
            return -1;
        }else{
            int mid =(low +high) /2;
            int result=0;
            if(nums[mid] == target){
                result=mid;
            }
            if(nums[mid]<target){
                result= splite(target,nums,mid+1,high);
            }
            if(nums[mid]>target){
                result=splite(target,nums,low,mid-1);
            }
            return result;
        }
    }
    /**
     *你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     *
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     *
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/first-bad-version
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 这题更像是二叉树的后序遍历
     */
    public int firstBadVersion(int n) {
        int  low =1 , high =n;

        while (low <high){

            int mid =  low +(high - low ) /2;
            //如果当前版本错误则表明错误版本在该版本之前
            if(isBadVersion(mid)){
                //关键在于high的取值，当mid的版本错误时，第一个版本错误的部件应当包含mid的值
                high=mid;
            }
            if(!isBadVersion(mid)){
                low=mid +1;
            }
        }
        return  low;
    }
    public  boolean isBadVersion(int version){
        return false;
    }
    /**
     *  给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
     *
     * 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
     *
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     *
     *  
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/perfect-squares
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 动态规划算法：通过将原问题缩小为更小规模的问题，从而将问题简化
     * 例如题目中将完全平方数的和缩小f(i) ,缩小为1+f(i-j*j),从而将问题的规模缩小，但是在缩小问题的同时我们需要注意的是
     * 问题的下界
     */

    public static  int numSquares(int n) {
        //问题是从1开始并包括零
        int[] min =new int[n+1];
         for( int i =1;i<=n;i++){
             int  sMin =Integer.MAX_VALUE;
             for(int j =1; i>= j*j; j++){
                 sMin=Math.min(sMin,min[i-j*j]);
             }
             min[i]=sMin+1;
         }
        return min[n];
    }
    /**
     * 给定平面上 n 对 互不相同 的点 points ，其中 points[i] = [xi, yi] 。回旋镖 是由点 (i, j, k) 表示的元组 ，其中 i 和 j 之间的距离和 i 和 k 之间的距离相等（需要考虑元组的顺序）。
     *
     * 返回平面上所有回旋镖的数量。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/number-of-boomerangs
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static int numberOfBoomerangs(int[][] points) {
        //获取二维数组中点的个数
        int size =points.length;
        int result=0;
        for(int i=0; i<size; i++){
            //设定一个hashMap用来保存其他所有点到i点的距离相同点的数量，因为回旋镖其实就是以某一个点为基准选择两个距离相同的点
            Map<Integer ,Integer> distances =new HashMap<>();
            //这里应当从零开始计算，因为是i点到其他所有点之间的距离
            for( int j =0; j<size  ;j++){
                //计算i到j之间的距离
                int dis = (points[i][0] -points[j][0])*(points[i][0] -points[j][0])+(points[i][1] -points[j][1])*(points[i][1] -points[j][1]);
                //将距离相同的点的数量存入map中
                distances.put(dis,distances.getOrDefault(dis,0)+1);
            }
            //由于有顺序要求所以要在所有的距离中有排列的选出两个距离相同的点
            for(Map.Entry<Integer,Integer> entry :distances.entrySet()){
              result+= entry.getValue()*(entry.getValue()-1);
            }
        }

        return result;
    }

}
