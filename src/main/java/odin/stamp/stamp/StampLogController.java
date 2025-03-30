package odin.stamp.stamp;

import lombok.RequiredArgsConstructor;
import odin.stamp.common.authentication.CustomUserDetails;
import odin.stamp.stamp.dto.StampCollectDto;
import odin.stamp.stamp.dto.StampCollectResDto;
import odin.stamp.stamp.dto.StampUseReqDto;
import odin.stamp.stamp.dto.StampUseResDto;
import odin.stamp.store.Store;
import odin.stamp.store.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class StampLogController {

    private final StampLogService stampLogService;
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StampCollectResDto> collect(
            @RequestBody StampCollectDto dto,
            @AuthenticationPrincipal CustomUserDetails principal) {
        Long accountId = principal.getId();
        Store store = storeService.get(accountId);
        StampCollectResDto stampCollectResDto = stampLogService.collect(dto,store.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(stampCollectResDto);
    }

//    @PutMapping("/use")
//    public ResponseEntity<StampUseResDto> use(
//            @RequestBody StampUseReqDto dto,
//            @AuthenticationPrincipal CustomUserDetails principal) {
//
//        StampUseResDto stampUseResDto = stampLogService.useStamp(dto);
//
//        return ResponseEntity.ok(stampUseResDto);
//    }
}
