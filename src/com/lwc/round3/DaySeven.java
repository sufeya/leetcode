package com.lwc.round3;


import java.util.*;

public class DaySeven {
    public static void main(String[] args) {
        DaySeven seven = new DaySeven() ;

        System.out.println(seven.outerTrees(new int[][]{{1,1},{2,2},{2,0},{2,4},{3,3},{4,2}}));


    }

    /**
     * 给定一个字符串 s 表示一个整数嵌套列表，实现一个解析它的语法分析器并返回解析的结果 NestedInteger 。
     *
     * 列表中的每个元素只可能是整数或整数嵌套列表
     *
     * @param s
     * @return
     */
    public NestedInteger deserialize(String s) {
        if(s.charAt(0) != '['){
            return new NestedInteger(Integer.parseInt(s));
        }
        Stack<NestedInteger> stack = new Stack<>();
        boolean isNegative= false;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i <s.length() ;i++){
            char c = s.charAt(i);
            if(c == '['){
                stack.push(new NestedInteger());
            }else if(c<='9' && c>='0'){
                sb.append(c);
            }else if(c == ','){
                if(!stack.isEmpty()){
                    System.out.println();
                    if(!sb.toString().equals("")){
                        int temp =  Integer.parseInt(sb.toString());
                        if(isNegative){
                            temp=-temp;
                            isNegative=false;
                        }
                        stack.peek().setVal(temp);
                        sb=new StringBuffer();
                    }
                }
            }else if(c == '-'){
                isNegative=true;
            }else if(c == ']'){
                if(!sb.toString().equals("")){
                    int temp =  Integer.parseInt(sb.toString());
                    System.out.println("转化后的值"+temp);
                    if(isNegative){
                        temp=-temp;
                        isNegative=false;
                    }
                    stack.peek().setVal(temp);
                    sb=new StringBuffer();
                }
                System.out.println("栈顶元素值为"+stack.peek().val);
                if(stack.size()>1){
                    NestedInteger ni = stack.pop();
                    stack.peek().add(ni);
                }
            }
        }
        return stack.pop();
    }

    /**
     * 题号：479
     * 难度：困难
     * 时间：20220417
     * 给定一个整数 n ，返回 可表示为两个 n 位整数乘积的 最大回文整数 。因为答案可能非常大，所以返回它对 1337 取余 。
     * 分析：本题自己本生不会看完别人的视屏解析之后才有一下结果
     */
    public int largestPalindrome(int n) {
        //n为一位时其范围是81-1，但是回文77，66，不能被单独的1位数整除，所以只能是9
        if( n ==1){
            return 9;
        }
        //首先确定n位数的最大数比如2位最大的值位99，最小为10
        long upBound = (long)Math.pow(10,n)-1;//99999
        long downBound= (long)Math.pow(10,n-1);//10000
        long half = (long)((upBound*upBound)/Math.pow(10,n));//99998

        long res = 0;

        boolean found = false;
        //循环遍历
        while(!found){
            res = generlizeNum(half);
            for(long i =upBound ; i>=downBound;i--){
                //如果大于最大的值就从新找回文数字
                if(res> i*i){
                    break;
                }
                if(res % i ==0){
                    found =true;
                    break;
                }
            }
            half--;
        }
        return (int)(res%1337);
    }
    //生成回文数字
    public long generlizeNum(long half){
        long temp = half;
        while(temp != 0){
            half = half*10+temp%10;
            temp/=10;
        }
        return half;
    }
    /**
     * 题号：819
     * 难度：简单
     * 时间：20220417
     * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。
     *
     * 题目保证至少有一个词不在禁用列表中，而且答案唯一。
     *
     * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
     */
    public String mostCommonWord(String paragraph, String[] banned) {
        int max = 0;
        String res = "";
        Set<String> bans =new HashSet<>();
        //处理对应的被禁止的字符
        for(String temp :banned){
            bans.add(temp);
        }
        paragraph=paragraph.replace('?',' ').replace('!',' ').replace(',',' ').replace('\'',' ').replace('.',' ').replace(';',' ');
        HashMap<String,Integer> map = new HashMap<>();
        //先全转化为小写
        String str = paragraph.toLowerCase();
        //按照空格进行分割
        String[] strings = str.split(" ");
        //处理标点符号
        for(String temp : strings){
            if(!bans.contains(temp) && !temp.equals("")){
                int counts = map.getOrDefault(temp,0)+1;
                if(counts > max){
                    max = counts;
                    res = temp;
                }
                map.put(temp,counts);
            }
        }
        return res;
    }
    /**
     * 题号：386
     * 难度：中等
     * 时间：20220418
     * 给你一个整数 n ，按字典序返回范围 [1, n] 内所有整数。
     *
     * 你必须设计一个时间复杂度为 O(n) 且使用 O(1) 额外空间的算法。
     */

    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        for(int i =0,j=1;i<n;i++){
            res.add(j);
            if(j*10<=n){
                j*=10;
            }else{
                //如果当前前缀已经遍历完了，则进行回退,遍历下一层前缀
                while(j%10 == 9 || j+1>n){
                    j/=10;
                }
                j++;
            }
        }
        return res;
    }
    /**
     * 题号：821
     * 难度：简单
     * 时间：20220419
     * 给你一个字符串 s 和一个字符 c ，且 c 是 s 中出现过的字符。
     *
     * 返回一个整数数组 answer ，其中 answer.length == s.length 且 answer[i] 是 s 中从下标 i 到离它 最近 的字符 c 的 距离 。
     *
     * 两个下标 i 和 j 之间的 距离 为 abs(i - j) ，其中 abs 是绝对值函数。
     */
    public int[] shortestToChar(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];

        for (int i = 0, idx = -n; i < n; ++i) {
            if (s.charAt(i) == c) {
                idx = i;
            }
            ans[i] = i - idx;
        }

        for (int i = n - 1, idx = 2 * n; i >= 0; --i) {
            if (s.charAt(i) == c) {
                idx = i;
            }
            ans[i] = Math.min(ans[i], idx - i);
        }
        return ans;
    }
    /**
     * 题号：388
     * 难度：中等
     * 时间：20220421
     * 这里将 dir 作为根目录中的唯一目录。dir 包含两个子目录 subdir1 和 subdir2 。subdir1 包含文件 file1.ext 和子目录 subsubdir1；subdir2 包含子目录 subsubdir2，该子目录下包含文件 file2.ext 。
     *
     * 在文本格式中，如下所示(⟶表示制表符)：
     *
     */
    public int lengthLongestPath(String input) {
        //对字符串进行入栈操作
        Stack<String> stack = new Stack<>();
        //对等级进行划分
        Stack<Integer> operator = new Stack<>();
        //len对栈里面的元素进行记录，判断当前路径的长度,max_len记录最长的路径长度
        int len=0,max_len=0;
        //首先先将整个字符按照换行符进行分割
        String[] strs =input.split("\n");

        //按照优先级对字符进行入栈
        for(String str :strs){
            //判断转义字符的个数
            int level =0;
            for(int i = 0;i<str.length();i++){
                if(str.charAt(i) == '\t'){
                    level++;
                }else{
                    break;
                }
            }
            //如果栈中有等级比level大的则全部出栈
            while(!operator.isEmpty() && operator.peek()>=level){
                operator.pop();
                String temp = stack.pop();
                //如果包含了.则说明是文件
                if(temp.contains(".")){
                    //则需要进行比较
                    max_len =Math.max(max_len,len);
                }
                len -= temp.length();
            }
            //将制表符全部替换
            str = str.replace("\t","");
            //然后将当前的字符进行入栈，如果栈中没有元素则不需要加/如果有则需要在该字符前加上
            if(!stack.isEmpty()){
                len=len+ str.length()+1;
                //栈不空，则需要加上/
                stack.push("/"+str);

            }else{
                len+=str.length();
                stack.push(str);
            }
            operator.push(level);

        }

        //有可能栈里面还没有全部出来
        while(!operator.isEmpty()){
            operator.pop();
            String temp = stack.pop();
            //如果包含了.则说明是文件
            if(temp.contains(".")){
                //则需要进行比较
                max_len =Math.max(max_len,len);
            }
            len -= temp.length();
        }

        return max_len;
    }
    /**
     * 给你一个由若干单词组成的句子 sentence ，单词间由空格分隔。每个单词仅由大写和小写英文字母组成。
     *
     * 请你将句子转换为 “山羊拉丁文（Goat Latin）”（一种类似于 猪拉丁文 - Pig Latin 的虚构语言）。山羊拉丁文的规则如下：
     *
     * 如果单词以元音开头（'a', 'e', 'i', 'o', 'u'），在单词后添加"ma"。
     * 例如，单词 "apple" 变为 "applema" 。
     * 如果单词以辅音字母开头（即，非元音字母），移除第一个字符并将它放到末尾，之后再添加"ma"。
     * 例如，单词 "goat" 变为 "oatgma" 。
     * 根据单词在句子中的索引，在单词最后添加与索引相同数量的字母'a'，索引从 1 开始。
     * 例如，在第一个单词后添加 "a" ，在第二个单词后添加 "aa" ，以此类推。
     * 返回将 sentence 转换为山羊拉丁文后的句子。
     */
    public String toGoatLatin(String sentence) {
        //按照空格分割
        String[] strs = sentence.split(" ");
        StringBuffer ans = new StringBuffer();
        char[] chars =new char[]{'a','e','i','o','u','A','E','I','O','U'};
        for(int i =0 ;i<strs.length;i++){
            //标记是否有元音字母
            boolean flag = false;
            char c = strs[i].charAt(0);
            for(char t :chars){
                if(t == c ){
                    flag = true;
                    break;
                }
            }
            int temp =i+1;
            StringBuffer sb =new StringBuffer();
            while(temp>0){
                sb.append('a');
                temp--;
            }
            if(flag){
                strs[i]= strs[i]+"ma";
            }else{
                strs[i] =strs[i].substring(1,strs[i].length())+c+"ma";
            }
            strs[i]+=sb.toString();
            ans.append(strs[i]+" ");
        }
        return ans.deleteCharAt(ans.length()-1).toString();
    }

    /**
     * 题号：396
     * 难度：中等
     * 时间：20220422
     * 给定一个长度为 n 的整数数组 nums 。
     *
     * 假设 arrk 是数组 nums 顺时针旋转 k 个位置后的数组，我们定义 nums 的 旋转函数  F 为：
     *
     * F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1]
     * 返回 F(0), F(1), ..., F(n-1)中的最大值 。
     *
     * 生成的测试用例让答案符合 32 位 整数。
     */
    public int maxRotateFunction(int[] nums) {
        int maxVal=Integer.MIN_VALUE,len=nums.length;
        for(int i =0;i<len ;i++){
            int temp =0;
            for(int j =0;j<len ;j++){
                temp+= ((i+j)%len)*nums[j];
            }
           maxVal=Math.max(maxVal,temp);
        }
        return maxVal;
    }
    //利用动态规划进行解题，上诉解法超出时间显示
    public int maxRotateFunction2(int[] nums) {
        int prev = 0,next=0,len=nums.length,sum=0,maxVal=Integer.MIN_VALUE;
       for(int i =0 ;i<len ;i++){
           sum+=nums[i];
           prev+=i*nums[i];
       }
       maxVal=prev;
        for(int i =1;i<len ;i++){
            next = prev+sum-len*nums[len-i];
            maxVal = Math.max(next,maxVal);
            prev=next;
        }
        return maxVal;
    }
    /**
     * 题号：587
     * 难度：困难
     * 时间：20220424
     * 在一个二维的花园中，有一些用 (x, y) 坐标表示的树。由于安装费用十分昂贵，你的任务是先用最短的绳子围起所有的树。只有当所有的树都被绳子包围时，花园才能围好栅栏。你需要找到正好位于栅栏边界上的树的坐标。
     * 分析：可以跟据给定的坐标做成一个对应的链表，然后对每个轴进行扫描获取每个轴上距离最远的树，从而选取点选取好了点也就意味着描好了边
     *该题上述分析解法存在问题，不应该这样去解题，应当利用向量来进行解题，先选取坐标值最左边的一个点作为基点q，然后任意一个点p
     * 使得所有的点都在qp向量的左边，当存在点不在qp向量的左边时则将p点更换为该点再进行遍历，同时要找到与qp向量在同一条直线上的点
     * 以防止重复遍历
     */
    public int[][] outerTrees(int[][] trees) {
        int leftest = 0,len=trees.length;
        boolean[] visited = new boolean[len];
        List<int[]> ans = new ArrayList<>();
       for(int i=1;i<len;i++){
           if(trees[i][0]<trees[leftest][0]){
               leftest=i;
           }
       }
       int p=leftest;
       ans.add(trees[p]);
       visited[p] =true;
       do{
           int q=(p+1)%len;
           for(int i =0;i<len;i++){
               //如果i不在pq向量的左边，则更新q
               if(cross(trees[p],trees[q],trees[i])<0){
                    q=i;
               }

           }
           for(int i =0;i<len;i++){
               if(i == q || visited[i]){
                   continue;
               }
               //同时要找出在同一直线上的点
               if(cross(trees[p],trees[q],trees[i]) == 0){
                   visited[i] = true;
                   ans.add(trees[i]);
               }
           }
           if(!visited[q]){
               ans.add(trees[q]);
               visited[q] =true;
           }
            p=q;

       }while(p !=leftest);
        return ans.toArray(new int[][]{});
    }
    //这里干脆直接算角度得了，角度在（0，2pi）之间
    public int cross(int[] p,int[] q ,int[] r){
        return (q[0]-p[0])*(r[1]-q[1])-(q[1]-p[1])*(r[0]-q[0]);
    }
}

/**
 * 实现RandomizedSet 类：
 * <p>
 * RandomizedSet() 初始化 RandomizedSet 对象
 * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
 * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
 * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
 * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
 */
class RandomizedSet {
    List<Integer> list;

    public RandomizedSet() {
        list = new ArrayList<>();
    }

    public boolean insert(int val) {
        if (list.contains(val)) {
            return false;
        }
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if (list.contains(val)) {
            return false;
        }
        list.remove(list.indexOf(val));
        return true;
    }

    public int getRandom() {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}

class NestedInteger{
    List<NestedInteger> list = new ArrayList<>();
    int val=0;
    public NestedInteger(){

    }
    public void setVal(int val){
        this.val =val;
    }
    public NestedInteger(int val ){
        this.val =val;
    }
    public void add(NestedInteger ni){
        list.add(ni);
    }
}