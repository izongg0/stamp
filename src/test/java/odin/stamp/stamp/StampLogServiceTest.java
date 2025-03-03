package odin.stamp.stamp;

import jakarta.persistence.EntityNotFoundException;
import odin.stamp.customer.Customer;
import odin.stamp.customer.CustomerService;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.customer.dto.CustomerStampStatusDto;
import odin.stamp.customer.repository.CustomerRepository;
import odin.stamp.customer.repository.StoreCustomerRepository;
import odin.stamp.stamp.dto.StampCollectDto;
import odin.stamp.stamp.dto.StampCollectResDto;
import odin.stamp.stamp.dto.StampUseReqDto;
import odin.stamp.stamp.dto.StampUseResDto;
import odin.stamp.stamp.repository.StampLogRepository;
import odin.stamp.stampconfig.StampConfig;
import odin.stamp.stampconfig.repository.StampConfigRepository;
import odin.stamp.store.Store;
import odin.stamp.store.StoreService;
import odin.stamp.store.dto.StoreCreateDto;
import odin.stamp.store.repository.StoreRepository;
import odin.stamp.user.account.Account;
import odin.stamp.user.account.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@ExtendWith(MockitoExtension.class)
class StampLogServiceTest {

    @Autowired
    private StampLogService stampLogService;

    @Autowired
    private StampLogRepository stampLogRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private CustomerService customerService;

    private Long collectStamp(int count) {
        Long storeCustomerId = 1L;
        for (int i = 0; i < count; i++) {
            StampCollectDto dto = new StampCollectDto(1L, "01011111111");
            StampCollectResDto stampCollectResDto = stampLogService.collect(dto);
            storeCustomerId = stampCollectResDto.getStoreCustomerId();
        }

        return storeCustomerId;
    }

    @Test
    void 스탬프_사용_테스트() {

        //given
        // 스탬프를 사용하기 전에 적립이 되어있어야함
        int stampCount = 22;
        Store store = storeService.getByStoreId(1L);
        Long storeCustomerId = collectStamp(stampCount);

        // when
        // 스탬프 사용
        StampUseResDto stampUseResDto = stampLogService.useStamp(new StampUseReqDto(1L, storeCustomerId));

        /*
         * 스탬프 설정에서 스탬프는 10개가 모여야 사용할 수 있다고 하였을 때,
         * 22개가 적립되어있는 상태에서 스탬프 사용을 하면 12개가 남아야한다.
         */

        // then
        CustomerStampStatusDto stampStatus = customerService.getStampStatus(storeCustomerId);

        assertEquals(
                stampCount - store.getStampConfig().getCompletedStampCount(),
                stampUseResDto.getStampStatusDto().getTotalStamp()
        );
        assertEquals(stampCount - store.getStampConfig().getCompletedStampCount(),stampStatus.getTotalStamp());

    }


    @Test
    void 일반_적립_테스트() {

        //given
        int count = 1;

        // 10개 적립
        Long storeCustomerId = 1L;

        // when
        for (int i = 0; i < count; i++) {
            StampCollectDto dto = new StampCollectDto(1L, "01011111111");
            StampCollectResDto stampCollectResDto = stampLogService.collect(dto);
            storeCustomerId = stampCollectResDto.getStoreCustomerId();

        }

        // then
        // 고객 적립 상태
        CustomerStampStatusDto stampStatus = customerService.getStampStatus(storeCustomerId);

        List<StampLog> stampLogs = stampLogRepository.findValidStampLogs(storeCustomerId);
        assertEquals(count,stampLogs.size());
        assertEquals(count,stampStatus.getTotalStamp());

    }


}