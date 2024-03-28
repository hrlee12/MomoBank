package com.ssafy.community.report.presentation;

import com.ssafy.community.report.dto.*;
import com.ssafy.community.report.application.ReportService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
            description = "매월 데이터를 읽어와서 리포트를 작성합니다.",
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
    public ResponseEntity<ReportsDto> makeReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "연도와 월 (YYYY.MM 형식)", required = true, content = @Content(schema = @Schema(implementation = String.class))) String yearMonth) throws IOException
    {

        MonthlyReportDto monthlyReportDto = collectMonthlyReportData("2023.03").getBody();

//        String reportStr = reportService.getAIReport(monthlyReportDto);
        String reportStr = "## 1. 회계\n" +
                "\n" +
                "- **불완전 납부**: 김철수님이 15,000원을 미납하셨습니다. 다음 회차에는 납부를 잊지 말아주세요.\n" +
                "- **초과 입금**: 이영희님께서는 30,000원을 초과 납부하셨습니다. 너무 감사합니다! 모임의 발전에 큰 도움이 됩니다.\n" +
                "- **완납**: 홍길동님은 물론이고 모임비를 제때 납부하셨습니다. 감사합니다!\n" +
                "\n" +
                "## 2. 게시판 활동\n" +
                "\n" +
                "- '**함께 본 시간을 달리는 소녀**': 작성자 홍길동님, 좋아요 120개와 댓글 2개라는 큰 사랑을 받았습니다. 영화에 대한 세심한 후기와 그날의 모임에 대한 이야기가 독자들의 공감을 얻었네요.\n" +
                "- '**오늘의 영화 후기**': 작성자 김철수님, 좋아요 95개, 댓글 2개를 얻으셨습니다. 영화에 대한 깊이 있는 생각을 공유해주셔서 감사합니다.\n" +
                "- '**카페에서의 작은 모임**': 작성자 이영희님, 좋아요 110개, 댓글 2개를 얻었습니다. 모임 전의 모습을 공유해주셔서 더욱 풍성해진 회원님들의 이야기를 만나보았습니다.\n";


        BestMemberDto bestMemberDto = reportService.getBestMember(monthlyReportDto, reportStr);

        ReportsDto reportsDto = new ReportsDto();
        reportsDto.setReportMonth(3);
        reportsDto.setReportYear(2024);
        reportsDto.setBestMemberDto(bestMemberDto);
        reportsDto.setContent(reportStr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportsDto);

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
    @GetMapping("/recommend-next-activity")
    public ResponseEntity<List<RecommendationDto>> recommendNextActivity(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "연도와 월 (YYYY.MM 형식)", required = true, content = @Content(schema = @Schema(implementation = String.class))) String yearMonth) throws IOException {

        // 두 번째 인자 make 말고 그냥 읽어오는걸로 대체해야 함.
        List<RecommendationDto> list;


        try {
            list = reportService.getAIRecommendationNextActivity(collectMonthlyReportData("2023.03").getBody(), makeReport("2023.03").getBody().getContent());
        }catch(Exception e) {
            list = null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return ResponseEntity.ok()
                .headers(headers)
                .body(list);
    }

}
