package odin.stamp.stamp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.customer.dto.CustomerStampStatusDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class StampUseResDto {

    private Long storeCustomerId;

    private String phoneNumber;


    private CustomerStampStatusDto stampStatusDto;

    public StampUseResDto(Long storeCustomerId, String phoneNumber, CustomerStampStatusDto stampStatusDto) {
        this.storeCustomerId = storeCustomerId;
        this.phoneNumber = phoneNumber;
        this.stampStatusDto = stampStatusDto;
    }

    public static StampUseResDto of(Long storeCustomerId, String phoneNumber,  CustomerStampStatusDto stampStatusDto){
        return new StampUseResDto(
                storeCustomerId,
                phoneNumber,
                stampStatusDto
        );

    }
}
