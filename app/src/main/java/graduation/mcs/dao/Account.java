package graduation.mcs.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "account".
 */
public class Account implements Serializable{

    private Long id;
    /** Not-null value. */
    private String nickname;
    /** Not-null value. */
    private String phone;
    private String password_plain;
    private String password_encrypted;
    private String real_name;
    private String sax;
    private Long birthday;
    private String job;
    private String work_place;
    private byte[] head_img;
    private Integer status;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Account(Long id, String nickname, String phone, String password_plain, String password_encrypted, String real_name, String sax, Long birthday, String job, String work_place, byte[] head_img, Integer status) {
        this.id = id;
        this.nickname = nickname;
        this.phone = phone;
        this.password_plain = password_plain;
        this.password_encrypted = password_encrypted;
        this.real_name = real_name;
        this.sax = sax;
        this.birthday = birthday;
        this.job = job;
        this.work_place = work_place;
        this.head_img = head_img;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getNickname() {
        return nickname;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /** Not-null value. */
    public String getPhone() {
        return phone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword_plain() {
        return password_plain;
    }

    public void setPassword_plain(String password_plain) {
        this.password_plain = password_plain;
    }

    public String getPassword_encrypted() {
        return password_encrypted;
    }

    public void setPassword_encrypted(String password_encrypted) {
        this.password_encrypted = password_encrypted;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getSax() {
        return sax;
    }

    public void setSax(String sax) {
        this.sax = sax;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public byte[] getHead_img() {
        return head_img;
    }

    public void setHead_img(byte[] head_img) {
        this.head_img = head_img;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
