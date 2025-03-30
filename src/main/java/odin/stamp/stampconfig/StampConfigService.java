package odin.stamp.stampconfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.stampconfig.dto.StampConfigUpdateDto;
import odin.stamp.stampconfig.exception.StampConfigNotFoundException;
import odin.stamp.stampconfig.repository.StampConfigRepository;
import odin.stamp.store.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class StampConfigService {

    private final StampConfigRepository stampConfigRepository;

    public StampConfig create(Store store){
        StampConfig stampConfig = StampConfig.of(store);
        return stampConfigRepository.save(stampConfig);
    }

    @Transactional
    public void update(StampConfigUpdateDto dto,Long storeId){

        StampConfig stampConfig = stampConfigRepository.findByStoreId(storeId)
                .orElseThrow(StampConfigNotFoundException:: new);

        stampConfig.update(
                dto.getCompletedStampCount(),
                dto.getMaxStampCount(),
                dto.getRewardItem(),
                dto.getStampCollectRule(),
                dto.getRecollectTime(),
                dto.getStampValidityPeriod(),
                dto.getCouponValidityPeriod(),
                dto.getUsePassword()
        );
    }

    public StampConfig get(Long storeId){
        return stampConfigRepository.findByStoreId(storeId)
                .orElseThrow(StampConfigNotFoundException::new);
    }




}
