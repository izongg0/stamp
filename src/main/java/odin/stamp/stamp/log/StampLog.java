package odin.stamp.stamp.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.customer.StoreCustomer;

@Entity
@Table(name = "stamp_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StampLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_customer_id")
    private StoreCustomer storeCustomer;

    /** 몇개적립인지 */
    private int collectCount;

    /**
     * 몇번 째 적립 시퀀스인지
     * 적립 완료 횟수 +1
     */
    private int stampSequence;

    /** 스탬프 만료일 */
    private int expiredDate;

    /** 사용 여부 */
    private boolean isUse;

    /** 만료 여부 */
    private boolean isExpired;

}
