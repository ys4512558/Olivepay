package kr.co.olivepay.member.repository;

import kr.co.olivepay.member.entity.User;
import kr.co.olivepay.member.global.handler.AppException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static kr.co.olivepay.member.global.enums.ErrorCode.NOT_FOUND_MEMBER;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.userKey FROM User u WHERE u.member.id = :memberId")
    Optional<String> findUserKeyByMemberId(Long memberId);

    @Query("Select u FROM User u WHERE u.member.id = :memberId")
    Optional<User> findByMemberId(Long memberId);

    /**
     * MemberID로 유저를 찾는 메소드<br>
     * 없다면 NOT_FOUND_MEMBER Error
     * @param memberId
     * @return
     */
    default User getByMemberId(Long memberId){
        return findByMemberId(memberId)
                .orElseThrow(() -> new AppException(NOT_FOUND_MEMBER));
    }
}
