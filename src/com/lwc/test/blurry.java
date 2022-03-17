package com.lwc.test;

import java.util.ArrayList;
import java.util.List;

public class blurry{
    public static void main(String[] args) {
        System.out.println("请输入年龄、专业相关度、学历和工作经验：");
        Elem e=new Elem();
        @SuppressWarnings("resource")
        java.util.Scanner sc=new java.util.Scanner(System.in);
        e.age=sc.nextInt();
        e.subject=sc.nextFloat();
        e.scholar=sc.nextInt();
        e.p=sc.nextFloat();
        e.result = Function(e)/8;
        System.out.println("选择此人的概率为："+e.result);
        if(e.result<0.7){
            System.out.println("淘汰");
        }
    }
    //将年龄划分等级
    static double calAge(Elem e) {
        if(e.age<0||e.age>65)
            return 0.0;
        else {
            @SuppressWarnings("unused")
            double result;
            if(e.age>=25&&e.age<=30)
                return 2.0;
            else {
                return 1.0;
            }
        }
    }
    //将专业划分等级
    static double calSubject(Elem e) {
        if(e.subject<0||e.subject>100)
            return 0.0;
        else {
            @SuppressWarnings("unused")
            double result;
            if(e.subject>=0&&e.subject<=50)
                return 1.0;
            else {
                return 2.0;
            }
        }
    }
    //将学历划分等级(从小学算到博士一共7个学位等等级)
    static double calScholar(Elem e) {
        if(e.scholar<0||e.scholar>7)
            return 0.0;
        else {
            @SuppressWarnings("unused")
            double result;
            if(e.scholar>=0&&e.scholar<5)
                return 1.0;
            else {
                return 2.0;
            }
        }
    }
    //将工作经验划分等级（最早18岁开始工作，最晚65岁退休）
    static double calP(Elem e) {
        if(e.p<0||e.p>47)
            return 0.0;
        else {
            @SuppressWarnings("unused")
            double result;
            if(e.p>=7&&e.p<=12)
                return 2.0;
            else {
                return 1.0;
            }
        }
    }
    static double Function(Elem e) {
        double result_I,result_D,result_T,result_P;
        result_I=calAge(e);
        result_D=calSubject(e);
        result_T=calScholar(e);
        result_P=calP(e);
        return (result_I+result_D+result_T+result_P);
    }
    long maxScore = 0;
    int cnt = 0;
    int n;
    List<Integer>[] children;

    public int countHighestScoreNodes(int[] parents) {
        n = parents.length;
        children = new List[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            int p = parents[i];
            if (p != -1) {
                children[p].add(i);
            }
        }
        dfs(0);
        System.out.println(maxScore);
        return cnt;
    }

    public int dfs(int node) {
        long score = 1;
        int size = n - 1;
        for (int c : children[node]) {

            int t = dfs(c);
            if(node == 34){
                System.out.println("孩子节点数"+t);
            }
            score *= t;
            size -= t;
        }
        if (node != 0) {
            score *= size;
        }
        if(node == 34){
            System.out.println("分数："+score);
            System.out.println("size："+size);

        }
        if (score == maxScore) {
            cnt++;
        } else if (score > maxScore) {


            maxScore = score;
            cnt = 1;
        }
        return n - size;
    }
}

class Elem {
    int age;//年龄
    double subject;//专业
    int scholar;//学历
    double p;//工作经验
    public double result;
}



