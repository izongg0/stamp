package odin.stamp.stampconfig;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.stampconfig.dto.StampConfigGetResDto;
import odin.stamp.stampconfig.dto.StampConfigUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 서비스 주입을 위해
@RequestMapping("/stampconfig")
@Slf4j
public class StampConfigController {

    private final StampConfigService stampConfigService;

    /**
     *
     * @param  dto 상점 설정 수정
     * @param storeId 상점 id
     * @return ResponseEntity
     */
    @PatchMapping("/{storeId}")
    @Parameters({
            @Parameter(name = "storeId", description = "상점 Id", required = true,in = ParameterIn.PATH),
    })
    public ResponseEntity<Void> update(@RequestBody StampConfigUpdateDto dto,
                                            @PathVariable("storeId") Long storeId) {
        log.info("durldhsk");
        stampConfigService.update(dto,storeId);
        return ResponseEntity.noContent().build();
    }

    @Parameters({
            @Parameter(name = "storeId", description = "상점 Id", required = true,in = ParameterIn.PATH),
    })
    @GetMapping("/{storeId}")
    public ResponseEntity<StampConfigGetResDto> getStampConfig(@PathVariable("storeId") Long storeId){

        StampConfig stampConfig = stampConfigService.get(storeId);
        return ResponseEntity.ok(StampConfigGetResDto.from(stampConfig));
    }

}
