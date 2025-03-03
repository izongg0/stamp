package odin.stamp.customer;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.customer.dto.CustomerStampStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {


    private final CustomerService customerService;

    @GetMapping("/stamp/{storeCustomerId}")
    @Parameters({
            @Parameter(name = "storeCustomerId", description = "상점고객 Id", required = true,in = ParameterIn.PATH),
    })
    public ResponseEntity<CustomerStampStatusDto> stampStatus(@PathVariable(name = "storeCustomerId") Long storeCustomerId){

        CustomerStampStatusDto customerStampStatusDto = customerService.getStampStatus(storeCustomerId);

        return ResponseEntity.ok(customerStampStatusDto);
    }

}
