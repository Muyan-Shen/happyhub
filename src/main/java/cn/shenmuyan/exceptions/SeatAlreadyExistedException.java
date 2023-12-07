package cn.shenmuyan.exceptions;

/**
 * @className: SeatAlreadyExistedException
 * @author: 叶宝谦
 * @date: 2023/12/07 11:27
 **/
public class SeatAlreadyExistedException extends RuntimeException{
    private Integer code;

    public SeatAlreadyExistedException() {
        this(null,400);
    }

    public SeatAlreadyExistedException(String message, Integer code) {
        super("该活动已创建座位");
        this.code = code;
    }
}
