package odin.stamp.store;

import odin.stamp.store.dto.StoreCreateDto;
import odin.stamp.store.repository.StoreRepository;
import odin.stamp.user.account.Account;
import odin.stamp.user.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mockito 기능 활성화
class StoreServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void testCreateStore_Success() {
        // Given
        Long accountId = 1L;
        Account mockAccount = Account.of("test","test@example.com", "password");
        StoreCreateDto storeCreateDto = new StoreCreateDto("My Cafe", "010-1234-5678", "123-45-67890");

        Store mockStore = Store.of(mockAccount, storeCreateDto.getName(), storeCreateDto.getPhoneNumber(), storeCreateDto.getRegistrationNumber());

        // ⭐ Mock 객체가 특정 값 반환하도록 설정
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(storeRepository.save(any(Store.class))).thenReturn(mockStore);

        // When
        Store createdStore = storeService.create(storeCreateDto, accountId);

        // Then
        assertNotNull(createdStore);
        assertEquals(storeCreateDto.getName(), createdStore.getName());
        assertEquals(storeCreateDto.getPhoneNumber(), createdStore.getPhoneNumber());
        assertEquals(storeCreateDto.getRegistrationNumber(), createdStore.getRegistrationNumber());

        // 특정 메서드가 호출되었는지 검증
        verify(accountRepository).findById(accountId);
        verify(storeRepository).save(any(Store.class));
    }
}