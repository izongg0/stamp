package odin.stamp.stamp.repository;

import odin.stamp.stamp.StampLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StampLogRepository extends JpaRepository<StampLog,Long> {

//    @Query("SELECT s FROM StampLog s WHERE s.storeCustomer.id = :storeCustomerId AND s.isExpired = false AND s.isUse = false ORDER BY s.expiredDate DESC")
//    List<StampLog> findValidStampLogs(@Param("storeCustomerId") Long storeCustomerId);

    @Query("SELECT s FROM StampLog s " +
            "JOIN FETCH s.storeCustomer " +
            "WHERE s.storeCustomer.id = :storeCustomerId " +
            "AND s.isExpired = false AND s.isUse = false " +
            "ORDER BY s.expiredDate ASC")
    List<StampLog> findValidStampLogs(@Param("storeCustomerId") Long storeCustomerId);

    @Query("SELECT s FROM StampLog s " +
            "JOIN FETCH s.storeCustomer " +
            "WHERE s.storeCustomer.id = :storeCustomerId")
    List<StampLog> findByStoreCustomer_Id(@Param("storeCustomerId") Long storeCustomerId);


    @Modifying
    @Query("UPDATE StampLog s SET s.isExpired = true WHERE s.storeCustomer.id = :storeCustomerId AND s.expiredDate < :now AND s.isExpired = false")
    int expireStamps(@Param("storeCustomerId") Long storeCustomerId, @Param("now") LocalDateTime now);

}
