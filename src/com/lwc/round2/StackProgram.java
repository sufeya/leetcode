package com.lwc.round2;

/**
 * 该类用于锻炼栈使用的编程能力训练
 */
public class StackProgram {
    public static void main(String[] args) {
        TreeNode node1 =new TreeNode();
        TreeNode node2 =new TreeNode();
        TreeNode node3 =new TreeNode();
        TreeNode node4 =new TreeNode();
        TreeNode node5 =new TreeNode();
        TreeNode node6 =new TreeNode();
        node1.val =1 ;
        node2.val =2 ;
        node3.val =3 ;
        node4.val =4 ;
        node5.val =5 ;
        node6.val =6 ;
        node1.left=node2;
        node1.right=node5;
        node2.right=node4;
        node2.left=node3;
        node5.right=node6;
        StackProgram stackProgram=new StackProgram();
        stackProgram.flatten(node1);
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
    /**
     * 题号：114
     * 难度：中等
     * 时间：20220318
     *给你二叉树的根结点 root ，请你将它展开为一个单链表：
     *
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     */
    public void flatten(TreeNode root) {
        TreeNode[] stack =new TreeNode[2000];
        int pos = 0;
        TreeNode p = root;

        while(pos >0 || p !=null){
            if(p != null){
                //如果p不为空则遍历p

            }
        }

    }
}
