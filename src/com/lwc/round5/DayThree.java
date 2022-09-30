package com.lwc.round5;

import javafx.util.Pair;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/9/19 15:32
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayThree {

    /**
     * 题号：1636
     * 难度：简单
     * 时间：20220919
     * 给你一个整数数组 nums ，请你将数组按照每个值的频率 升序 排序。如果有多个值的频率相同，请你按照数值本身将它们 降序 排序。
     *
     * 请你返回排序后的数组。
     */
    public int[] frequencySort(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i : nums){
            map.put(i,map.getOrDefault(i,0)+1);
        }
       //交换排序
        for(int i =0;i<nums.length;i++){
            for(int j = i;j<nums.length;j++){
                int m = map.get(nums[i]),n = map.get(nums[j]);
                if(m>n){
                    int temp = nums[j];
                    nums[j]=nums[i];
                    nums[i]=temp;
                }else if( m == n){
                    if(nums[i]<nums[j]){
                        int temp = nums[j];
                        nums[j]=nums[i];
                        nums[i]=temp;
                    }
                }
            }
        }
        return nums;
    }
    /**
     * 题号：698
     * 难度：中等
     * 时间：20220920
     * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
     */
    int[] buket,nums;
    int k;

    public boolean canPartitionKSubsets(int[] nums, int k) {
        buket = new int[k];
        this.k = k;

        //对数组进行排序，这样可以减少递归运算的次数，从而进行剪枝

        int target = 0;
        for(int i :nums){
            target += i;
        }
        Arrays.sort(nums);
        this.nums=nums;

        if(target%k !=0){
            return false;
        }
        target = target/k;
        dfsBall(nums.length-1,target);
        return false;
    }
    //球的角度，将球放到每个桶里，每个球有k种选择，即n^k种可能
    public boolean dfsBall(int index,int target){
        //递归推出条件,球全部用完则说明符合要求
        if(index == -1){
            return true;
        }
        for(int i =0;i<k;i++){
            //如果当前桶跟前一个桶是一样的数量的话，那么球放这个桶里面的情况跟前一个桶情况相同
            if(i>0 && buket[i-1] == buket[i]){
                continue;
            }
            if(buket[i]+nums[index]>target){
                continue;
            }
            //将该球放入桶中，选择下一个球
            buket[i] += nums[index];
            if(dfsBall(index-1,target)){
                return true;
            }
            //同时进行回溯
            buket[i] -=nums[index];
        }
        return false;
    }

    //以桶的角度进行求解
    boolean[] dp;
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        //dp表示的桶中选择球的状态
        dp = new boolean[1<<nums.length];
        Arrays.fill(dp,true);
        int target = 0;
        for(int i :nums){
            target += i;
        }

        if(target%k !=0){
            return false;
        }
        this.nums = nums;
        target = target/k;
        return dfsBuket((1<<nums.length)-1,0,target);
    }
    public boolean dfsBuket(int status,int val,int target){
        //所有球都用完了，则说明可以
        if(status == 0){
            return true;
        }
        //其中val表示当前桶的值
        //如果当前所有桶中选择状态存在说明该状态不能成立，需要进行剪枝
        if(!dp[status]){
            return dp[status];
        }
        dp[status] = false;
        for(int i = 0;i<nums.length;i++){
            if(val+nums[i]>target){
                continue;
            }
            //判断第i个球是否被使用过
            if(((status>>i) & 1) == 0){
                continue;
            }
            if(dfsBuket(status^(1<<i),(val+nums[i])%target,target)){
                return true;
            }
        }
        return false;
    }

    /**
     * 题号：854
     * 难度：困难
     * 时间：20220921
     * 对于某些非负整数 k ，如果交换 s1 中两个字母的位置恰好 k 次，能够使结果字符串等于 s2 ，则认为字符串 s1 和 s2 的 相似度为 k 。
     *
     * 给你两个字母异位词 s1 和 s2 ，返回 s1 和 s2 的相似度 k 的最小值。
     */
    public int kSimilarity(String s1, String s2) {
        int n = s1.length();
        Set<String> set = new HashSet<>();
        Queue<Pair<String,Integer>> queue = new ArrayDeque<>();
        queue.offer(new Pair<>(s1,0));
        int step = 0;
        //广度优先遍历
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0;i<size;i++){
               Pair<String,Integer> pair = queue.poll();
               String cur = pair.getKey();
               int pos = pair.getValue();
               if(cur.equals(s2)){
                   return step;
               }
               while(pos<n && cur.charAt(pos) == s2.charAt(pos)){
                   pos++;
               }
               for(int j = pos ;j<n;j++){
                   if(cur.charAt(j) == s2.charAt(j)){
                       continue;
                   }
                   if(cur.charAt(j) == s2.charAt(pos)){
                        char[] cs = cur.toCharArray();
                        char c = cs[j];
                        cs[j] = cs[pos];
                        cs[pos] = c;
                        String s = new String(cs);
                        if(!set.contains(s)){
                            set.add(s);
                            queue.add(new Pair<>(s,pos+1));
                        }
                   }
               }
            }
            step++;
        }
        return step;
    }

    /**
     * 题号：1640
     * 难度：简单
     * 时间：20220922
     * 给你一个整数数组 arr ，数组中的每个整数 互不相同 。另有一个由整数数组构成的数组 pieces，其中的整数也 互不相同 。请你以 任意顺序 连接 pieces 中的数组以形成 arr 。但是，不允许 对每个数组 pieces[i] 中的整数重新排序。
     *
     * 如果可以连接 pieces 中的数组形成 arr ，返回 true ；否则，返回 false 。
     */
    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer,int[]> map = new HashMap<>();
        for(int i=0;i<pieces.length;i++){
            map.put(pieces[i][0],pieces[i]);
        }
        int n = arr.length,i = 0;
       while(i<n){
            if(map.containsKey(arr[i])){
                int[] temp = map.get(arr[i]);
                for(int j = 0;j<temp.length;j++){
                    if(temp[j] == arr[i]){
                        i++;
                    }else{
                        return false;
                    }
                }
            }else{
                return false;
            }
        }
        return true;
    }
    //换硬币
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i :coins){
            dp[i] = 1;
        }
        for(int i = 1 ;i<=amount;i++){
            for(int j : coins){
                if(i-j >=0 && dp[i-j] != Integer.MAX_VALUE){
                    dp[i] = Math.min(dp[i-j]+1,dp[i]);
                }
            }
        }
        return dp[amount]== Integer.MAX_VALUE ? -1:dp[amount];
    }

    /**
     * ["MyLinkedList","addAtHead","deleteAtIndex","deleteAtIndex","addAtTail","deleteAtIndex","addAtTail","addAtTail","addAtHead","addAtIndex","addAtTail","get"]
     * [[],[1],[1],[1],[7],[2],[1],[8],[2],[5,2],[7],[2]]
     * @param args
     */

    /**
     * 题号：788
     * 难度：中等
     * 时间：20220926
     *我们称一个数 X 为好数, 如果它的每位数字逐个地被旋转 180 度后，我们仍可以得到一个有效的，且和 X 不同的数。要求每位数字都要被旋转。
     *
     * 如果一个数的每位数字被旋转以后仍然还是一个数字， 则这个数是有效的。0, 1, 和 8 被旋转后仍然是它们自己；2 和 5 可以互相旋转成对方（在这种情况下，它们以不同的方向旋转，换句话说，2 和 5 互为镜像）；6 和 9 同理，除了这些以外其他的数字旋转以后都不再是有效的数字。
     *
     * 现在我们有一个正整数 N, 计算从 1 到 N 中有多少个数 X 是好数？
     */
    public int rotatedDigits(int n) {
        int[][] pairs = new int[][]{{0,0},{1,1},{2,5},{5,2},{8,8},{6,9},{9,6}};

        int ans = 0;
        for(int i =1;i<n;i++){
            int temp = i;
            boolean res = true;
            List<Integer> list = new ArrayList<>();
            while(temp >0){
                int val = temp %10;
                temp/=10;
                boolean tempR = false;
                for(int[] pair:pairs){
                    if(pair[0] == val){
                        list.add(pair[1]);
                        tempR = true;
                        break;
                    }
                }
                if(!tempR){
                    res = false;
                    break;
                }
            }
            int temp2 = 0;
            for(int j = list.size()-1;j>=0;j--){
                temp2=temp2*10+list.get(j);
            }
            if(res && temp2!=i){
                ans++;
            }
        }
        return ans;
    }

    /**
     * 题号：17.19
     * 难度：困难
     * 时间：20220926
     * 给定一个数组，包含从 1 到 N 所有的整数，但其中缺了两个数字。你能在 O(N) 时间内只用 O(1) 的空间找到它们吗？
     *
     * 以任意顺序返回这两个数字均可。
     */
    public int[] missingTwo(int[] nums) {
        int len = nums.length+2;
        long squareSum = 0,sum=0,twoSquareSum=0,towSum=0;
        for(int i =1;i<=len;i++){
            squareSum += i*i;
            sum+=i;
        }
        for(int j : nums){
            twoSquareSum += j*j;
            towSum += j;
        }
         long delt = sum - towSum,squareDelt = squareSum - twoSquareSum;
         long mutiply = delt*delt - squareDelt;
         long substract = (long)Math.sqrt(squareDelt -mutiply);

        return new int[]{(int)(delt+substract)/2,(int)(delt-substract)/2};
    }
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        int len = plants.length,i=0,j=len-1,tempA = capacityA,tempB=capacityB;
        int ans = 0;
        while(i<=j){
            if(i == j){
                if(tempB>tempA){
                   if(tempB<plants[i]){
                        ans++;
                   }
                   i++;
                }else{
                    if(tempA<plants[j]){
                        ans++;
                    }
                    j--;
                }
            }else{
                if(tempA>=plants[i]){
                    tempA-=plants[i];

                }else{
                    ans++;
                    tempA = capacityA-plants[i];
                }
                i++;
                if(tempB>=plants[j]){
                    tempB-=plants[j];

                }else{
                    ans++;
                    tempB = capacityB - plants[j];
                }
                j--;
            }
        }
        return ans;
    }

    /**
     * 题号：17.09
     * 难度：中等
     * 时间：20220928
     * 有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
     */
    public int getKthMagicNumber(int k) {
        int[] dp = new int[k+1];
        dp[1] = 1;
        int three = 1,five = 1,seven = 1;
        for(int i = 2;i<k+1;i++){
            int nThree = 3*dp[three],nFive = 5*dp[five],nSeven = dp[seven]*7;
            dp[i] = Math.min(nThree,Math.min(nFive,nSeven));
            if(dp[i] == nThree){
                three++;
            }
            if(dp[i] == nFive){
                five++;
            }
            if(dp[i] == nSeven){
                seven++;
            }
        }
        return dp[k];
    }

    /**
     *字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成（比如，waterbottle是erbottlewat旋转后的字符串）。
     */
    public boolean isFlipedString(String s1, String s2) {
        if(s1.equals("") && s2.equals("")){
            return true;
        }

        String str = s1+s1;
        int len = s1.length(),len2 = s2.length();
        if((len == 0 && len2!=0) || (len!=0&&len2==0)){
            return false;
        }
        for(int i =0;i<2*len-len2;i++){
            String st = str.substring(i,i+len2);
            if(st.equals(s2)) return true;
        }
        return false;
    }

    public int lengthOfLongestSubstring(String s) {
        return 0;
    }
    public static void main(String[] args) {
        DayThree three = new DayThree();
        three.isFlipedString("rxOpSEXvfIuoRJfjwgwuomevMMBOfeSMvYSPBaovrZBSgmCrSLDirNnILhARNShOYIFBHIRiFKHtfxWHjawaLRAEYPIZokUKgiqyZpvcOHdfPpRqHADKAXzEfzhxdXXb"
                ,"");


    }
}
class MyLinkedList {
    class MyNode{
        int val;
        MyNode next;
        public MyNode(int val ,MyNode next){
            this.val = val;
            this.next = next;
        }
    }
    MyNode head = new MyNode(-1,null);
    MyNode tail = head;
    public MyLinkedList() {

    }

