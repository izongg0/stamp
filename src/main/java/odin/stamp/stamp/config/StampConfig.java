package odin.stamp.stamp.config;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.store.Store;

@Entity
@Table(name = "stamp_configs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StampConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
    /** 적립 완료하려면 스탬프 몇개 적립해야하는지 */
    private int completedStampCount;

    /** 적립 완료하면 보상이 뭔지 */
    private String rewardItem;

    /** 무슨 조건으로 스탬프를 하나 적립해주는 지 */
    private String stampCollectRule;

    /** 적립 후 재적립 하는 간격 */
    private int recollectTime;

    /** 스탬프 유효기간 */
    private int stampValidityPeriod;

    /** 스탬프 사용 시 비밀번호 */
    private int usePassword;




}
