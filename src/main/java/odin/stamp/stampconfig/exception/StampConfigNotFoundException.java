package odin.stamp.stampconfig.exception;

import odin.stamp.common.exception.NotFoundException;

public class StampConfigNotFoundException extends NotFoundException {
    public StampConfigNotFoundException() {
        super("스탬프 설정을 찾을 수 없습니다.");
    }
}
