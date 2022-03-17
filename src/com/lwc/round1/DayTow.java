package com.lwc.round1;

import java.util.ArrayList;
import java.util.List;

public class DayTow {
    public static void main(String[] args) {
     TreeNode treeNode =new TreeNode();
     treeNode.val=0;
     TreeNode treeNode1 =new TreeNode();
        treeNode1.val=1;
     TreeNode treeNode2 =new TreeNode();
        treeNode2.val=2;
     TreeNode treeNode3 =new TreeNode();
        treeNode3.val=3;
     TreeNode treeNode4 =new TreeNode();
        treeNode4.val=4;
     treeNode.left =treeNode1;
     treeNode.right=treeNode2;
     treeNode1.right=treeNode3;
     treeNode1.left=treeNode4;
     String str=afterSearch(treeNode);
        System.out.println(str);
    }
    /**
     * 题目难度简单
     * 题目序号448
     * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
     *
     *  
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static  List<Integer> findDisappearedNumbers(int[] nums) {
        int size =nums.length;
        int result[] =new int[size];
        List<Integer> re=new ArrayList<>();
        //设置一个数组通过接收nums中的数据当作result数组中的下标从而统计数据出现的次数
        for(int i:nums){
           result[i-1] +=1;
        }
        //如果统计出来的数字的次数为零则将该下标直接放入集合中
       for( int i=0 ;i<size;i++){
           if(result[i]==0){
               re.add(i+1) ;
           }

       }
        return re;
    }
    /**
     * 题目难度 中等
     * 题目序号 449
     * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
     *
     * 设计一个算法来序列化和反序列化 二叉搜索树 。 对序列化/反序列化算法的工作方式没有限制。 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
     *
     * 编码的字符串应尽可能紧凑。
     * 分析：我认为该题可以认为是一个二叉树的遍历，以及跟据遍历的结果还原为原先的二叉树
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/serialize-and-deserialize-bst
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    // Encodes a tree to a single string.

    public String serialize(TreeNode root) {

        //定义一个数组当作栈用于保存遍历的节点
        TreeNode[] treeStack=new TreeNode[10000];
        String result="";
        //先序遍历生成先序字符串用逗号进行拼接
        if(root !=null){
            int i =0;
            treeStack[i++]=root;
            while(i>0 ){
                TreeNode j=treeStack[--i];
                result=result+j.val+",";
                //先让右孩子节点进栈
                if(j.right !=null){
                    treeStack[i++]=j.right;
                }
                //然后让左孩子节点入栈，这样可以下次栈访问的依然是做孩子节点的子树
                if(j.left !=null){
                    treeStack[i++] =j.left;
                }
            }
        }
        //将最后一个逗号去掉
        result=result.substring(0,result.length()-1);
        //中序遍历，用逗号进行拼接
        String mResult="";
        if(root !=null){
            //定义栈的下标，入栈时先入栈后下标增加，出栈时下标先减后出栈
            int i =0;
            //根节点先入栈
            TreeNode j=root;
            while(i>0|| j != null){
                //将根节点的所有左子树进栈
                while(j != null){
                    treeStack[i++] =j;
                    j=j.left;
                }
                j=treeStack[--i];
                //然后对该节点进行遍历
                mResult=mResult+j.val+",";
                //然后开始遍历右子树，将右边节点入栈
                j=j.right;
            }
        }
        mResult=mResult.substring(0,mResult.length()-1);
        return result+":"+mResult;
    }
    //非递归先序遍历二叉树，根左右，同时还需要进行中序遍历拼接成一个字符串
   public static String search(TreeNode root){
        //定义一个数组当作栈用于保存遍历的节点
       TreeNode[] treeStack=new TreeNode[10000];
       String result="";
      //先序遍历生成先序字符串用逗号进行拼接
       if(root !=null){
           int i =0;
           treeStack[i++]=root;
           while(i>0 ){
               TreeNode j=treeStack[--i];
                result=result+j.val+",";
                //先让右孩子节点进栈
               if(j.right !=null){
                   treeStack[i++]=j.right;
               }
               //然后让左孩子节点入栈，这样可以下次栈访问的依然是做孩子节点的子树
               if(j.left !=null){
                   treeStack[i++] =j.left;
               }
           }
       }
       //将最后一个逗号去掉
       if(result.length()>0){
           result=result.substring(0,result.length()-1);
       }

       //中序遍历，用逗号进行拼接
       String mResult="";
       if(root !=null){
           //定义栈的下标，入栈时先入栈后下标增加，出栈时下标先减后出栈
           int i =0;
           //根节点先入栈
           TreeNode j=root;
           while(i>0|| j != null){
               //将根节点的所有左子树进栈
               while(j != null){
                   treeStack[i++] =j;
                   j=j.left;
               }
               j=treeStack[--i];
               //然后对该节点进行遍历
               mResult=mResult+j.val+",";
               //然后开始遍历右子树，将右边节点入栈
               j=j.right;
           }
       }
       //考虑到空字符串
       if(mResult.length()>0){
           mResult=mResult.substring(0,mResult.length()-1);
       }

       return result+":"+mResult;
   }

   //后序遍历二叉树-左右根
    public static String afterSearch(TreeNode root){
        TreeNode[] treeStack=new TreeNode[10000];
        String result="";
        if(root !=null){
            int i=0;
            TreeNode j =root;
            while(i>0 || j != null){
                //遍历到最左变的节点
                while(j !=null){
                    treeStack[i++]=j;
                    j=j.left;
                }
                //节点出栈
                j=treeStack[--i];
                //当j的右节点为空，或者j的右节点遍历过则可以遍历该节点
                if(j.right == null ||  j.right.flag == 1){
                    result =result +j.val;
                    j.flag =1;
                    //将该节点遍历完之后，赋值为空防止后序重复遍历
                    j=null;
                }else{//如果不满足上述条件则将该节点入栈然后访问右子节点
                    treeStack[i++] =j;
                    j=j.right;
                }
            }
        }
        return result;
    }
    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        //将序列化的数据拆分，然后按照先序从新编码为一个二叉树
        String[] str=data.split(":");
        if(str.length>0){
            //将先序遍历结果进行分割
            String before[]=str[0].split(",");
            //将后序遍历结果进行分割
            String mid[]=str[1].split(",");
            //跟据两次遍历的结果重新生成二叉树,构建过程其实是一个迭代的过程，对于每一个节点仅需考虑其左节点和右节点的位置
            return buildTree(before,mid);
        }
       return null;
    }
    //这里也可以不必每次都重新构造数组进行构建二叉树，可以通过使用指针指向数组的位置，这样就不必每次都重新构建数组
    //在查找的过程中我们可以使用hashMap对数组的元素的键进行查找，这样就不必每次都遍历中序遍历进行查找根的位置
    public static TreeNode buildTree(String[] before,String[] mid){
        //如果先序遍历中的长度为一时，则说明只有一个节点
        if(before ==null || before.length ==0){
            return null;
        }
        //如果长度大于1时,则先跟跟据先序确定跟根节点
        TreeNode root =new TreeNode();
        root.val=Integer.parseInt(before[0]);

        //定义flag，用来查找跟节点在中序遍历中的位置，从而区分左子树和右子树，用于递归调用
        int flag=-1;
       for(int i=0 ;i<mid.length;i++){
           if(before[0].equals(mid[i])){
               flag=i;
               break;
           }
       }
       //如果找到flag的位置
       if(flag>=0){
           //左子树构建递归
           String[] lMid=new String[flag];//左子树中序遍历
           for(int i=0;i<flag;i++){
               lMid[i]=mid[i];
           }
           String[] lBefore=new String[flag];
           for(int i=1;i<flag+1;i++){
               lBefore[i-1]=before[i];
           }
           root.left=buildTree(lBefore,lMid);

           //右子树递归构建
           int length=mid.length -(flag+1);
           String[] rMid =new String[length];
           for(int i=flag+1;i<mid.length;i++){
            rMid[i-flag-1]=mid[i];
           }
           String[] rBefore=new String[length];
           for(int i= flag+1;i<before.length;i++){
               rBefore[i-flag-1]=before[i];
           }
           root.right =buildTree(rBefore,rMid);
       }
        return root;
    }




}
class TreeNode{
     int val;
    //用于判断该节点是否已经遍历
     int flag;
     TreeNode left;
     TreeNode right;
     TreeNode(){}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
      }
}