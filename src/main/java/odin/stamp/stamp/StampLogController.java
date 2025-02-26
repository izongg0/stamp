package odin.stamp.stamp;

import lombok.RequiredArgsConstructor;
import odin.stamp.common.authentication.CustomUserDetails;
import odin.stamp.stamp.dto.StampCollectDto;
import odin.stamp.stamp.dto.StampCollectResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class StampLogController {

    private final StampLogService stampLogService;

    @PostMapping
    public ResponseEntity<StampCollectResDto> collect(
            @RequestBody StampCollectDto dto,
            @AuthenticationPrincipal CustomUserDetails principal) {

        StampCollectResDto stampCollectResDto = stampLogService.collect(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(stampCollectResDto);
    }
}
