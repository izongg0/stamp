package odin.stamp.coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.stamp.StampLog;
import odin.stamp.stampconfig.StampConfig;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_customer_id")
    private StoreCustomer storeCustomer;

    /** 스탬프 만료일 */
    private LocalDateTime expiredDate;

    /** 사용 여부 */
    private boolean isUse;

    /** 만료 여부 */
    private boolean isExpired;

    private LocalDateTime useDate;

    public static Coupon collect(StoreCustomer storeCustomer, StampConfig stampConfig){

        Coupon coupon = new Coupon();
        coupon.storeCustomer = storeCustomer;
        coupon.expiredDate = LocalDateTime.now().plusDays(stampConfig.getStampValidityPeriod());
        return coupon;
    }

    public void use(){
        this.isUse = true;
        this.useDate = LocalDateTime.now();
    }
}
