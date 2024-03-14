package com.ssafy.user.budget.dto.response;

import java.sql.Date;

public record budgetResponseDto(
    String name,
    long monthlyFee,
    Date monthlyDueDate,
    long finalFee,
    long currentFee
){ }
