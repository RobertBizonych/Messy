package com.course.berkley;

import java.util.ArrayList;
import java.util.TreeSet;

public class Polynom {
    private int[] sequence; // s
    private int bit_number; // n
    private int denote_point; // k
    private int length; // l
    private int controlFlagA; // a
    private int controlFlagB; // b
    private TreeSet<Integer> denote_list; // f
    private TreeSet<Integer> global_bits; // g

    public Polynom(int[] sequence) {
        this.sequence = sequence;
        this.bit_number = sequence.length;
        this.controlFlagB = 0;

        generateDenotePoint();
        this.length = denote_point + 1;
        this.controlFlagA = denote_point;

        this.denote_list = new TreeSet<Integer>();
        this.denote_list.add(0);
        this.denote_list.add(denote_point + 1);

        this.global_bits = new TreeSet<Integer>();
        this.global_bits.add(0);
    }

    private void generateDenotePoint() {
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = 0; i < bit_number; i++) {
            range.add(i);
        }
        for (Integer node : range) {
            denote_point = node;

            if (sequence[node] == 1) {
                break;
            }
        }
    }

    public void encode() {
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = denote_point + 1; i < bit_number; i++) {
            range.add(i);
        }

        TreeSet<Integer> temp;
        for (int n : range) {
            int digital = 0;
            for (int i : denote_list) {
                digital ^= sequence[i + n - length];
            }
            if (digital == 0) {
                controlFlagB += 1;
            } else {

                if(2 * length > n){
                    TreeSet<Integer> time = new TreeSet<Integer>();
                    for(int bit : global_bits){
                        time.add(controlFlagA - controlFlagB + bit);
                    }
                    denote_list = (TreeSet<Integer>) Comparator.symDifference(denote_list, time);
                    controlFlagB += 1;
                }else{
                    temp = new TreeSet<Integer>();
                    for(int bit : denote_list){
                        temp.add(bit);
                    }
                    TreeSet<Integer> time = new TreeSet<Integer>();
                    for(int d : denote_list){
                        time.add(controlFlagB - controlFlagA + d);
                    }
                    denote_list = (TreeSet<Integer>) Comparator.symDifference(global_bits, time);
                    length = n + 1 - length;
                    global_bits = temp;
                    controlFlagA = controlFlagB;
                    controlFlagB = n - length + 1;

                }
            }
        }

    }

    @Override
    public String toString() {
        String result = "";
        for (int i : denote_list) {

            if (i == 0) {
                result += "1";
            } else {
                result += "x^" + i;
            }
            if( i != denote_list.last() ){
                result += " + ";
            }

        }
        return "Функція зворотнього зв’язку: " + result;
    }

    public String span(){
        return "Лінійний інтервал: " + length;
    }

    public static void main(String[] args) {
        String[] input = "0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0".split(",");
        int[] sequence = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            sequence[i] = new Integer(input[i].trim());
        }
        Polynom a = new Polynom(sequence);
        a.encode();
        System.out.println(a);
    }
}
