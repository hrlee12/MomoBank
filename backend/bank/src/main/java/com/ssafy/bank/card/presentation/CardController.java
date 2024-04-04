package com.ssafy.bank.card.presentation;

import com.ssafy.bank.card.application.CardInfoService;
import com.ssafy.bank.card.dto.request.CreateCardInfoRequest;
import com.ssafy.bank.card.dto.request.DeleteCardInfoRequest;
import com.ssafy.bank.card.dto.response.CardInfoResponse;
import com.ssafy.bank.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("card")
@Tag(name = "카드", description = "카드 생성 및 조회")
public class CardController {
    private final CardInfoService cardInfoService;
    @Operation(summary = "당행 카드 생성", description = "모모뱅크의 카드를 생성합니다.")
    @PostMapping("/create-card")
    public ResponseEntity<?> createCard(@RequestBody CreateCardInfoRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 카드를 생성했습니다.",
            cardInfoService.createCardInfo(request.memberId(), request.cardProductId(), request.accountId()));
    }

    @Operation(summary = "카드 삭제", description = "사용자의 카드를 삭제합니다.")
    @DeleteMapping("/delete-card")
    public ResponseEntity<?> deleteCard(@RequestBody DeleteCardInfoRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 카드를 삭제했습니다.",
            cardInfoService.deleteCardInfo(request.cardId()));
    }

}
