package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
