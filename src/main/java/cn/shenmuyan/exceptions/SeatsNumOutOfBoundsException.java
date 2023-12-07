package cn.shenmuyan.exceptions;

/**
 * @className: SeatsNumOutOfBoundsException
 * @author: 叶宝谦
 * @date: 2023/12/06 21:10
 **/
public class SeatsNumOutOfBoundsException extends RuntimeException{
    private int code;

    public SeatsNumOutOfBoundsException(String message, int code) {
        super(message);
        this.code = code;
    }
}
