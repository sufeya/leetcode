package com.lwc.round1;

import java.util.*;

public class DayFour {
    public static void main(String[] args) {
        DayFour dayFour=new DayFour();
       int[] nums =new int[] {-1,0,1,2,-1,-4};

        System.out.println(dayFour.threeSum(nums));
    }
    /**
     * 题号 454
     * 难度中等
     * 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
     *
     * 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -228 到 228 - 1 之间，最终结果不会超过 231 - 1 。
     *
     * 分析：分别在四个数组中找到四个数字，要求四个数字的和为零，最暴力的方法就是四个循环将四个数组中的所有数进行遍历
     * 这样就可以得到
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int result=0;
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0 ;i<nums1.length;i++){
            for(int j=0;j<nums2.length;j++){
                map.put(nums1[i]+nums2[j],map.getOrDefault(nums1[i]+nums2[j],0)+1);
            }
        }
        for(int i=0 ;i<nums3.length;i++){
            for(int j=0;j<nums4.length;j++){
               if(map.containsKey(-nums3[i]-nums4[j])){
                   result+=map.get(-nums3[i]-nums4[j]);
               }
            }
        }

        return result;
    }
    /**
     * 题号 18
     * 难度 中等
     * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] ：
     *
     * 0 <= a, b, c, d < n
     * a、b、c 和 d 互不相同
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * 你可以按 任意顺序 返回答案 。
     *
     *  分析：1.这个数组中可能存在多个答案，2.其中a,b,c,d的顺序各不相同，3.i ,j 跟j ,i顺序可以不同,但是不能相同
     *  解法：1.暴力解法：直接使用for循环进行遍历即可，但这种大概率会超时 ，2.双指针法
     *
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        DayThree three=new DayThree();

        //对该节点进行排序
        three.quickSort(0,nums.length-1,nums);
        List<List<Integer>> result =new ArrayList<>();
        for(int i=0 ;i<nums.length-2;i++){
            //如果前一个数与后一个数相同则放弃遍历
            if(i> 0 && nums[i] == nums[i-1]){
                continue;
            }
            for(int j =i+1 ; j<nums.length-1 ;j++){
                //不从刚开始就开始，如果前一个数与后一个数相同则放弃遍历
                if(j> i+1 && nums[j] == nums[j-1]){
                    continue;
                }
                int n =j+1,m=nums.length-1;

                //双指针用于循环该数组减少时间复杂度
                while(m>n){
                    int k= nums[i] + nums[j] + nums[n] + nums[m];
                    //前一个与后一个相同时则放弃当前循环
                    if(m< nums.length-1 && nums[m] == nums[m+1] ){
                        m--;
                        continue;
                    }
                    if(n>j+1 && nums[n] == nums[n-1]){
                        n++;
                        continue;
                    }
                    //如果小于目标值则m应当向后移动
                    if(k<target){
                        n++;
                    }
                    if(k> target){
                        m--;
                    }
                    if(k == target){
                        List<Integer> temp =new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[n]);
                        temp.add(nums[m]);
                        result.add(temp);
                        m--;
                        n++;
                    }
                }
            }
        }
        return result;
    }
    /**
     * 题号 15
     * 难度 中
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *与之前四数之和是一类题目
     *  
     *

     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result=new ArrayList<>();
        Arrays.sort(nums);
        //这里由于是三个数相加所以要空出两个位置
        for(int i=0 ;i<nums.length -2 ;i++ ){
            //当前数与前一个数相同时则放弃该层循环
            if(i> 0 && nums[i] == nums[i-1]){
                continue;
            }
            int m =i+1,n=nums.length-1;
            while(m<n){
                if(m> i+1 && nums[m] == nums[m-1]){
                    m++;
                    continue;
                }
                if(n< nums.length-1 && nums[n] == nums[n+1]){
                    n--;
                    continue;
                }
                int k=nums[i] +nums[n] + nums[m];
                if( k < 0){
                    m++;
                }
                if(k > 0){
                   n--;
                }
                if(k == 0){
                    result.add(Arrays.asList(nums[i],nums[n],nums[m]));
                    m++;
                    n--;
                }
            }
        }
        return result;
    }
}