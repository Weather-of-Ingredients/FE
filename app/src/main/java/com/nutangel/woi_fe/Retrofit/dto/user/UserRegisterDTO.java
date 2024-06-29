package com.nutangel.woi_fe.Retrofit.dto.user;

public class UserRegisterDTO {
    private String name; // 이름

    private String loginId; // 아이디

    private String password; // 비밀번호

    private String checkedPassword; // 비밀번호 확인

    private String email;

    private String phoneNum;

    private String school;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCheckedPassword() {
        return checkedPassword;
    }

    public void setCheckedPassword(String checkedPassword) {
        this.checkedPassword = checkedPassword;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
