package odin.stamp.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.store.Store;

@Getter
@RequiredArgsConstructor // DTO에서는 final을 사용하고 Require을 하는게 좋다.
public class StoreGetResDto {

    private final Long storeId;

    private final String name;

    /** 전화번호 */
    private final String phoneNumber;

    /** 사업자 번호 */
    private final String registrationNumber;


    public static StoreGetResDto from(Store store) {
        return new StoreGetResDto(
                store.getId(),
                store.getName(),
                store.getPhoneNumber(),
                store.getRegistrationNumber()
        );
    }
}
