package com.lwc.round3;

import java.util.HashMap;
import java.util.Map;

public class DayThree {
    public static void main(String[] args) {
        DayThree three = new DayThree();
        System.out.println( three.trailingZeroes(0));
    }
  public int findKthNumber(int n, int k) {//2730010//2730010
        DictTree root = new DictTree(0);
        //跟据给定的n构建字典树
        for(int i = 1; i<=n ;i++){
            buildTree(i+"",root);
        }
        int[] pack ={k,0};
        findAns(root.next,pack);
        return pack[1];
    }
    public void findAns(Map<String,DictTree> map,int[] pack ){
       if(pack[0]>0){
           for(Map.Entry<String,DictTree> entry:map.entrySet()){
               //这里递归回来的时候还会接着执行一遍所以需要重新判断一遍
               if(pack[0]>0){
                   DictTree tempTree = entry.getValue();
                   //如果k减去count的值之后还大于0，则说明第k小的值不在该子节点中
                   if(pack[0]-tempTree.count>0){
                       pack[0]=  pack[0]-tempTree.count;
                   }else{
                       //如果减去k之后小于零则说明第k小的值在该子节点中
                       pack[1] = tempTree.val+pack[1]*10;
                       //同时要排除子节点中最小的值
                       pack[0]--;
                       //在遍历子节点中所有的节点
                       findAns(tempTree.next,pack);
                   }
               }else{
                   break;
               }
           }
       }
    }
    public void buildTree(String str,DictTree root){
        DictTree p = root;
        char[] chars = str.toCharArray();
        for(char c : chars){
          if(p.next.get(c+"") == null){
             p.next.put(c+"",new DictTree(c-48));
          }
          p.pre++;
          p=p.next.get(c+"");
          p.count++;
        }
    }
    //字典树类
    class DictTree{
        public int val;
        //表示每个节点下所拥有的数量
        public int count;
        public int pre;
        //表明下一个节点
        public Map<String,DictTree> next;
        public DictTree(int val){
            this.val = val;
            count=0;
            pre =0;
            next = new HashMap<>();
        }
    }
    //上述答案存在时间超时，下面是跟据别人的解析得到的答案
    public int findKthNumber2(int n, int k) {
        //pre等于就要事先先排除1也就是要k--
        int pre =1;
        k--;
        while(k>0){
            int count = getCount(pre,n);
            //如果k-count小于时说明第k小的数就在前缀为pre的节点当中
            if(k-count<0){
                //则开始遍历其子节点，但是要先判断就以该前缀为数的数是否是第k小
                pre*=10;
                k--;
            }else{
                //如果不在就pre++，注意这里就算是k-count == 0 也不是指第k个数就再pre的前缀中
                pre++;
                //更新k的值要减去前缀为pre的所有节点
                k-=count;
            }
        }
       return pre;
    }
    //计算前缀为pre下面所有的节点的数量
    public int getCount(int pre,int n){
        int count =0;
        long prev = pre;
        long next = pre+1;
        //例如前缀为1的节点数，next就是2，先计算的是2-1，再接着进行迭代计算也就是20-10，往后
        //同理计算一直到n+1,为什么是n+1因为当到最大值溢出时，n本身也要计算到当中去
        while(prev<=n){
            count +=Math.min(next,n+1)-prev;
            prev *=10;
            next *= 10;
        }
        return count;
    }
    /**
     * 题号：661
     * 难度：简单
     * 时间：20220324
     * 图像平滑器 是大小为 3 x 3 的过滤器，用于对图像的每个单元格平滑处理，平滑处理后单元格的值为该单元格的平均灰度。
     *
     * 每个单元格的  平均灰度 定义为：该单元格自身及其周围的 8 个单元格的平均值，结果需向下取整。（即，需要计算蓝色平滑器中 9 个单元格的平均值）。
     *
     * 如果一个单元格周围存在单元格缺失的情况，则计算平均灰度时不考虑缺失的单元格（即，需要计算红色平滑器中 4 个单元格的平均值）。
     */
    public int[][] imageSmoother(int[][] img) {
        int line=img.length;
        int col = img[0].length;
        //操作范文
        int[][] offset ={{1,1},{1,0},{0,1},{0,0},{-1,0},{0,-1},{-1,-1},{-1,1},{1,-1}};
        int[][] ans = new int[line][col];
        //直接暴力遍历即可
        for(int i = 0 ;i<line;i++){
            for(int j = 0; j<col ;j++){
                int sum =0,count=0;
               for(int[] temp :offset){
                   if(i+temp[0]<line && i+temp[0]>=0 && j+temp[1]<col && j+temp[1]>=0){
                       sum+=img[i+temp[0]][j+temp[1]];
                       count++;
                   }
               }
               ans[i][j] =sum/count;
               sum=0;
                count=0;
            }
        }
        return ans;
    }
    /**
     * 题号：172
     * 难度：中等
     * 时间：20220325
     * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
     *
     * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
     */
    public int trailingZeroes(int n) {
        int k = n;
        int countFive=0,countTwo=0;
        //计数零时可以边乘然后在这个相乘的过程中边进行累加10的个数
        while(k>0){
            int tempFive=k,tempTwo=k;
            //当k能够被五整除时进行累加五的个数
            while(tempFive%5 == 0){
                tempFive /=5;
                countFive++;
            }
            while(tempTwo %2 ==0){
                tempTwo /=2;
                countTwo++;
            }
            k--;
        }
        return Math.min(countFive,countTwo);
    }
}
