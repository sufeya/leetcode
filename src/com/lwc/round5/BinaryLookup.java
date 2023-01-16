package com.lwc.round5;

/**
 * 二分查找，是在有序的情况下查找需要的目标值，通常表现了
 * 在一个单调不减的整数值域内查找需要的目标值
 * 比如说对于查找满足某些条件的第n个数，往往值域中的值并不为一，存在有很多变体形势
 * 如值域中存在需要查找的值，并且有多个需要找到第一个等于该值的位置，或者最后一个等于该值的位置
 * 如值域中不存在该值，查找在值域中小于该值的最大值的位置，或者查找值域中大于该值的最小值的位置
 * @author liuweichun
 * @date 2022/11/24 11:22
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class BinaryLookup {

    /**
     * 题号：34
     * 难度：中等
     * 时间：20221124
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     *
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     *
     * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
     */
    public int[] searchRange(int[] nums, int target) {
        int start = findFirst(nums,target);
        if(start == nums.length || nums[start] !=target){
            return new int[]{-1,-1};
        }
        //因为start存在那么end一定存在
        int end = findFirst(nums,target+1)-1;
        return new int[]{start,end};
    }
    //找到第一个大于等于目标元素的值
    public int findFirst(int[] nums,int target){
        //二分查找，通过染色法进行分析，我们定义，白色的为不确定的，红色的为大于等于
        //target，蓝色的为小于等于target的值
        //刚开是时区间选择全闭区间
        int left = 0 , right = nums.length-1;
        while(right>=left){
            int mid = (right-left)/2+left;
            if(nums[mid]<target){
                //当nums[mid]小于target时，可以将left和mid之间的所有元素
                //染成蓝色
                left = mid+1;
            }else{
                //当nums[mid]大于target时，将mid和right之间全部染成红色
                //这样也就可以确定right+1始终时指向红色区域的
                //所以最终循环条件不成立时，也即right<left,也即仅有left == right == mid,当nums[mid]<target 时
                //left = mid+1,right<left,那么right+1也一定指向的是红色的部分,也即right+1指向的是
                // 第一个大于等于target的值,当时nums[mid]>=target时 right = mid-1; 此时left = mid这时
                right = mid-1;
            }
        }
        //均大于目标元素时那么，right+1必然指向的是第一个元素
        //如果都小于目标元素时，那么right+1必然指向的是数组的长度
        /**
         * left = 0 ,right = 5 mid = 2 target = 6
         *   白     红
         * 5,7|, |7,8,8,10
         * left = 0 ,right = 1 ,target = 6 mid = 0
         *  蓝   白     红
         * 5 | ,7, |7,8,8,10
         * left = 1, right = 1, target = 6, mid = 1
         *  蓝     红
         * 5 ,|7,7,8,8,10
         * left = 1,right = 0 ,target = 6 ,mid = 1
         * 此时可以看出right+1必然指向的是大于等于目标元素的第一个值
         */
        return right+1;
    }
    //找到大于等target的最后一个值，可以转化为大于等于taget+1的第一个值

    /**
     * 题号：162
     * 难度：中等
     * 时间：20221124
     *峰值元素是指其值严格大于左右相邻值的元素。
     *
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     *
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     *
     * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
     * 这题目我想法是用双指针来写，从某个节点出发然后向两边进行扩展进行判断
     * 但是显然时间复杂度是o(n),看了题解的意思是采用二分法进行选择，随机选择一个点i
     * 当nums[i-1]<nums[i] > nums[i+1]那么该点就是山顶
     * 如果 nums[i-1]>nums[i]>nums[i+1]那么山顶就是左边，不会一直增因为-1 为负无穷
     * 如果nums[i+1]>nums[i]<nums[i-1]如果在山底那么也往左变走，一定会存在一个山顶
     * 如果nums[i+1]>nums[i]>nums[i-1]那么山定一定在右边
     */
    public int findPeakElement(int[] nums) {
        int left = 0,right = nums.length-1;
        while(left<=right){
            int mid = left +(right - left)/2;
            if(compare(nums,mid,mid+1)>0 && compare(nums,mid,mid-1)>0){
                return mid;
            }
            if(compare(nums,mid,mid-1)<0){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return right+1;
    }
    //第一个大于第二个返回正数，否则返回负数
    public int compare(int[] nums ,int index1 ,int index2){
        if(index1 == -1 || index1 == nums.length){
            return -1;
        }
        if(index2 == -1 || index2 == nums.length){
            return 1;
        }
        if(nums[index1]> nums[index2]){
            return 1;
        }
        return -1;
    }
}
