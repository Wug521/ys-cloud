package com.ys.office.auth.controller;

import com.ys.office.auth.service.ValidateCodeService;
import com.ys.office.common.entity.ResponseMessage;
import com.ys.office.common.exception.BaseException;
import com.ys.office.common.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
public class SecurityController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }


    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }


    @DeleteMapping("signout")
    public ResponseMessage signout(HttpServletRequest request) throws BaseException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        ResponseMessage ysResponse = new ResponseMessage();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new BaseException("退出登录失败");
        }
        return ysResponse.message("退出登录成功");
    }


    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }
}
