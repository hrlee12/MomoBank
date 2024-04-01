package com.ssafy.community.member.dto;

import com.querydsl.core.annotations.QueryProjection;

public class MemberToCheckDTO {
    private String id;


    @QueryProjection
    public MemberToCheckDTO(String id){
        this.id = id;
    }
}
