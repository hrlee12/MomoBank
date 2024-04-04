package com.ssafy.user.bank.dto.request;

public record GetAccountDetailRequest(
    int memberId,
    int accountId
) {

}
