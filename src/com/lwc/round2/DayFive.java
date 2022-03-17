package com.lwc.round2;

import java.util.*;

public class DayFive {
    /**
     * 题号：1705
     * 难度：中等
     * 时间：20211224
     * 有一棵特殊的苹果树，一连 n 天，每天都可以长出若干个苹果。在第 i 天，树上会长出 apples[i] 个苹果，这些苹果将会在 days[i] 天后（也就是说，第 i + days[i] 天时）腐烂，变得无法食用。也可能有那么几天，树上不会长出新的苹果，此时用 apples[i] == 0 且 days[i] == 0 表示。
     *
     * 你打算每天 最多 吃一个苹果来保证营养均衡。注意，你可以在这 n 天之后继续吃苹果。
     *
     * 给你两个长度为 n 的整数数组 days 和 apples ，返回你可以吃掉的苹果的最大数目。
     * 分析：每天吃的都是前快要坏了的苹果，同时每天都要计算还有多少苹果没有过期，还剩多少天会过期
     * 解题：
     */
    public int eatenApples(int[] apples, int[] days) {
        //用于保存结果
        int res = 0,date=0,appleLen= apples.length,dayLen=days.length;
        //计算出最后一个苹果能够持续的最大天数
        for(int i =0 ;i< dayLen;i++){
            date=Math.max(date,days[i]+i+1);
        }
        //以保质期为索引，内容为具体的苹果个数
       int[] dateLine =new int[date];
        //跟据所有苹果能持续的最大天数进行判别,每次拿的都是快要过期的苹果这样可以使得苹果的剩余的最多
        for(int i = 0; i<date;i++){
            //判断今天是否吃了苹果
            boolean falg=false;
            //首先判断dateLine中的苹果是否有快过期的,先吃快要过期的苹果
            if(i<dayLen && apples[i] >0){
                for(int j=i+1; j<days[i]+i+1;j++){
                    if(dateLine[j] != 0 ){
                        res++;
                        dateLine[j]--;
                        falg=true;
                        break;
                    }
                }
            }else{
                //当没有新的苹果生成时，直接在dateLine中吃
                for(int j = i+1;j<date;j++){
                    if(dateLine[j] !=0){
                        res++;
                        dateLine[j]--;
                        falg=true;
                        break;
                    }
                }
            }
            //如果没有吃苹果则在今天的苹果中减去1
            if(!falg){
                if(i<appleLen && apples[i]>0){
                    dateLine[days[i]+i]=apples[i]-1;
                    res++;
                }
            }else{
                //就算吃了苹果也要在dateLine中新增相对应的今天生成的苹果
                if(i<appleLen && apples[i]>0){
                    dateLine[days[i]+i] =apples[i];
                }
            }
        }
        return res;
    }

