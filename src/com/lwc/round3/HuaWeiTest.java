package com.lwc.round3;

import java.util.Scanner;

public class HuaWeiTest {
    public static void main(String[] args) {
        //获取输入器
        Scanner sc = new Scanner(System.in);
        //获取所输入的数字
        int n =sc.nextInt();
        int i = 1;
        int end = 0;
        while(i<=n){
           //记录上一行需要打印数字的最大值，从而判断下一行的开始和结尾
            int end2 = end+i;
            //判断是否正序
            if(i%2 == 0){
                //行首输出空格控制格式
                for(int k = 0;k<n-i;k++){
                    System.out.print("    ");
                }
                //偶数的话逆序
                for(int j = end2;j>end;j--){
                    if(j==end2){
                        System.out.print(j+"   ");
                    }else{
                        System.out.print("    "+j+"***");
                    }

                }
                System.out.println();
            }else{
                //行首输出空格控制格式
                for(int k = 0;k<n-i;k++){
                    System.out.print("    ");
                }
                //奇数正序
                for(int j = end+1;j<=end2;j++){
                    if(j== end+1){
                        System.out.print(j+"***");
                    }else{
                        System.out.print("    "+j+"***");
                    }

                }
                System.out.println();
            }
            i++;
            end = end2;
        }
    }
}
