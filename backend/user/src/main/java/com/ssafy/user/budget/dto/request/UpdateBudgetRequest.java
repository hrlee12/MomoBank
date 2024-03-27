package com.ssafy.user.budget.dto.request;

import java.time.LocalDate;

public record UpdateBudgetRequest(
    int memberId,
    int monthlyDueDate,
    String name,
    long finalFee,

    long monthlyFee,
    LocalDate finalDueDate
) {

}
