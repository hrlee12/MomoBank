package com.ssafy.bank.transfer.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("transfer")
@Tag(name = "송금", description = "계좌이체")
public class TransferController {

    @Operation(summary = "은행사 목록", description = "은행사 목록을 조회합니다.")
    @GetMapping("/get-bank")
    public ResponseEntity<?> getBank() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "송금", description = "송금합니다.")
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer() {
        return ResponseEntity.ok().build();
    }
}
