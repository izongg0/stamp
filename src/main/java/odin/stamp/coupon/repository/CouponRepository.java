package odin.stamp.coupon.repository;

import odin.stamp.coupon.Coupon;
import odin.stamp.stamp.StampLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    @Query("SELECT s FROM Coupon s " +
            "JOIN FETCH s.storeCustomer " +
            "WHERE s.storeCustomer.id = :storeCustomerId " +
            "AND s.isExpired = false AND s.isUse = false " +
            "ORDER BY s.expiredDate ASC")
    List<Coupon> findValidCoupon(@Param("storeCustomerId") Long storeCustomerId);


    @Modifying
    @Query("UPDATE Coupon s SET s.isExpired = true WHERE s.storeCustomer.id = :storeCustomerId AND s.expiredDate < :now AND s.isExpired = false")
    int expireCoupons(@Param("storeCustomerId") Long storeCustomerId, @Param("now") LocalDateTime now);

}
