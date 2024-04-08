package com.ssafy.community.recommend.presentation;

import com.ssafy.community.recommend.application.RecommendService;
import com.ssafy.community.recommend.dto.CardDto;
import com.ssafy.community.recommend.dto.CardsCategoryDto;
import com.ssafy.community.recommend.dto.UserSelectionDto;
import com.ssafy.community.report.dto.ActivityPreferencesDto;
import com.ssafy.community.report.dto.MonthlyReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;


    public ResponseEntity<List<CardDto>> getCards() {

        List<CardDto> cards = RecommendService.getCards("card/cards.json");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.ok()
                .headers(headers)
                .body(cards);
    }



    public ResponseEntity<CardsCategoryDto> getCardsCategory() {


        CardsCategoryDto cardsCategoryDto = recommendService.getCardsCategory("card/cards_data.json");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.ok()
                .headers(headers)
                .body(cardsCategoryDto);
    }



    @Operation(summary = "카드 추천 결과 리턴",
            description = "유저의 선택을 기반으로 카드 추천 결과와 설명을 리턴합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "유저의 선택 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"selections\": {\"1\": [0], \"2\": [1], \"3\": [2], \"4\": [1]}}"
                            )
                    )
            )
    )
    @PostMapping("/recommend-card")
    public ResponseEntity<List<CardDto>> RecommendCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "선택", required = true, content = @Content(schema = @Schema(implementation = UserSelectionDto.class)))
            @RequestBody UserSelectionDto userSelectionDto) {

        List<CardDto> cards = recommendService.recommendCard(userSelectionDto, getCards().getBody(), getCardsCategory().getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.ok()
                .headers(headers)
                .body(cards);
    }




//    @Operation(summary = "월간 리포트 데이터 수집",
//            description = "지정된 월의 리포트 데이터를 수집합니다.",
//            responses = {
//                    @ApiResponse(responseCode = "200",
//                            description = "데이터 수집 성공",
//                            content = @Content(mediaType = "application/json",
//                                    schema = @Schema(implementation = ActivityPreferencesDto.class))),
//                    @ApiResponse(responseCode = "400",
//                            description = "잘못된 요청 형식"),
//                    @ApiResponse(responseCode = "500",
//                            description = "서버 내부 오류")
//            })
//    @PostMapping("/get-cards")
//    public ResponseEntity<MonthlyReportDto> getCards() {
//
//
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(monthlyReportDto);
//    }






}
