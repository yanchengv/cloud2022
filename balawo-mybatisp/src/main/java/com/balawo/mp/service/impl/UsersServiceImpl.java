package com.balawo.mp.service.impl;

import com.balawo.mp.entity.Users;
import com.balawo.mp.mapper.UsersMapper;
import com.balawo.mp.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
