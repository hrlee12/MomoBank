package com.ssafy.user.bank.domain.repository;

import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import java.util.List;

public interface AccountRepositoryCustom {

    public List<GetMyAccountResponse> findMyAccountByMember(int memberId);

    public AccountResponse findAccountDetailByMember(int memberId, int accountId);

    public SearchAccountResponse findAccountByBankAndAccount(String bankName, String accountNumber);
}
