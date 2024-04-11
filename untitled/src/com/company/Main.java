package com.company;

import java.util.Scanner;

public class Main {

    static void showfibonacci(int no){
        int f1, f2=0, f3=1;
        for(int i=1;i <=no;i++){
            System.out.println(f3);
            f1 = f2;
            f2 = f3;
            f3 = f1 + f2;
        }
    }

    public static void main(String args[]){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Number: ");
        int n = s.nextInt();

        if(n > 0){
            showfibonacci(n);
        }else{
            System.out.println("Please enter positive number");
        }
    }

}
