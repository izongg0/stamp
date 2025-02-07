package odin.stamp.user.account.exception;

import odin.stamp.common.exception.AlreadyExistsException;

public class AccountAlreadyExistsException extends AlreadyExistsException {
    public AccountAlreadyExistsException() {
        super("alreadyExists.account");
    }
}
