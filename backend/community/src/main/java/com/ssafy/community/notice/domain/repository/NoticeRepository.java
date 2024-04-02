package com.ssafy.community.notice.domain.repository;

import com.ssafy.community.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Query("SELECT n FROM Notice n WHERE n.isDeleted = false ORDER BY n.createdAt DESC")
    List<Notice> findAllByIsDeletedFalseOrderByCreatedAtDesc();
}
