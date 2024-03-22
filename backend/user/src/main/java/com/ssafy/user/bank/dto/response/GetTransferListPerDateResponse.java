package com.ssafy.user.bank.dto.response;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTransferListPerDateResponse {
    private Date date;
    private List<GetTransferResponse> transferlist;
}
