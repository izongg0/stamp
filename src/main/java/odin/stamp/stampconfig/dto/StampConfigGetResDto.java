package odin.stamp.stampconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.stampconfig.StampConfig;

@Getter
@RequiredArgsConstructor
public class StampConfigGetResDto {
    /** 적립 완료하려면 스탬프 몇개 적립해야하는지 */
    private final Integer completedStampCount;

    /** 적립 완료하면 보상이 뭔지 */
    private final String rewardItem;

    /** 무슨 조건으로 스탬프를 하나 적립해주는 지 */
    private final String stampCollectRule;

    /** 적립 후 재적립 하는 간격 */
    private final Integer recollectTime;

    /** 스탬프 유효기간 */
    private final Integer stampValidityPeriod;



    /** 스탬프 사용 시 비밀번호 */
    private final String usePassword;

    public static  StampConfigGetResDto from(StampConfig stampConfig){
        return new StampConfigGetResDto(
                stampConfig.getCompletedStampCount(),
                stampConfig.getRewardItem(),
                stampConfig.getStampCollectRule(),
                stampConfig.getRecollectTime(),
                stampConfig.getStampValidityPeriod(),
                stampConfig.getUsePassword()
        );

    }
}
