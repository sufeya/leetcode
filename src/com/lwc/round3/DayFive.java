package com.lwc.round3;


import java.util.*;

public class DayFive {


    /**
     * 题号：954
     * 难度：中等
     * 时间：20220401
     * 给定一个长度为偶数的整数数组 arr，只有对 arr 进行重组后可以满足 “对于每个 0 <= i < len(arr) / 2，都有 arr[2 * i + 1] = 2 * arr[2 * i]” 时，返回 true；否则，返回 false。
     * 分析:该题可以转化将一个数组分成相等的两份，找到与其对位绝对值是其两倍的数组即可
     * @param arr
     * @return
     */
    public boolean canReorderDoubled(int[] arr) {
       int len = arr.length;
       //直接采用冒泡排序进行排列
        for(int i=0;i<len;i++){
            for(int j =i ;j<len;j++){
                if(Math.abs(arr[i])>Math.abs(arr[j])){
                    int temp = arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        //需要把零排除在外
        int countZ=0;
        while(arr[countZ] == 0){
            countZ++;
        }
        if(countZ%2!=0){
            return false;
        }

        List<Integer> list = new ArrayList<>();
        for(int i = len/2+countZ-1;i<len;i++){
            list.add(arr[i]);
        }
        for(int i =countZ;i<len/2;i++){
            if(!list.contains(2*arr[i])){
                return false;
            }
        }
        return true;
    }
    //看完解析后的答案
    public boolean canReorderDoubled2(int[] arr) {
        Map<Integer,Integer> map =new HashMap<>();
        for(int i :arr){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        //将零先处理
        if(map.getOrDefault(0,0)%2!=0){
            return false;
        }
        List<Integer> list = new ArrayList<>();
        for(int i :map.keySet()){
            list.add(i);
        }
        //给整个集合进行排序
        list.sort((a,b)->{return Math.abs(a)-Math.abs(b);});
        for(int i :list){
            if(map.getOrDefault(2*i,0)<map.getOrDefault(i,0)){
                return false;
            }
            map.put(2*i,map.getOrDefault(2*i,0)-map.getOrDefault(i,0));
        }
        return true;
    }
    /**
     * 题号：420
     * 难度：困难
     * 时间：20220402
     * 如果一个密码满足下述所有条件，则认为这个密码是强密码：
     * 由至少 6 个，至多 20 个字符组成。
     * 至少包含 一个小写 字母，一个大写 字母，和 一个数字 。
     * 同一字符 不能 连续出现三次 (比如 "...aaa..." 是不允许的, 但是 "...aa...a..." 如果满足其他条件也可以算是强密码)。
     * 给你一个字符串 password ，返回 将 password 修改到满足强密码条件需要的最少修改步数。如果 password 已经是强密码，则返回 0 。
     *
     * 在一步修改操作中，你可以：
     *
     * 插入一个字符到 password ，
     * 从 password 中删除一个字符，或
     * 用另一个字符来替换 password 中的某个字符。
     */
    public int strongPasswordChecker(String password) {
        int len = password.length();
        //首先判断是否含有大写小写数字等字符
        int typeA=0,typeB=0,typeC=0;
        for(int i =0;i<len;i++){
            char c = password.charAt(i);
            if(c>='a'&&c<='z'){
                typeA=1;
            }
            if(c>='A' && c<='Z'){
                typeB=1;
            }
            if(c>='0'&&c<='9'){
                typeC=1;
            }
            if(typeA == 1 && typeB ==1&& typeC==1){
                break;
            }
        }
        int m = typeA+typeC+typeB;
        int ans = 0;
        if(len<6){
            //如果password的长度小于6的话则可能需要的操作是增加跟替换操作，这样使得操作数最小,如果存在三个四个或者五个相同的
            //只需在中间添加或者替换对应字符即可，因为首先得判断其种类需要其有三种不同的种类‘
            return Math.max(6-len,3-m);
        }else if(len>=6 && len<=20){
            //如果字符串在20以内，则需要替换能减少对应操作，连续三个以上需要进行替换
            int i=0,j=0,replace=0;
           while(j<len){
                if(password.charAt(i) ==password.charAt(j)){
                    j++;
                }else{
                    if(j-i>=3){
                        replace+=(j-i)/3;
                    }
                    i=j;
                }
            }
           if(j-i>=3){
               replace+=(j-i)/3;
           }

           return Math.max(replace,3-m);
        }else{
            //如果字符串在20以上则首先必须要进行删除操作，但是合理删除操作可以减少相对应的替换操作
            //比如连续三个字符，删除一个可以减少一次替换，四个则两个删除减少一次替换，五个则三个删除减少一个替换操作
            int[] count= new int[3];//count[i]表示连续字符串长度%3的个数
            int remove = len-20,replace=0,tempRemove=remove,i=0,j=0;
            while(j<len){
                if(password.charAt(i) ==password.charAt(j)){
                    j++;
                }else{
                    int tLen=j-i;
                    if(j-i>=3){
                        replace+=(tLen)/3;
                        count[tLen%3]++;
                    }
                    i=j;
                }
            }
            if(j-i>=3){
                replace+=(j-i)/3;
                count[(j-i)%3]++;
            }
            //对应减少替换操作,同时要考虑当其删除操作不足替换整个类型替换时
            for(int k=0 ;k<count.length;k++){
                if(k==2){

                    //因为前面处理模为一和模为二的其实只是处理了一段而已，其他的都是多个连续在一起，所以这里
                    //直接代替模为二的进行计算即可
                    count[k] = replace;
                }
                if(tempRemove>0&&count[k] != 0){
                    //移除更小的才可以以防为负
                    int temp = Math.min(tempRemove,count[k]*(k+1));
                    replace-=temp/(k+1);
                    tempRemove-=temp;
                }
            }
            return remove+Math.max(replace,3-m);
        }

    }

    /**
     * 题号：762
     * 难度：简单
     * 时间：20220405
     * 给你两个整数 left 和 right ，在闭区间 [left, right] 范围内，统计并返回 计算置位位数为质数 的整数个数。
     *
     * 计算置位位数 就是二进制表示中 1 的个数。
     *
     * 例如， 21 的二进制表示 10101 有 3 个计算置位。
     */
    public int countPrimeSetBits(int left, int right) {
        int ans =0;
        List<Integer> lst = Arrays.asList(new Integer[]{ 2, 3, 5, 7, 11, 13, 17,19});
        for(int i =left; i<=right;i++){
            //位运算符进行计数
            int count =0;
            int temp =i;
            while(temp>0){
                if((temp&1) == 1 ){
                    count++;
                }
                temp=(temp>>1);
            }
            if(lst.contains(count)){
                ans++;
            }

        }
        return ans;
    }

    public static void main(String[] args){
        DayFive five =new DayFive();
        System.out.println(  five.countPrimeSetBits(6,10));
    }
}
//前缀和求取有问题
class NumArray2 {
    int[] prex;
    int[] nums;

    public NumArray2(int[] nums) {
        prex = new int[nums.length];
        this.nums=nums;
        prex[0]=nums[0];
        //计算前缀和
        for(int i =1;i<nums.length;i++){
            prex[i]=prex[i-1]+nums[i];
        }
    }

    public void update(int index, int val) {
        int  diff = val-nums[index];
        nums[index]=val;
        //跟新前缀和
        for(int i=index;i<prex.length;i++){
            prex[i]+=diff;
        }
    }

    public int sumRange(int left, int right) {
        return prex[right]-prex[left]+nums[left];
    }
}
/**
 * 给你一个数组 nums ，请你完成两类查询。
 *
 * 其中一类查询要求 更新 数组 nums 下标对应的值
 * 另一类查询要求返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * int sumRange(int left, int right) 返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和 （即，nums[left] + nums[left + 1], ..., nums[right]）
 *  

 */
class NumArray{
    public TreeNode root;
    class TreeNode{
        //表示的是该节点的范围
        public int start,end;
        //该节点范围对应的值
        public int sum;
        public TreeNode right,left;
        public TreeNode(int start,int end){
            this.start=start;
            this.end =end;
        }
    }
    public NumArray(int[] nums) {
        root = buildTree(nums,0,nums.length-1);
    }
    public void update(int index, int val) {
        update(root,index,val);
    }
    public int sumRange(int left, int right) {

        return qury(root,left,right);
    }
    //查询值
    public int qury(TreeNode node ,int left,int right){
        if(node !=null){
            int mid = node.start + ((node.end -node.start)>>1);
            if(node.start==left && node.end==right){
                return node.sum;
            }else if(right<=mid){
                //如果在左边
                return qury(node.left,left,right);
            }else if(left>mid){
                //如果在右边
                return qury(node.right,left,right);
            }else{

                //如果在中间
                return qury(node.left,left,mid)+qury(node.right,mid+1,right);
            }
        }else{
            return 0;
        }

    }
    //更新节点的值
    public void update(TreeNode node,int index,int val){
        if(node !=null){
            if(node.start == index && node.end == index ){
                node.sum=val;
            }else{
                int mid = node.start + ((node.end -node.start)>>1);
                //如果index小于或等于mid则说明在左子树
                if(index<=mid){
                    update(node.left,index,val);
                }else{
                    //如果大于则说明在右子树
                    update(node.right,index,val);
                }
                int rightVal=0,leftVal=0;
                if(node.left != null){
                    leftVal=node.left.sum;
                }
                if(node.right != null){
                    rightVal=node.right.sum;
                }
                node.sum=rightVal+leftVal;
            }

        }
    }

    //构建树
    public TreeNode buildTree(int[] nums,int start,int end ){
        //如果start和end是一样的则直接等于索引值
        if(start == end){
            TreeNode node = new TreeNode(start,end);
            node.sum=nums[start];
            return node;
        }else if(start > end ){
            return null;
        }else{
            TreeNode node = new TreeNode(start,end);

            int mid = start + ((end -start)>>1);
            node.left=buildTree(nums,start,mid);
            node.right=buildTree(nums,mid+1,end);
            node.sum=node.left.sum+node.right.sum;
            return node;
        }
    }
}


