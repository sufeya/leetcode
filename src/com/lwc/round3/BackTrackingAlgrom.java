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
}
