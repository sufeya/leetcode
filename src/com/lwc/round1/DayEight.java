package com.lwc.round1;

public class DayEight {
    public static void main(String[] args) {
        DayEight eight=new DayEight();
        int[] a=new int[]{3,4,5,1};
        System.out.println(eight.countAndSay(5));
    }

    /**
     * 难度：简单
     * 题号：69
     *日期：20211014
     * 符合下列属性的数组 arr 称为 山峰数组（山脉数组） ：
     *
     * arr.length >= 3
     * 存在 i（0 < i < arr.length - 1）使得：
     * arr[0] < arr[1] < ... arr[i-1] < arr[i]
     * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
     * 给定由整数组成的山峰数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i ，即山峰顶部。
     */
    public int peakIndexInMountainArray(int[] arr) {
        int i=0,result=0,temp1 =i+1,temp2=i+2;
        while(i<arr.length-2){
            if(arr[temp1]>arr[i] && arr[temp2]< arr[temp1]){
                return 0;
            }
            i++;
            temp1=i+1;
            temp2=i+2;
        }
        return 0;
    }

    /**
     *题号：273
     *难度：困难
     *时间：20211014
     * @param num
     * @return
     * 将非负整数 num 转换为其对应的英文表示。

     * 示例 1：
     *
     * 输入：num = 123
     * 输出："One Hundred Twenty Three"
     * 示例 2：
     *
     * 输入：num = 12345
     * 输出："Twelve Thousand Three Hundred Forty Five"
     * 示例 3：
     *
     * 输入：num = 1234567
     * 输出："One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
     * 示例 4：
     *
     * 输入：num = 1234567891
     * 输出："One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
     *  One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eigh Hundred Ninety One
     *分析：
     *
     * 0 <= num <= 231 - 1
     * billion十亿，hundred百，thousand千，million百万
     */
    public String numberToWords(int num) {
        if(num ==0){
            return "Zero";
        }
        int i=1000;
        int time =0;
        String res="";
        while(num != 0){
            int temp = num % i;
            num =num /i;
            time =time +3;
            res = dealTree(temp) +res;
            switch (time){
                case 3: {
                    if(num !=0 && num % i != 0) {
                        res = "Thousand " + res;
                    }
                    break;
                }
                case 6:{
                    if(num !=0 && num % i != 0){
                    res= "Million "+res;
                    }
                    break;
                }
                case 9 :{
                    if(num !=0 ){
                        res = "Billion "+res;
                    }

                    break;
                }
            }
        }
        res=res.trim();
        return res;
    }
    //处理三位数以下的数字讲数字转化为字符
    public String dealTree(int num){
        String str="";
        //处理百位上的数据
        int k =num / 100;
        //处理两位数的十位数用英文表示
        int i=(num -k*100) / 10;
        //处理两位数的个位数用英文表示
        int j=(num -k*100 -i*10);
        switch (k) {
            case 1: {
                str += "One Hundred ";
                break;
            }
            case 2: {
                str += "Two Hundred ";
                break;
            }
            case 3: {
                str += "Three Hundred ";
                break;
            }
            case 4: {
                str += "Four Hundred";
                break;
            }
            case 5: {
                str += "Five Hundred ";
                break;
            }
            case 6: {
                str += "Six Hundred ";
                break;
            }
            case 7: {
                str += "Seven Hundred ";
                break;
            }
            case 8: {
                str += "Eight Hundred ";
                break;
            }
            case 9: {
                str += "Nine Hundred ";
                break;
            }
        }
        switch (i){
            case 1 : {
                switch (j){
                    case 0:{
                        str+="Ten ";
                        break;
                    }
                    case 1: {
                        str+="Eleven ";
                        break;
                    }
                    case 2: {
                        str+="Twelve ";
                        break;
                    }
                    case 3: {
                        str+="Thirteen ";
                        break;
                    }
                    case 4: {
                        str+="Fourteen ";
                        break;
                    }
                    case 5: {
                        str+="Fifteen ";
                        break;
                    }
                    case 6: {
                        str+="Sixteen ";
                        break;
                    }
                    case 7: {
                        str+="Seventeen ";
                        break;
                    }
                    case 8: {
                        str+="Eighteen ";
                        break;
                    }
                    case 9: {
                        str+="Nineteen ";
                        break;
                    }
                }
                break;
            }
            case 2 : {
                str+="Twenty ";
                break;
            }
            case 3 : {
                str+="Thirty ";
                break;
            }
            case 4 : {
                str+="Forty ";
                break;
            }
            case 5 : {
                str+="Fifty ";
                break;
            }
            case 6 : {
                str+="Sixty ";
                break;
            }
            case 7 : {
                str +="Seventy ";
                break;
            }
            case 8 : {
                str+="Eighty ";
                break;
            }
            case 9 : {
                str+="Ninety ";
                break;
            }
        }
        //当十位上不是1是处理个位
        if(i != 1){
            switch (j){
                case 1 : {
                    str+="One ";
                    break;
                }
                case 2 : {
                    str+="Two ";
                    break;
                }
                case 3 : {
                    str+="Three ";
                    break;
                }
                case 4 : {
                    str+="Four ";
                    break;
                }
                case 5 : {
                    str+="Five ";
                    break;
                }
                case 6 : {
                    str+="Six ";
                    break;
                }
                case 7 : {
                    str +="Seven ";
                    break;
                }
                case 8 : {
                    str+="Eight ";
                    break;
                }
                case 9 : {
                    str+="Nine ";
                    break;
                }
            }
        }

        return str;
    }

    /**
     * 题号：38
     * 难度：中
     * 时间：20211016
     *给定一个正整数 n ，输出外观数列的第 n 项。
     *
     * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
     *
     * 你可以将其视作是由递归公式定义的数字字符串序列：
     *
     * countAndSay(1) = "1"
     * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
     * 前五项如下：
     *分析：该题目指的是第一项是一个一所以读作11，第二项是对第一项进行解读第一项为11所以记作21，每次都
     * 是对上一项进行描述，判断上一项的数字组成是按照顺序对字符串进行分割解读
     * 解题：首先得设置解读字符串的方法，对每一个字符串进行解读，然后通过设置迭代的方式对每一个字符串
     * 进行迭代解读即可
     */
    public String countAndSay(int n) {
        n=n-1;
        String temp="",begin="1";
        while(n>0){
            temp=countString(begin,0);
            begin=temp;
            n--;
        }
        return begin;
    }
    //对字符串进行解读，要求对每一个最大连续的字符串数出其个数并进行分割,进行递归,
    //begin用于记录计数字符个数的开始
    public String countString(String str,int begin){
        //递归退出条件
        if(begin >= str.length()){
            return "";
        }
        //设置sb用于接收解读后的字符串
        StringBuilder sb=new StringBuilder();
        int j=begin;
        while(j <= str.length()){
            //如果j所指向的字符与begin所指向的字符不相同时，则可以确定该区间的字符个数
            if(j >= str.length() || str.charAt(j) != str.charAt(begin)){
                sb.append((j-begin)+""+str.charAt(begin));
                begin=j;
                break;
            }
            j++;

        }
        return sb.append(countString(str,begin)).toString();
    }

}
