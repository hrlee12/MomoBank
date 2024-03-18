package com.ssafy.community.report.controller;

import com.ssafy.community.report.dto.ActivityPreferencesDto;
import com.ssafy.community.report.dto.MonthlyReportDto;
import com.ssafy.community.report.entity.Recommendations;
import com.ssafy.community.report.service.ReportService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportController {


    @Autowired
    ReportService reportService;

    @Operation(summary = "월간 리포트 데이터 수집",
            description = "지정된 월의 리포트 데이터를 수집합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "데이터 수집 성공",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityPreferencesDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "잘못된 요청 형식"),
                    @ApiResponse(responseCode = "500",
                            description = "서버 내부 오류")
            })
    @PostMapping("/collect-monthly-data")
    public ResponseEntity<MonthlyReportDto> collectMonthlyReportData(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "연도와 월 (YYYY.MM 형식)", required = true, content = @Content(schema = @Schema(implementation = String.class))) String yearMonth) {
        MonthlyReportDto monthlyReportDto = MonthlyReportDto.createExample();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.ok()
                .headers(headers)
                .body(monthlyReportDto);
    }
    @Operation(summary = "월간 리포트 조회", description = "지정된 월의 월간 리포트를 조회합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReport(
            @Parameter(description = "연도와 월 (YYYY.MM 형식)", required = true) @RequestParam String yearMonth) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "월간 리포트 생성",
            description = "지정된 월의 데이터를 읽어와서 리포트를 작성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "데이터 수집 성공",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityPreferencesDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "잘못된 요청 형식"),
                    @ApiResponse(responseCode = "500",
                            description = "서버 내부 오류")
            })
    @GetMapping("/make")
    public ResponseEntity<?> makeReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "연도와 월 (YYYY.MM 형식)", required = true, content = @Content(schema = @Schema(implementation = String.class))) String yearMonth) throws IOException {

        // 데이터 가져오기
        MonthlyReportDto monthlyReportDto = collectMonthlyReportData("2023.03").getBody();

        reportService.getAIRecomendation(monthlyReportDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.ok()
                .headers(headers)
                .body(monthlyReportDto);

    }
    @Operation(summary = "연간 리포트 조회", description = "지정된 연도의 연간 리포트를 조회합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/annual")
    public ResponseEntity<?> getAnnualReport(
            @Parameter(description = "연도 (YYYY 형식)", required = true) @RequestParam int year) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "월간 대비 회비 변동 조회", description = "지난 달 대비 이번 달의 회비 변동을 조회합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/fee-changes")
    public ResponseEntity<?> getMonthlyFeeChanges(
            @Parameter(description = "조회할 월 (YYYY.MM 형식)", required = true) @RequestParam String yearMonth) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Best/Worst 활동 리포트", description = "모임의 Best 및 Worst 활동을 리포트합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/activity-report")
    public ResponseEntity<?> getActivityReport(
            @Parameter(description = "리포트 기준 월 (YYYY.MM 형식)", required = true) @RequestParam String yearMonth) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회비 납입 상태 조회", description = "지정된 월의 회비 납입 상태를 조회합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/fee-status")
    public ResponseEntity<?> getFeeStatus(
            @Parameter(description = "조회할 월 (YYYY.MM 형식)", required = true) @RequestParam String yearMonth) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "우수 회원 조회", description = "지정된 기간 동안의 우수 회원을 조회합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200")
    })
    @GetMapping("/top-members")
    public ResponseEntity<?> getTopMembers(
            @Parameter(description = "조회 시작 월 (YYYY.MM 형식)", required = true) @RequestParam String startMonth,
            @Parameter(description = "조회 종료 월 (YYYY.MM 형식)", required = true) @RequestParam String endMonth) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "통장 추천", description = "사용자의 니즈에 맞는 통장을 추천합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/recommend-account")
    public ResponseEntity<?> recommendAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "통장 추천을 위한 질문에 대한 답변", required = true, content = @Content(schema = @Schema(implementation = String.class))) String answers) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "다음 활동 추천", description = "수집된 데이터를 바탕으로 모임의 다음 활동을 추천합니다.", responses = {
            @ApiResponse(description = "성공", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/recommend-next-activity")
    public ResponseEntity<String> recommendNextActivity(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "모임의 간단한 소개, 결제내역, 게시글 데이터를 포함한 한 달 동안의 데이터", required = true, content = @Content(schema = @Schema(implementation = String.class))) String monthlyData) {

        return ResponseEntity.ok("여기에 다음 활동 추천 결과");
    }

}
