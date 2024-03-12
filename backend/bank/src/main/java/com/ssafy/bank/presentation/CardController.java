package com.ssafy.bank.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("card")
@Tag(name = "카드", description = "카드 생성 및 조회")
public class CardController {

    @Operation(summary = "당행 카드 상품 목록", description = "모모뱅크의 카드 상품 목록을 조회합니다.")
    @GetMapping("/get-card-product")
    public ResponseEntity<?> getCardProduct() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "당행 카드 생성", description = "모모뱅크의 카드를 생성합니다.")
    @PostMapping("/create-card")
    public ResponseEntity<?> createCard() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카드 삭제", description = "사용자의 카드를 삭제합니다.")
    @DeleteMapping("/delete-card")
    public ResponseEntity<?> deleteCard() {
        return ResponseEntity.ok().build();
    }
}
