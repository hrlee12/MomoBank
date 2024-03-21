package com.ssafy.user.budget.dto.request;

import java.sql.Date;

public record CreateNewBudgetRequest(
    String name,
    long finalFee,
    Date finalDueDate
) { }
