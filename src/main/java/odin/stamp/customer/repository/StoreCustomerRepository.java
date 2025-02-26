package odin.stamp.customer.repository;

import odin.stamp.customer.Customer;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreCustomerRepository extends JpaRepository<StoreCustomer,Long> {
    boolean existsByStoreAndCustomer(Store store, Customer customer);

    // 전화번호를 통해 고객 테이블을 거쳐서 상점 고객 데이터를 가져옴
    // 패치 조인을 안쓰면 어떻게 쿼리가 늘어나는지 꼭 확인 필요함
    @Query("SELECT DISTINCT sc FROM StoreCustomer sc " +
            "JOIN FETCH sc.customer c " +
            "JOIN FETCH sc.store s " +
            "WHERE c.phoneNumber = :phoneNumber AND s.id = :storeId")
    Optional<StoreCustomer> findByCustomerPhoneAndStoreId(
            @Param("phoneNumber") String phoneNumber,
            @Param("storeId") Long storeId
    );
}
