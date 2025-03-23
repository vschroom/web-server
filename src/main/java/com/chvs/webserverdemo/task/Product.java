package com.chvs.webserverdemo.task;

public class Product {

    private int id;
    // битовые маски
    // 0000 0001 -> enabled -> 1
    // 0000 0010 -> readonly -> 2
    // 0000 0100 -> special -> 4
    // 0000 1000 -> provided -> 8
    // 0001 0000 -> stub -> 16
    private byte properties; // 0000 0000

    public boolean isEnable() {
        return (properties & (byte) 1) != 0;
    }

    public boolean isReadonly() {
        return (properties & (byte) 2) != 0;
    }

    public boolean isSpecial() {
        return (properties & (byte) 4) != 0;
    }

    public boolean isProvided() {
        return (properties & (byte) 8) != 0;
    }

    public boolean isStub() {
        return (properties & (byte) 16) != 0;
    }

    public void setEnable(boolean value) {
        properties = value
                ? (byte) (properties | 1)
                : (byte) (properties ^ 1);
    }

    public void setReadonly(boolean value) {
        properties = value
                ? (byte) (properties | 2)
                : (byte) (properties ^ 2);
    }

    public void setSpecial(boolean value) {
        properties = value
                ? (byte) (properties | 4)
                : (byte) (properties ^ 4);
    }

    public void setProvided(boolean value) {
        properties = value
                ? (byte) (properties | 8)
                : (byte) (properties ^ 8);
    }

    public void setStub(boolean value) {
        properties = value
                ? (byte) (properties | 16)
                : (byte) (properties ^ 16);
    }

    /*public static void main(String[] args) {
        var product = new Product();

        byte props;
        props = (byte) (product.properties | 1);
        // 0000 0000
        // |
        // 0000 0001
        // = 0000 0001
        System.out.println(props);
        props = (byte) (props | 2);
        // 0000 0001
        // |
        // 0000 0010
        // = 0000 0011
        System.out.println(props);
        props = (byte) (props | 4);
        // 0000 0011
        // |
        // 0000 0100
        // = 0000 0111
        System.out.println(props);

        // 0000 0111
        // &
        // 0000 0100
        // = 0000 0100 -> 4 -> 4 != 0 (если бы было 0000 0011 & 0000 0100 = 0000 0000 -> это 0 след. значение false)
        System.out.println((props & 4) != 0);
        System.out.println((props & 2) != 0);
        System.out.println((props & 1) != 0);

        System.out.println(7 ^ 4); // 0000 0011
        System.out.println(3 ^ 2); // 0000 0001
        System.out.println(1 ^ 1); // 0000 0000
    }*/
}
