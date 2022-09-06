package com.balawo.mp.service.impl;

import com.balawo.mp.entity.Payment;
import com.balawo.mp.mapper.PaymentMapper;
import com.balawo.mp.service.PaymentService;
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
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

}
