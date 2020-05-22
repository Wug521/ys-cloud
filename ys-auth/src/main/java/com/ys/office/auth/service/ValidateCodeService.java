package com.ys.office.auth.service;

import com.ys.office.auth.properties.AuthProperties;
import com.ys.office.common.entity.Constant;
import com.ys.office.common.exception.ValidateCodeException;
import com.ys.office.common.properties.ValidateCodeProperties;
import com.ys.office.common.service.RedisService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ValidateCodeService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthProperties properties;


    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        String key = request.getParameter("key");
        if (StringUtils.isBlank(key)) {
            throw new ValidateCodeException("验证码key不能为空");
        }
        ValidateCodeProperties code = properties.getCode();
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);
        redisService.set(Constant.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), code.getTime());
        captcha.out(response.getOutputStream());
    }


    public void setHeader(HttpServletResponse response, String type) {

        if (StringUtils.equalsIgnoreCase(type, Constant.GIF)){
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        }else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }

        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    public Captcha createCaptcha(ValidateCodeProperties properties) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(properties.getType(), Constant.GIF)) {
            captcha = new GifCaptcha(properties.getWidth(), properties.getHeight(), properties.getLength());
        } else {
            captcha = new SpecCaptcha(properties.getWidth(), properties.getHeight(), properties.getLength());
        }
        captcha.setCharType(properties.getCharType());
        return captcha;
    }

    /**
     * 校验验证码
     *
     * @param key
     * @param value
     */
    public void check(String key, String value) throws ValidateCodeException {
        Object codeInRedis = redisService.get(Constant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }
}
