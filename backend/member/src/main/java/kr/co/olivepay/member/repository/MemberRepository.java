package kr.co.olivepay.member.repository;

import kr.co.olivepay.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(final String phoneNumber);
}
