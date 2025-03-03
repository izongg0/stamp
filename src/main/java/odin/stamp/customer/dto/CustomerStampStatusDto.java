package odin.stamp.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.stamp.StampLog;
import odin.stamp.stamp.dto.StampLogResDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CustomerStampStatusDto {

    private Long storeCustomerId;
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

    public CustomerStampStatusDto(Long storeCustomerId, int totalStamp, LocalDateTime lastCollectedDate, LocalDateTime recentExpiredDate, List<StampLogResDto> stampLogs) {
        this.storeCustomerId = storeCustomerId;
        this.totalStamp = totalStamp;
        this.lastCollectedDate = lastCollectedDate;
        this.recentExpiredDate = recentExpiredDate;
        this.stampLogs = stampLogs;
    }

    public static CustomerStampStatusDto of(Long storeCustomerId, List<StampLog> stampLogs, List<StampLogResDto> resDtos){
        return new CustomerStampStatusDto(
                storeCustomerId,
                stampLogs.size(),
                !stampLogs.isEmpty() ? stampLogs.get(0).getCreatedAt():null,
                !stampLogs.isEmpty() ? stampLogs.get(stampLogs.size() - 1).getExpiredDate():null,
                resDtos
        );


    }
}
