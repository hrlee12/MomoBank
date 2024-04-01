package com.ssafy.community.feed.domain.repository;

import com.ssafy.community.feed.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findAllByFeedFeedId(Integer feedId);

}
