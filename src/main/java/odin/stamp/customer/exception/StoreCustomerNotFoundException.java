package odin.stamp.customer.exception;

import odin.stamp.common.exception.NotFoundException;

public class StoreCustomerNotFoundException extends NotFoundException {
    public StoreCustomerNotFoundException() {
        super("해당 상점 고객을 찾을 수 없습니다.");
    }
}
