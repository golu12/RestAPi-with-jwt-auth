package com.international.bank.demo.service;

public class ArrayTest {
    public static void main(String args[]){
        int [] data = {3,5,-4,8,11,1,-1,6};
        int targetSum = 10;

       for(int i =0;i<data.length-1;i++){
        for(int j=(i+1) ; j <data.length ; j++){
            if(data[i] + data[j] == targetSum){
                System.out.println(data[i] + "," + data[j]);
            }
        }

        }
       }
}
