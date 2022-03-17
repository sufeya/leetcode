package com.lwc.round3;

import java.util.*;

public class DayOne {
    /**
     * 题号：2044
     * 难度：中等
     * 时间：20220315
     * 给你一个整数数组 nums ，请你找出 nums 子集 按位或 可能得到的 最大值 ，并返回按位或能得到最大值的 不同非空子集的数目 。
     * 如果数组 a 可以由数组 b 删除一些元素（或不删除）得到，则认为数组 a 是数组 b 的一个 子集 。如果选中的元素下标位置不一样，则认为两个子集 不同 。
     * 对数组 a 执行 按位或 ，结果等于 a[0] OR a[1] OR ... OR a[a.length - 1]（下标从 0 开始）。
     */
    static int res =0;
    public int countMaxOrSubsets(int[] nums) {
        //最大的值是整个数组的或
        int maxVal=nums[0];
        for(int i :nums){
            maxVal |= i;
        }
        getBackVal(maxVal,nums,0,nums[0]);
        return res;
    }
    //利用回溯法求子集，回溯的实质是枚举所有可能的情况进行计算，然后在枚举的过程中进行剪枝
    public void getBackVal(int maxVal, int[] nums, int begin,int current){
        int len = nums.length;
        if(begin<len){
            if(maxVal == current){
                res++;
            }
            getBackVal(maxVal,nums,begin+1,current);
            current |= nums[begin];
            getBackVal(maxVal,nums,begin+1,(current));
        }
    }
    int[] nums;
    int maxOr, cnt;

    public int countMaxOrSubsets2(int[] nums) {
        this.nums = nums;
        this.maxOr = 0;
        this.cnt = 0;
        dfs(0, 0);
        return cnt;
    }

    public void dfs(int pos, int orVal) {
        if (pos == nums.length) {
            if (orVal > maxOr) {
                maxOr = orVal;
                cnt = 1;
            } else if (orVal == maxOr) {
                cnt++;
            }
            return;
        }
        dfs(pos + 1, orVal | nums[pos]);
        dfs(pos + 1, orVal);
    }

    /**
     * 题号：720
     * 难度：简单
     * 时间：20220317
     * 给出一个字符串数组 words 组成的一本英语词典。返回 words 中最长的一个单词，该单词是由 words 词典中其他单词逐步添加一个字母组成。
     *
     * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
     */
    public String longestWord(String[] words) {
        return null;
    }




    public static void main(String[] args) {
        DayOne one=new DayOne();
        AllOne allOne = new AllOne();
        allOne.inc("a");
        allOne.inc("b");
        allOne.inc("c");
        allOne.inc("d");
        allOne.inc("a");
        allOne.inc("b");

        allOne.inc("c");
        allOne.inc("d");
        allOne.inc("c");
        allOne.inc("d");
        allOne.inc("a");

        System.out.println(allOne.getMinKey() );
    }
}
/**
 * 请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
 * 实现 AllOne 类：
 * AllOne() 初始化数据结构的对象。
 * inc(String key) 字符串 key 的计数增加 1 。如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
 * dec(String key) 字符串 key 的计数减少 1 。如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。测试用例保证：在减少计数前，key 存在于数据结构中。
 * getMaxKey() 返回任意一个计数最大的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * getMinKey() 返回任意一个计数最小的字符串。如果没有元素存在，返回一个空字符串 "" 。
 */
class AllOne {
    Map<String,Integer> list;
    String max_tag;
    String min_tag;
    public AllOne() {
        list = new HashMap<>();
        max_tag ="";
        min_tag ="";
    }

    public void inc(String key) {
        //获取原先的索引并且加一
        int index = list.getOrDefault(key,0)+1;
        //存入hash表中
        list.put(key,index);
        int max_index = list.getOrDefault(max_tag,-1);
        int min_index = list.getOrDefault(min_tag,-1);

        if(max_index == -1 || (max_index !=-1 && max_index<index)){
            max_tag = key;
        }
        if(min_index == -1 || (min_index !=-1 && min_index>index)){
            min_tag = key;
        }else if(key.equals(min_tag)){
            //如果当前的最小键增加了，则需要重新寻找最小的键
            min_tag=getMaxAndMin()[1];
        }


    }
    public void dec(String key) {
        //获取原先的索引
        int index = list.getOrDefault(key,0)-1;
        int max_index = list.getOrDefault(max_tag,-1);
        int min_index = list.getOrDefault(min_tag,-1);
        //判断index是否为零
        if(index == 0 ){
            list.remove(key);
            //如果移除的是,最大的或者最小的
            if(min_tag.equals(key)  || max_tag.equals(key)){
                String[] temp =getMaxAndMin();
                max_tag=temp[0];
                min_tag=temp[1];
            }
        }else{
            list.put(key,index);
            //更新最大和最小的
            if(max_index == -1 || (max_index !=-1 && max_index<index)){
                max_tag = key;
            }else if(max_tag.equals(key) ){
                //如果减少的键值是最大的那个键，减少之后要重新获取最大的值
                max_tag = getMaxAndMin()[0];
            }
            if(min_index == -1 || (min_index !=-1 && min_index>index)){
                min_tag = key;
            }
        }
    }
    public String getMaxKey() {

        return  max_tag;
    }

    public String getMinKey() {
        return min_tag;
    }
    public String[] getMaxAndMin(){
        int min = Integer.MAX_VALUE;
        String max_tag ="";
        int max = Integer.MIN_VALUE;
        String min_tag ="";
        for(Map.Entry<String,Integer> entry :list.entrySet()){
            int tempKey = entry.getValue();
            String tempTag = entry.getKey();
            if(min>tempKey){
                min = tempKey;
                min_tag = tempTag;
            }
            if (max < tempKey){
                max = tempKey;
                max_tag = tempTag;
            }
        }
        return new String[]{max_tag,min_tag};
    }
}
//题解的时间复杂度为o(1)的解法
class AllOne2 {
    public AllOne2() {

    }
    public void inc(String key) {


    }
    public void dec(String key) {


    }
    public String getMaxKey() {

        return "";
    }

    public String getMinKey() {
        return "";
    }
    class Node{
        Set<String> keys;
        Node pre;
        Node next;
        int count;
        public Node(String key,int count){
            keys =new HashSet<>();
            keys.add(key);
            this.count = count;
        }
        //将给定的节点插入
        public void insert(Node node){
            node.pre=this;
            node.next=this.next;
            this.next=node;
        }
        //移除节点
        public void remove(){
            this.pre.next=this.next;
            this.next.pre=this.pre;
        }
    }
}