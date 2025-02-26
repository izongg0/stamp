package odin.stamp.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odin.stamp.common.entity.BaseEntity;
import odin.stamp.store.Store;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<StoreCustomer> storeCustomers = new ArrayList<>();

    public static Customer of(String phoneNumber){
        Customer customer = new Customer();
        customer.phoneNumber = phoneNumber;
        return customer;
    }

}
