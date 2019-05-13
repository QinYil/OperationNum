package com.company;

import java.util.*;

public class Operation {

    public int leastOpsExpressTarget(int x, int target) {
        System.out.println();
        OrderOperationUtil orderOperationUtil = new OrderOperationUtil(x);
        int i =0;
        while (true){
            i++;
            orderOperationUtil.reSet(i,target);
            if (orderOperationUtil.operation()) {
                System.out.println(orderOperationUtil.print());
                return i - 1;
            }
        }
    }
    private static class OperationCharUtil{
        private static OperationChar[] list ;

        public static OperationChar getOperationChars(Integer serial){
            if (list==null){
                list = new OperationChar[4];
                list[0]=OperationChar.MULTIPLICATION;
                list[1]=OperationChar.DIVISION;
                list[2]=OperationChar.ADDITION;
                list[3]=OperationChar.SUBTRACTION;
            }
            return list[serial];
        }
    }
    private enum OperationChar{
        MULTIPLICATION(0,0),DIVISION(1,1),ADDITION(2,2),SUBTRACTION(3,2);
        private Integer serial;
        private Integer order;
        OperationChar(Integer serial,Integer order){
            this.serial=serial;
            this.order=order;
        }
        public Integer getSerial() {
            return serial;
        }
        public Integer getOrder() {
            return order;
        }
    }
    private class OrderOperationUtil{
        private int sourceNum;
        private int target;
        private int numQiatity;
        private OperationChar[] operations;
        private StringBuilder stringBuilder;
        Stack<OperationChar> operationStack;
        Stack<Integer> numStack;
        public OrderOperationUtil(int source){
            this.sourceNum=source;
            this.numQiatity = 1;
            this.target = source;
            this.operations = new OperationChar[1];
            this.numStack = new Stack<Integer>();
            this.operationStack = new Stack<OperationChar>();
            stringBuilder = new StringBuilder(String.valueOf(sourceNum));
        }
        public void reSet(int numQiatity,int target) { this.target = target;
            this.numQiatity = numQiatity;
            this.operations = new OperationChar[numQiatity-1];
        }
        public boolean operation(){
            return numOperation(numQiatity-1);
        }
        String print(){
            stringBuilder = new StringBuilder(String.valueOf(sourceNum));
            for (int i=0;i<operations.length;i++){
                String oper="";
                switch (operations[i].serial){
                    case 0:
                        oper="*";
                        break;
                    case 1:
                        oper="/";
                        break;
                    case 2:
                        oper="+";
                        break;
                    case 3:
                        oper="-";
                        break;
                }
                stringBuilder.append(oper).append(String.valueOf(sourceNum));
            }
            return stringBuilder.toString();
        }
        private int operationAllNum(){
            for (int i=0;i<operations.length;i++) {
                if (numStack.empty()){
                    numStack.push(sourceNum);
                }
                while (!operationStack.empty()&&operationStack.peek().order<=operations[i].order){
                    numStack.push(towNumoperation(operationStack.pop().serial,numStack.pop(),numStack.pop()));
                }
                operationStack.push(operations[i]);
                numStack.push(sourceNum);

            }
            while (!operationStack.empty()&&numStack.size()>1){
                numStack.push(towNumoperation(operationStack.pop().serial,numStack.pop(),numStack.pop()));
            }
            int target =  numStack.pop();
            operationStack.clear();
            numStack.clear();
            return target;
        }
        private boolean numOperation(int operationNum){
            if (numQiatity==1){
                return sourceNum==target?true:false;
            }
            for (int i=0;i<4;i++){
                operations[numQiatity-1-operationNum]=OperationCharUtil.getOperationChars(i);
                if (operationNum==1){
                    if (this.operationAllNum()==this.target){
                        return true;
                    }
                    if (i==3){
                        return false;
                    }
                    continue;
                } else {
                    if (numOperation(operationNum-1)){
                        return true;
                    }
                    continue;
                }
            }
            if (operationNum==0){
                return sourceNum==target?true:false;
            }
            return false;
        }
        private int towNumoperation(int status,int o2,int o1)  {
            switch (status){
                case 0:
                    return o1*o2;
                case 1:
                    try {
                        return o1/o2;
                    } catch (Exception e) {
                        System.out.println(print());
                        numStack.clear();
                        operationStack.clear();
                        //  operationAllNum();
                        e.printStackTrace();
                    }
                case 2:
                    return o1+o2;
                case 3:
                    return o1-o2;
            }
            return 0;
        }

    }
}