    /**
     * 难度：中等
     * 题号：1609
     * 时间：20211225
     * 如果一棵二叉树满足下述几个条件，则可以称为 奇偶树 ：
     *
     * 二叉树根节点所在层下标为 0 ，根的子节点所在层下标为 1 ，根的孙节点所在层下标为 2 ，依此类推。
     * 偶数下标 层上的所有节点的值都是 奇 整数，从左到右按顺序 严格递增
     * 奇数下标 层上的所有节点的值都是 偶 整数，从左到右按顺序 严格递减
     * 给你二叉树的根节点，如果二叉树为 奇偶树 ，则返回 true ，否则返回 false 。
     * 分析：整颗树的第一层是0且下标为偶数层的所有节点的数都是奇数，其数值从左到右严格递增
     * 当为奇数层时所有的节点的数都是偶数且其数值是从左到右进行严格的递减
     * 解题：该题目可以使用层次遍历对整颗树进行遍历同时用一个索引记录当前的所在的层次树，用一个队列保存该层次的所有
     * 节点然后进行遍历，但是在进行层次遍历的时候同时要记录当前层次的结束节点从而进行层次迭代
     */
    public boolean isEvenOddTree(TreeNode root) {
        if(root == null){
            return false;
        }
        //队列用于保存遍历的节点
        TreeNode[] nodes =new TreeNode[100000];
        //尾进头出
        int head =0,rear=-1;
        //指示当前遍历的层次
        int level=0;
        //用于指向当前层的最后一个节点,lastVal用于记录上一个节点的值
        int last =0,lastVal=0;
        //将根节点进行入栈
        nodes[++rear] = root;
        //当队列中还有元素就进行遍历
        while(rear>=head){
            int nodeVal =nodes[head].val;
            //如果是偶数层
            if(level%2 ==0){
                if(nodeVal % 2 ==0){
                    return false;
                }else{
                    //偶数层值是递增的
                   if(lastVal != 0){
                       if(lastVal >= nodeVal){
                           return false;
                       }else{
                           lastVal = nodeVal;
                       }
                   }else{
                       lastVal = nodeVal;
                   }
                }
            }else{
                //如果是奇数层
                if(nodeVal % 2 == 0){
                    //机数层值是递减的
                    if(lastVal != 0){
                        if(lastVal <= nodeVal){
                            return false;
                        }else{
                            lastVal = nodeVal;
                        }
                    }else{
                        lastVal = nodeVal;
                    }
                }else{
                    return false;
                }
            }
            if(nodes[head].left !=null){
                nodes[++rear]=nodes[head].left;
            }
            if(nodes[head].right !=null){
                nodes[++rear]=nodes[head].right;
            }
            //将层次指针指向下一层的开始
            if(last == head){
                level++;
                last = rear;
                lastVal=0;
            }
            head++;
        }
        return true;
    }
    public String[] findOcurrences(String text, String first, String second) {
        //跟据空格将字符串进行划分
        String[] str =text.split(" ");
        List<String> list =new ArrayList<>();

        for(int i =0 ;i<str.length-2;i++){
            if( first.equals(str[i]) &&  second.equals(str[i+1]) ){
                list.add(str[i+2]);
            }
        }
        String[] res =new String[list.size()];
        for(int i =0 ;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * 题号：825
     * 难度：中等
     * 时间：20211227
     *在社交媒体网站上有 n 个用户。给你一个整数数组 ages ，其中 ages[i] 是第 i 个用户的年龄。
     *
     * 如果下述任意一个条件为真，那么用户 x 将不会向用户 y（x != y）发送好友请求：
     *
     * age[y] <= 0.5 * age[x] + 7
     * age[y] > age[x]
     * age[y] > 100 && age[x] < 100
     * 否则，x 将会向 y 发送一条好友请求。
     *
     * 注意，如果 x 向 y 发送一条好友请求，y 不必也向 x 发送一条好友请求。另外，用户不会向自己发送好友请求。
     *
     * 返回在该社交媒体网站上产生的好友请求总数。
     * 分析：年纪相同的两个人可以互相发送好友请求，年纪小的不会像年纪大的发送好友请求,看完答案之后的算法，大致是先将整个
     * 年龄数组进行遍历同时维护一个双指真来圈定y所在的范围，同时分析题设条件可以知道
     * 只有当0.5*age[x]+7<age[y]<=age[x],如果age[y]满足条件二那么其一定会满足条件三
     * 解题：
     */
    public int numFriendRequests(int[] ages) {
        int res =0;
        //给数组进行排序
        Arrays.sort(ages);
        int left =0,right=0;
        for(int i=0;i<ages.length;i++){
            if(ages[i]<15){
                continue;
            }
            while(ages[left]<=0.5*ages[i]+7){
                left++;
            }
            while(right+1<ages.length && ages[right+1]<=ages[i]){
                right++;
            }
            res+= (right-left);
        }
        return res;
    }

    /**
     * 题号：2022
     * 时间：20220101
     * 难度：简单
     * 给你一个下标从 0 开始的一维整数数组 original 和两个整数 m 和  n 。你需要使用 original 中 所有 元素创建一个 m 行 n 列的二维数组。
     *
     * original 中下标从 0 到 n - 1 （都 包含 ）的元素构成二维数组的第一行，下标从 n 到 2 * n - 1 （都 包含 ）的元素构成二维数组的第二行，依此类推。
     *
     * 请你根据上述过程返回一个 m x n 的二维数组。如果无法构成这样的二维数组，请你返回一个空的二维数组。
     *分析：首先需要将整个数组进行填满，所以可以先进行计算需要转化的二维数组的长度，然后判断一维数组的
     * 长度进行判断是否符合长度，如果符合则进行填满，在填的过程中可以采用模操作从而进行填充
     * 解题：
     */
    public int[][] construct2DArray(int[] original, int m, int n) {

        int len = original.length;
        if(len != m*n){
            return new int[][]{};
        }
        int[][] res =new int[m][n];

        for(int i =0 ;i< len;i++){
           int index = i/n;
           res[index][i%n]=original[i];
        }
        return res;
    }

    /**
     * 题号：390
     * 时间：20220102
     * 难度：中等
     * 列表 arr 由在范围 [1, n] 中的所有整数组成，并按严格递增排序。请你对 arr 应用下述算法：
     *
     * 从左到右，删除第一个数字，然后每隔一个数字删除一个，直到到达列表末尾。
     * 重复上面的步骤，但这次是从右到左。也就是，删除最右侧的数字，然后剩下的数字每隔一个删除一个。
     * 不断重复这两步，从左到右和从右到左交替进行，直到只剩下一个数字。
     * 给你整数 n ，返回 arr 最后剩下的数字。
     */
    public int lastRemaining(int n) {
        //保存结果的处理后的长度
        int len = n;
        int[] temp =new int[n];
        int[] find =new int[n];
        for(int i=0 ;i<n;i++){
            find[i]=i+1;
        }
        //用于判断是从哪边开始进行遍历，偶数表示从左边开始，奇数则表示从右边开始
        int time =0;
       while(len >1){
           int i=0;
           if(time % 2 == 0){
               for(;1+2*i<len;i++){
                   temp[i]=find[1+2*i];
               }
               time++;
               len =i--;
               for(int j = 0 ;j<len;j++){
                   find[j]=temp[j];
               }

           }else{
               for(;len-2-2*i>=0;i++){
                    temp[i]=find[len-2-2*i];
               }
               time++;
               //将整个数组进行翻转
               int m =0, j =i-1;
               len =i--;
               while(m<j){
                   int t=temp[m];
                   temp[m]=temp[j];
                   temp[j]=t;
                   m++;
                   j--;
               }
               for(int k = 0 ;k<len;k++){
                   find[k]=temp[k];
               }
           }
       }
        return find[0];
    }
    public String dayOfTheWeek(int day, int month, int year) {
        String[] weekDay =new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        int w= (day+2*month+3*(month+1)/5+year+year/4-year/100+year/400) % 7;
        /*int[] monthDays= new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int days = 0;
        for(int i =0 ;i<month-1;i++){
            days=days+monthDays[i];
        }
        //判断今年是否是闰年
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
            if(month>2){
                days++;
            }
        }
        //算出当前的所有天数
        days =day+days;*/
        String res = weekDay[w];
        return res;
    }

    /**
     * 题号：71
     * 难度：中等
     * 时间：20220106
     * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
     *
     * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
     *
     * 请注意，返回的 规范路径 必须遵循下述格式：
     *
     * 始终以斜杠 '/' 开头。
     * 两个目录名之间必须只有一个斜杠 '/' 。
     * 最后一个目录名（如果存在）不能 以 '/' 结尾。
     * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
     * 返回简化后得到的 规范路径 。
     * 分析：先将整个字符串按照斜线进行划分分成一个对应的字符串数组，起始路径是root目录表示为/,当碰到.时可以进行忽略
     * 因为指向的是当前目录，当碰到..时表示上一层目录，碰到""说明这里可能有//或者在开头可以不用管他
     * 解题：
     */
    public String simplifyPath(String path) {
        if(path.equals("")){
            return "";
        }
        String[] paths =path.split("/");
        String res = "";
        int len = paths.length;
        if(len>0){
            //队列的指针用于指向头尾
            int rear =0;
            int pre =0;
            //队列用于存放真实路径的信息
            String[] quequ =new String[len];
            quequ[rear++]="/";

            //遍历整个字符数组对路径进行处理
            for(int i =0 ;i<len;i++){
                //处理返回上层目录，如果是根目录则不进行返回
                if(paths[i].equals("..") && rear !=1){
                    rear--;
                }
                //忽略双斜杠，和头尾字符
                if((!paths[i].equals("")) && (!paths[i].equals("."))&&(!paths[i].equals(".."))){
                    quequ[rear++]=paths[i]+"/";
                }
            }

            //将队列转化为字符串
            while(pre<rear){
                res += quequ[pre++];
            }
            //去掉尾部多余的斜杠,当不仅只有根目录的时候
            if(res.length() !=1 ){
                res=res.substring(0,res.length()-1);
            }
        }else{
            res ="/";
        }
        return res;
    }
    public static void main(String[] args) {
        String str ="/root//root/..";
        String[] test = str.split("/");
        DayFive five =new DayFive();
        five.simplifyPath( "/a/./b/../../c/");

    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}