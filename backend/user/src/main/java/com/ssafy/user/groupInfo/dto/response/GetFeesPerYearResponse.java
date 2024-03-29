package com.ssafy.user.groupInfo.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetFeesPerYearResponse {
    private List<GetFeesPerMonthResponse> feesPerMonthList;
    private int totalAmmount;
}
