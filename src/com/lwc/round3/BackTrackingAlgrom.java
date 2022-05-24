package com.lwc.round3;

import java.util.*;

public class BackTrackingAlgrom {
    public static void main(String[] args) {
        BackTrackingAlgrom bta=new BackTrackingAlgrom();
        System.out.println(bta.letterCombinations(""));
    }
    /**
     * 题号：17
     * 难度：中等
     * 时间：20220328
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     */
    public List<String> letterCombinations(String digits) {
        Map<Character,List<Character>> map = new HashMap<>();
        map.put('2',new ArrayList<Character>(Arrays.asList(new Character[]{'a','b','c'})));
        map.put('3',new ArrayList<Character>(Arrays.asList(new Character[]{'d','e','f'})));
        map.put('4',new ArrayList<Character>(Arrays.asList(new Character[]{'g','h','i'})));
        map.put('5',new ArrayList<Character>(Arrays.asList(new Character[]{'j','k','l'})));
        map.put('6',new ArrayList<Character>(Arrays.asList(new Character[]{'m','o','n'})));
        map.put('7',new ArrayList<Character>(Arrays.asList(new Character[]{'p','q','r','s'})));
        map.put('8',new ArrayList<Character>(Arrays.asList(new Character[]{'t','u','v'})));
        map.put('9',new ArrayList<Character>(Arrays.asList(new Character[]{'w','x','y','z'})));
        List<String> res = new ArrayList<>();
        if(digits.length()>0){
            backTrack(0,map,res,digits.length(),new StringBuffer(),digits);
        }
        return res;
    }
    public void backTrack(int i, Map<Character,List<Character>> map,List<String> res,int len
    ,StringBuffer sb,String diag){
        //如果i小于字符串长度时说明遍历还没有到底可以接着往下一层进行遍历
        if(i<len){
            //先获取
            List<Character> temp = map.get(diag.charAt(i));
            for(Character c : temp){
                sb.append(c);
                backTrack(i+1,map,res,len,sb,diag);
                //回到之前的状态
                sb.delete(sb.length()-1,sb.length());
            }
        }else if(i==len){
            //i已经到达最底层了则说明可以直接拼上去
            res.add(sb.toString());
        }
    }
    /**
     * 题号:51
     * 难度：困难
     * 时间：20220412
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     *
     * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     * 分析：n皇后问题指的是其中q同一行同一列不能拥有一个q解决对应的问题，即可，可以采用回溯法进行枚举判断
     * 可以按照顺序每一行先确定一个皇后所在的位置，然后在确定其他的位置进行判断即可
     */
    static List<List<String>> ans =new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        StringBuffer sb = new StringBuffer();
        //初始化棋盘
        for(int i =0 ;i<n ;i++){
            sb.append('.');
        }
        List<String> board = new ArrayList<>();
        for(int i =0 ;i<n ;i++){
            board.add(sb.toString());
        }
        backTrackSolveQ(n,0,new HashSet<Integer>(),board);
        return ans;
    }
    public void backTrackSolveQ(int n,int level,Set<Integer> col,List<String> board ){
        if(level < n){
            //获取对应的层次数字符串进行改编
            String str =board.get(level);
            //因为我们是按照每一行进行遍历的所有可以确定的是该行之前一定没有q
            for(int i=0;i<n;i++){
                //如果该列存在皇后则直接进行剪枝
                if(col.contains(i)){
                    continue;
                }

                //对角存在也要进行剪枝,由于是一行一行进行添加，所以只需要判断两个对角即可
                if(!isValid(board,level,i)){
                    continue;
                }

                str = str.substring(0,i)+'Q'+str.substring(i,str.length()-1);
                board.set(level,str);
                col.add(i);
                backTrackSolveQ(n,level+1,col,board);
                //进行回溯
                str = str.replace('Q','.');
                board.set(level,str);
                col.remove(i);
            }
        }else{
            List<String> temp = new ArrayList<>();
            for(String str :board){
                temp.add(str);
            }
            ans.add(temp);
        }

    }
    //判断同一对角线上是否具有相同的q
    public boolean isValid(List<String> board ,int level,int i){
        for(int n = 0;n<=level;n++){
            String str = board.get(level-n);
            if(i-n>=0){
                if(str.charAt(i-n) == 'Q'){
                    return false;
                }
            }
            if(i+n<str.length()){
                if(str.charAt(i+n) == 'Q'){
                    return false;
                }
            }
        }
        return true;
    }

}
