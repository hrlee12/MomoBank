package com.ssafy.bank.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReturnPasswordDTO {
    private String id;
    private String oldPassword;
    private String newPassword;
}
