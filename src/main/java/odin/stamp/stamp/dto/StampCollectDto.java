package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StampCollectDto {


    private String phoneNumber;

    private final Integer collectCount = 1;

    public StampCollectDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
