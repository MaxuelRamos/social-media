package ramos.maxuel.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ramos.maxuel.socialmedia.domain.User;
import ramos.maxuel.socialmedia.domain.UserProfileVO;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u as user, count(*) as postCount " +
            " from User u " +
            " left join Post p on p.authorId = u.id " +
            " where u.id = :id " +
            " group by u.id ")
    Optional<UserProfileVO> findProfile(@Param("id") Long id);

}
