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
                new MemberIdName(1, "김성수"),
                new MemberIdName(2, "손준성"),
                new MemberIdName(3, "곽민우"),
                new MemberIdName(4, "이효리"),
                new MemberIdName(5, "명소이"),
                new MemberIdName(6, "엄세현"));

        dto.unpaidFees = Map.of(
                "김성수", "완납",
                "손준성", "15000원 미납",
                "곽민우", "30000원 초과 납부",
                "이효리", "완납",
                "명소이", "10000원 초과 납부",
                "엄세현", "3000원 미납");

        dto.popularPosts = List.of(
                Map.of(
                        "작성자", "곽민우",
                        "글 타이틀", "보드게임카페 정말 재미있었어 !",
                        "내용", "다함께 너무 재미있는 시간을 보내서 좋았어요. 진짜 레전드였어 !!",
                        "작성 일시", "2024-03-15",
                        "좋아요 수", 3,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "이효리", "내용", "맞아 정말 재미있었어."),
                                Map.of("작성자", "김성수", "내용", "난 재미없었는데")
                        )
                ),
                Map.of(
                        "작성자", "곽민우",
                        "글 타이틀", "오늘 피씨방 재미있었어!",
                        "내용", "오늘 롤 너무 재미있었어 !",
                        "작성 일시", "2023-03-16",
                        "좋아요 수", 95,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "엄세현", "내용", "맞아, 정말 생각할 거리를 많이 주는 한 판이었어."),
                                Map.of("작성자", "이효리", "내용", "나도 좋았어.")
                        )
                ),
                Map.of(
                        "작성자", "이효리",
                        "글 타이틀", "카페 재미있었어 !",
                        "내용", "다음엔 영화도 보고싶어 !",
                        "작성 일시", "2023-03-17",
                        "좋아요 수", 110,
                        "댓글 수", 2,
                        "댓글 내용", List.of(
                                Map.of("작성자", "김성수", "내용", "오 나쁘지 않은데 ?"),
                                Map.of("작성자", "손준성", "내용", "오 좋아 !")
                        )
                )
        );


        dto.userPostCounts = Map.of(
                "곽민우", 3,
                "이효리", 1,
                "명소이", 1,
                "손준성", 1,
                "김성수", 1,
                "엄세현", 1);

        dto.userCommentCounts = Map.of(
                "곽민우", 13,
                "이효리", 3,
                "명소이", 0,
                "손준성", 0,
                "김성수", 0,
                "엄세현", 0);

        return dto;
    }


}
