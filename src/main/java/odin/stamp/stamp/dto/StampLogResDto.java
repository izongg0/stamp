package odin.stamp.stamp.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.stamp.StampLog;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class StampLogResDto {

    private Long id;


    private Long storeCustomerId;


    /** 몇개적립인지 */
    private int collectCount;

    /**
     * 몇번 째 적립 시퀀스인지
     * 적립 완료 횟수 +1
     */
    private int stampSequence;


    /** 스탬프 만료일 */
    private LocalDateTime expiredDate;

    /** 사용 여부 */
    private boolean isUse;

    /** 만료 여부 */
    private boolean isExpired;

    public StampLogResDto(StampLog stampLog) {
        this.id = stampLog.getId();
        this.storeCustomerId = stampLog.getStoreCustomer().getId();
        this.collectCount = stampLog.getCollectCount();
        this.expiredDate = stampLog.getExpiredDate();
        this.isUse = stampLog.isUse();
        this.isExpired = stampLog.isExpired();
    }

    public static List<StampLogResDto> fromEntityList(List<StampLog> stampLogs) {
        return stampLogs.stream().map(StampLogResDto::new).toList();
    }
}
