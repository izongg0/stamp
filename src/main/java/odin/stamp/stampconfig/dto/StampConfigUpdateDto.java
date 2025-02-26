package odin.stamp.stampconfig.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StampConfigUpdateDto {

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


}
