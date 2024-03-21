package com.ssafy.user.budget.dto.response;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBudgetResponse {
    private String name;
    private long monthlyFee;
    private long currentFee;
    private long finalFee;
    private int monthlyDueDate;
    private Date FinalDueDate;
    private boolean status;
}
