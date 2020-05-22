package com.ys.office.auth.mapper;

import com.ys.office.common.entity.system.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<SystemUser> {

    SystemUser findByName(String username);
}
