package kr.co.olivepay.member.repository;

import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.global.handler.AppException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static kr.co.olivepay.member.global.enums.ErrorCode.NOT_FOUND_MEMBER;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(final String phoneNumber);

    /**
     * MemberID로 회원을 찾는 메소드<br>
     * 없다면 NOT_FOUND_MEMBER Error
     * @param memberId
     * @return
     */
    default Member getById(Long memberId){
        return findById(memberId)
                .orElseThrow(() -> new AppException(NOT_FOUND_MEMBER));
    }


}
