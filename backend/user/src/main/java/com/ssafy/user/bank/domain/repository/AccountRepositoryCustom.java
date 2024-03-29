package com.ssafy.user.bank.domain.repository;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import com.ssafy.user.groupMember.dto.response.AccountDTO;

import java.util.List;

public interface AccountRepositoryCustom {

    public List<GetMyAccountResponse> findMyAccountByMember(int memberId);

    public AccountResponse findAccountDetailByMember(int memberId, int accountId);

    public SearchAccountResponse findAccountByBankAndAccount(String bankName, String accountNumber);

    public Account findAccountByIdAndMemberId(int accountId, String memberId);

    public List<AccountDTO> findAccountDTOByMemberId(String memberId);
}
