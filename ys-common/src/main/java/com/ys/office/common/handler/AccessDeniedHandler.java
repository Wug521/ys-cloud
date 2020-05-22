package com.ys.office.common.handler;

import com.ys.office.common.entity.ResponseMessage;
import com.ys.office.common.utils.CommonUtil;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseMessage ysResponse = new ResponseMessage();
        CommonUtil.makeResponse(
                httpServletResponse, MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_FORBIDDEN, ysResponse.message("没有权限访问该资源"));
    }
}
