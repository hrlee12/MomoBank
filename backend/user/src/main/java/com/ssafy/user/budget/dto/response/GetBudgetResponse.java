package com.ssafy.user.budget.dto.response;

import com.ssafy.user.budget.domain.Budget;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBudgetResponse {

    private int budgetId;
    private String name;
    private long monthlyFee;
    private long currentFee;
    private long finalFee;
    private int monthlyDueDate;
    private Date FinalDueDate;
    private boolean status;

    public static GetBudgetResponse from(Budget budget) {
        return new GetBudgetResponse(
            budget.getBudgetId(),
            budget.getName(),
            (budget.getFinalMoney() - budget.getCurrentMoney()) / leftCollectionDate(
                budget.getMonthlyDueDate(), LocalDate.now(), budget.getDueDate().toLocalDate()),
            budget.getCurrentMoney(),
            budget.getFinalMoney(),
            budget.getMonthlyDueDate(),
            budget.getDueDate(),
            true // status
        );
    }

    public static int leftCollectionDate(int day, LocalDate today, LocalDate lastDay) {
        int cnt = 0;

        LocalDate tempDate = today.withDayOfMonth(day);

        if (tempDate.isBefore(today)) {
            tempDate = tempDate.plusMonths(1);
        }

        while (!tempDate.isAfter(lastDay)) {
            if (tempDate.getDayOfMonth() == day) {
                cnt++;
            }
            tempDate = tempDate.plusMonths(1);
        }

        return cnt;
    }
}
