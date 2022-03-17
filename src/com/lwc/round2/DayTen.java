package com.lwc.round2;

import com.sun.org.apache.regexp.internal.RE;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class DayTen {


    /**
     * 时间：20220308
     * 难度：简单
     * 题号：2099
     * 给你一个整数数组 nums 和一个整数 k 。你需要找到 nums 中长度为 k 的 子序列 ，且这个子序列的 和最大 。
     *
     * 请你返回 任意 一个长度为 k 的整数子序列。
     *
     * 子序列 定义为从一个数组里删除一些元素后，不改变剩下元素的顺序得到的数组。
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSubsequence(int[] nums, int k) {
        int len =nums.length;
        int[] res =new int[k];
        List<int[]> list =new ArrayList<>();
        for(int i =0 ; i< len ;i++){
            int[] temp=new int[2];
            temp[0]=i;
            temp[1]=nums[i];
            list.add(temp);
        }
        //首先跟据值进行排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1]-o1[1];
            }
        });
        //将前k个进行截取之后再按照索引进行排序即可
        List<int[]> list2 =new ArrayList<>();
        for(int i =0 ;i<k;i++){
            list2.add(list.get(i));
        }
        list2.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });
        for(int i=0 ;i<k;i++){
            res[i]=list2.get(i)[1];
        }
        return res;
    }
    /**
     * 给你一个炸弹列表。一个炸弹的 爆炸范围 定义为以炸弹为圆心的一个圆。
     *
     * 炸弹用一个下标从 0 开始的二维整数数组 bombs 表示，其中 bombs[i] = [xi, yi, ri] 。xi 和 yi 表示第 i 个炸弹的 X 和 Y 坐标，ri 表示爆炸范围的 半径 。
     *
     * 你需要选择引爆 一个 炸弹。当这个炸弹被引爆时，所有 在它爆炸范围内的炸弹都会被引爆，这些炸弹会进一步将它们爆炸范围内的其他炸弹引爆。
     *
     * 给你数组 bombs ，请你返回在引爆 一个 炸弹的前提下，最多 能引爆的炸弹数目。
     */
    public int maximumDetonation(int[][] bombs) {

        return 0;
    }
    /**
     * 给你一个数组 nums，我们可以将它按一个非负整数 k 进行轮调，这样可以使数组变为 [nums[k], nums[k + 1], ... nums[nums.length - 1], nums[0], nums[1], ..., nums[k-1]] 的形式。此后，任何值小于或等于其索引的项都可以记作一分。
     *
     * 例如，数组为 nums = [2,4,1,3,0]，我们按 k = 2 进行轮调后，它将变成 [1,3,0,2,4]。这将记为 3 分，因为 1 > 0 [不计分]、3 > 1 [不计分]、0 <= 2 [计 1 分]、2 <= 3 [计 1 分]，4 <= 4 [计 1 分]。
     * 在所有可能的轮调中，返回我们所能得到的最高分数对应的轮调下标 k 。如果有多个答案，返回满足条件的最小的下标 k 。
     */
    public int bestRotation(int[] nums) {
        int len = nums.length;
        int[] res = new int[2];
        int[] temp = new int[len];
        //跟据长度k进行轮调
        for(int k = 0 ;k<len ;k++){
            int[] tempRes =new int[2];
            tempRes[1]=k;
            //先将后面的进行填充
            for(int i =0;i<k ;i++){
                temp[len-k+i]=nums[i];
            }
            //再处理前面k个数
            for(int i =0 ;i+k<len;i++){
                temp[i]=nums[i+k];
            }
            for(int i = 0;i<len ;i++){
                if(temp[i]<=i){
                    tempRes[0]++;
                }
            }
            if(tempRes[0]>res[0] ||(tempRes[0]==res[0] && tempRes[1]<res[1])){
                res[0]=tempRes[0];
                res[1]=tempRes[1];
            }
        }
        return res[1];
    }
    //正确解法
    static int N = 100010;
    static int[] c = new int[N];
    void add(int l, int r) {
        c[l] += 1; c[r + 1] -= 1;
    }
    public int bestRotation2(int[] nums) {
        Arrays.fill(c, 0);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int a = (i - (n - 1) + n) % n, b = (i - nums[i] + n) % n;
            if (a <= b) {
                add(a, b);
            } else {
                add(0, b);
                add(a, n - 1);
            }
        }
        for (int i = 1; i <= n; i++) c[i] += c[i - 1];
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (c[i] > c[ans]) ans = i;
        }
        return ans;
    }


    /**
     * 时间：20220309
     * 难度：中等
     * 题号：1109
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
     *
     * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
     *
     * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff =new int[n];
        for(int[] booking:bookings){
            diff[booking[0]-1]+=booking[2];
            if(booking[1]<n){
                diff[booking[1]]-=booking[2];
            }
        }
        for(int i =1 ;i<n;i++){
            diff[i]+=diff[i-1];
        }
        return diff;
    }
    /**
     * 题号：589
     * 难度：简单
     * 时间：2022310
     * 给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历 。
     *
     * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
     */
    public List<Integer> preorder(Node root) {
        Map<Node,Integer> map =new HashMap<>();
        List<Integer> res =new ArrayList<>();
        //定义栈用于存放节点进行遍历
        Node[] stack= new Node[10000];
        Node p =root;
        //指针用于指向头部位置
        int pos=0;
        while(pos>0 || p!=null){
            if(p!=null){
                res.add(p.val);
                if(p.children !=null && p.children.size()>0){
                    stack[pos++]=p;
                    map.put(p,0);
                    p=p.children.get(0);
                }else{
                    p=null;
                }
            }else{
                p=stack[pos-1];
                int index = map.getOrDefault(p,-1)+1;
                if(p.children !=null && p.children.size()>index){
                    stack[pos++]=p.children.get(index);
                    map.put(p,index);
                    p=stack[--pos];
                }else{
                    //如果p节点所有的子节点都已经访问过则将该节点剔除
                    pos--;
                    map.remove(p);
                    p=null;
                }
            }
        }
        return res;
    }
    /**
     * 时间：20220311
     * 难度：中等
     * 题号：2049
     * 给你一棵根节点为 0 的 二叉树 ，它总共有 n 个节点，节点编号为 0 到 n - 1 。同时给你一个下标从 0 开始的整数数组 parents 表示这棵树，其中 parents[i] 是节点 i 的父节点。由于节点 0 是根，所以 parents[0] == -1 。
     *
     * 一个子树的 大小 为这个子树内节点的数目。每个节点都有一个与之关联的 分数 。求出某个节点分数的方法是，将这个节点和与它相连的边全部 删除 ，剩余部分是若干个 非空 子树，这个节点的 分数 为所有这些子树 大小的乘积 。
     *
     * 请你返回有 最高得分 节点的 数目 。
     */
    public int countHighestScoreNodes(int[] parents) {
        //可以使用栈或者队列，或则hashmap进行存储根节点从而计算子树的节点的数量
        //可以先计算以该节点为根左右两个子树的节点数量，然后拿总的节点数减去左右子树节点即可
        long maxScore=0;
        int count=0;
        //预处理根节点
        Map<Integer,List<Integer>> map =new HashMap<>();
        int len = parents.length;
        int[] node =new int[len];
        for(int i =0;i<len;i++){
            if(parents[i] >=0){
                List<Integer> lst= map.get(parents[i]);
                if(lst != null){
                    lst.add(i);
                }else{
                    lst =new ArrayList<>();
                    lst.add(i);
                    map.put(parents[i],lst);
                }
            }
        }
        dfs(map,0,node);
        for(int i =0 ;i<len;i++){
            long tempScore = 0;
            List<Integer> list = map.get(i);
            if(list != null ){
                //如果该节点有子节点
                long mul =1;
                for(Integer no : list){
                    if(i == 34){
                        System.out.println("孩子节点数:"+node[no]);
                    }
                    mul*=node[no];
                }
                tempScore=((len - node[i])==0? 1:(len - node[i]))*mul;
            }else{
                //没有子节点
                tempScore=len-node[i];
            }
            if(tempScore>maxScore){
                maxScore=tempScore;
                count=1;
            }else if(tempScore == maxScore){
                count++;
            }
        }
        return count;
    }
    public int dfs(Map<Integer,List<Integer>> map ,int root,int[] node){
        int count=1;
        List<Integer> list = map.get(root);
        if(list != null){
            for(Integer i :list){
                count+= dfs(map,i,node);
            }
        }
        node[root] =count;
        return count;
    }
    /**
     * 题号：590
     * 难度：简单
     * 时间：
     * 给定一个 n 叉树的根节点 root ，返回 其节点值的 后序遍历 。
     *
     * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
     */
    public List<Integer> postorder(Node root) {
        //后序遍历是左右根进行遍历
        Node[] stack =new Node[10000];
        //指向栈的位置
        int pos =0;
        Node p =root;
        //用于记录所访问的子节点的索引
        Map<Node,Integer> map =new HashMap<>();
        //结果数组集
        List<Integer> res =new ArrayList<>();
        while(pos>0 || p != null){
            //先到最左边的节点
            if(p!=null){
                List<Node> lst =p.children;
                int index = map.getOrDefault(p,-1)+1;
                //更新索引
                if(lst !=null && index < lst.size()){
                    stack[pos++] = p;
                    map.put(p,index);
                    p=lst.get(index);
                }else if(lst == null || (lst !=null && lst.size()<= index)){
                    //如果该节点没有子节点或者该节点的所有子节点均已经访问过了，则访问当前节点
                   res.add(p.val);
                   map.remove(p);
                   //如果我这里访问了p，p就不应该还在栈中就应该出去了
                   p=null;
                }
            }else{
                //如果已经到达最左
                p=stack[--pos];
            }
        }
        return res;
    }
    /**
     * 时间：20220313
     * 难度：中等
     * 题号：393
     *给定一个表示数据的整数数组 data ，返回它是否为有效的 UTF-8 编码。
     *
     * UTF-8 中的一个字符可能的长度为 1 到 4 字节，遵循以下的规则：
     *
     * 对于 1 字节 的字符，字节的第一位设为 0 ，后面 7 位为这个符号的 unicode 码。
     * 对于 n 字节 的字符 (n > 1)，第一个字节的前 n 位都设为1，第 n+1 位设为 0 ，后面字节的前两位一律设为 10 。剩下的没有提及的二进制位，全部为这个符号的 unicode 码。
     * 这是 UTF-8 编码的工作方式：
     * 分析：看了别人的理解总算看懂这个题目啥意思，大概就是整个编码都是跟据第一位数字的一的个数，如果
     * 是128-0那么这个字符就是一个字节编码，如果是192-223是两个字符组成一个码，224-239为三个字符组成一个码
     * 240-247为三个字符组成一个码
     */
    public boolean validUtf8(int[] data) {
        int len = data.length;
        int i=0;
        while(i<len){
            if(data[i]<128){
                i++;
            }else if(data[i]<224 && data[i]>=192 && i+1<len){
                if((data[i+1]>>6) == 2){
                    i+=2;
                }else{
                    return false;
                }
            }else if(data[i]>=224 && data[i]<=239 && i+2<len){
                if((data[i+1]>>6) == 2 && (data[i+2]>>6) == 2){
                    i+=3;
                }else{
                    return false;
                }
            }else if(data[i]>=240 && data[i]<=247 && i+3<len){
                if((data[i+1]>>6) == 2 && (data[i+2]>>6)==2 &&(data[i+3]>>6)==2 ){
                    i+=4;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * 时间：20220314
     * 难度：简单
     * 题号：599
     * 假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
     *
     * 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。

     */
    public String[] findRestaurant(String[] list1, String[] list2) {
        int len1 = list1.length;
        int len2 = list2.length;
        List<String> res =new ArrayList<>();
        int minIndex=Integer.MAX_VALUE;
        Map<String,Integer> map =new HashMap<>();
        Map<String,Integer> map2 =new HashMap<>();
        for(int i =0; i<len1 ;i++){
            map.put(list1[i],i);
        }
        for(int i =0; i<len2 ;i++){
            map2.put(list2[i],i);
        }
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            int index = map2.getOrDefault(entry.getKey(),-1);
            if( index != -1 && (index+entry.getValue())<minIndex){
                res=new ArrayList<>();
                res.add(entry.getKey());
                minIndex= index+entry.getValue();
            }else if(index != -1 && (index+entry.getValue()) == minIndex){
                res.add(entry.getKey());
            }
        }
        return res.toArray(new String[res.size()]);

    }
    public static void main(String[] args) {
        DayTen ten =new DayTen();
        System.out.println(ten.findRestaurant(new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"},new String[]{"KFC", "Shogun", "Burger King"}));
    }
}
class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}
