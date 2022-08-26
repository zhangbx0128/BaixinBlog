package com.baixin.prayblog.aspect;

import cn.hutool.json.JSONObject;
import com.baixin.prayblog.annotation.OperatorLog;
import com.baixin.prayblog.common.dto.ResultDto;
import com.baixin.prayblog.utils.IpAddressUtils;
import com.baixin.prayblog.utils.JwtUtils;
import com.baixin.prayblog.utils.UserAgentUtils;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authc.AuthenticationToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

/**

 *
 * @author zhangbaixin
 */
@Component
@Aspect
@Slf4j
public class OperatorLogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    ISysOperatorLogService iSysOperatorLogService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserAgentUtils userAgentUtils;

//    @Autowired
//    RedisService redisService;


    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(operatorLog)")
    public void log(OperatorLog operatorLog){}

    /**
     * 配置环绕通知
     *
     * @param joinPoint 连接点
     * @return 返回方法执行后的结果
     */
    @Around("log(operatorLog)")
    public Object logAround(ProceedingJoinPoint joinPoint,OperatorLog operatorLog) throws Throwable {

        currentTime.set(System.currentTimeMillis());
        //获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //让目标方法执行 获取返回的结果
        Object result = joinPoint.proceed();
        //访问时间
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //获取账号
        String token = request.getHeader("Authorization");

        String account = jwtUtils.getUserNameFromToken(token);;
        //异步保存至数据库
        log.info(account);
        saveVisitLog(joinPoint, operatorLog, request, result, times, account);

        return result;
    }
    /**
     * 异步设置VisitLogger对象属性并保存到数据库中
     *
     * @param joinPoint
     * @param visitLogger
     * @param result
     * @param times
     * @return
     */
    @Async
    void saveVisitLog(ProceedingJoinPoint joinPoint, OperatorLog visitLogger, HttpServletRequest request, Object result,
                      int times, String account) {
        //请求接口
        String url = request.getRequestURI();
        //请求方式
        String method = request.getMethod();
        //访问行为
        String behavior = visitLogger.behavior();
        //访问内容
        String content = visitLogger.content();
        //ip
        String ip = request.getHeader("x-forwarded-for");
        //ip源头
        String ipSource = IpAddressUtils.getCityInfo(ip);
        //用户代理--不明确
        String userAgent = request.getHeader("User-Agent");
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        //操作系统
        String os = userAgentMap.get("os");
        //浏览器
        String browser = userAgentMap.get("browser");
        //获取参数名和参数值
        Map<String, Object> requestParams = new LinkedHashMap<>();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if( args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile){
                continue;
            }
            requestParams.put(parameterNames[i], args[i]);
        }
        //根据访问内容和返回的结果判断访问的内容并进行备注
//        Map<String, String> map = judgeBehavior(behavior, content, requestParams, result);
//        SysOperatorLog log = new SysOperatorLog();
//        log.setAccount(account);
//        log.setUrl(url);
//        log.setMethod(method);
//        log.setOs(os);
//        log.setTimes(times);
//        log.setIp(ip);
//        log.setBrowser(browser);
//        log.setBehavior(behavior);
//        log.setParam(new JSONObject(requestParams).toString());
//        log.setIpSource(ipSource);
//        log.setContent(map.get("content"));
//        log.setRemark(map.get("remark"));
//        log.setCreateTime(new Date());
//        log.setUserAgent(userAgent);
//        iSysOperatorLogService.saveOrUpdate(log);
    }
    /**
     * 根据访问行为，设置对应的访问内容或备注
     *
     * @param behavior
     * @param content
     * @param requestParams
     * @param result
     * @return 返回内容和备注为主键的map
     */
    private Map<String, String> judgeBehavior(String behavior, String content, Map<String, Object> requestParams, Object result) {
        Map<String, String> map = new HashMap<>();
        String remark = "";
        if (behavior.equals("访问页面") && (content.equals("首页"))) {

            remark = "第"   + "页";
        } else if (behavior.equals("查看博客")) {
            ResultDto res = (ResultDto) result;
            if (res.getMessage().contains("200")) {
                User blog = (User) res;
                String title = blog.getFullName();
                content = title;
                remark = "文章标题：" + title;
            }
        }
        map.put("remark", remark);
        map.put("content", content);
        return map;
    }

}