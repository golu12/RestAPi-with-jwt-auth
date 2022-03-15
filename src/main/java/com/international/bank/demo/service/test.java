package com.international.bank.demo.service;

import java.util.Arrays;

public class test {

    public static void main(String[] args){
/*        int [] Array = {3,5,-4,8,11,1,-1,6} ;
        Integer [] Array1 = {3,5,-4,8,11,1,-1,6} ;*/
    /*    System.out.println(Arrays.asList(Array));
        List<Integer> list = new ArrayList<>(Arrays.asList(Array));
         list.add(10);
         Array = list.toArray(Array);
        System.out.println(Arrays.toString(Array));*/
  /*      int a = 0;
        int b = 1;
        System.out.println(a);
        System.out.println(b);
        int c = a+ b;
        System.out.println(c);
        int count = 3;
        while (count < 7){
            a = b;
            b = c;
            c = a + b;
            System.out.println(c);
            count++;
        }*/
      /*  int [] data = {33,55,34,22,45};
        int min = 0;
        int  minArray = data[0];
        for(int i =1 ; i< data.length;i++){
            if (minArray < data[i]){
                minArray = data[i];
            }
        }*/


        int [] data = {1, 5, 10, 4, 7,9, 6};
            Arrays.sort(data);
            int low = 0, high = data.length - 1, ans = 0 + 1;
            while (low <= high)
            {
                int mid = (low + high) / 2;
                if (data[mid] <= ans)
                {
                    if (data[mid] == ans)
                    {
                        ans++;
                        high = data.length - 1;
                    }
                    low = mid + 1;
                }
                else
                    high = mid - 1;
            }
            System.out.println(ans);
    }
}
