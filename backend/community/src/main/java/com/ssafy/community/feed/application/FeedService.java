package com.ssafy.community.feed.application;

import com.ssafy.community.common.util.KafkaUtil;
import com.ssafy.community.feed.domain.Comments;
import com.ssafy.community.feed.domain.Feed;
import com.ssafy.community.feed.domain.GroupInfo;
import com.ssafy.community.feed.domain.GroupMember;
import com.ssafy.community.feed.domain.GroupMember.memberType;
import com.ssafy.community.feed.domain.Likes;
import com.ssafy.community.feed.domain.Media;
import com.ssafy.community.feed.domain.Member;
import com.ssafy.community.feed.domain.repository.CommentsRepository;
import com.ssafy.community.feed.domain.repository.FeedRepository;
import com.ssafy.community.feed.domain.repository.GroupMemberRepository;
import com.ssafy.community.feed.domain.repository.LikesRepository;
import com.ssafy.community.feed.domain.repository.MediaRepository;
import com.ssafy.community.feed.dto.CommentDto;
import com.ssafy.community.feed.dto.request.FeedCreateRequest;
import com.ssafy.community.feed.dto.request.FeedUpdateRequest;
import com.ssafy.community.feed.dto.response.FeedListResponse;
import com.ssafy.community.feed.dto.response.MediaResponse;
import com.ssafy.community.groupMember.domain.repository.GroupInfoRepository;
import com.ssafy.community.groupMember.domain.repository.MemberRepository;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;
    private final LikesRepository likesRepository;
    private final CommentsRepository commentsRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MediaRepository mediaRepository;
    private final String uploadDir = "/uploads/feed/";

    /**
     * 피드 목록 조회 - 피드 목록을 조회하고, 특정 사용자가 좋아요를 눌렀는지 여부를 확인한다. - 피드 목록을 조회할 때, 페이징 처리를 한다. - 피드 목록을 조회할
     * 때, 댓글 정보를 함께 조회한다. - 피드 목록을 조회할 때, 미디어 파일 URL 리스트를 함께 조회한다. - 피드 목록을 조회할 때, 그룹 멤버 ID와 이름을 함께
     * 조회한다. (프로필 이미지는 없음)
     *
     * @param pageable 페이징 정보
     * @param groupId  그룹 ID
     * @return 피드 목록
     */
    public Page<FeedListResponse> getFeeds(Pageable pageable, int groupId) {
        log.info("groupId: {}", groupId);
        // 그룹에 속한 피드 목록 조회
        Page<Feed> feeds = feedRepository.findByGroupId(groupId, pageable);

        // 피드 목록을 FeedListResponse로 변환
        List<FeedListResponse> feedListResponses = feeds.getContent().stream().map(feed -> {

            String content = feed.getContent();
            String modifiedContent =
                content.length() > 30 ? content.substring(0, 30) + "..." : content;
            FeedListResponse dto = FeedListResponse.builder()
                .feedId(feed.getFeedId())
                .content(feed.getContent())
                .contentOneLine(modifiedContent)
                .commentsCount(feed.getCommentsCount())
                .likesCount(feed.getLikesCount())
                .createdAt(feed.getCreatedAt())
                .updatedAt(feed.getUpdatedAt())
                .groupMemberId(feed.getGroupMember().getGroupMemberId())
                .groupMemberName(feed.getGroupMember().getMember().getName())
                .build();

            // 특정 사용자가 좋아요를 눌렀는지 여부 확인
            Likes likes = likesRepository.findByFeedFeedId(feed.getFeedId());
            boolean likedByUser =
                likes != null && likes.getGroupMember().getGroupMemberId() == groupId;
            dto.setLikedByUser(likedByUser);

            // 피드 댓글 조회
            List<Comments> comments = commentsRepository.findAllByFeedFeedId(feed.getFeedId());
            dto.setComments(
                comments.stream().map(this::convertToCommentDto).collect(Collectors.toList()));

            // 미디어 파일 정보 조회 및 설정
            List<Media> mediaList = mediaRepository.findByFeedFeedIdOrderBySequenceAsc(
                feed.getFeedId());
            List<MediaResponse> mediaResponses = mediaList.stream()
                .map(media -> {
                    Path filePath = Paths.get(media.getMediaUrl());
                    return new MediaResponse(media.getMediaId(), media.getSequence(),
                        media.getMediaType(), media.getMediaUrl());
                })
                .collect(Collectors.toList());
            dto.setMediaList(mediaResponses);

            return dto;
        }).toList();

        // 피드 목록 반환
        return new PageImpl<>(feedListResponses, pageable, feeds.getTotalElements());
    }


    /**
     * 그룹 안에서 피드 생성
     *
     * @param feedCreateRequest 피드 생성 요청 DTO
     */
    @Transactional
    public void createFeed(FeedCreateRequest feedCreateRequest, List<MultipartFile> files) {
        GroupMember groupMember = groupMemberRepository.findById(
                feedCreateRequest.getGroupMemberId())
            .orElseThrow(() -> new EntityNotFoundException(
                "GroupMember not found with id " + feedCreateRequest.getGroupMemberId()));

        log.info("groupMemberId: {}", feedCreateRequest.getGroupMemberId());

        Feed feed = new Feed();
        feed.setContent(feedCreateRequest.getContent());
        feed.setGroupMember(groupMember);
        feed.setCreatedAt(LocalDateTime.now());
        feed.setUpdatedAt(LocalDateTime.now());
        feed.setIsDeleted(false);
        feed.setLikesCount(0);
        feed.setCommentsCount(0);

        feed = feedRepository.save(feed); // 피드 정보 먼저 저장

        // 파일 처리 로직
        int sequence = 1; // 파일 순서를 위한 변수
        for (MultipartFile file : files) {
            String fileName = saveFileOnServer(file, String.valueOf(feed.getFeedId()));
            Media media = new Media();
            media.setFeed(feed);
            media.setSequence(sequence++);
            media.setMediaUrl("http://j10a505.p.ssafy.io/api/community/static/" + fileName);
            media.setMediaType(file.getContentType()); // 파일 타입 결정 로직
            media.setCreatedAt(LocalDateTime.now());
            media.setUpdatedAt(LocalDateTime.now());
            media.setIsDeleted(false);

            // 미디어 정보 저장
            mediaRepository.save(media);
        }

    }

    /**
     * 파일 저장 로직
     *
     * @param file   파일
     * @param feedId 피드 ID
     * @return 파일명
     */
    private String saveFileOnServer(MultipartFile file, String feedId) {
        String originalFileName = file.getOriginalFilename();

        // 파일명에 피드 ID와 원래 파일명을 조합
        String fileName = "Feed" + feedId + "_" + originalFileName;
        Path targetLocation = Paths.get(uploadDir).resolve(fileName);
        if (!Files.exists(targetLocation)) {
            try {
                Files.createDirectories(targetLocation.getParent());
            } catch (IOException e) {
                throw new RuntimeException("createDirectories Error", e);
            }
        }

        try {
            // 파일 저장 로직
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("File storage error", e);
        }
    }

    @Transactional
    public void updateFeed(Integer feedId, FeedUpdateRequest requestDto) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new EntityNotFoundException("Feed not found with id " + feedId));
        feed.setContent(requestDto.getContent());
        // 추가 필드 수정...
        feedRepository.save(feed);
    }

    @Transactional
    public void deleteFeed(Integer feedId) {
        feedRepository.deleteById(feedId);
    }

    @Transactional
    public void addLike(Integer feedId, Integer memberId) {
        Likes likes = new Likes();
        likes.setCreatedAt(LocalDateTime.now());
        likes.setGroupMember(groupMemberRepository.findGroupMemberByMemberId(memberId));

        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new RuntimeException("Feed not found"));

        likes.setFeed(feed);
        likesRepository.save(likes);

        feed.setLikesCount(feed.getLikesCount() + 1);
        feedRepository.save(feed);
    }

    @Transactional
    public void removeLike(Integer feedId, Integer memberId) {
        likesRepository.deleteByFeedFeedIdAndGroupMemberGroupMemberId(feedId, memberId);

        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new RuntimeException("Feed not found"));
        feed.setLikesCount(feed.getLikesCount() - 1);
        feedRepository.save(feed);
    }

    @Transactional
    public CommentDto addComment(Integer feedId, CommentDto commentDto) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new RuntimeException("Feed not found"));
        Comments comment = new Comments();
        comment.setContent(commentDto.getContent());
        // 추가 필드 설정...
        comment.setFeed(feed);
        feedRepository.save(feed);
        return convertToCommentDto(comment);
    }

    @Transactional
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comments comment = feedRepository.findCommentById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDto.getContent());
        // 추가 필드 수정...
        feedRepository.save(comment.getFeed());
        return convertToCommentDto(comment);
    }

    @Transactional
    public void deleteComment(Integer commentId) {
        Comments comment = feedRepository.findCommentById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        Feed feed = comment.getFeed();
        feedRepository.save(feed);
    }

    private FeedListResponse convertToFeedListDto(Feed feed) {
        return FeedListResponse.builder()
            .feedId(feed.getFeedId())
            .likedByUser(false) // 사용자가 좋아요를 눌렀는지 여부
            .comments(null)
            .build();
    }


    private CommentDto convertToCommentDto(Comments comment) {
        return CommentDto.builder()
            .commentId(comment.getCommentId())
            .content(comment.getContent())
            .build();
    }

    public List<FeedListResponse> getGroupMemberFeeds(Integer groupMemberId) {
        // 그룹 멤버 ID로 그룹 멤버 정보 조회
        GroupMember groupMember = groupMemberRepository.findById(groupMemberId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid group member ID"));

        // 그룹 멤버가 작성한 피드 목록 조회
        List<Feed> feeds = feedRepository.findByGroupMember(groupMember);

        // 피드 목록을 FeedListResponse로 변환
        return feeds.stream().map(feed -> {
            String content = feed.getContent();
            String modifiedContent =
                content.length() > 30 ? content.substring(0, 30) + "..." : content;

            FeedListResponse feedListResponse = FeedListResponse.builder()
                .feedId(feed.getFeedId())
                .content(feed.getContent())
                .contentOneLine(modifiedContent)
                .commentsCount(feed.getCommentsCount())
                .likesCount(feed.getLikesCount())
                .createdAt(feed.getCreatedAt())
                .updatedAt(feed.getUpdatedAt())
                .build();

            // 특정 사용자가 좋아요를 눌렀는지 여부 확인
            Likes likes = likesRepository.findByFeedIdAndGroupMemberId(feed.getFeedId(),
                groupMember.getGroupMemberId());
            feedListResponse.setLikedByUser(likes != null);

            // 피드 댓글 조회
            List<Comments> comments = commentsRepository.findAllByFeedFeedId(feed.getFeedId());
            feedListResponse.setComments(
                comments.stream().map(this::convertToCommentDto).collect(Collectors.toList()));

            // 미디어 파일 정보 조회 및 설정
            List<Media> mediaList = mediaRepository.findByFeedFeedIdOrderBySequenceAsc(
                feed.getFeedId());
            List<MediaResponse> mediaResponses = mediaList.stream()
                .map(media -> new MediaResponse(media.getMediaId(), media.getSequence(),
                    media.getMediaType(), media.getMediaUrl()))
                .collect(Collectors.toList());
            feedListResponse.setMediaList(mediaResponses);

            // 그룹 멤버 정보 설정
            feedListResponse.setGroupMemberId(groupMember.getGroupMemberId());
            feedListResponse.setGroupMemberName(groupMember.getName());

            return feedListResponse;
        }).collect(Collectors.toList());
    }


    private final KafkaUtil kafkaUtil;
    private final MemberRepository memberRepository;
    private final GroupInfoRepository groupInfoRepository;

    @KafkaListener(topics = "createGroup", groupId = "community")
    public void createGroup(Object data) {

        System.out.println("createGroup start");
        Map<String, Object> groupInfoInfo = kafkaUtil.dataToMap(data);

        groupInfoInfo.get("createdBy");
//        Member member = memberRepository.findById((int) groupInfoInfo.get("createdBy"))
//            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));

        Member member = memberRepository.findById((int) groupInfoInfo.get("createdBy"))
            .orElse(null);

        if (member == null) {
            log.info("member null : {}", member);
        }

        GroupInfo groupInfo = GroupInfo.builder()
            .groupInfoId((int)groupInfoInfo.get("groupInfoId"))
            .member(member)
            .groupName((String) groupInfoInfo.get("groupName"))
            .description((String) groupInfoInfo.get("description"))
            .build();


        System.out.println("createGroup ing...");
        System.out.println(groupInfo.getGroupInfoId());

        GroupInfo result = groupInfoRepository.save(groupInfo);


        if (result == null) {
            System.out.println("result null");
        } else {
            System.out.println("result not null");
            System.out.println(result.getGroupInfoId());
        }
        System.out.println("create done");
    }

    @KafkaListener(topics = "createGroupMemberAsGroupCreated", groupId = "community")
    public void createGroupMemberAsGroupCreated(Object data) {
        System.out.println("그룹멤버 시작");
        Map<String, Object> groupMemberInfo = kafkaUtil.dataToMap(data);

//        Member member = memberRepository.findById((int) groupMemberInfo.get("memberId"))
//            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));

        Member member = memberRepository.findById((int) groupMemberInfo.get("memberId"))
            .orElse(null);

        if (member == null) {
            log.info("member : {}", member);
        }

//        GroupInfo groupInfo = groupInfoRepository.findById((int) groupMemberInfo.get("groupInfoId"))
//            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_GROUP_INFO));

        GroupInfo groupInfo = groupInfoRepository.findById((int) groupMemberInfo.get("groupInfoId"))
            .orElse(null);

        if (groupInfo == null) {
            log.info("groupInfo : {}", groupInfo);
        }

        GroupMember groupMember = GroupMember.builder()
            .groupMemberId((int)groupMemberInfo.get("groupMemberId") )
            .name((String)groupMemberInfo.get("name"))
            .role(GroupMember.memberType.모임원)
            .totalFee(Long.parseLong(groupMemberInfo.get("totalFee").toString()))
            .groupInfo(groupInfo)
            .member(member)
            .build();

        groupMemberRepository.save(groupMember);

        System.out.println("그룹멤버 끝");
    }


    @Transactional
    @KafkaListener(topics = "createGroupAndGroupMember", groupId = "community")
    public void createGroupAndGroupMember(Object data) {
        Map<String, Object> info = kafkaUtil.dataToMap(data);

        Member member = memberRepository.findById((int) info.get("createdBy"))
                .orElse(null);

        if (member == null) {
            log.info("member null : {}", member);
        }

        GroupInfo groupInfo = GroupInfo.builder()
                .groupInfoId((int)info.get("groupInfoId"))
                .member(member)
                .groupName((String) info.get("groupName"))
                .description((String) info.get("description"))
                .groupMembers(null)
                .build();



//        System.out.println("createGroup ing...");
//        System.out.println(groupInfo.getGroupInfoId());

        GroupInfo result = groupInfoRepository.save(groupInfo);



        if (result == null) {
            System.out.println("result null");
        } else {
            System.out.println("result not null");
            System.out.println(result.getGroupInfoId());
        }
//        System.out.println("create done");
//        System.out.println("그룹 멤버 시작");



        Member member2 = memberRepository.findById((int) info.get("memberId"))
                .orElse(null);

        if (member2 == null) {
            log.info("member : {}", member);
        }

//        GroupInfo groupInfo = groupInfoRepository.findById((int) groupMemberInfo.get("groupInfoId"))
//            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_GROUP_INFO));



        GroupMember groupMember = GroupMember.builder()
                .groupMemberId((int)info.get("groupMemberId") )
                .name((String)info.get("name"))
                .role(GroupMember.memberType.모임원)
                .totalFee(Long.parseLong(info.get("totalFee").toString()))
                .groupInfo(groupInfo)
                .member(member)
                .build();

        groupMemberRepository.save(groupMember);

//        System.out.println("그룹멤버 끝");
    }
}
