package com.ssafy.community.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportDto {

    private List<MemberIdName> memberList;
    private Map<String, String> unpaidFees;
    private List<Map<String, Object>> popularPosts;
    private Map<String, Integer> userPostCounts;
    private Map<String, Integer> userCommentCounts;


    @Override
    public String toString() {

        String str =  "MonthlyReportDto:" +
                "memberList=" + memberList +
                ", unpaidFees=" + unpaidFees +
                ", popularPosts=" + popularPosts.stream()
                .map(post -> "Post: " +
                        "작성자=" + post.get("작성자") +
                        ", 글 타이틀=" + post.get("글 타이틀") +
                        ", 내용=" + post.get("내용") +
                        ", 작성 일시=" + post.get("작성 일시") +
                        ", 좋아요 수=" + post.get("좋아요 수") +
                        ", 댓글 수=" + post.get("댓글 수") +
                        ", 댓글 내용=" + post.get("댓글 내용"))
                .collect(Collectors.joining(", ")) +
                ", userPostCounts=" + userPostCounts +
                ", userCommentCounts=" + userCommentCounts;

        str = str.replaceAll("\\{", "(");
        str = str.replaceAll("\\}", ")");
        str = str.replaceAll("'", "");
        str = str.replaceAll("'", "");


        return str;
    }

    public static MonthlyReportDto createExample() {
        MonthlyReportDto dto = new MonthlyReportDto();


        dto.memberList = List.of(
                new MemberIdName(1, "홍길동"),
                new MemberIdName(2, "김철수"),
                new MemberIdName(3, "이영희"));

        dto.unpaidFees = Map.of(
                "홍길동", "완납",
                "김철수", "15000원 미납",
                "이영희", "30000원 초과 납부");

        dto.popularPosts = List.of(
                Map.of(
                        "작성자", "홍길동",
                        "글 타이틀", "함께 본 '시간을 달리는 소녀'",
                        "내용", "저녁을 먹고 간단히 카페에서 수다를 떨다가 결정한 영화, '시간을 달리는 소녀'를 봤습니다. 각자의 생각을 나누며 즐거운 시간을 보냈어요.",
                        "작성 일시", "2023-03-15",
                        "좋아요 수", 120,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "김철수", "내용", "영화 정말 재밌었어! 다음에 또 보자."),
                                Map.of("작성자", "이영희", "내용", "영화 후기 공유해줘서 고마워, 정말 감동적이었어.")
                        )
                ),
                Map.of(
                        "작성자", "김철수",
                        "글 타이틀", "오늘의 영화 후기",
                        "내용", "함께 영화를 본 후 감정이 넘치는 바람에 글을 써봅니다. 시간을 달리는 소녀, 시간이라는 주제를 다루면서도 가볍지만 의미 있는 메시지를 전달해주네요.",
                        "작성 일시", "2023-03-16",
                        "좋아요 수", 95,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "홍길동", "내용", "맞아, 정말 생각할 거리를 많이 주는 영화였어."),
                                Map.of("작성자", "이영희", "내용", "영화 추천해줘서 고마워! 함께 볼 수 있어서 좋았어.")
                        )
                ),
                Map.of(
                        "작성자", "이영희",
                        "글 타이틀", "카페에서의 작은 모임",
                        "내용", "영화를 보기 전, 카페에서 모여 각자의 최근 소식을 공유했습니다. 영화만큼이나 소중한 시간이었어요.",
                        "작성 일시", "2023-03-17",
                        "좋아요 수", 110,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "홍길동", "내용", "다음 모임도 기대됩니다!"),
                                Map.of("작성자", "김철수", "내용", "카페에서의 시간도 정말 즐거웠어.")
                        )
                )
        );


        dto.userPostCounts = Map.of(
                "홍길동", 3,
                "김철수", 4,
                "이영희", 1);

        dto.userCommentCounts = Map.of(
                "홍길동", 13,
                "김철수", 25,
                "이영희", 0);

        return dto;
    }


}
