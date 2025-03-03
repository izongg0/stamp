package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StampUseReqDto {

    private Long storeId;

    private Long storeCustomerId;

    public StampUseReqDto(Long storeId, Long storeCustomerId) {
        this.storeId = storeId;
        this.storeCustomerId = storeCustomerId;
    }
}
