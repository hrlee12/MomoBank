package com.ssafy.user.bank.dto.request;

public record GetAccountTransferRequest(
    int memberId,
    int accountId
) {

}
