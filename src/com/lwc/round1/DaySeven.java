package com.lwc.round1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaySeven {
    public static void main(String[] args) {
        DaySeven seven=new DaySeven();

        System.out.println( seven.divide(-2147483648,-1));
    }
    /**
     * 题号：187
     * 难度：中等
     * 所有 DNA 都由一系列缩写为 'A'，'C'，'G' 和 'T' 的核苷酸组成，例如："ACGAATTCCG"。在研究 DNA 时，识别 DNA 中的重复序列有时会对研究非常有帮助。
     *
     * 编写一个函数来找出所有目标子串，目标子串的长度为 10，且在 DNA 字符串 s 中出现次数超过一次。
     *分析：本题要求找到出现一次以上的所有字串，且该字符长度为10字符中出现的字母只有可能是'A'、'C'、'G' 或 'T'
     * 解题：可以设置两个指针两个指针分别对这两个字符进行比较，如果碰到字符不相同的位置则将指针进行偏移
     * 第二个指针向后偏移一位，直到指针后最后九位字符时第一个指针向右偏移一位同样第二个指针到最后九位停止偏移，当字符中10位都相同时则将
     * 该字符记录下来，并且对第一个指针进行偏移，可知其时间复杂度应当为o(n^2)
     */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res=new ArrayList<>();
        Map<String,Integer> map=new HashMap();
        if(s.length()<10){
            return res;
        }
        int left=0,right=1;
        while(left<s.length()-9){
            int tLeft=left;
            int length=0;
            while(right<s.length()){
                if(s.charAt(tLeft) == s.charAt(right)){
                    //如果指针所指的字符相同时则左右指针跟其所对应的长度均加一
                    right++;
                    tLeft++;
                    length++;
                    if(length == 10){
                        //如果匹配的字符长度为10的话则将该字符保留
                       String temp= s.substring(tLeft-length,tLeft);
                       map.put(temp,map.getOrDefault(temp,0)+1);
                       //将字符串保留之后对字符串进行下一轮扫描
                       break;
                    }
                }else{
                    //如果匹配到字符串不相等时，left指针回归原来的位置，right指针往后退一位重新开始进行扫描
                    tLeft=left;
                    right=right-length+1;
                    length=0;
                }
            }
            //当扫描完第一轮时重新开始扫描
            left++;
            right=left+1;
        }
        for(Map.Entry<String,Integer> entry : map.entrySet()){
           res.add( entry.getKey());
        }
        return res;
    }
    /**
     * 难度：中等
     * 题号：29
     * 日期 ：20211012
     * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
     *
     * 返回被除数 dividend 除以除数 divisor 得到的商。
     *
     * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
     *

     */
    /**
     * 利用位运算进行加法
     * @param a
     * @param b
     * @return
     */
    public int  add(int a,int b){
        int c,d;
        while(b != 0){
            //二进制加法进位相当于两数
            c= ((a & b)<<1);
            //a与b之间的二进制加法相当于异或操作 相同为零相异为一
            d= a^b;
            a=d;
            b=c;
        }
        return a;
    }

    //加法按照二进制的减法运算，【a-b】补= [a]补 +[-b]补，算负数的补码是按位取反再加一
    public int subtraction(int a,int b){
        return add(a,negtive(b));
    }
    int negtive(int i){
        i= (~i);
        return add(i,1);
    }

    //利用位运算计算乘法
    int multiply(int a, int b){
        boolean flag= true;
        if((getsign(a) == getsign(b)) ){
            flag=false;
        }
        a =bepositive(a);
        b =bepositive(b);
        int ans=0;
        while(b != 0){
            //加数跟1按位与获得最后一位是1，还是0，从而判断是否相加
            if((b & 1) == 1 ){
                ans= add(ans,a);
            }
            //被乘数向左移一位进行累加
            a=(a<<1);
            //乘数向右移一位，保证获取的时最后一位的数
            b=(b>>1);
        }
        //判断符号位
        if(flag){
            ans=negtive(ans);
        }
        return ans;
    }
    //再做乘法之前首先需要对符号进行判断，当乘数为负时需要转变为其补码然后进行计算
    int bepositive(int i){
        if(getsign(i) == 1){
           int k= negtive(i);
           return k;
        }else{
            return i;
        }
    }
    int getsign(int i){
        i= (i>>>31);
        return i;
    }
    //利用位运算符做除法，除法相当于做减法判断减的次数即可
    public int divide(int dividend, int divisor) {
        // 考虑被除数为最小值的情况
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }

        //首先判断符号
        boolean flag =true;
        if(getsign(dividend) == getsign( divisor)){
            flag =false;
        }
        int x = bepositive(dividend);
        int y =bepositive(divisor);
        int i= 31;
        int ans =0;
        //除法从x能减y的最大倍数开始减，这样能大大减少做减法的次数
        while(i >= 0){
            //判断y<<i是否够减
            if((x>>i) >= y){
                //这里加的应该是多少倍的i
                ans =add(ans,1<<i);
                x=subtraction(x,(y<<i));
            }
            i=subtraction(i,1);
        }
        //判断符号
        if(flag){
            ans=negtive(ans);
        }
        return ans;
    }

    /**
     * 难度：简单
     * 题号：412
     * 日期：20211014
     * 给你一个整数 n ，找出从 1 到 n 各个整数的 Fizz Buzz 表示，并用字符串数组 answer（下标从 1 开始）返回结果，其中：
     *
     * answer[i] == "FizzBuzz" 如果 i 同时是 3 和 5 的倍数。
     * answer[i] == "Fizz" 如果 i 是 3 的倍数。
     * answer[i] == "Buzz" 如果 i 是 5 的倍数。
     * answer[i] == i 如果上述条件全不满足。
     */
    public List<String> fizzBuzz(int n) {
        List<String> lst=new ArrayList<>();
        for(int i= 1 ;i <= n ;i++){
            if(i % 3 ==0 && i % 5 == 0){
                lst.add( "FizzBuzz");
            }
            if(i % 3 == 0 && i % 5 != 0 ){
                lst.add("Fizz");
            }
            if(i % 3 !=0 && i % 5 ==0){
                lst.add("Buzz");
            }
            if(i % 3 !=0 && i % 5 != 0){
                lst.add(i+"");
            }
        }
        return lst;
    }

}
