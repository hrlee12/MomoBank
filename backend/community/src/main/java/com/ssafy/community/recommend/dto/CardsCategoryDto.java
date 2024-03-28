package com.ssafy.community.recommend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class CardsCategoryDto {
    private Map<String, List<String>> results;

    @JsonProperty("all_card_ids")
    private List<String> allCardIds;
}
