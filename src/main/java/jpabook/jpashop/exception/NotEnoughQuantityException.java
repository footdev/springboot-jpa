package jpabook.jpashop.exception;

public class NotEnoughQuantityException extends RuntimeException{

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
