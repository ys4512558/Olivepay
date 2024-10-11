package kr.co.olivepay.gateway.repository;

import kr.co.olivepay.gateway.entity.Tokens;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Tokens, Long> {

}
