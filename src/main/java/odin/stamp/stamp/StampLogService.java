package odin.stamp.stamp;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.customer.Customer;
import odin.stamp.customer.CustomerService;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.customer.repository.CustomerRepository;
import odin.stamp.customer.repository.StoreCustomerRepository;
import odin.stamp.stamp.dto.StampCollectDto;
import odin.stamp.stamp.dto.StampCollectResDto;
import odin.stamp.stamp.dto.StampLogResDto;
import odin.stamp.stamp.repository.StampLogRepository;
import odin.stamp.stampconfig.StampConfig;
import odin.stamp.stampconfig.repository.StampConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StampLogService {

    private final StampConfigRepository stampConfigRepository;
    private final StoreCustomerRepository storeCustomerRepository;
    private final CustomerService customerService;
    private final StampLogRepository stampLogRepository;


    @Transactional
    public StampCollectResDto collect(StampCollectDto dto) {
        try {

            // 스탬프 설정 조회 (없으면 예외 발생)
            StampConfig stampConfig = stampConfigRepository.findByStoreId(dto.getStoreId())
                    .orElseThrow(() -> new EntityNotFoundException("스탬프 설정을 찾을 수 없습니다."));

            // 고객 정보 조회 (없으면 새로 생성)
            StoreCustomer storeCustomer = storeCustomerRepository.findByCustomerPhoneAndStoreId(dto.getPhoneNumber(), dto.getStoreId())
                    .orElseGet(() -> customerService.create(dto.getPhoneNumber(), dto.getStoreId()));

            log.info(storeCustomer.getId().toString());
            // 스탬프 리스트에서 만료된거 확인한 후 업데이트
            expireStamps(storeCustomer.getId());

            // 만료처리하고 난 이후,  사용 가능한 스탬프 개수 확인
            List<StampLog> stampLogs = get(storeCustomer.getId());

            List<StampLogResDto> stampLogResDtos = StampLogResDto.fromEntityList(stampLogs);

            log.info(stampLogs.toString());
            // 스탬프가 최대 스탬프 적립 개수 이상이면 적립 금지 (예외 발생)
            if (stampLogs.size() >= stampConfig.getMaxStampCount()) {
                return StampCollectResDto.of(
                        storeCustomer.getId(),
                        dto.getPhoneNumber(),
                        false,
                        "최대 적립 개수를 초과하였습니다.",
                        stampLogs.size(),
                        stampLogs.get(0).getCreatedAt(),
                        stampLogs.get(stampLogs.size() - 1).getExpiredDate(),
                        stampLogResDtos
                );

            }
            log.info("여기는?");

            // 적립
            StampLog stampLog = StampLog.collect(storeCustomer, stampConfig, dto.getCollectCount());

            log.info(stampLog.toString());
            // 고객 마지막 적립 날짜 업데이트
            storeCustomer.updateLastCollectedDate();

            stampLogRepository.save(stampLog);

            // 최종 총 스탬프
            List<StampLog> finalStampLogs = get(storeCustomer.getId());
            List<StampLogResDto> finalResDtos = StampLogResDto.fromEntityList(finalStampLogs);


            log.info(finalStampLogs.toString());
            return StampCollectResDto.of(
                    storeCustomer.getId(),
                    dto.getPhoneNumber(),
                    true,
                    "",
                    finalStampLogs.size(),
                    finalStampLogs.get(0).getCreatedAt(),
                    finalStampLogs.get(finalStampLogs.size() - 1).getExpiredDate(),
                    finalResDtos
            );
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }


    }


    // 만료되지 않은 스탬프 조회
    public List<StampLog> get(Long storeCustomerId) {
        return stampLogRepository.findValidStampLogs(storeCustomerId);
    }

    public void expireStamps(Long storeCustomerId) {
        stampLogRepository.expireStamps(storeCustomerId, LocalDateTime.now());
    }

}
