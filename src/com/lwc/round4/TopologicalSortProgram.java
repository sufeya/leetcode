package com.lwc.round4;

import java.util.*;

public class TopologicalSortProgram {
    /**
     * 题号：207
     * 难度：中等
     * 时间：20220605
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     *
     * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     *
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int count =0;
        //判断改图是否符合规则，成环了就说明不符合规则
        boolean valid = true;
        List<Integer>[] graph =new List[numCourses];
        for(int i =0 ;i<numCourses;i++){
            graph[i] = new ArrayList<>();
        }
        //记录所有节点对应的度
        Map<Integer,Integer> degree = new HashMap<>();
        //先跟据数组构建图
        for(int[] curse:prerequisites){
           List<Integer> list =  graph[curse[1]];
           list.add(curse[0]);
           degree.put(curse[0],degree.getOrDefault(curse[0],0)+1);
        }

        //广度优先遍历将度为零的进行入队
        Queue<Integer> queue =new ArrayDeque();
        for(int i =0 ;i<numCourses;i++){
            if(!degree.containsKey(i)){
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()){
           int temp =  queue.poll();
           count++;
           List<Integer> list = graph[temp];
           for(int i :list){
               int j = degree.get(i)-1;
               if(j == 0){
                   queue.offer(i);
               }
               degree.put(i,j);
           }
        }
        return count == numCourses;
    }

    public static void main(String[] args) {
        TopologicalSortProgram tsp = new TopologicalSortProgram();
        System.out.println(tsp.canFinish(2,new int[][]{{1,0}}));
    }
}
