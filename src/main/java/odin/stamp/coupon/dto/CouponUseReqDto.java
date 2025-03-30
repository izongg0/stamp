package odin.stamp.coupon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CouponUseReqDto {

    private Long storeCustomerId;

    public CouponUseReqDto(Long storeCustomerId) {
        this.storeCustomerId = storeCustomerId;
    }
}
