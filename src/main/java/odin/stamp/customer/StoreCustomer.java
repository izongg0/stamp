package odin.stamp.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.stamp.StampLog;
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

    @OneToMany(mappedBy = "storeCustomer",fetch = FetchType.LAZY)
    private final List<StampLog> stampLogs = new ArrayList<>();

    /** 총 스탬프 몇번 적립했는지 */
    private int totalStampCount;

    /** 몇번 스탬프 적립 완료 했는지 */
    private int totalUseStampCount;

    /** 현재 교환권 개수 */
    private int couponCount;

    /** 해당 상점 고객 만료 여부 */
    private boolean isExpired;

    /** 마지막 적립일 */
    private LocalDateTime lastCollectedDate;

    /** 언제 고객 만료 되는지 */
    private LocalDateTime expiration_date;

    public static StoreCustomer of(Store store,Customer customer){
        StoreCustomer storeCustomer = new StoreCustomer();
        storeCustomer.store = store;
        storeCustomer.customer = customer;
        storeCustomer.totalStampCount = 0;
        storeCustomer.totalUseStampCount = 0;
        storeCustomer.couponCount = 0;
        return storeCustomer;
    }

    public void updateTotalStampCount(Integer count){
        this.totalStampCount+=count;
    }

    public void updateTotalUseStampCount(){
        this.totalUseStampCount++;
    }

    public void updateLastCollectedDate(){
        this.lastCollectedDate = LocalDateTime.now();

        // Todo 고객 만료일도 업데이트
    }

}
