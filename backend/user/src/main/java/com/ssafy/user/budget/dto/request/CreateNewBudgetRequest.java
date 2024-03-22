package com.ssafy.user.budget.dto.request;

import java.sql.Date;

public record CreateNewBudgetRequest(
    int memberId,
    int monthlyDueDate,
    String name,
    long finalFee,
    Date finalDueDate
) { }
