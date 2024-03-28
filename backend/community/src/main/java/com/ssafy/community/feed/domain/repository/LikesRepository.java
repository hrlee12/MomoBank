package com.ssafy.community.feed.domain.repository;

import com.ssafy.community.feed.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Likes findByFeedFeedId(int feedId);
}
