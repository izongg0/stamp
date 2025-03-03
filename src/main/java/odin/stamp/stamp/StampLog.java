package odin.stamp.stamp;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.stampconfig.StampConfig;
import odin.stamp.store.Store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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




    /** 스탬프 만료일 */
    private LocalDateTime expiredDate;

    /** 사용 여부 */
    private boolean isUse;

    /** 스탬프 사용일 */
    private LocalDateTime useDate;

    /** 만료 여부 */
    private boolean isExpired;

//    public StampLog(StoreCustomer storeCustomer,int collectCount, int stampSequence, LocalDateTime expiredDate) {
//        this.storeCustomer = storeCustomer;
//        this.collectCount = collectCount;
//        this.stampSequence = stampSequence;
//        this.expiredDate = expiredDate;
//        this.isUse = false;
//        this.isExpired = false;
//    }

    public static StampLog collect(StoreCustomer storeCustomer, StampConfig stampConfig, Integer collectCount){
        StampLog stampLog = new StampLog();

        stampLog.storeCustomer = storeCustomer;
        stampLog.collectCount = collectCount;



        /*
          현재 날짜 + 스탬프 설정에서 저장한 스탬프 만료기간
         */
        stampLog.expiredDate = LocalDateTime.now().plusDays(stampConfig.getStampValidityPeriod());

        return stampLog;
    }


    public void use(){
        this.isUse = true;
        this.useDate = LocalDateTime.now();
    }



}
