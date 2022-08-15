package ramos.maxuel.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.domain.PostVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    int countByAuthorIdAndTimestampGreaterThan(Long userId, ZonedDateTime yesterday);

    @Query("select p as post, a as author, rp as referencePost, ra as referencePostAuthor " +
            " from Post p " +
            " join User a on a.id = p.authorId " +
            " left join Post rp on rp.id = p.referencePostId " +
            " left join User ra on ra.id = rp.authorId " +
            " where (:authorId IS NULL or p.authorId = :authorId)" +
            " and (:start IS NULL or p.timestamp >= :start)" +
            " and (:end IS NULL or p.timestamp <= :end) ")
    Page<PostVO> findByParams(
            @Param("authorId") Long authorId,
            @Param("start") ZonedDateTime start,
            @Param("end") ZonedDateTime end,
            Pageable pageable
    );
}
