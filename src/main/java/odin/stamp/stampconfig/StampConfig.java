package odin.stamp.stampconfig;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.store.Store;

@Entity
@Table(name = "stamp_configs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StampConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    /** 적립 완료하려면 스탬프 몇개 적립해야하는지 */
    private Integer completedStampCount;

    /** 적립 완료하려면 스탬프 몇개 적립해야하는지 */
    private Integer maxStampCount;

    /** 적립 완료하면 보상이 뭔지 */
    private String rewardItem;

    /** 무슨 조건으로 스탬프를 하나 적립해주는 지 */
    private String stampCollectRule;

    /** 적립 후 재적립 하는 간격 */
    private Integer recollectTime;

    /** 스탬프 유효기간 */
    private Integer stampValidityPeriod;


    /** 스탬프 사용 시 비밀번호 */
    private String usePassword;


    public static StampConfig of(Store store){

        StampConfig stampConfig = new StampConfig();
        stampConfig.store = store;
        stampConfig.completedStampCount = 10;
        stampConfig.maxStampCount = 30;
        stampConfig.rewardItem = "아메리카노 1잔";
        stampConfig.stampCollectRule = "한 잔 구매 시 1개 적립";
        stampConfig.recollectTime = 5;
        stampConfig.stampValidityPeriod = 90;
        stampConfig.usePassword = "1234";

        return stampConfig;
    }

    public void update(Integer completedStampCount, Integer maxStampCount, String rewardItem, String stampCollectRule, Integer recollectTime, Integer stampValidityPeriod, String usePassword){

        if(completedStampCount != null) this.completedStampCount = completedStampCount;
        if(maxStampCount != null) this.maxStampCount = maxStampCount;
        if(rewardItem != null) this.rewardItem = rewardItem;
        if(stampCollectRule != null) this.stampCollectRule = stampCollectRule;
        if(recollectTime != null) this.recollectTime = recollectTime;
        if(stampValidityPeriod != null) this.stampValidityPeriod = stampValidityPeriod;
        if(usePassword != null) this.usePassword = usePassword;

    }


}
