package com.ssafy.community.feed.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "group_member")
@Schema(description = "그룹 멤버 정보")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "그룹 멤버 ID", example = "1")
    private Integer groupMemberId;

     @ManyToOne
     @JoinColumn(name = "user_id")
     @Schema(description = "사용자 ID", example = "1")
     private Users user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @Schema(description = "그룹 ID", example = "1")
    private GroupInfo groupInfo;

    @Schema(description = "역할", example = "회원")
    private String role;

    @Schema(description = "총 회비", example = "100000")
    private Long totalFee;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;

    @Schema(description = "이름", example = "홍길동")
    private String name;
}
