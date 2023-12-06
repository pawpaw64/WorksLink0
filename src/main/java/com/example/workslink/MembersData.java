package com.example.workslink;

public class MembersData {
    private String memberId;
    private String memberUserName;
    private String memberEmail;
    private String memberDOB;


    public MembersData(String memberUserName, String memberEmail, String memberDOB) {
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberDOB = memberDOB;
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
}
