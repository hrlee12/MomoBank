package com.ssafy.community.recommend.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.community.recommend.dto.CardDto;
import com.ssafy.community.recommend.dto.CardsCategoryDto;
import com.ssafy.community.recommend.dto.UserSelectionDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecommendService {

    public static List<CardDto> getCards(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource(filePath);
        List<CardDto> cardDtos = null;

        try {
            cardDtos = mapper.readValue(resource.getInputStream(), new TypeReference<List<CardDto>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cardDtos;
    }


    public CardsCategoryDto getCardsCategory(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource(filePath);
        CardsCategoryDto cardDtos = null;

        try {
            cardDtos = mapper.readValue(resource.getInputStream(), new TypeReference<CardsCategoryDto>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cardDtos;
    }


    public void recommendCard(UserSelectionDto userSelectionDto,List<CardDto> cards, CardsCategoryDto cardsCategoryDto) {
        Map<String, List<String>> dataMap = new HashMap<>();

        dataMap.put("1", Arrays.asList("kb국민카드", "삼성카드", "롯데카드", "BC 바로카드", "신한카드", "현대카드", "우리카드", "NH농협카드", "하나카드", "IBK기업은행", ""));
        dataMap.put("2", Arrays.asList("통신+공과금 혜택", "주유+차량정비 혜택", "쇼핑 혜택", "항공마일리지 혜택", "점심+교통 혜택", "무실적+모든가맹점 혜택", "구독/스트리밍 혜택", "해외직구 혜택", "배달앱+간편결제 혜택", "편의점+카페 혜택", "마트+교육비 혜택", "여행+바우처 혜택", "제휴/PLCC 혜택", "증권사CMA 혜택", ""));
        dataMap.put("3", Arrays.asList("할인형", "포인트형", "마일리지형", ""));
        dataMap.put("4", Arrays.asList("예측 불가능", "30만원 이하", "30만원 이상"));

        Map<String, Integer> cardPoint = new HashMap<>();

        for(String cardId : cardsCategoryDto.getAllCardIds()) {
            cardPoint.put(cardId, 0);
        }

        for(int i = 1; i <= 4; i++) {
            System.out.println(String.valueOf(i)); // 1
            System.out.println(userSelectionDto.getSelections().get(String.valueOf(i))); // [0, 3, 4]
            System.out.println(dataMap.get(String.valueOf(i))); // [kb국민카드, 삼성카드, 롯데카드, BC 바로카드, 신한카드, 현대카드, 우리카드, NH농협카드, 하나카드, IBK기업은행, ]

            for(int j : userSelectionDto.getSelections().get(String.valueOf(i))) {

                System.out.println(dataMap.get(String.valueOf(i)).get(j)); // kb국민카드

                for(String id : cardsCategoryDto.getResults().get(dataMap.get(String.valueOf(i)).get(j))) {
                    cardPoint.put(id, cardPoint.get(id) + 1);
                }
            }

            System.out.println("------------------------------------------------------------");
        }

        Map<String, Integer> sortedByValueDesc = cardPoint.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        List<CardDto> recommendedCards = new ArrayList<>();

        for(CardDto card : cards) {
            if(sortedByValueDesc.keySet().contains(card.getId())) {
                recommendedCards.add(card);
                System.out.println(card);
            }
        }
        // System.out.println(recommendedCards);





    }

}
