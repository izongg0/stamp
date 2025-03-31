package odin.stamp;

import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/health-check")
    public String test(){

        return "혜령 사랑해 ♥";
    }
}
