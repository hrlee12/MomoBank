package com.ssafy.user.budget.dto.request;

import java.time.LocalDate;

public record UpdateBudgetRequest(
    int memberId,
    int monthlyDueDate,

    long monthlyFee,
    String name,
    long finalFee,
    LocalDate finalDueDate
) {

}
