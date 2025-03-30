package odin.stamp.stampconfig;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.CustomUserDetails;
import odin.stamp.stampconfig.dto.StampConfigGetResDto;
import odin.stamp.stampconfig.dto.StampConfigUpdateDto;
import odin.stamp.store.Store;
import odin.stamp.store.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 서비스 주입을 위해
@RequestMapping("/stampconfig")
@Slf4j
public class StampConfigController {

    private final StampConfigService stampConfigService;
    private final StoreService storeService;

    /**
     *
     * @param  dto 상점 설정 수정
     * @return ResponseEntity
     */
    @PatchMapping()
    public ResponseEntity<Void> update(@RequestBody StampConfigUpdateDto dto,
                                       @AuthenticationPrincipal CustomUserDetails principal) {
        Store store = storeService.get(principal.getId());
        stampConfigService.update(dto,store.getId());
        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    public ResponseEntity<StampConfigGetResDto> getStampConfig(@AuthenticationPrincipal CustomUserDetails principal){
        Store store = storeService.get(principal.getId());

        StampConfig stampConfig = stampConfigService.get(store.getId());
        return ResponseEntity.ok(StampConfigGetResDto.from(stampConfig));
    }

}
