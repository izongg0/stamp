package odin.stamp.coupon;

import lombok.RequiredArgsConstructor;
import odin.stamp.common.authentication.CustomUserDetails;
import odin.stamp.coupon.dto.CouponUseReqDto;
import odin.stamp.customer.dto.CustomerStampStatusDto;
import odin.stamp.stamp.StampLogService;
import odin.stamp.stamp.dto.StampUseReqDto;
import odin.stamp.store.Store;
import odin.stamp.store.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final StampLogService stampLogService;
    private final StoreService storeService;

    @PutMapping("/use")
    public ResponseEntity<CustomerStampStatusDto> use(
            @RequestBody CouponUseReqDto dto,
            @AuthenticationPrincipal CustomUserDetails principal) {

        Long accountId = principal.getId();
        Store store = storeService.get(accountId);
            CustomerStampStatusDto stampUseResDto = stampLogService.useCoupon(dto,store.getId());

        return ResponseEntity.ok(stampUseResDto);
    }

}




