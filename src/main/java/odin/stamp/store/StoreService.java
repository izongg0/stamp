package odin.stamp.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.stampconfig.StampConfig;
import odin.stamp.stampconfig.StampConfigService;
import odin.stamp.store.dto.StoreCreateDto;
import odin.stamp.store.exception.StoreNotFountException;
import odin.stamp.store.repository.StoreRepository;
import odin.stamp.user.account.Account;
import odin.stamp.user.account.exception.AccountNotFoundException;
import odin.stamp.user.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final AccountRepository accountRepository;
    private final StoreRepository storeRepository;
    private final StampConfigService stampConfigService;

    @Transactional
    public Store create(StoreCreateDto dto,Long accountId){

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        Store store = Store.of(account, dto.getName(), dto.getPhoneNumber(), dto.getRegistrationNumber());

        stampConfigService.create(store);

        return storeRepository.save(store);
    }

    public Store get(Long accountId){

        Store store = storeRepository.findByAccount_Id(accountId);
        List<StoreCustomer> storeCustomers =  store.getStoreCustomers();
        return storeRepository.findByAccount_Id(accountId);
    }
    public Store getByStoreId(Long storeId){

        return storeRepository.findById(storeId)
                .orElseThrow(StoreNotFountException::new);
    }
}
