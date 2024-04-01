package com.ssafy.community.feed.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedUpdateRequest {
    private Integer userId;
    private String content;
}
