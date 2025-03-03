package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.customer.dto.CustomerStampStatusDto;
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

    private CustomerStampStatusDto stampStatusDto;

    public StampCollectResDto(Long storeCustomerId, String phoneNumber, boolean isCollected, String reason,CustomerStampStatusDto stampStatusDto) {
        this.storeCustomerId = storeCustomerId;
        this.phoneNumber = phoneNumber;
        this.isCollected = isCollected;
        this.reason = reason;
        this.stampStatusDto = stampStatusDto;
    }

    public static StampCollectResDto of(Long storeCustomerId, String phoneNumber, boolean isCollected, String reason, CustomerStampStatusDto stampStatusDto) {
        return new StampCollectResDto(
                storeCustomerId,
                phoneNumber,
                isCollected,
                reason,
                stampStatusDto
        );
    }



}
