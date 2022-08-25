package com.baixin.prayblog.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;

/**
 * restful接口返回值.
 *
 * @author baixin
 * @since 2022/7/13 9:31
 */
@Data
public class ResultDto<T> {

  @ApiModelProperty("提示信息")
  private String message;

  @ApiModelProperty("返回结果值")
  private T value;

  @ApiModelProperty("成功失败标识(true/false)")
  private boolean success;

  @ApiModelProperty("消息代码")
  private String msgCode;

  @ApiModelProperty("其他结果值Map")
  private Map<Object, Object> resultMap;

  @ApiModelProperty("消息提示参数")
  private List<String> msgParams;

  public ResultDto() {}

  public ResultDto(T value) {
    this.success = true;
    this.value = value;
  }

  @ApiModelProperty("使用 new ResultDto(value)")
  //@Deprecated
  public static <T> ResultDto<T> valueOfSuccess(T value) {
    ResultDto<T> vo = new ResultDto<>();
    vo.value = value;
    vo.success = true;
    return vo;
  }

  public static <T> ResultDto<T> valueOfSuccess() {
    return valueOfSuccess(null);
  }

  public static <T> ResultDto<T> valueOfError(String msg, String msgCode, String... msgParam) {
    ResultDto<T> vo = new ResultDto<>();
    vo.message = msg;
    vo.success = false;
    vo.msgCode = msgCode;
    vo.addMsgParams(msgParam);
    return vo;
  }

  public static <T> ResultDto<T> valueOfError(String msg, Integer msgCode) {
    return valueOfError(msg, msgCode.toString());
  }

  public static <T> ResultDto<T> valueOfError(String msg, String msgCode) {
    return valueOfError(msg, msgCode, "");
  }

  public void addMsgParams(String... param) {
    if (msgParams == null) {
      msgParams = new ArrayList<>();
    }
    if (param == null || param.length == 0) {
      return;
    }
    msgParams.addAll(Arrays.asList(param));
  }

  /**
   * 在 resultMap 里设置翻页参数
   *
   * @param pageNum 页码
   * @param pageSize 页长
   * @param total 总条目数
   */
  public ResultDto<T> withPagination(int pageNum, int pageSize, long total) {
    if (resultMap == null) {
      resultMap = new HashMap<>();
    }
    resultMap.put("pageNum", pageNum);
    resultMap.put("pageSize", pageSize);
    resultMap.put("total", total);
    return this;
  }
}
