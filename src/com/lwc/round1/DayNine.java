package com.lwc.round1;

import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class DayNine {
    public static void main(String[] args) {
        DayNine nine = new DayNine();
        //System.out.println(nine.isValid(new StringBuilder("1+2-3*4*5+6+7+89"),45));;
        System.out.println(nine.plusOne2(new int[]{9}));;

    }

    /**
     * 题号：282
     * 难度：困难
     * 时间：20211020
     * 给定一个仅包含数字 0-9 的字符串 num 和一个目标值整数 target ，在 num 的数字之间添加 二元 运算符（不是一元）+、- 或 * ，返回所有能够得到目标值的表达式。
     * <p>
     *  
     * <p>
     * 示例 1:
     * <p>
     * 输入: num = "123", target = 6
     * 输出: ["1+2+3", "1*2*3"]
     * 示例 2:
     * <p>
     * 输入: num = "232", target = 8
     * 输出: ["2*3+2", "2+3*2"]
     * 示例 3:
     * <p>
     * 输入: num = "105", target = 5
     * 输出: ["1*0+5","10-5"]
     * 示例 4:
     * <p>
     * 输入: num = "00", target = 0
     * 输出: ["0+0", "0-0", "0*0"]
     * 示例 5:
     * <p>
     * 输入: num = "3456237490", target = 9191
     * 输出: []
     * 分析：从题目来看该题中属于字符分割形式，且在分割字符处添加一些算术操作使得他最后的值达到目标值，且题目中的
     * 不存在分割两次零的形式
     * 解题：
     */

    public List<String> addOperators(String num, int target) {
        List<String> list = new ArrayList<>();
        backTrack(new StringBuilder(num), 1, target, list);
        return list;
    }
    //回溯算法，利用回溯算法遍历所有的可能组合

    /**
     * @param num 待分割的字符串
     * @param i   表示分割字符穿的起始位置
     *            在每个要分割的字符周围
     */
    public void backTrack(StringBuilder num, int i, int target, List<String> lst) {
        String[] signal = new String[]{"*", "-", "+"};
        //递归终止条件,且当切割位置达到字符长度时对该字符串进行计算
        if (i >= num.length()) {
            if (isValid(num, target)) {
                lst.add(num.toString());
            }
            return;
        }
        //字符划分应当可以划分到字符长度的后一位，同时要避免对零开头数字的划分
        for (int k = i; k <= num.length() && (k == i || num.charAt(i-1) != '0'); k++) {

            //为字符串添加符号
            for (String str : signal) {
                //给该字符串添加相对应的运算符，只有当k的值在最后一个字符的前面时才可以添加
                if(k<num.length()){
                    num.insert(k, str);
                }
                backTrack(num, k + 2, target, lst);
                //撤销之前的操作，进行回溯
                if(k< num.length()) {
                    num.deleteCharAt(k);
                }
                //如果达到符号长度时则直接退出循环防止重复
                if(k == num.length()){
                     break;
                }
            }
        }
    }

    //判断字符串最后添加的结果是否符合目标值并且进行计算
    public boolean isValid(StringBuilder result, int target) {
        //字符数组用于当作栈
        long[] stack = new long[10];
        //符号的栈
        String[] single = new String[10];
        int pos2 = 0;
        //表示当前栈中元素的位置
        int pos = 0;
        int j = 0;
        int i = 1;
        for (; i < result.length(); i++) {

            if (result.charAt(i) == '*') {
                String str = result.substring(j, i);
                j = i + 1;
                long temp = Long.parseLong(str);
                //如果不存在运算符或者运算符不为乘法时乘法入栈
                if (pos2 == 0 || single[pos2 - 1] != "*") {
                    //数值入栈
                    stack[pos++] = temp;
                    //乘号入栈
                    single[pos2++] = "*";
                } else {
                    //如果里面存在乘法运算符则进行计算乘法，因为如栈的还是乘法符号所以就不需要进行出栈
                    long temp2 = stack[--pos] * temp;
                    stack[pos++] = temp2;
                }
            }
            if (result.charAt(i) == '-') {
                String str = result.substring(j, i);
                j = i + 1;
                stack[pos++] = Integer.parseInt(str);

                //只有当符号栈中为空时减号才可以入栈
                if (pos2 == 0) {
                    //减号入栈
                    single[pos2++] = "-";
                } else {
                    while (pos2 > 0) {
                        //如果栈中有其他符号时则先计算其他运算符在入栈
                        if (single[pos2 - 1] == "+") {
                            //计算完结果入栈
                            long m = stack[--pos];
                            long n = stack[--pos];
                            //符号出栈
                            pos2--;
                            stack[pos++] = m + n;
                        } else if (single[pos2 - 1] == "-") {
                            //计算完结果入栈
                            long m = stack[--pos];
                            long n = stack[--pos];
                            long temp2 = n - m;
                            //符号出栈
                            pos2--;
                            stack[pos++] = temp2;
                        } else if (single[pos2 - 1] == "*") {
                            long m = stack[--pos];
                            long n = stack[--pos];
                            //符号出栈
                            pos2--;
                            stack[pos++] = m * n;
                        }
                    }
                    single[pos2++] = "-";
                }
            }
            if (result.charAt(i) == '+') {
                String str = result.substring(j, i);
                j = i + 1;
                stack[pos++] = Long.parseLong(str);
                //只有当符号栈中为空时减号才可以入栈
                if (pos2 == 0) {

                    //减号入栈
                    single[pos2++] = "+";
                } else {
                    while (pos2 > 0) {
                        //如果栈中有其他符号时则先计算其他运算符在入栈
                        if (single[pos2 - 1] == "+") {
                            //计算完结果入栈
                            long m = stack[--pos];
                            long n = stack[--pos];
                            //符号出栈
                            pos2--;
                            stack[pos++] = m + n;


                        } else if (single[pos2 - 1] == "-") {
                            //计算完结果入栈
                            long m = stack[--pos];
                            long n = stack[--pos];
                            //符号出栈
                            pos2--;
                            stack[pos++] = n - m;
                        } else if (single[pos2 - 1] == "*") {
                            long m = stack[--pos];
                            long n = stack[--pos];
                            //符号出栈
                            pos2--;
                            stack[pos++] = m * n;
                        }
                    }
                    single[pos2++] = "+";
                }
            }
        }
        //最后一个数字入栈
        stack[pos++] = Long.parseLong(result.substring(j, i));

        //计算栈中的数值
        while (pos != 0 && pos2 != 0) {
            if (single[(pos2 - 1)] == "-") {
                long m = stack[--pos];
                long n = stack[--pos];
                stack[pos++] = n - m;
            } else if (single[(pos2 - 1)] == "*") {
                long m = stack[--pos];
                long n = stack[--pos];
                stack[pos++] = m * n;
            } else if (single[(pos2 - 1)] == "+") {
                long m = stack[--pos];
                long n = stack[--pos];
                stack[pos++] = m + n;
            }
            pos2--;
        }
        if (stack[0] == target) {
            return true;
        }
        return false;
    }


    int n;
    String num;
    int target;
    List<String> ans;

    public List<String> addOperators2(String num, int target) {
        this.n = num.length();
        this.num = num;
        this.target = target;
        this.ans = new ArrayList<String>();
        StringBuffer expr = new StringBuffer();
        backtrack(expr, 0, 0, 0);
        return ans;
    }

    public void backtrack(StringBuffer expr, int i, long res, long mul) {
        if (i == n) {
            if (res == target) {
                ans.add(expr.toString());
            }
            return;
        }
        int signIndex = expr.length();
        if (i > 0) {
            expr.append(0); // 占位，下面填充符号
        }
        long val = 0;
        // 枚举截取的数字长度（取多少位），注意数字可以是单个 0 但不能有前导零
        for (int j = i; j < n && (j == i || num.charAt(i) != '0'); ++j) {
            val = val * 10 + num.charAt(j) - '0';
            expr.append(num.charAt(j));
            if (i == 0) { // 表达式开头不能添加符号
                backtrack(expr, j + 1, val, val);
            } else { // 枚举符号
                expr.setCharAt(signIndex, '+');
                backtrack(expr, j + 1, res + val, val);
                expr.setCharAt(signIndex, '-');
                backtrack(expr, j + 1, res - val, -val);
                expr.setCharAt(signIndex, '*');
                backtrack(expr, j + 1, res - mul + mul * val, mul * val);
            }
        }
        expr.setLength(signIndex);
    }

    /**
     * 难度：简单
     * 题号：66
     * 时间：21211021
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     *
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     *
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     *分析：也就是输入数组时是表示一个数在数组的末尾加一，但是要考虑连续进位的问题
     * 解题：可以考虑将整个数字解析出来然后对数进行加一，也可以对末尾数字加一，但是要考虑进位问题
     * ,不能将数字进行解析出来然后进行处理，数组长度最高为100没有在数字进行存储
     *
     */
    public int[] plusOne(int[] digits) {
        long digit=0;
        //将数组解析成数字
        for(int i=0; i < digits.length;i++){
            //这个算是10的次幂要乘几个10
            int temp =digits.length-i-1;
            digit=digit+digits[i] * (long)Math.pow(10,temp);
        }
        digit=digit+1;
        //如果产生进位的话就需要对数字增加长度，否则就直接使用原数组即可
        if(digit/Math.pow(10,digits.length) >= 1){
            digits=new int[digits.length+1];
        }
        for(int i=0 ;i<digits.length;i++){
            int temp =digits.length-i-1;
            digits[i]=(int)(digit/Math.pow(10,temp));
            digit=digit-digits[i]*(long)Math.pow(10,temp);
        }

        return digits;
    }
    public int[] plusOne2(int[] digits) {
        //要考虑连续进位的问题，可以考虑直接采用n+1长度的数组接收最后判断第一位是否为零进行截取即可
        int[] digits2 =new int[digits.length+1];
        int up=1;
        for(int i=digits2.length-1;i>0;i--){
            //如果当前位置上的数字加一产生进位时则,当前位置上赋值为零，进位符保持为1,由于之前的进位符无法处理到零的位置
            //所以最后一位需要进行额外的处理
           if(digits[i-1]+up == 10){
               digits2[i]=0;
           }else{
               //如果没有产生进位时
               digits2[i]=digits[i-1]+up;
               up=0;
           }
        }
        //处理最后一位进位
        if(up != 0){
            digits2[0]=up;
        }
        //如果最后没有产生数组长度加一时则对数组进行重新赋值
        if(digits2[0] == 0){
            for(int i=0;i<digits.length ;i++){
                digits[i] =digits2[i+1];
            }
            return digits;
        }
       return digits2;
    }

    /**
     * 题号：211
     * 难度：中等
     * 时间：20211021
     * 211. 添加与搜索单词 - 数据结构设计
     * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
     *
     * 实现词典类 WordDictionary ：
     *
     * WordDictionary() 初始化词典对象
     * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
     * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
     */
    public DayNine() {

    }

    public void addWord(String word) {

    }

    public boolean search(String word) {
        return false;
    }
}
