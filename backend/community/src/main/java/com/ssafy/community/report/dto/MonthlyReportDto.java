package com.ssafy.community.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportDto {

    private List<String> memberList;
    private Map<String, String> unpaidFees;
    private List<Map<String, Object>> popularPosts;
    private Map<String, Integer> userPostCounts;
    private Map<String, Integer> userCommentCounts;

    public static MonthlyReportDto createExample() {
        MonthlyReportDto dto = new MonthlyReportDto();

        // 멤버 리스트 예제 데이터
        dto.memberList = List.of("홍길동", "김철수", "이영희");

        // 각 멤버별 미납 요금 예제 데이터
        dto.unpaidFees = Map.of(
                "홍길동", "미납 없음",
                "김철수", "15000원 미납",
                "이영희", "30000원 납부 더함");

        // 인기 있는 게시물 예제 데이터
        dto.popularPosts = List.of(
                Map.of("작성자", "홍길동", "작성 일시", "2023-03-15", "좋아요 수", 120, "댓글 수", 30, "댓글 내용", List.of("대단해요!", "정말 좋네요!", "감사합니다.")),
                Map.of("작성자", "김철수", "작성 일시", "2023-03-16", "좋아요 수", 95, "댓글 수", 25, "댓글 내용", List.of("잘 봤어요.", "공감합니다.", "좋은 글이네요.")),
                Map.of("작성자", "이영희", "작성 일시", "2023-03-17", "좋아요 수", 110, "댓글 수", 40, "댓글 내용", List.of("정보 감사합니다.", "도움이 됐어요.", "잘 읽었습니다."))
        );

        // 각 유저별 이번 달 게시물 작성 수 예제 데이터
        dto.userPostCounts = Map.of(
                "홍길동", 3,
                "김철수", 4,
                "이영희", 1);

        // 각 유저별 이번 달 댓글 작성 수 예제 데이터
        dto.userCommentCounts = Map.of(
                "홍길동", 13,
                "김철수", 25,
                "이영희", 0);

        return dto;
    }
}
