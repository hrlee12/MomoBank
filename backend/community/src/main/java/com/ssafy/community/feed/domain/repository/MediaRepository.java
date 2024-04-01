package com.ssafy.community.feed.domain.repository;

import com.ssafy.community.feed.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    List<Media> findByFeedFeedIdOrderBySequenceAsc(Integer feedId);
}
