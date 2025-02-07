package odin.stamp;

import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails principal){
        log.info(principal.getId().toString());
        return "true";
    }
}
