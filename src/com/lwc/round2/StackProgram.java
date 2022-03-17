package com.lwc.round2;

/**
 * 该类用于锻炼栈使用的编程能力训练
 */
public class StackProgram {
    public static void main(String[] args) {
        StackProgram stackProgram=new StackProgram();
        System.out.println(stackProgram.isValid("[])"));
    }
    /**
     * 题号：20
     * 难度：简单
     * 时间：20211212
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *  
     *
     */
    public boolean isValid(String s) {
        if(s.charAt(0) == ']'||s.charAt(0) == ')' ||s.charAt(0) == '}'){
            return false;
        }
        //字符数组用于保存括号当成栈使用
        char[] stack =new char[10000];
        //栈顶的位置，先进栈时先加一然后再进，出栈时先出栈再减一
        int pos =-1;
        for(char c :s.toCharArray()){
            if(c == '('){
              stack[++pos] = '(';
            }
            if(c == '{'){
                stack[++pos] = '{';
            }
            if(c == '['){
                stack[++pos] = '[';
            }
           if(c == ')' && pos>=0 && stack[pos] == '('){
               pos--;
           }else if(c == ')'){
               stack[++pos] = ')';
           }
           if(c == '}' && pos>=0 && stack[pos] == '{'){
               pos--;
           }else if(c == '}'){
               stack[++pos] = '}';
           }
           if(c == ']' && pos>=0 && stack[pos] == '['){
               pos--;
           }else if(c == ']'){
               stack[++pos] = ']';
           }
        }
        //如果括号中所有的字符都为空就说明该字符符合要求
        if(pos == -1){
            return true;
        }
        return false;
    }
}
