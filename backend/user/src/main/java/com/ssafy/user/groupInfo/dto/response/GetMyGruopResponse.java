package com.ssafy.user.groupInfo.dto.response;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMyGruopResponse {

    private String name;
    private long monthlyFee;
    private Date joinDate;
    private int joinMembers;
    private Boolean state;
}