    public int get(int index) {
        int idx = 0;
        MyNode node = head.next;
        while(node != null && idx<index){
            idx++;
            node = node.next;
        }
        if(node == null || idx<index){
            return -1;
        }
        return node.val;
    }

    public void addAtHead(int val) {
        MyNode node = new MyNode(val,head.next);
        if(head == tail){
            tail = node;
        }
        head.next = node;
    }

    public void addAtTail(int val) {
        MyNode node = new MyNode(val,null);
        tail.next = node;
        tail = node;
    }

    public void addAtIndex(int index, int val) {
        if(index <=1){
            addAtHead(val);
            return;
        }
        MyNode node = head.next;
        int idx = 0;
        while(node != null && idx<index-1){
            node = node.next;
            idx++;
        }
        if(node == null){
            return;
        }
        MyNode newNode = new MyNode(val,node.next);
        node.next = newNode;
        if(newNode.next == null){
            tail = newNode;
        }
    }

    public void deleteAtIndex(int index) {
        MyNode pre = head,node = head.next;
        int idx = 0;
        while(node != null && idx<index){
            pre = node;
            node = node.next;
            idx++;
        }
        if(node != null){
            pre.next = node.next;
            if(pre == head){
                tail = head;
            }
            if(node.next == null){
                tail = pre;
            }
        }


    }
}