package com.lwc.round2;

import java.util.Arrays;
import java.util.Comparator;

public class DayTwo {
    public static void main(String[] args) {
        DayTwo two = new DayTwo();
        System.out.println(two.scheduleCourse(new int[][]{{7,17},{3,12},{10,20},{9,10},{5,20},{10,19},{4,18}}));
    }
    /**
     * 难度：中
     * 题号：807
     * 时间：20211213
     * 给你一座由 n x n 个街区组成的城市，每个街区都包含一座立方体建筑。给你一个下标从 0 开始的 n x n 整数矩阵 grid ，其中 grid[r][c] 表示坐落于 r 行 c 列的建筑物的 高度 。
     *
     * 城市的 天际线 是从远处观察城市时，所有建筑物形成的外部轮廓。从东、南、西、北四个主要方向观测到的 天际线 可能不同。
     *
     * 我们被允许为 任意数量的建筑物 的高度增加 任意增量（不同建筑物的增量可能不同） 。 高度为 0 的建筑物的高度也可以增加。然而，增加的建筑物高度 不能影响 从任何主要方向观察城市得到的 天际线 。
     *
     * 在 不改变 从任何主要方向观测到的城市 天际线 的前提下，返回建筑物可以增加的 最大高度增量总和
     * 分析：大致是找到每一行每一列的最大值，然后其他的值不能操过这个横竖行所限定的值即可
     * 解题：
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        //保存最后增加结果的大小
        int res = 0;
        int len =grid.length;
        //上下观察的最大的值
        int[] row =new int[len];
        //记录左右观看的最大值
        int[] col =new int[len];
        //遍历数组然后对数组进行增加
        for(int i =0 ;i<len ;i++){
            for(int j =0 ; j<len;j++){
                //获取每一行的最大值
                if(row[i] < grid[i][j]){
                    row[i] = grid[i][j];
                }
                //获取每一列的最大值
                if(col[i] < grid[j][i]){
                    col[i] = grid[j][i];
                }
            }
        }
        //对数组的值进行增加
        for(int i =0 ;i< len ;i++){
            for(int j =0 ;j<len ;j++){
                if(row[i] > col[j]){
                    res += (col[j] - grid[i][j]);
                }else{
                    res += (row[i] - grid[i][j]);
                }
            }
        }
        return res;
    }
    /**
     * 题号:630
     * 难度：困难
     * 时间：20211214
     * 这里有 n 门不同的在线课程，按从 1 到 n 编号。给你一个数组 courses ，其中 courses[i] = [durationi, lastDayi] 表示第 i 门课将会 持续 上 durationi 天课，并且必须在不晚于 lastDayi 的时候完成。
     *
     * 你的学期从第 1 天开始。且不能同时修读两门及两门以上的课程。
     *
     * 返回你最多可以修读的课程数目。
     * 分析：也就是可以选择课程结束时间最早的，这样就可以修最多课程，同时课程的开始时间和终止时间不能重合
     * 每次状态转移应当为dp[i][0]=
     * 解题：
     */
    public int scheduleCourse(int[][] courses) {
        int len = courses.length;
        int[] choosed =new int[len];
        int res=0,dateLine=0,index=0;
        //对数组进行排序，按照结束时间的大小进行排序
       /* for(int i =0 ; i<len;i++){
            for(int j = i; j<len; j++){
                if(courses[i][1]>courses[j][1]){
                    int[] temp =courses[i];
                    courses[i] = courses[j];
                    courses[j] =temp;
                }
            }
        }*/
        Arrays.sort(courses, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];
            }
        });
       for(int i =0 ;i < len ;i++){
           //如果时间线大于加上课程持续时间则可以直接加入
            if(dateLine + courses[i][0]<=courses[i][1]){
                dateLine += courses[i][0];
                choosed[res]=courses[i][0];
                if(courses[i][0]> choosed[index]){
                    index=res;
                }
                res++;
            }else{
                //如果不能直接加入，则可以考虑替换之前已选择中时间长度最大的值
                if(choosed[index] > courses[i][0] && dateLine-choosed[index]+courses[i][0]<=courses[i][1]){
                    dateLine = dateLine-choosed[index]+courses[i][0];
                    choosed[index] = courses[i][0];
                    //换完之后还要接着寻找最大的值，已经选择的里面的最大值
                    for(int j = 0; j<res;j++){
                        if(choosed[index] < choosed[j]){
                            index = j;
                        }
                    }
                }
            }
       }
       return res;
    }
}
/**
 * 题号：911
 * 难度：中等
 * 时间：20211211
 * 给你两个整数数组 persons 和 times 。在选举中，第 i 张票是在时刻为 times[i] 时投给候选人 persons[i] 的。
 *
 * 对于发生在时刻 t 的每个查询，需要找出在 t 时刻在选举中领先的候选人的编号。
 *
 * 在 t 时刻投出的选票也将被计入我们的查询之中。在平局的情况下，最近获得投票的候选人将会获胜。
 *
 * 实现 TopVotedCandidate 类：
 *
 * TopVotedCandidate(int[] persons, int[] times) 使用 persons 和 times 数组初始化对象。
 * int q(int t) 根据前面描述的规则，返回在时刻 t 在选举中领先的候选人的编号。
 * 分析：
 * 解题：
 */
class  TopVotedCandidate {
    private int[] person;
    private int[] time;

    public TopVotedCandidate(int[] persons, int[] times) {
        person = persons;
        time = times;
    }

    public int q(int t) {
        int lenTime =time.length;
        int lenPerson = person.length;
        //定义一个计数人投票的数组
        int[] count =new int[lenPerson];
        //先找到t时刻所在数组的位置
        int pos=0;
       /* while(pos<lenTime){
            //有可能找不到该时刻
            if(time[pos] == t ){
                break;
            }else if(time[pos] > t){
                pos--;
                break;
            }
            pos++;
        }*/
       if(t>=time[lenTime-1]){
           pos =lenTime-1;
       }else{
           pos=findPoint(0,lenTime-1,t,time);
       }
        //maxcount 用于统计最大投票数，result用于保存最小获得最大票数的结果
        int maxCount = 0;
        int result = 0;
        //对票数进行统计
        for(int i =0 ; i <= pos; i++ ){
           count[person[i]]++;
           if(count[person[i]] >= maxCount){
               maxCount = count[person[i]];
               result = person[i];
           }
        }
        return result;
    }
    //二分法进行查找时间节点的位置
    public int findPoint(int min ,int max ,int aim , int[] a){
        if(max >min){
            int mid =(min + max)/2;
            if(a[mid]>aim){
                return findPoint(min,mid,aim,a);
            }
            if(a[mid] <aim){
                return  findPoint(mid+1,max ,aim ,a);
            }
            if (aim == a[mid]){
                return mid;
            }
        }else{
            return max-1>0 ? max-1:0;
        }

        return 0;
    }
    /**
     * 题号：709
     * 难度：简单
     * 时间：202111212
     * 给你一个字符串 s ，将该字符串中的大写字母转换成相同的小写字母，返回新的字符串。
     */
    public String toLowerCase(String s) {

        return s.toLowerCase();
    }

}