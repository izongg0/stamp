package odin.stamp.stampconfig.repository;

import odin.stamp.stampconfig.StampConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampConfigRepository extends JpaRepository<StampConfig,Long> {
    Optional<StampConfig> findByStoreId(Long storeId);

}
