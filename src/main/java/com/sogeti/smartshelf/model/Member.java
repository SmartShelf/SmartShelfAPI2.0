/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sogeti.smartshelf.model;

import java.util.List;

/**
 *
 * @author johnmart2k
 */
public class Member {
    int age;
    String gender;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    

    @Override
    public String toString() {

          return "{ age: "+age+",gender: " + gender + "}";
    }
}
