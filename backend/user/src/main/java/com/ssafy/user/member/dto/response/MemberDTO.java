package com.ssafy.user.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberDTO {
    private int memberId;
    private String name;
    private int sincerity;

    @QueryProjection
    public MemberDTO(int memberId, String name, int sincerity) {
        this.memberId = memberId;
        this.name = name;
        this.sincerity = sincerity;
    }

}
