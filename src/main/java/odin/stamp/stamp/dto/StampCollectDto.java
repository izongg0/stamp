package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StampCollectDto {

    private Long storeId;

    private String phoneNumber;

    private final Integer collectCount = 1;

    public StampCollectDto(Long storeId, String phoneNumber) {
        this.storeId = storeId;
        this.phoneNumber = phoneNumber;
    }
}
