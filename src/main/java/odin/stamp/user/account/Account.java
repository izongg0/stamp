package odin.stamp.user.account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    private LocalDateTime lastLogonAt;

    private boolean isWithdrawal;

    private Account(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static Account of(String name, String email, String password) {
        return new Account(name, email, password);
    }

    /**
     * 마지막 로그인 시간 업데이트
     */
    public void updateLastLogonAt() {
        lastLogonAt = LocalDateTime.now();
    }

    /**
     * 비밀번호 업데이트
     * @param password
     */
    public void updatePassword(String password) {
        this.password = password;
    }

    public void withdraw() {
        isWithdrawal = true;
    }
}
