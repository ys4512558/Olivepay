package kr.co.olivepay.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.co.olivepay.member.global.entity.BaseEntity;
import kr.co.olivepay.member.global.handler.AppException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import static kr.co.olivepay.member.global.enums.ErrorCode.PIN_LOCKED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Member member;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 8)
    private String birthdate;

    @Column(nullable = false, length = 6)
    private String pin;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Min(0)
    @Max(3)
    private Byte pinCount;

    @Column(nullable = false, length = 60)
    private String userKey;

    @Builder
    public User(Member member, String nickname, String birthdate,
                String pin, String userKey){
        this.member = member;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.pin = pin;
        this.userKey = userKey;
    }

    /**
     * 유저의 닉네임 변경 시 사용
     * @param nickname 변경 할 닉네임
     */
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * 간편 결제 시도 횟수 증가 메소드 <br>
     * 3회 이상 실패 시, PIN_LOCKED(423) error 
     */
    public void increasePinCount(){
        if (this.pinCount == 3) {
            throw new AppException(PIN_LOCKED);
        }

        this.pinCount++;
    }

    /**
     * 간편 결제 시도 횟수 초기화 메소드 <br>
     * 결제 성공 or 간편 결제 비밀번호 재설정 시 사용
     */
    public void resetPinCount(){
        this.pinCount = 0;
    }

    /**
     * 간편 결제 비밀번호 재설정 메소드
     * @param pin
     */
    public void setPin(String pin){
        this.pin = pin;
    }

}
