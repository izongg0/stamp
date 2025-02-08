package odin.stamp.store;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.customer.Customer;
import odin.stamp.stamp.config.StampConfig;
import odin.stamp.user.account.Account;

import java.util.ArrayList;
import java.util.List;

/*
@NoArgsConstructor(access = AccessLevel.PRIVATE)
✅ JPA 프록시 객체를 생성할 수 있도록 함
✅ 외부에서 new Classroom()으로 직접 생성하는 것을 막음
✅ 객체 생성 시 of 같은 정적 팩토리 메서드를 강제하여 일관성 유지
✅ 상속을 고려하여 최소한의 접근만 허용 (protected)
이렇게 하면 엔티티의 무분별한 인스턴스 생성을 방지하면서도 JPA의 기능을 활용할 수 있음! 🚀
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "store",fetch = FetchType.LAZY)
    private StampConfig stampConfig;

    /** 상점이름 */
    private String name;

    /** 전화번호 */
    private String phoneNumber;

    /** 사업자 번호 */
    private String registrationNumber;

    /** 상점 활성화여부 */
    private boolean isActive;

    @OneToMany(mappedBy = "store")
    private List<Customer> customers = new ArrayList<>();


}
