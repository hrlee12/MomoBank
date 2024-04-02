package com.ssafy.user.member.presentation;


import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.user.member.dto.request.MemberRequest;
import com.ssafy.user.member.dto.response.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "은행 api")
@RestController
@RequestMapping("/bank-home")
@RequiredArgsConstructor
public class BankHomeController {

    private final MemberRepositoryCustom memberRepositoryCustom;


    @SecurityRequirements({
            @SecurityRequirement(name = "Bearer Authentication"),
            @SecurityRequirement(name = "refreshToken")})
    @GetMapping
    @ApiResponse(responseCode = "200", description = "뱅킹 메인 조회 성공",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BankHomeResponse.class))})
    public ResponseEntity getBankHome(@RequestBody MemberRequest request) {

        Member member = memberRepositoryCustom.findMemberToBankHome(request.getMemberId());


        // 더미데이터
        List<BankHomeAccountResponse> accounts = new ArrayList<>();
        List<BankHomeGroupResponse> groups = new ArrayList<>();

        for (Account account : member.getAccounts()){
            accounts.add(BankHomeAccountResponse.builder().bankName(account.getBankName()).number(account.getAccountNumber()).balance(account.getBalance()).build());
        }

        for (GroupMember groupMember : member.getGroupMembers()){
            GroupInfo group = groupMember.getGroupInfo();
            groups.add(BankHomeGroupResponse.builder().name(group.getGroupName()).desc(group.getDescription()).amount(group.getGroupMembers().size()).memberCount)

        }
        account.add(BankHomeAccountResponse.builder().bankName("모모").number("1234-56-789").balance(Long.valueOf("10000000000")).build());
        account.add(BankHomeAccountResponse.builder().bankName("모모").number("5667-56-789").balance(Long.valueOf("123000000")).build());

        List<BankHomeGroupResponse> group = new ArrayList<>();
        group.add(BankHomeGroupResponse.builder().name("5반5린이들").desc("오늘도 즐거운 어린이").amount(Long.valueOf("100000")).memberCount(6).build());
        group.add(BankHomeGroupResponse.builder().name("6반6린이들").desc("오늘도 즐거운 6").amount(Long.valueOf("200000")).memberCount(10).build());
        BankHomeResponse responseData = BankHomeResponse.builder()
                .member(BankHomeMemberResponse.builder()
                        .id("ssafy1234")
                        .name("싸피")
                        .build())
                .account(account)
                .group(group)
                .build();


        return CommonResponse.toResponseEntity(HttpStatus.OK, "뱅킹 메인 조회 성공", responseData);

    }
}