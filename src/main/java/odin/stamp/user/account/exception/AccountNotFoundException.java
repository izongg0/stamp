package odin.stamp.user.account.exception;


import odin.stamp.common.exception.AuthenticationFailedException;

public class AccountNotFoundException extends AuthenticationFailedException {
    public AccountNotFoundException() {
        super("notFound.account");
    }
}
