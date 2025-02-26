package odin.stamp.customer.exception;

import odin.stamp.common.exception.AlreadyExistsException;

public class CustomerAlreadyExistException extends AlreadyExistsException {
    public CustomerAlreadyExistException() {
        super("이미 존재하는 고객입니다.");
    }

}
