package kr.co.olivepay.member.repository;

import kr.co.olivepay.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.userKey FROM User u WHERE u.member.id = :memberId")
    Optional<String> findUserKeyByMemberId(Long memberId);
}
