package ramos.maxuel.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ramos.maxuel.socialmedia.domain.Post;

import java.time.ZonedDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    int countByAuthorIdAndTimestampGreaterThan(Long userId, ZonedDateTime yesterday);

    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
}
