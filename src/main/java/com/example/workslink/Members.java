package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class Members {
    private SimpleStringProperty memberId;
    private SimpleStringProperty memberUserName;
    private SimpleStringProperty memberEmail;
    private SimpleStringProperty memberDOB;


    public Members(SimpleStringProperty memberId, SimpleStringProperty memberUserName, SimpleStringProperty memberEmail, SimpleStringProperty memberDOB) {
        this.memberId = memberId;
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberDOB = memberDOB;
    }

    public String getMemberId() {
        return memberId.get();
    }

    public SimpleStringProperty memberIdProperty() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId.set(memberId);
    }

    public String getMemberUserName() {
        return memberUserName.get();
    }

    public SimpleStringProperty memberUserNameProperty() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName.set(memberUserName);
    }

    public String getMemberEmail() {
        return memberEmail.get();
    }

    public SimpleStringProperty memberEmailProperty() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail.set(memberEmail);
    }

    public String getMemberDOB() {
        return memberDOB.get();
    }

    public SimpleStringProperty memberDOBProperty() {
        return memberDOB;
    }

    public void setMemberDOB(String memberDOB) {
        this.memberDOB.set(memberDOB);
    }
}
