package com.baixin.prayblog.exception;



import com.baixin.prayblog.common.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * 全局异常处理
 *
 * @author zhangdada
 * @date 2022/7/15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro的授权异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public ResultDto jsonExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("权限不足:-------------->{}", e.getMessage());
        return ResultDto.valueOfError( "你的级别还不够高,加油吧！少年。",400);
    }

    /**
     * 捕捉shiro的认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultDto handle401(ShiroException e) {
        log.error("未登录访问:-------------->{}", e.getMessage());
        return ResultDto.valueOfError(e.getMessage(), 400);
    }

    /**
     * 处理Assert的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultDto handler(IllegalArgumentException e) throws IOException {
        log.error("Assert异常:-------------->{}", e.getMessage());
        return ResultDto.valueOfError(e.getMessage(),400);
    }

    /**
     * @Validated 方法参数异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultDto handler(MethodArgumentNotValidException e) throws IOException {
        log.error("方法参数异常:-------------->", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return ResultDto.valueOfError(objectError.getDefaultMessage(),400);
    }

    /**
     * @Validated 校验错误异常处理
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = RuntimeException.class)
//    public Result handler(RuntimeException e) throws IOException {
//        log.error("运行时异常:-------------->", e);
//        return Result.fail(e.getMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SQLException.class)
    public ResultDto handler(SQLException e) {
        log.error("SQL异常:-------------->", e);
        return ResultDto.valueOfError(e.getMessage(), "400");

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultDto handler(HttpMessageNotReadableException e) {
        log.error("传入参数未识别:-------------->", e);
        return ResultDto.valueOfError(e.getMessage(), "400");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public ResultDto handler(SQLSyntaxErrorException e) {
        log.error("SQL语句错误:-------------->", e);
        return ResultDto.valueOfError(e.getMessage(), "400");
    }
}
