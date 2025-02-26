package odin.stamp.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// Model 을 Body 로 받을 때 기본 생성자가 필요함.
// 따라서 NoArgsConstructor 가 있어야함.
// final 로 해서 force true 해줘야함
// 그래야 초기값을 강제로 설정해줌.
// final 없애면 강제안해도됨
@NoArgsConstructor(force = true)
public class StoreCreateDto {

    // 토큰으로 유저 정보 가져오기 때문에 필요없음.
//    /** 계정 아이디 */
//    private final Long accountId;

    /** 상점이름 */
    private final String name;

    /** 전화번호 */
    private final String phoneNumber;

    /** 사업자 번호 */
    private final String registrationNumber;


}
