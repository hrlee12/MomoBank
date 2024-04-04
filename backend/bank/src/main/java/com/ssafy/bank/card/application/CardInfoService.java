package com.ssafy.bank.card.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.card.domain.CardInfo;
import com.ssafy.bank.card.domain.CardProduct;
import com.ssafy.bank.card.domain.repository.CardInfoRepository;
import com.ssafy.bank.card.domain.repository.CardProductRepository;
import com.ssafy.bank.card.dto.response.CardInfoResponse;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CardInfoService {

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final CardProductRepository cardProductRepository;
    private final CardInfoRepository cardInfoRepository;

    public CardInfoResponse createCardInfo(int memberId, int cardProductId, int accountId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));
        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_MEMBER);
        }

        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));
        if (account.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT);
        }

        CardProduct cardProduct = cardProductRepository.findById(cardProductId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_CARD_PRODUCT));
        if (cardProduct.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_CARD_PRODUCT);
        }

        SecureRandom secureRandom = new SecureRandom();

        int secureRandomNumber = secureRandom.nextInt(1000);

        CardInfo cardInfo = CardInfo.builder()
            .cardInfoNum("505-01-"
                + String.format("%03d", (cardProduct.getCardProductId()* member.getMemberId())%1000)
                + String.format("%03d", secureRandomNumber))
            .cardProduct(cardProduct)
            .account(account)
            .build();
        cardInfoRepository.save(cardInfo);

        return CardInfoResponse.from(cardInfo,
            cardInfo.getCardProduct().getBank() == null ?
                cardInfo.getCardProduct().getCard().getCardName() :
                cardInfo.getCardProduct().getBank().getBankName());
    }

    public CardInfoResponse deleteCardInfo(int cardInfoId) {
        CardInfo cardInfo = cardInfoRepository.findById(cardInfoId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_CARD_INFO));
        if (cardInfo.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_CARD_INFO);
        }
        cardInfo.softDelete();

        return CardInfoResponse.from(cardInfo,
            cardInfo.getCardProduct().getBank() == null ?
                cardInfo.getCardProduct().getCard().getCardName() :
                cardInfo.getCardProduct().getBank().getBankName());
    }
}
