package cn.shenmuyan.exceptions;

import lombok.Data;

/**
 * @Title: ParamException
 * @Author ShenMuyan
 * @Package cn.shenmuyan.exceptions
 * @Date 2023/11/28 21:14
 * @description:
 */
@Data
public class ParamException extends RuntimeException{
    private  int code;

    public ParamException(int code, String message) {
        super(message);
        this.code = code;
    }
}
