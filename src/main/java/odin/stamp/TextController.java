package odin.stamp;

import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.AuthenticationInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TextController {
    @GetMapping("/test")
    public String test(AuthenticationInfo authInfo){
        Long accountId = authInfo.getAccountId();

        log.info(accountId.toString());

        return "true";
    }
}
