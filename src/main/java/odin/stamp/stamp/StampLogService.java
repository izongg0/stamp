package odin.stamp.stamp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.customer.Customer;
import odin.stamp.customer.CustomerService;
import odin.stamp.customer.StoreCustomer;
import odin.stamp.customer.dto.CustomerStampStatusDto;
import odin.stamp.customer.exception.StoreCustomerNotFoundException;
import odin.stamp.customer.repository.CustomerRepository;
import odin.stamp.customer.repository.StoreCustomerRepository;
import odin.stamp.stamp.dto.*;
import odin.stamp.stamp.exception.NotEnoughStampsException;
import odin.stamp.stamp.repository.StampLogRepository;
import odin.stamp.stampconfig.StampConfig;
import odin.stamp.stampconfig.repository.StampConfigRepository;
import odin.stamp.store.Store;
import odin.stamp.store.exception.StoreNotFountException;
import odin.stamp.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final StoreRepository storeRepository;

    @Transactional
    public StampCollectResDto collect(StampCollectDto dto) {

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(StoreNotFountException::new);

        StampConfig stampConfig = store.getStampConfig();


        // 고객 정보 조회 (없으면 새로 생성)
        StoreCustomer storeCustomer = storeCustomerRepository.findByCustomerPhoneAndStoreId(dto.getPhoneNumber(), dto.getStoreId())
                .orElseGet(() -> customerService.create(dto.getPhoneNumber(), dto.getStoreId()));

        // 스탬프 리스트에서 만료된거 확인한 후 업데이트
        expireStamps(storeCustomer.getId());

        // 만료처리하고 난 이후,  사용 가능한 스탬프 개수 확인
        List<StampLog> stampLogs = get(storeCustomer.getId());

        // 적립 실패했을 경우를 위한 반환 데이터
        List<StampLogResDto> stampLogResDtos = StampLogResDto.fromEntityList(stampLogs);


        // 스탬프가 최대 스탬프 적립 개수 이상이면 적립 금지 (예외 발생)
        if (stampLogs.size() >= stampConfig.getMaxStampCount()) {
            // 적립 실패했을 경우를 위한 반환 데이터
            CustomerStampStatusDto stampStatusDto = CustomerStampStatusDto.of(
                    storeCustomer.getId(),
                    stampLogs,
                    stampLogResDtos
            );
            return StampCollectResDto.of(
                    storeCustomer.getId(),
                    dto.getPhoneNumber(),
                    false,
                    "최대 적립 개수를 초과하였습니다.",
                    stampStatusDto
            );
        }
        // 적립
        StampLog stampLog = StampLog.collect(storeCustomer, stampConfig, dto.getCollectCount());

        // 고객 마지막 적립 날짜 업데이트
        storeCustomer.updateLastCollectedDate();

        // 스탬프 디비에 저장
        stampLogRepository.save(stampLog);

        // 최종 총 스탬프
        List<StampLog> finalStampLogs = get(storeCustomer.getId());
        List<StampLogResDto> finalResDtos = StampLogResDto.fromEntityList(finalStampLogs);

        // 고객 총 스탬프 적립 횟수 증가
//        storeCustomer.updateTotalStampCount();

        CustomerStampStatusDto finalStampStatusDto = CustomerStampStatusDto.of(
                storeCustomer.getId(),
                finalStampLogs,
                finalResDtos
        );

        return StampCollectResDto.of(
                storeCustomer.getId(),
                dto.getPhoneNumber(),
                true,
                "",
                finalStampStatusDto
        );

    }


    @Transactional
    public StampUseResDto useStamp(StampUseReqDto dto) {

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(StoreNotFountException::new);

        // 고객 정보 조회 (없으면 새로 생성)
        StoreCustomer storeCustomer = storeCustomerRepository.findById(dto.getStoreCustomerId())
                .orElseThrow(StoreCustomerNotFoundException::new);

        // 스탬프 리스트에서 만료된거 확인한 후 업데이트
        expireStamps(storeCustomer.getId());

        // 만료처리하고 난 이후,  사용 가능한 스탬프 개수 확인
        List<StampLog> stampLogs = get(storeCustomer.getId());

        // 상점 완료스탬프 개수 가져오기 위해
        // 스탬프 설정 정보 가져오기
        StampConfig stampConfig = store.getStampConfig();

        // 현재 가진 스탬프 개수 검사
        if (stampLogs.size() < stampConfig.getCompletedStampCount()) {
            throw new NotEnoughStampsException();
        }

        // 사용한 스탬프로 표시
        // 10개를 모아야 사용할 수 있으면
        // 오래전에 적립한 스탬프부터 10개 isUse true 로 변경
        stampLogs.stream()
                .limit(stampConfig.getCompletedStampCount())
                .forEach(StampLog::use); // use() 메서드 호출

        // 고객 스탬프 사용 횟수 증가
        storeCustomer.updateTotalUseStampCount();

        return createStampUseResponse(storeCustomer, get(storeCustomer.getId()));
    }

    // 응답 dto
    private StampUseResDto createStampUseResponse(StoreCustomer storeCustomer, List<StampLog> stampLogs) {

        List<StampLogResDto> finalResDtos = StampLogResDto.fromEntityList(stampLogs);
        CustomerStampStatusDto stampStatusDto = CustomerStampStatusDto.of(
                storeCustomer.getId(),
                stampLogs,
                finalResDtos
        );

        return StampUseResDto.of(
                storeCustomer.getId(),
                storeCustomer.getCustomer().getPhoneNumber(),
                stampStatusDto
        );
    }


    // 만료되지 않은 스탬프 조회
    public List<StampLog> get(Long storeCustomerId) {
        return stampLogRepository.findValidStampLogs(storeCustomerId);
    }

    public void expireStamps(Long storeCustomerId) {
        stampLogRepository.expireStamps(storeCustomerId, LocalDateTime.now());
    }

}
