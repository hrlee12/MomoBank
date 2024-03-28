package com.ssafy.community.feed.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notification_group")
@Schema(description = "그룹 알림 정보")
public class NotificationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "알림 그룹 ID", example = "1")
    private Integer notificationGroupId;

    @ManyToOne
    @JoinColumn(name = "group_member_id")
    @Schema(description = "그룹 멤버 ID", example = "1")
    private GroupMember groupMember;

    @Schema(description = "제목", example = "새로운 이벤트 알림")
    private String title;

    @Schema(description = "내용", example = "이번 주 토요일에 새로운 이벤트가 있습니다.")
    private String content;

    @Schema(description = "상태", example = "unread")
    private String status;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;
}