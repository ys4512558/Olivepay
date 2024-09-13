package kr.co.olivepay.member.entity;

import jakarta.persistence.*;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.handler.AppException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static kr.co.olivepay.member.global.enums.ErrorCode.PROMOTE_DUPLICATED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false, length = 16)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String phoneNumber, String password, Role role){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    /**
     * 비밀번호 변경 메소드
     * @param password 변경할 비밀번호
     */
    public void updatePassword(String password){
        this.password = password;
    }

    /**
     * 임시 유저를 일반 유저로 전환 시 사용
     */
    public void promoteUser(){
        if(this.role != Role.TEMP_USER) {
            throw new AppException(PROMOTE_DUPLICATED);
        }

        this.role = Role.USER;
    }

}
