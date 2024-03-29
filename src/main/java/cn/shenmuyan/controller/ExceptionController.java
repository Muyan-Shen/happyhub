package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.exceptions.SeatAlreadyExistedException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import cn.shenmuyan.exceptions.SeatsNumOutOfBoundsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 异常处理controller
 * @className: ExceptionController
 * @author: 叶宝谦
 * @date: 2023/12/04 19:33
 **/
@RestControllerAdvice
public class ExceptionController {
    /**
     * 处理vo数据异常的问题，返回400
     * @param e
     * @return
     */
    @ExceptionHandler
    public SaResult handle(BindException e){
        SaResult result = SaResult.error();
        result.setCode(400);
        result.setMsg("参数错误");
        //得到所有报错的字段
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String,List<String>> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            List<String> o = errors.get(fieldError.getField());
            if (o==null){
                o = new ArrayList<>();
                errors.put(fieldError.getField(),o);
            }
            o.add(fieldError.getDefaultMessage());
        }
        return result.setData(errors);
    }
    @ExceptionHandler
    public SaResult handle(ConstraintViolationException e){
        SaResult result = SaResult.error();
        result.setCode(400);
        result.setMsg("参数校验失败");
        Map<String,List<String>> errors=new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl propertyPath = (PathImpl) constraintViolation.getPropertyPath();
            String PropertyPath = propertyPath.getLeafNode().toString();
            String message = constraintViolation.getMessage();
            List<String> list =errors.get(PropertyPath);
            if(list==null){
                list=new ArrayList<>();
                errors.put(PropertyPath,list);
            }
            list.add(message);

        }
        return result.setData(errors);
    }

    /**
     * 订座大于当前剩余座位数异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public SaResult handle(SeatsNumOutOfBoundsException e){
        return SaResult.error(e.getMessage()).setCode(400);
    }
    /**
     * 活动座位已创建异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public SaResult handle(SeatAlreadyExistedException e){
        return SaResult.error(e.getMessage()).setCode(400);
    }
}
