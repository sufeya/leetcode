package com.lwc.round5;

import javafx.util.Pair;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/9/30 9:51
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayFour {

    public String removeDuplicates(String s) {
        Stack<Pair<Character,Integer>> stack = new Stack<>();
        for(int i =0;i<s.length();i++){
            char c = s.charAt(i);
            if(!stack.isEmpty()){
                Pair<Character,Integer> pair = stack.peek();
                if(pair.getKey() == c){
                    if( pair.getValue() == 1){
                        while(!stack.isEmpty() && stack.peek().getKey() == c){
                            stack.pop();
                        }
                    }else{
                        stack.push(new Pair<>(pair.getKey(),pair.getValue()+1));
                    }
                    continue;
                }
            }

            stack.push(new Pair(c,1));
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.insert(0,stack.pop().getKey());
        }
        return sb.toString();
    }
    /**
     * 题号：01.08
     *编写一种算法，若M × N矩阵中某个元素为0，则将其所在的行与列清零。
     */
    public void setZeroes(int[][] matrix) {
        Set<Integer> col = new HashSet<>();
        Set<Integer> row = new HashSet<>();
        int m = matrix.length,n=matrix[0].length;
        for(int i = 0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j] == 0){
                    col.add(j);
                    row.add(i);
                }
            }
        }

        for(int i =0;i<m;i++){
            if(row.contains(i)){
                for(int j = 0;j<n;j++){
                    matrix[i][j] = 0;
                }
            }else{
               Iterator<Integer> it = col.iterator();
               while(it.hasNext()){
                   matrix[i][it.next()] = 0;
               }
            }

        }
    }

    /**
     * 题号：91
     * 难度：中等
     * 时间：20220930
     * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
     *
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
     *
     * "AAJF" ，将消息分组为 (1 1 10 6)
     * "KJF" ，将消息分组为 (11 10 6)
     * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
     *
     * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
     *
     * 题目数据保证答案肯定是一个 32 位 的整数。
     */
    public int numDecodings(String s) {

        int len = s.length();
        int[] dp = new int[len];
        if(s.charAt(0) != '0'){
            dp[0] = 1;
        }

        for(int i = 1;i<len;i++){
            char c = s.charAt(i);
            if(c != '0'){
                dp[i] = dp[i-1];
            }

            StringBuffer sb = new StringBuffer();
            sb.append(s.charAt(i-1)).append(s.charAt(i));
            int val = Integer.parseInt(sb.toString());
            if(val<27 && val >9 ){
                if(i-2<0){
                    dp[i]++;
                }else{
                    dp[i]+=dp[i-2];
                }
            }
        }
        return dp[len-1];
    }


    public boolean equalFrequency(String word) {
        Map<Character,Integer> map = new HashMap<>();
        int len = word.length();
        for(int j = 0;j<len;j++){
            char c = word.charAt(j);
            map.put(c,map.getOrDefault(c,0)+1);
        }
        Map<Integer,Integer> res = new HashMap<>();
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            //字符出现的次数 ， 出现次数相同字符的个数
           res.put(entry.getValue(),res.getOrDefault(entry.getValue(),0)+1);
        }

        List<int[]> list = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry : res.entrySet()){
            list.add(new int[]{entry.getKey(),entry.getValue()});
        }

        if(res.size()>2){
            return false;
        }else if(res.size() == 1 ){
            if(list.get(0)[0] == 1){
                return true;
            }else{
                return false;
            }
        }
        Collections.sort(list,(a,b)-> a[0]-b[0]);
        int i = list.get(0)[0],j = list.get(0)[1],m = list.get(1)[0],n = list.get(1)[1];
        if((i == 1 && j == 1) ){
            return true;
        }
        if( m-i>1 ||(Math.abs(m-i) == 1 && (Math.abs(j-n)>1 &&(j!=1 || n !=1)) ) ){
            return false;
        }
        return true;
    }

    //哎我太菜了，感觉一点都不会思考，不知道该如何下手这道题，我只知道这个序列肯定是
    //改变前后的序列的相对为止是一样的
    public boolean canTransform(String start, String end) {
        int i=0,j =0,len = start.length();
        while(i<len && j <len){
            while(i<len && start.charAt(i) == 'X'){
                i++;
            }
            while(j<len && end.charAt(j) =='X'){
                j++;
            }
            if(i<len && j<len){
                if(start.charAt(i) != end.charAt(j)){
                    return false;
                }
                if((start.charAt(i) =='L' && i>=j)||(start.charAt(i)=='R' && j>=i)){
                    i++;
                    j++;
                }else{
                    return false;
                }
            }

        }
        while(i<len){
            if(start.charAt(i) !='X'){
                return false;
            }
            i++;
        }
        while(j<len){
            if(end.charAt(j) !='X'){
                return false;
            }
            j++;
        }
        return true;
    }

    public boolean checkOnesSegment(String s) {
        int i =0 ,j =0,len = s.length();
        while(i<len && j<len){

           if(s.charAt(j) == '0' && (j-i)>1){
              i=j;
           }else if(s.charAt(j) == '0' && i==j){
               i++;
               j++;
           } else if(s.charAt(j) == '0' && (j-i)<=1){
              return false;
           } else if(s.charAt(j) == '1'){
               j++;
           }
        }
        return true;
    }
    public static void main(String[] args) {
        DayFour four = new DayFour();
        four.minAddToMakeValid( "(((");
    }
    public int minAddToMakeValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i =0;i<s.length();i++){
            char c = s.charAt(i);
            if(c == '('){
                stack.push('(');
            }
            if(c == ')'){
                if(!stack.isEmpty() && stack.peek() == '('){
                    stack.pop();
                }else{
                    stack.push(')');
                }
            }
        }
        return stack.size();
    }
}
