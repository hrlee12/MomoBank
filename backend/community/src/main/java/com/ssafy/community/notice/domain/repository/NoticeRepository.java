package com.ssafy.community.notice.domain.repository;

import com.ssafy.community.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Query("SELECT n FROM Notice n WHERE n.isDeleted = false ORDER BY n.createdAt DESC")
    List<Notice> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    // groupId를 이용해 삭제되지 않은 공지사항을 조회하는 메서드 추가
    @Query("SELECT n FROM Notice n WHERE n.isDeleted = false AND n.groupInfo.groupInfoId = :groupInfoId ORDER BY n.createdAt DESC")
    List<Notice> findAllByGroupIdAndIsDeletedFalseOrderByCreatedAtDesc(Integer groupInfoId);

}
