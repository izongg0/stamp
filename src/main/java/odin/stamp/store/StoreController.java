package odin.stamp.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.CustomUserDetails;
import odin.stamp.store.dto.StoreCreateDto;
import odin.stamp.store.dto.StoreGetResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody StoreCreateDto dto, @AuthenticationPrincipal CustomUserDetails principal){
        Store createStore = storeService.create(dto, principal.getId());
        return ResponseEntity.created(URI.create("/store" + createStore.getId())).build();

    }

    @GetMapping
    public ResponseEntity<StoreGetResDto> getStore(@AuthenticationPrincipal CustomUserDetails principal){
        Long accountId = principal.getId();
        Store store = storeService.get(accountId);

        return ResponseEntity.ok(
                StoreGetResDto.from(store));
    }

//    @GetMapping
//    public String test(){
//        return "done";
//    }
}
