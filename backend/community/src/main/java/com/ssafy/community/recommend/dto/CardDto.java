package com.ssafy.community.recommend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class CardDto {
    private String id;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("corp_name")
    private String corpName;

    private List<String> benefits;

    @JsonProperty("card_img_url")
    private String cardImgUrl;
}
