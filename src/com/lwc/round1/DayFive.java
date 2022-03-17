package com.lwc.round1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DayFive {
    public static void main(String[] args) {
        DayFive five =new DayFive();

        System.out.println(five.findMedianSortedArrays(new int[]{1,2},new int[]{3,4}));

    }
    /**
     * 题号 2
     * 难度 中等
     *给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     *
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     *
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *分析：该题首先是数字的逆序所以要想得到两个数字的真实值应当采用栈来进行存储，其次两数累加其实就是对应位置上的数字进行累加
     * 但是考虑到两数的位数可能不一致，且存在进位，所以应当从个位开始进行累加然后产生进位
     * 解题方法：直接对应遍历两个链表，且设置一位数据对各个位数的进位进行保存，每次使用完毕置零

     * @param l1
     * @param l2
     * @return
     *
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //设置进位数
      int carryInt =0;
      //算是一个指针用于指向最后面的一个节点
      ListNode m =null;
      ListNode result= null;
      //设置两个变量对两链表进行遍历
      ListNode j=l1;
      ListNode i =l2;
      //对两链表进行遍历
      while(j !=null && i != null){
          //如果前面的数据产生了进位则先处理进位数
          if(carryInt != 0){
              j.val=j.val + carryInt;
              carryInt=0;
          }
          //处理完进位之后，对两数据进行相加
          int k =j.val +i.val;
          //如果k的值产生了进位
          if( k >= 10){
              carryInt = 1;
              k=k-10;
          }
          //处理产生的结果
          if(result ==null){
              result =new ListNode(k);
              m=result;
          }else{
              //如果不是节点刚开始时
              ListNode node =new ListNode(k);
              m.next=node;
              m=node;
          }
          j=j.next;
          i=i.next;
      }
      //处理剩下来的哪个链表的值,同时如果上面还有进位没有进行处理的话，下面也要接着进行处理,同时这里加完进位之后依然有可能产生进位
      if(i != null){
          while(i != null ){
              if(carryInt != 0){
                  i.val +=carryInt;
                  carryInt =0;
              }
              if(i.val >= 10){
                  i.val -=10;
                  carryInt =1;
              }
              m.next= i;
              m=i;
              i=i.next;
          }
      }
      if( j != null){
          while(j != null){
              if(carryInt != 0){
                  j.val +=carryInt;
                  carryInt =0;
              }
              if(j.val >= 10){
                  j.val -=10;
                  carryInt =1;
              }
              m.next= j;
              m=j;
              j=j.next;
          }
      }
      //如果最后依然产生了进位则新增节点
      if(carryInt != 0){
          m.next =new ListNode(carryInt);
          carryInt=0;
      }

        return result;
    }

    /**
     * 题号： 3
     * 难度：中
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     *分析:本题需要考虑的是子串而不是整个字符串所以应该用双指针的形式来对字串进行匹配
     * 解题：本题最简单的就是暴力解法，时间复杂度跟空间复杂度较高，使用滑动窗口能大大降低算法的时间空间复杂度
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0 || s ==null){
            return 0;
        }
        char[] chars =s.toCharArray();
        //用于以该位置为字符为首位的最大子串长度
        int[] result =new int[chars.length];
        //两个指针用于指向字符串的两个位置
        int i=0;
        while(i<chars.length){
            int j=i+result[i-1]-2;
            HashMap<String,Integer> map =new HashMap<>();
            while(j < chars.length){
                map.put(chars[j]+"",map.getOrDefault(chars[j]+"",0)+1);
                if(map.get(chars[j]+"") > 1){
                    break;
                }
                j++;
            }
            result[i]=j-i;
            i++;
        }
        Arrays.sort(result);
        return result[chars.length-1] ;
    }
    //滑动窗口算法计算无重复最长字串
    public int lengthOfLongestSubstring2(String s) {
        if(s.length() <=0 || s ==null){
            return 0;
        }
        //用于存放字串中的字符
        Set<Character> set =new HashSet<>();
        char[] chars =s.toCharArray();
        int res=Integer.MIN_VALUE;
        int left = 0,right =-1;
        while(left < chars.length){
            //如果右边界小于字符数组边界且set中不包含该字符
            if(right+1 <s.length() && !set.contains(chars[right+1])){
                set.add(chars[right+1]);
                right++;
            }else{
                //如果set中包含该字符，则左指针右移直到将该字符清除出set
                set.remove(chars[left]);
                left++;
            }
            res= Math.max(res,right-left+1);
        }
        return res;
    }

    /**
     * 题号 4
     * 难度 困难
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * 分析：中位数是两个数组长度中间的，如果两个数组长度之和为奇数则数组中位数只有一个，如果数组之和为偶数则数组
     * 中位数为两数之和的平均值，所以该题的关键是如何找到两数
     * 解题：
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double res=0;
        //首先先将两个数组进行合并之后寻求中位数
        int[] temp =new int[nums1.length + nums2.length];
        int i=0,j=0,m=0;
        while(i< nums1.length && j<nums2.length){
            if(nums1[i]<nums2[j]){
                temp[m++] = nums1[i++];
            }else{
                temp[m++] = nums2[j++];
            }
        }
        while(i < nums1.length){
            temp[m++] = nums1[i++];
        }
        while(j < nums2.length){
            temp[m++] = nums2[j++];
        }
        //合并之后再算两数组的中位数
        if((nums1.length+nums2.length) %2 ==0){
            //如果长度为偶数时
            res= ((double)temp[temp.length/2] +(double)temp[temp.length/2 -1])/2;
        }else{
            //如果为奇数时则直接为中的的数
            res= temp[temp.length/2];
        }
        return res;
    }

    /**
     * 二分法查找有序数组的中位数
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int length1= nums1.length,length2=nums2.length;
        //如果长度为偶数时
        if((length1+length2) %2 == 0){
            return (findKthNumber(nums1,nums2,(length1 +length2)/2) +findKthNumber(nums1,nums2,(length1 +length2)/2 +1))/2.0;
        }else{
            //如果长度为奇数时
            return findKthNumber(nums1,nums2,(length1+length2)/2);
        }
    }
    //寻找第k小的数
    public int findKthNumber(int[] nums1, int[] nums2,int k){
        //表示的时两个数组的查找第k小的数时的开始位置，在递归过程中会进行更新
        int index1=0,index2=0;
        while(true){
            //考虑 边界情况推出循环
            if(index1  >= nums1.length){
                //如果nums1中所有的数均已经排除，则第k小的数就时nums2中的第k-index1+1的数
                return nums2[k -index1 +1];
            }
            if(index2 >= nums2.length){
                //如果nums2中所有的数均已经排除，则第k小的数就时nums1中的第k-index2+1的数
                return nums1[k-index2+1];
            }
            if(k == 1){
                //如果k等于1时，则第k小的数就是nums1[index] 和nums2[index]中更小的数
                return Math.min(nums1[index1],nums2[index2]);
            }
            //正常情况下
            int mid =k/2;
            int newIndex1 = Math.min(index1 + mid -1,nums1.length);
            int newIndex2 = Math.min(index2 + mid -1,nums2.length);

            if(nums1[newIndex1] <= nums2[newIndex2]){
                //如果数组1中的第k/2个数要小于等于数组2中的第k/2 则可以直接排除有数组1中的 k/2 个数
                k -= (newIndex1 -index1 +1);//由于排除了之前的第k/2个数所以现在就是更新为查找第 k- k/2小的数
                index1 =newIndex1 +1;

            }else{
                k -= (newIndex2 -index2 +1);
                index2 =newIndex2 +1;
            }
        }

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}