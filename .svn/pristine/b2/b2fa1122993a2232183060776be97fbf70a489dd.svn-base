package com.wanmi.sbc.goods.info.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMinusStockByIdRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsInfoStockTccService
 * @description 扣减库存tcc，需要外层去开启事务@GlobalTransactional
 * @date 2022/7/4 15:44
 */
@Slf4j
@Service
public class GoodsInfoStockTccService implements GoodsInfoStockTccInterface {

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private RedisUtil redisUtil;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired private GoodsInfoStockService goodsInfoStockService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subStock(List<GoodsInfoMinusStockDTO> dtoList) {
        String xid = RootContext.getXID();
        String key = RedisKeyConstant.STOCK_TCC+xid;
        dtoList.forEach(dto -> subStockById(dto.getStock(), dto.getGoodsInfoId(),key));
        return true;
    }

    @Override
    public boolean subCommit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.STOCK_TCC+xid;
        redisUtil.delete(key);
        return true;
    }

    @Override
    public boolean subRollback(BusinessActionContext actionContext) {

        String xid = actionContext.getXid();
        String key = RedisKeyConstant.STOCK_TCC+xid;
        this.subRollback(key);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addStock(List<GoodsInfoPlusStockDTO> dtoList) {
        String key = RedisKeyConstant.STOCK_TCC+RootContext.getXID();
        dtoList.forEach(dto -> addStockById(dto.getStock(), dto.getGoodsInfoId(),key));
        return true;
    }

    @Override
    public boolean addCommit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.STOCK_TCC+xid;
        redisUtil.delete(key);
        return true;
    }

    @Override
    public boolean addRollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.STOCK_TCC+xid;
        Map<String, String> skuStockMap = redisUtil.hgetall(key);
        if (MapUtils.isNotEmpty(skuStockMap)) {
            skuStockMap
                    .entrySet()
                    .forEach(
                            entry -> {
                                String redisKey =
                                        RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + entry.getKey();
                                Long stock = Long.parseLong(entry.getValue());
                                redisUtil.decrByKey(redisKey, stock);
                                // 发送mq，将扣减数据库库存重新加回来
                                GoodsInfoMinusStockByIdRequest request =
                                        GoodsInfoMinusStockByIdRequest.builder()
                                                .goodsInfoId(entry.getKey())
                                                .stock(stock)
                                                .build();
                                MqSendDTO mqSendDTO = new MqSendDTO();
                                mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_SUB);
                                mqSendDTO.setData(JSONObject.toJSONString(request));
                                mqSendProvider.send(mqSendDTO);
                            });
            redisUtil.delete(key);
        }
        return true;
    }

    @Override
    public List<String> subGifStock(List<GoodsInfoMinusStockDTO> dtoList) {
        List<String> errorSkuIds = new ArrayList<>();
        String key = RedisKeyConstant.GIF_STOCK_TCC+RootContext.getXID();
        for(GoodsInfoMinusStockDTO dto: dtoList){
            try{
                subStockById(dto.getStock(),dto.getGoodsInfoId(),key);
            }catch (Exception e){
                log.warn("扣减赠品库存出错，skuId:{},err:{}",dto.getGoodsInfoId(),e);
                errorSkuIds.add(dto.getGoodsInfoId());
            }
        }
        return errorSkuIds;
    }

    @Override
    public boolean subGifCommit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.GIF_STOCK_TCC+xid;
        redisUtil.delete(key);
        return true;
    }

    @Override
    public boolean subGifRollback(BusinessActionContext actionContext) {

        String xid = actionContext.getXid();
        String key = RedisKeyConstant.GIF_STOCK_TCC+xid;
        this.subRollback(key);
        return true;
    }

    private void subStockById(Long stock, String goodsInfoId,String key) {

        goodsInfoId = getGoodsInfoId(goodsInfoId);

        Long count =
                redisUtil.decrByKey(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增量扣减库存结束：skuId={},扣减的库存增量:{},扣减后的库存:{}", goodsInfoId, stock, count);
        if (count < 0) {
            log.info("redis中库存不足，返还redis库存开始，skuId={},stock={}...", goodsInfoId, stock);
            // 扣到负数时，返还库存。
            redisUtil.incrByKey(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
            log.info("redis中库存不足，返还redis库存结束，skuId={},stock={}...", goodsInfoId, stock);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
        } else {

            redisUtil.hset(key, goodsInfoId, stock.toString());
            // 发送mq，扣减数据库库存
            GoodsInfoMinusStockByIdRequest request =
                    GoodsInfoMinusStockByIdRequest.builder()
                            .goodsInfoId(goodsInfoId)
                            .stock(stock)
                            .build();
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_SUB);
            mqSendDTO.setData(JSONObject.toJSONString(request));
            mqSendProvider.send(mqSendDTO);
            log.info("扣减redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);
        }
    }

    private void addStockById(Long stock, String goodsInfoId,String key) {
        goodsInfoId = getGoodsInfoId(goodsInfoId);
        Long count =
                redisUtil.incrByKey(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增加库存结束：skuId={},增加的库存增量:{},增加后的库存:{}", goodsInfoId, stock, count);

        redisUtil.hset(key, goodsInfoId, stock.toString());
        // 发送mq，增加数据库库存
        GoodsInfoMinusStockByIdRequest request =
                GoodsInfoMinusStockByIdRequest.builder()
                        .goodsInfoId(goodsInfoId)
                        .stock(stock)
                        .build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
        log.info("增加redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);
    }

    private String getGoodsInfoId(String goodsInfoId) {
        // 更新商品库存时，判断其中商品是否来自供应商，来自供应商的商品，要改为更新供应商商品库存
        GoodsInfo goodsInfo = goodsInfoRepository.getOne(goodsInfoId);

        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfoId = goodsInfo.getProviderGoodsInfoId();
        }

        // 检查数据准确
        goodsInfoStockService.checkStockCache(goodsInfoId);
        return goodsInfoId;
    }

    /**
     * 扣减库存的事务回滚
     * @param key redis的key
     */
    private void subRollback(String key){
        Map<String, String> skuStockMap = redisUtil.hgetall(key);
        if (MapUtils.isNotEmpty(skuStockMap)) {
            skuStockMap
                    .entrySet()
                    .forEach(
                            entry -> {
                                String redisKey =
                                        RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + entry.getKey();
                                Long stock = Long.parseLong(entry.getValue());
                                redisUtil.incrByKey(redisKey, stock);
                                // 发送mq，将扣减数据库库存重新加回来
                                GoodsInfoMinusStockByIdRequest request =
                                        GoodsInfoMinusStockByIdRequest.builder()
                                                .goodsInfoId(entry.getKey())
                                                .stock(stock)
                                                .build();
                                MqSendDTO mqSendDTO = new MqSendDTO();
                                mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_ADD);
                                mqSendDTO.setData(JSONObject.toJSONString(request));
                                mqSendProvider.send(mqSendDTO);
                            });
            redisUtil.delete(key);
        }
    }
}
