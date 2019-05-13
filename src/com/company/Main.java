package com.company;

public class Main {

    public static void main(String[] args) {
        Operation operation = new Operation();
        System.out.println();
        System.out.println("一共:"+operation.leastOpsExpressTarget(3,365));
    }
}
