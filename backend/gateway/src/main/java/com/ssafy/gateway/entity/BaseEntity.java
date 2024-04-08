package com.ssafy.gateway.entity;


import com.ssafy.gateway.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모델 간 공통 사항 정의.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "created_at", updatable = false, nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("0")
    private boolean isDeleted = false;

    public boolean softDelete() {
        if (isDeleted)
            throw new IllegalStateException(ErrorCode.ALREADY_DELETED.getMessage());
        this.isDeleted = true;
        return true;
    }
}

