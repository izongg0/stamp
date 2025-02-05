package odin.stamp.user.account.repository;

import odin.stamp.user.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("select a from Account a" +
            "    where a.email = :email and a.isWithdrawal = false ")
    Optional<Account> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

}
