package com.ssafy.user.budget.dto.request;

import java.sql.Date;

public record budgetRequestDto(
    String name,
    long finalFee,
    Date finalDueDate
) { }
