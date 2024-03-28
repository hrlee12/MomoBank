package com.ssafy.community.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class MediaResponse {
    private Integer mediaId;
    private Integer sequence;
    private String mediaType;
    private String mediaUrl;
}
