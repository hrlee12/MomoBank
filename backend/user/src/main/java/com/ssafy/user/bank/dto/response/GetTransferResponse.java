package com.ssafy.user.bank.dto.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTransferResponse {
    private String description;
    private Date date;
    private long ammount;
    private long transferType;
}
