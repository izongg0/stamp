package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StampCollectDto {

    private Long storeId;

    private String phoneNumber;

    private final Integer collectCount = 1;

}
