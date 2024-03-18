package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankHomeGroupResponse {
    @Schema(defaultValue = "모임 이름")
    private String name;
    @Schema(defaultValue = "설명")
    private String desc;
    @Schema(defaultValue = "납부액")
    private Long amount;
    @Schema(defaultValue = "그룹원 수")
    private int memberCount;

    // Getters and Setters
}
