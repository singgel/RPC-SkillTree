package com.hks.grpc.service;

public class HelloRequestJava {
    private String name;
    private int age;
    private double profit_rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getProfit_rate() {
        return profit_rate;
    }

    public void setProfit_rate(double profit_rate) {
        this.profit_rate = profit_rate;
    }

    public HelloRequestJava(String name, int age, double profit_rate) {
        this.name = name;
        this.age = age;
        this.profit_rate = profit_rate;
    }

    public HelloRequestJava() {
    }

    @Override
    public String toString() {
        return "HelloRequestJava{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", profit_rate=" + profit_rate +
                '}';
    }
}
