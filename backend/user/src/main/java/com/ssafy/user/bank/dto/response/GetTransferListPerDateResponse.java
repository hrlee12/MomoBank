package com.ssafy.user.bank.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTransferListPerDateResponse {

    private LocalDate date;
    private List<GetTransferResponse> transferlist;
}
