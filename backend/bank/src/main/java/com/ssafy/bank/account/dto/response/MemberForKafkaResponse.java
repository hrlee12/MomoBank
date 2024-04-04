package com.ssafy.bank.account.dto.response;

import com.ssafy.bank.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberForKafkaResponse {
    private int memberId;
    private String id;
    private String password;
    private String phoneNumber;
    private LocalDateTime birthDate;
    private String provider;
    private int sincerity;

    public MemberForKafkaResponse(Member member){
        this.memberId = member.getMemberId();
        this.id = member.getId();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.birthDate = member.getBirthDate();
        this.provider = member.getProvider();
        this.sincerity = member.getSincerity();
    }
}
