package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品缓存服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
public class GoodsStockService {


    @Autowired
    private RedisUtil redisService;

    @Autowired
    private GoodsRepository goodsRepository;
}
