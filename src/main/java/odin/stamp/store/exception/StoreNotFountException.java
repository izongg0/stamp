package odin.stamp.store.exception;

import odin.stamp.common.exception.NotFoundException;

public class StoreNotFountException  extends NotFoundException {
    public StoreNotFountException() {
        super("상점을 찾을 수 없습니다.");
    }
}
