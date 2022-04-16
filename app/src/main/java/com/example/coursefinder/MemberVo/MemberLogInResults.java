package com.example.coursefinder.MemberVo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberLogInResults {
    @SerializedName("results")
    List<MemberInfo> memberInfo;

    public List<MemberInfo> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(List<MemberInfo> memberInfo) {
        this.memberInfo = memberInfo;
    }
}
