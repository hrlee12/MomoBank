package com.ssafy.user.budget.dto.response;

import java.sql.Date;

public record budgetResponseDto(
    String name,
    Date monthlyFee,
    int monthlyDueDate,
    long finalFee,
    long currentFee
){ }
