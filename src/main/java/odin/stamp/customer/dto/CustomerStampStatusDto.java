package odin.stamp.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.coupon.Coupon;
import odin.stamp.stamp.StampLog;
import odin.stamp.stamp.dto.StampLogResDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CustomerStampStatusDto {

    private Long storeCustomerId;

    /** 전화번호 */
    private String phoneNumber;

    /** 현재 가진 스탬프 개수 */
    private int totalStamp;

    /** 총 스탬프 개수 */
    private int accumulatedStamp;

    /** 현재 가진 쿠폰 개수 */
    private int totalCoupon;

    /** 마지막 적립일 */
    private LocalDateTime lastCollectedDate;

    /** 제일 근접한 만료날짜 */
    private LocalDateTime recentExpiredDate;

    /** 제일 근접한 쿠폰 만료날짜 */
    private LocalDateTime couponRecentExpiredDate;

    /**
     * 현재 가진 스탬프
     * 만료일이 가까운 순서로
     */
    private List<StampLogResDto> stampLogs = new ArrayList<>();

    public CustomerStampStatusDto(Long storeCustomerId, String phoneNumber,int totalStamp,int accumulatedStamp, int totalCoupn, LocalDateTime lastCollectedDate, LocalDateTime recentExpiredDate,LocalDateTime couponRecentExpiredDate, List<StampLogResDto> stampLogs) {
        this.storeCustomerId = storeCustomerId;
        this.phoneNumber = phoneNumber;
        this.totalStamp = totalStamp;
        this.accumulatedStamp = accumulatedStamp;
        this.totalCoupon = totalCoupn;
        this.lastCollectedDate = lastCollectedDate;
        this.recentExpiredDate = recentExpiredDate;
        this.couponRecentExpiredDate = couponRecentExpiredDate;
        this.stampLogs = stampLogs;
    }

    public static CustomerStampStatusDto of(Long storeCustomerId, String phoneNumber, List<StampLog> stampLogs,List<StampLog> accumulatedStamps, List<Coupon> coupons, List<StampLogResDto> resDtos){
        return new CustomerStampStatusDto(
                storeCustomerId,
                phoneNumber,
                stampLogs.size(),
                accumulatedStamps.size(),
                coupons.size(),
                !stampLogs.isEmpty() ? stampLogs.get(0).getCreatedAt():null,
                !stampLogs.isEmpty() ? stampLogs.get(stampLogs.size() - 1).getExpiredDate():null,
                !coupons.isEmpty() ? coupons.get(coupons.size() - 1).getExpiredDate():null,
                resDtos
        );


    }
}
