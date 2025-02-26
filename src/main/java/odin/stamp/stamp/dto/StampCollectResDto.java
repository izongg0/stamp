package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.stamp.StampLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class StampCollectResDto {

    private Long storeCustomerId;

    private String phoneNumber;

    /** 적립 성공했는지 여부 */
    private boolean isCollected;

    /** 적립 못한 이유 */
    private String reason;

    /** 현재 가진 스탬프 개수 */
    private int totalStamp;

    /** 마지막 적립일 */
    private LocalDateTime lastCollectedDate;

    /** 제일 근접한 만료날짜 */
    private LocalDateTime recentExpiredDate;

    /**
     * 현재 가진 스탬프
     * 만료일이 가까운 순서로
     */
    private List<StampLogResDto> stampLogs = new ArrayList<>();

    public StampCollectResDto(Long storeCustomerId, String phoneNumber, boolean isCollected, String reason, int totalStamp, LocalDateTime lastCollectedDate, LocalDateTime recentExpiredDate,List<StampLogResDto> stampLogs) {
        this.storeCustomerId = storeCustomerId;
        this.phoneNumber = phoneNumber;
        this.isCollected = isCollected;
        this.reason = reason;
        this.totalStamp = totalStamp;
        this.lastCollectedDate = lastCollectedDate;
        this.recentExpiredDate = recentExpiredDate;
        this.stampLogs = stampLogs;
    }

    public static StampCollectResDto of(Long storeCustomerId, String phoneNumber, boolean isCollected, String reason, int totalStamp, LocalDateTime lastCollectedDate, LocalDateTime recentExpiredDate,List<StampLogResDto> stampLogs) {
        return new StampCollectResDto(
                storeCustomerId,
                phoneNumber,
                isCollected,
                reason,
                totalStamp,
                lastCollectedDate,
                recentExpiredDate,
                stampLogs
        );
    }



}
