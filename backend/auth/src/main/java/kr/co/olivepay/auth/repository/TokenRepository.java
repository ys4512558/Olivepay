package kr.co.olivepay.auth.repository;

import kr.co.olivepay.auth.entity.Tokens;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Tokens, Long> {

    Optional<Tokens> findByMemberId(Long memberId);

    Optional<Tokens> findByRefreshToken(String refreshToken);

}
