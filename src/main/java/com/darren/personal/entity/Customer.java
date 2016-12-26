package com.darren.personal.entity;

import java.util.Date;

public class Customer {

    private String phone;
    private String name;
    private int age;
    /**
     * M:male F:female
     */
    private String gender;
    private String comment;
    /**
     * Y:send N:not send
     */
    private String emailState;
    private Date sendTIme;
    /**
     * Y:pay N:not pay
     */
    private String payState;
    private Date createTime;
    private Date deleteTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public String getEmailState() {
        return emailState;
    }

    public void setEmailState(String emailState) {
        this.emailState = emailState;
    }

    public Date getSendTIme() {
        return sendTIme;
    }

    public void setSendTIme(Date sendTIme) {
        this.sendTIme = sendTIme;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

}
