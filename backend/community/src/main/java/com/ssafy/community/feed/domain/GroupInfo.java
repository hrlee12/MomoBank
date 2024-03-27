package com.ssafy.community.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Entity
@Table(name = "group_info")
@Schema(description = "그룹 정보")
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "그룹 ID", example = "1")
    private Integer groupId;

    @Schema(description = "계좌 아이디", example = "1")
    private Integer accountId;

    @Schema(description = "그룹 이름", example = "독서 클럽")
    private String groupName;

    @Schema(description = "설명", example = "매달 책을 읽고 토론하는 클럽")
    private String description;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @Schema(description = "생성자 ID", example = "1")
    private Member createdBy;
}
