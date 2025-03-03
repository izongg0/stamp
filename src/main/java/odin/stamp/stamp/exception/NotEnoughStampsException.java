package odin.stamp.stamp.exception;

public class NotEnoughStampsException extends RuntimeException{
    public NotEnoughStampsException() {
        super("보유한 스탬프 개수가 부족합니다.");
    }

}
