package com.ssafy.user.bank.dto.request;

public record DeleteCardRequest (
    int memberId,
    int cardId
){

}
