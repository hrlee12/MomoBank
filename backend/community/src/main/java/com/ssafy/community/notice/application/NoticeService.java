package com.ssafy.community.notice.application;

import com.ssafy.community.feed.domain.GroupMember;
import com.ssafy.community.feed.domain.repository.GroupMemberRepository;
import com.ssafy.community.notice.domain.Notice;
import com.ssafy.community.notice.domain.repository.NoticeRepository;
import com.ssafy.community.notice.dto.request.NoticeCreationRequest;
import com.ssafy.community.notice.dto.request.NoticeModificationRequest;
import com.ssafy.community.notice.dto.response.NoticeDetailResponse;
import com.ssafy.community.notice.dto.response.NoticeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final GroupMemberRepository groupMemberRepository;

    // 공지사항 작성
    @Transactional
    public void createNotice(NoticeCreationRequest request) {
        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedAt(LocalDateTime.now());
        notice.setIsDeleted(false);

        GroupMember groupMember = groupMemberRepository.findGroupMemberByMemberId(request.getMemberId());

        notice.setGroupInfo(groupMember.getGroupInfo());
        noticeRepository.save(notice);

        // TODO: 여기에 알림 전송 로직 추가 (Firebase Cloud Messaging 사용)
    }

    // 공지사항 수정
    @Transactional
    public void modifyNotice(NoticeModificationRequest request) {
        Notice notice = noticeRepository.findById(request.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Notice ID: " + request.getNoticeId()));
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setUpdatedAt(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    // 공지사항 삭제
    @Transactional
    public void deleteNotice(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Notice ID: " + noticeId));
        notice.setIsDeleted(true);
        noticeRepository.save(notice);
    }

    // 공지사항 상세 조회
    public NoticeDetailResponse getNoticeDetail(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Notice ID: " + noticeId));
        return NoticeDetailResponse.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    // 공지사항 리스트 조회
    public List<NoticeListResponse> getNoticeList() {
        List<Notice> notices = noticeRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();
        return notices.stream()
                .map(notice -> NoticeListResponse.builder()
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .createdAt(notice.getCreatedAt())
                        .updatedAt(notice.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
