package odin.stamp.store.repository;

import odin.stamp.store.Store;
import odin.stamp.user.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {
    /**
     * accountId로 상점 조회
     * @param accountId
     * @return
     */
//    Store findByAccount_Id(Long accountId);

    @Query("SELECT DISTINCT s FROM Store s " +
            "LEFT JOIN FETCH s.stampConfig " +
            "LEFT JOIN FETCH s.storeCustomers " +
            "WHERE s.account.id = :accountId")
    Store findByAccount_Id(@Param("accountId") Long accountId);
}
