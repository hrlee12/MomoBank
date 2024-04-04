package com.ssafy.user.common.exception;

import com.ssafy.user.common.ErrorCode;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.nio.charset.Charset;

public class HtmlToErrorResponseConverter extends AbstractHttpMessageConverter<ErrorResponse> {

    public HtmlToErrorResponseConverter() {
        super(MediaType.TEXT_HTML);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ErrorResponse.class == clazz;
    }

    @Override
    protected ErrorResponse readInternal(Class<? extends ErrorResponse> clazz, HttpInputMessage inputMessage) throws IOException {
        String html = StreamUtils.copyToString(inputMessage.getBody(), Charset.defaultCharset());
        // HTML에서 오류 코드와 메시지를 추출하는 로직 구현
        // 예제로, 단순화된 처리를 하고 있으며 실제로는 HTML 구조에 따라 다를 수 있습니다.
        return ErrorResponse.builder()
            .status(200)
            .message("CONVERTER_ERROR")
            .body(html)
            .build();
    }

    @Override
    protected void writeInternal(ErrorResponse errorResponse, HttpOutputMessage outputMessage) {
        // ErrorResponse 객체를 HTML로 변환하는 로직 구현 (필요한 경우에만)
    }
}
