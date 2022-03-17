package com.lwc.round2;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DayOne {
    public static void main(String[] args) {
        DayOne one=new DayOne();
        System.out.println(one.validTicTacToe(new String[]{"XXO","XOX","OXO"}));

    }
    /**
     * 时间：20211205
     * 题号：372
     * 难度：中等
     * 你的任务是计算 ab 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：a = 2, b = [3]
     * 输出：8
     * 示例 2：
     *
     * 输入：a = 2, b = [1,0]
     * 输出：1024
     * 示例 3：
     *
     * 输入：a = 1, b = [4,3,3,8,5,2]
     * 输出：1
     * 示例 4：
     *
     * 输入：a = 2147483647, b = [2,0,0]
     * 输出：1198
     *分析：该题自己写不会写,看了答案后知道了应当转化为子问题进行迭代解决，分治法的关键是如何将原先的问题降低为更低层次的
     * 子问题然后对该问题进行求解
     * 解题：
     */
    public int superPow(int a, int[] b) {
        return dfs(a,b.length-1,b);
    }
    public int dfs(int a,int b,int[] k){
        if(b == -1){
            return 1;
        }
        return (pow(dfs(a,b-1,k),10)*pow(a,k[b]))%1337;
    }
    //计算指数幂
    public int  pow(int a ,int b){
        //防止a溢出
         a = a%1337;
         int res =1;
         while(b>0){
             res =a*res%1337;
         }
         return res;
    }
    /**
     * 题号：1816
     * 难度：简单
     * 时间：20211206
     * 句子 是一个单词列表，列表中的单词之间用单个空格隔开，且不存在前导或尾随空格。每个单词仅由大小写英文字母组成（不含标点符号）。
     *
     * 例如，"Hello World"、"HELLO" 和 "hello world hello world" 都是句子。
     * 给你一个句子 s​​​​​​ 和一个整数 k​​​​​​ ，请你将 s​​ 截断 ​，​​​使截断后的句子仅含 前 k​​​​​​ 个单词。返回 截断 s​​​​​​ 后得到的句子。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "Hello how are you Contestant", k = 4
     * 输出："Hello how are you"
     * 解释：
     * s 中的单词为 ["Hello", "how" "are", "you", "Contestant"]
     * 前 4 个单词为 ["Hello", "how", "are", "you"]
     * 因此，应当返回 "Hello how are you"
     * 示例 2：
     *
     * 输入：s = "What is the solution to this problem", k = 4
     * 输出："What is the solution"
     * 解释：
     * s 中的单词为 ["What", "is" "the", "solution", "to", "this", "problem"]
     * 前 4 个单词为 ["What", "is", "the", "solution"]
     * 因此，应当返回 "What is the solution"
     * 示例 3：
     *
     * 输入：s = "chopper is not a tanuki", k = 5
     * 输出："chopper is not a tanuki"
     *分析：可以考虑将字符串按照空格分割成字符数组，然后将前k个数组元素再拼成一个句子
     * 便可以
     *解题：
     */
    public String truncateSentence(String s, int k) {
        //空串和无字符时仅返回空
        if( s == null || s.equals("")){
            return "";
        }
        //将字符串分割成字符数组
        String[] str =s.split(" ");
        //如果分割的长度小于k的值的话就直接返回原字符串即可
        if(str.length <= k){
            return s;
        }
        String res = "";
        for(int i=0 ;i<k;i++){
            res += str[i]+" ";
        }
        //除去字符串末尾的最后一个空格字符
        res =res.substring(0,res.length()-1);
        return res;
    }

    /**
     * 题号：794
     * 难度：中等
     * 时间：20211209
     * 给你一个字符串数组 board 表示井字游戏的棋盘。当且仅当在井字游戏过程中，棋盘有可能达到 board 所显示的状态时，才返回 true 。
     *
     * 井字游戏的棋盘是一个 3 x 3 数组，由字符 ' '，'X' 和 'O' 组成。字符 ' ' 代表一个空位。
     *
     * 以下是井字游戏的规则：
     *
     * 玩家轮流将字符放入空位（' '）中。
     * 玩家 1 总是放字符 'X' ，而玩家 2 总是放字符 'O' 。
     * 'X' 和 'O' 只允许放置在空位中，不允许对已放有字符的位置进行填充。
     * 当有 3 个相同（且非空）的字符填充任何行、列或对角线时，游戏结束。
     * 当所有位置非空时，也算为游戏结束。
     * 如果游戏结束，玩家不允许再放置字符。
     * 分析：这个borad不一定是结果，可能是过程种的某一个状态，但是x的个数一定大于或者等于o的个数相差不能超过一
     * 解题:
     */
    public boolean validTicTacToe(String[] board) {
        char[][] chars =new char[3][3];
        int countO = 0,countX=0,i=0;
        for(String str :board){
            chars[i]=str.toCharArray();
            for(char c :chars[i]){
                if(c == 'X'){
                    countX++;
                }else if(c == 'O'){
                    countO++;
                }
            }
            i++;
        }
        if(countX - countO == 0){
            if(countO <=2){
                return true;
            }else{
                //相同的话那就可能是x和o均没有连成一个线，或者o连成了一根线,x没有连成一根线，其他的均错
                if(!isLine(chars,'X') && !isLine(chars,'O')){
                    return true;
                }
                if(isLine(chars,'O') && !isLine(chars,'X')){
                    return true;
                }
            }
        }else if(countX- countO ==1){
            //如果相差为一的话那么,小于等于三的结果均可计算其中
            if(countX <= 3){
                return true;
            }else{
                //大于三的话那么o一定没有连成一根线
                if(!isLine(chars,'O')){
                    return true;
                }
            }

        }
        return false;
    }
    //判断字符c是否可以连成一条线
    public boolean isLine(char[][] borad ,char c){
        boolean res =false;
        int time =0;
        //三条横线
        for(int j =0 ;j<3 ;j++){
            for(int i=0;i<3 ;i++){
                if(borad[j][i] == c){
                    time++;
                }
            }
            if(time == 3){
                return true;
            }
            time = 0;

            //三条竖线
            for(int i=0;i<3 ;i++){
                if(borad[i][j] == c){
                    time++;
                }
            }
            if(time == 3){
                return true;
            }
            time = 0;
        }
        //两条对角线
        time=0;
        for(int i=0 ;i<3 ;i++){
            if(borad[i][i] == c){
                time++;
            }
        }
        if(time == 3){
            return true;
        }
        //斜对角线
        if(borad[0][2] == c && borad[1][1] == c && borad[2][0] == c){
            return true;
        }

        return false;
    }
    /**
     * 题号：748
     * 难度：简单
     * 时间：20211210
     * 给你一个字符串 licensePlate 和一个字符串数组 words ，请你找出并返回 words 中的 最短补全词 。
     *
     * 补全词 是一个包含 licensePlate 中所有的字母的单词。在所有补全词中，最短的那个就是 最短补全词 。
     *
     * 在匹配 licensePlate 中的字母时：
     *
     * 忽略 licensePlate 中的 数字和空格 。
     * 不区分大小写。
     * 如果某个字母在 licensePlate 中出现不止一次，那么该字母在补全词中的出现次数应当一致或者更多。
     * 例如：licensePlate = "aBc 12c"，那么它的补全词应当包含字母 'a'、'b' （忽略大写）和两个 'c' 。可能的 补全词 有 "abccdef"、"caaacab" 以及 "cbca" 。
     *
     * 请你找出并返回 words 中的 最短补全词 。题目数据保证一定存在一个最短补全词。当有多个单词都符合最短补全词的匹配条件时取 words 中 最靠前的 那个。
     * 分析：应当使用hash表对licensePlate中出现的字符进行计数，然后再分别对word中的所有单词中字符出现的次数进行计数，在比较包含的同时
     * 同时记录长度最小的那个单词
     * 解题：
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int len =licensePlate.length();
        //将字符串中的所有字符转话为小写
        licensePlate =licensePlate.toLowerCase();
        Map<String,Integer> stringCount=new HashMap<>();

        //对licensePlate中的字符进行计数
        for(int i = 0;i<len;i++){
            char c =licensePlate.charAt(i);
            //只考虑字母不考虑其他的字符
            if(c>='a' && c<='z'){
                Integer k =stringCount.getOrDefault(c+"",0)+1;
                stringCount.put(c+"",k);
            }
        }
        int minLength =99999;
        String result ="";
        for(String str : words){
            boolean content =false;
            int lens=0;
            Map<String, Integer> temp =new HashMap<>();
            for(int i=0 ;i<str.length() ;i++){
                lens++;
                char c =str.charAt(i);
                int k =temp.getOrDefault(c+"",0)+1;
                temp.put(c+"",k);
            }
            //这里要从给定字符串的角度进行搜索
            for(Map.Entry<String,Integer> set :stringCount.entrySet()){
                Integer s=temp.get(set.getKey());
                if(s == null || s < set.getValue()){
                    content =true;
                    break;
                }
            }
            //如果这个字符不符合基本要求则进行舍弃
            if(content){
                continue;
            }
            //如果符合基本要求则保存该字符串
            if(lens<minLength){
                minLength =lens;
                result=str;
            }
        }
        return result;

    }

}
