package com.ssafy.bank.transfer.presentation;

import com.ssafy.bank.common.CommonResponse;
import com.ssafy.bank.transfer.application.TransferService;
import com.ssafy.bank.transfer.dto.request.PasswordConfirmRequest;
import com.ssafy.bank.transfer.dto.request.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("transfers")
@Tag(name = "송금", description = "계좌이체")
public class TransferController {

    private final TransferService transferService;

    @Operation(summary = "송금", description = "송금합니다.")
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "송금 성공",
            transferService.transfer(request));
    }

    @PostMapping("/password-confirm")
    public ResponseEntity<?> passwordConfirm(@RequestBody PasswordConfirmRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "비밀번호 확인",
            transferService.passwordConfirm(request));
    }
}
