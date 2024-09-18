package kr.co.olivepay.card.mapper;

import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.openapi.dto.res.account.AccountRec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * AccountRec을 통해 Entity로 변환하여 반환합니다.
     * default AccountType : USER
     *
     * @param accountRec
     * @return Account 엔티티
     */
    @Mapping(source = "accountRec.accountNo", target = "accountNumber")
    @Mapping(target = "accountType", constant = "USER")
    Account toEntity(AccountRec accountRec);

}
