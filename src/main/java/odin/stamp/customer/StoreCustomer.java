package odin.stamp.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.stamp.log.StampLog;
import odin.stamp.store.Store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "store_customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCustomer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "storeCustomer")
    private List<StampLog> stampLogs = new ArrayList<>();

    /** 몇번 스탬프 적립 완료 했는지 */
    private int completedCount;

    /** 해당 상점 고객 만료 여부 */
    private boolean isExpired;

    /** 마지막 적립일 */
    private LocalDateTime lastCollectedDate;

    /** 언제 고객 만료 되는지 */
    private LocalDateTime expiration_date;

}
