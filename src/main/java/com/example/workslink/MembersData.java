//package com.example.workslink;
//
//import java.awt.*;
//
//public class MembersData {
//    private String memberId;
//    private String memberUserName;
//    private String memberEmail;
//    private String memberDOB;
//    private Checkbox select;
//
//    public Checkbox getSelect() {
//        return select;
//    }
//
//    public void setSelect(Checkbox select) {
//        this.select = select;
//    }
//
//    public MembersData(String memberUserName, String memberEmail, String memberDOB) {
//        this.memberUserName = memberUserName;
//        this.memberEmail = memberEmail;
//        this.memberDOB = memberDOB;
//        this.select=new Checkbox();
//        System.out.printf("Got from AllMembers: "+ memberUserName+memberEmail+memberDOB);
//    }
//
//
//    public String getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(String memberId) {
//        this.memberId = memberId;
//    }
//
//    public String getMemberUserName() {
//        return memberUserName;
//    }
//
//    public void setMemberUserName(String memberUserName) {
//        this.memberUserName = memberUserName;
//    }
//
//    public String getMemberEmail() {
//        return memberEmail;
//    }
//
//    public void setMemberEmail(String memberEmail) {
//        this.memberEmail = memberEmail;
//    }
//
//    public String getMemberDOB() {
//        return memberDOB;
//    }
//
//    public void setMemberDOB(String memberDOB) {
//        this.memberDOB = memberDOB;
//    }
//
//
//
//}
package com.example.workslink;

import javafx.scene.control.CheckBox;

public class MembersData {


    private String memberId;
    private String memberUserName;
    private String memberEmail;
    private String memberDOB;
    private CheckBox select;
  //  private Button sendRqst;


    public MembersData(String memberUserName, String memberEmail, String memberDOB, String id) {
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberDOB = memberDOB;
        this.memberId=id;
        this.select=new CheckBox();
     //   this.sendRqst=new Button("Send Request");



    }

//    public Button getSendRqst() {
//        return sendRqst;
//    }
//
//    public void setSendRqst(Button sendRqst) {
//        this.sendRqst = sendRqst;
//    }

    public MembersData(String memberUserName, String memberEmail, String memberDOB){
        this.memberDOB=memberDOB;
        this.memberEmail=memberEmail;
        this.memberUserName=memberUserName;


        // this.sendRqst = new Button("Send Request");
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberDOB() {
        return memberDOB;
    }

    public void setMemberDOB(String memberDOB) {
        this.memberDOB = memberDOB;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
}
