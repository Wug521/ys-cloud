package com.ys.office.common.interceptor;

import com.ys.office.common.entity.Constant;
import com.ys.office.common.entity.ResponseMessage;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 Zuul Token
        String token = request.getHeader(Constant.ZUUL_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(Constant.ZUUL_TOKEN_VALUE.getBytes()));

        // 校验 Zuul Token的正确性
        if (StringUtils.equals(token, zuulToken)) {
            return true;
        } else {
            ResponseMessage ysResponse = new ResponseMessage();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(JSONObject.toJSONString(ysResponse.message("请通过网关再请求资源")));
            return false;
        }
    }
}
