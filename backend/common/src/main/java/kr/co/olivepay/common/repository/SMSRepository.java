package kr.co.olivepay.common.repository;

import kr.co.olivepay.common.entity.SMS;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SMSRepository extends CrudRepository<SMS, String> {
    Optional<SMS> findByPhone(String phone);
}
