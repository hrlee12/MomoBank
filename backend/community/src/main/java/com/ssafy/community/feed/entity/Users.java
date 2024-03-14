package com.ssafy.community.feed.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "users")
@Schema(description = "사용자 정보")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID", example = "1")
    private Integer userId;

    @Column(unique = true)
    @Schema(description = "사용자 고유 ID", example = "user123")
    private String id;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "user123@example.com")
    private String email;

    @Schema(description = "FCM 토큰", example = "abcdef123456")
    private String fcmToken;

    @Schema(description = "성실도 점수", example = "100")
    private Integer sincerity;

    @Column(nullable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "0")
    private Boolean isDeleted;
}