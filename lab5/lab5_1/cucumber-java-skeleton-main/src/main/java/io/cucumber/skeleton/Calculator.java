package io.cucumber.skeleton;

import java.util.Arrays;

public class Calculator {
    private Integer[] ints;
    private String op;

    public Calculator() {
        this.ints = new Integer[]{null, null};
        this.op = null;
    }

    public void push(int integer) {
        int i;
        for (i = 0; i < ints.length; i++)
            if (ints[i] == null)
                break;
        ints[i] = integer;
    }

    public void push(String op) {
        this.op = op;
    }

    public int value() {
        switch(op) {
            case "+":
                return ints[0] + ints[1];
            case "-":
                return ints[0] - ints[1];
            default:
                return 0;
        }
    }
}
