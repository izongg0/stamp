package odin.stamp.customer;

import lombok.RequiredArgsConstructor;
import odin.stamp.customer.dto.CustomerStampStatusDto;
import odin.stamp.customer.dto.StoreCustomerCreateDto;
import odin.stamp.customer.exception.CustomerAlreadyExistException;
import odin.stamp.customer.exception.StoreCustomerNotFoundException;
import odin.stamp.customer.repository.CustomerRepository;
import odin.stamp.customer.repository.StoreCustomerRepository;
import odin.stamp.stamp.StampLog;
import odin.stamp.stamp.dto.StampLogResDto;
import odin.stamp.stamp.repository.StampLogRepository;
import odin.stamp.store.Store;
import odin.stamp.store.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final StampLogRepository stampLogRepository;
    private final StoreCustomerRepository storeCustomerRepository;
    private final StoreService storeService;

    @Transactional
    public StoreCustomer create(String phoneNumber,Long storeId){


        Store store = storeService.getByStoreId(storeId);


        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> customerRepository.save(Customer.of(phoneNumber)));

        // StoreCustomer 가 이미 존재하는지 확인 (중복 방지)
        boolean exists = storeCustomerRepository.existsByStoreAndCustomer(store, customer);

        if (exists) {
            throw new CustomerAlreadyExistException();
        }
        StoreCustomer storeCustomer= StoreCustomer.of(store,customer);

        return storeCustomerRepository.save(storeCustomer);

    }

    public StoreCustomer get(String phoneNumber, Long storeId){

        return storeCustomerRepository.findByCustomerPhoneAndStoreId(phoneNumber, storeId)
                .orElseThrow(StoreCustomerNotFoundException::new);
    }

    public CustomerStampStatusDto getStampStatus(Long storeCustomerId){
        List<StampLog> stampLogs = stampLogRepository.findValidStampLogs(storeCustomerId);
        List<StampLogResDto> stampLogResDtos = StampLogResDto.fromEntityList(stampLogs);

        return CustomerStampStatusDto.of(
                storeCustomerId,
                stampLogs,
                stampLogResDtos
        );
    }

}
