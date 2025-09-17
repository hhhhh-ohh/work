package com.wanmi.perseus.task;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.ares.request.mq.*;
import com.wanmi.perseus.param.StaticParam;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * PV/UV汇总redis统计数据并推送到统计系统
 *
 * @author bail 2017-09-21
 */

@Component
public class PvUvScheduler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * @return
     */
    @XxlJob(value = "PvUvJobHandler")
    public void execute() {
        // type 1 一天执行一次 2、上一次任务执行后间隔多长时间执行下一次任务
        String type = XxlJobHelper.getJobParam();
        XxlJobHelper.log("PV/UV汇总redis统计数据并推送到统计系统.");
        if ("1".equals(type)) {
            this.pushYesterdaySumDatas();//昨天
        } else {
            this.fixedRatePushSumDatas();//今天
        }
    }

    /**
     * 定时间隔汇总推送pv/uv汇总数据(5分钟执行一次)
     *
     * @author bail 2017-09-22
     */
    //fixedDelay: 上一次任务执行后间隔多长时间执行下一次任务
//    @Scheduled(fixedRate=300000)
    public void fixedRatePushSumDatas() {
        sumAndPush(LocalDate.now());//今天
    }

    /**
     * 凌晨0点01分推送昨日汇总数据(防止定时间隔推送会遗漏24:00前的一段间隔的数据)
     * 注意: 数据在凌晨2点就会过期失效
     *
     * @author bail 2017-09-22
     */
//    @Scheduled(cron = "0 1 0 * * ?")
    public void pushYesterdaySumDatas() {
        sumAndPush(LocalDate.now().minusDays(1L));//昨天
        sendMarketing(LocalDate.now().minusDays(1L));
    }


    /**
     * 汇总redsi统计数据并推送统计系统
     *
     * @author bail 2017-09-21
     */
    public void sumAndPush(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        /**1.定义汇总JSON对象(3层)*/
        FlowRequest sumObj = new FlowRequest();//统计对象
        sumObj.setOperationDate(LocalDateTime.now().toLocalDate());

        /**2.汇总整站分端pv*/
        sumObj.setPv(setPvCommon(StaticParam.PV_KEY, dateStr));

        /**3.汇总整站分端uv*/
        sumObj.setUv(setUvCommon(StaticParam.UV_KEY, dateStr));

        /**4.汇总各Sku分端pv以及uv*/
        Set<String> unoinSkuKeys;//各端出现的skuId的并集(即所有的skuId)
        List<GoodsInfoFlow> goodsInfoFlowList;//各sku的pv,uv汇总结果List
        GoodsInfoFlow goodsInfoFlow;//某sku的pv,uv
        /**4.1.获取各端skuId的并集(pv的sku并集同时也代表uv的sku并集)*/
        String allPatternSkuUv = String.format(StaticParam.SKU_UV_KEY, StaticParam.END_ALL, dateStr, "*", "*");
        unoinSkuKeys = stringRedisTemplate.keys(allPatternSkuUv);
        /**4.2.遍历各端skuId的并集,获取某skuId的各端统计数字*/
        Iterator<String> itSku = unoinSkuKeys.iterator();
        String skuId;
        String companyId;
        goodsInfoFlowList = new ArrayList<>(unoinSkuKeys.size());
        while (itSku.hasNext()) {
            String tmp = itSku.next();
            String[] str = tmp.split(":");
            if (str.length < 7) {
                continue;
            }
            //截取出skuId
            skuId = str[4];
            //截取出shopid
            companyId = str[6];
            goodsInfoFlow = new GoodsInfoFlow();
            goodsInfoFlowList.add(goodsInfoFlow);//多个skuId的各端pv与uv
            goodsInfoFlow.setSkuId(skuId);//记录skuId
            goodsInfoFlow.setCompanyId(companyId);
            /**4.3.汇总各Sku分端pv*/
            goodsInfoFlow.setPv(setPvCommon(StaticParam.SKU_PV_KEY, dateStr, skuId,companyId));
            /**4.4.汇总各Sku分端uv*/
            goodsInfoFlow.setUv(setUvCommon(StaticParam.SKU_UV_KEY, dateStr, skuId,companyId));
        }
        sumObj.setSkus(goodsInfoFlowList);

        /**5.汇总整站商品页总的分端pv*/
        sumObj.setSkuTotalPv(setPvCommon(StaticParam.PLAT_SKU_PV_KEY, dateStr));

        /**6.汇总整站商品页总的分端uv*/
        sumObj.setSkuTotalUv(setUvCommon(StaticParam.PLAT_SKU_UV_KEY, dateStr));

        /**7.汇总各店铺分端pv以及uv*/
        Set<String> unoinShopKeys;//各端出现的shopId的并集(即所有的shopId)
        List<CompanyFlow> companyFlowList;//各店铺的pv,uv汇总结果List
        CompanyFlow companyFlow;//某店铺的pv,uv
        /**7.1.获取各端shopId的并集(uv的shopId并集同时也是pv的shopId并集)*/
        String allPatternShopUv = String.format(StaticParam.SHOP_UV_KEY, StaticParam.END_ALL, dateStr, "*");
        unoinShopKeys = stringRedisTemplate.keys(allPatternShopUv);
        /**7.2.遍历各端shopId的并集,获取某shopId的各端统计数字*/
        Iterator<String> itShop = unoinShopKeys.iterator();
        int beginIndexShop = allPatternShopUv.length() - 1;
        String shopId;
        companyFlowList = new ArrayList<>(unoinShopKeys.size());
        while (itShop.hasNext()) {
            shopId = itShop.next().substring(beginIndexShop);//截取出shopId
            companyFlow = new CompanyFlow();
            companyFlowList.add(companyFlow);//多个shopId的各端pv与uv
            companyFlow.setCompanyId(shopId);//记录shopId
            /**7.3.汇总各店铺分端总的pv*/
            companyFlow.setPv(setPvCommon(StaticParam.SHOP_PV_KEY, dateStr, shopId));
            /**7.4.汇总各店铺分端总的uv*/
            companyFlow.setUv(setUvCommon(StaticParam.SHOP_UV_KEY, dateStr, shopId));
            /**7.5.汇总各店铺商品页分端pv*/
            companyFlow.setSkuTotalPv(setPvCommon(StaticParam.SHOP_SKU_PV_KEY, dateStr, shopId));
            /**7.6.汇总各店铺商品页分端uv*/
            companyFlow.setSkuTotalUv(setUvCommon(StaticParam.SHOP_SKU_UV_KEY, dateStr, shopId));
        }
        sumObj.setCompanyFlows(companyFlowList);

        /**8.形成推送JSON对象,并通过Rabbit MQ推送*/
        sumObj.setTime(date);
        sumObj.setSendTime(LocalDateTime.now());

        MqSendDTO sendDTO = new MqSendDTO();
        sendDTO.setTopic(ProducerTopic.FLOW_CUSTOMER_SYNCHRONIZATION);
        sendDTO.setData(com.alibaba.fastjson2.JSONObject.toJSONString(sumObj));
        mqSendProvider.send(sendDTO);
    }

    /**
     * 设置pv汇总数据的公共方法
     *
     * @param redisKey pv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @return 各端统计结果
     */
    private TerminalStatistics setPvCommon(String redisKey, String dateStr) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端汇总数量{pc:10, h5:20, app:30}
        String countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_PC, dateStr));//统计数字-字符串格式
        jsonObjTmp.setPC(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_H5, dateStr));
        jsonObjTmp.setH5(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_APP, dateStr));
        jsonObjTmp.setAPP(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr));
        jsonObjTmp.setMINIPROGRAM(countStr == null ? 0L : Long.parseLong(countStr));
        return jsonObjTmp;
    }

    /**
     * 设置pv汇总数据的公共方法_重载(3个参数)
     *
     * @param redisKey pv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @param singleId 替换redis key模板中的单个id
     * @return 各端统计结果
     */
    private TerminalStatistics setPvCommon(String redisKey, String dateStr, String singleId) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端汇总数量{pc:10, h5:20, app:30}
        String countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_PC, dateStr, singleId));////统计数字-字符串格式
        jsonObjTmp.setPC(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_H5, dateStr, singleId));
        jsonObjTmp.setH5(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_APP, dateStr, singleId));
        jsonObjTmp.setAPP(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr, singleId));
        jsonObjTmp.setMINIPROGRAM(countStr == null ? 0L : Long.parseLong(countStr));
        return jsonObjTmp;
    }

    /**
     * 设置pv汇总数据的公共方法_重载(4个参数)
     *
     * @param redisKey pv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @param first 替换redis key模板中的第一个id
     * @param first 替换redis key模板中的第二个id
     * @return 各端统计结果
     */
    private TerminalStatistics setPvCommon(String redisKey, String dateStr, String first,String second) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端汇总数量{pc:10, h5:20, app:30}
        String countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_PC, dateStr, first,second));////统计数字-字符串格式
        jsonObjTmp.setPC(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_H5, dateStr, first,second));
        jsonObjTmp.setH5(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_APP, dateStr, first,second));
        jsonObjTmp.setAPP(countStr == null ? 0L : Long.parseLong(countStr));
        countStr = stringRedisTemplate.opsForValue().get(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr, first,second));
        jsonObjTmp.setMINIPROGRAM(countStr == null ? 0L : Long.parseLong(countStr));
        return jsonObjTmp;
    }

    /**
     * 设置uv汇总数据的公共方法
     *
     * @param redisKey uv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @return 各端uv以及所有端汇总的统计结果
     */
    private TerminalStatistics setUvCommon(String redisKey, String dateStr) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端的用户标识集合{pc:[1,2,3], h5:[1,2,3], app:[1,2,3], total:[1,2,3]}
        Set<String> userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_PC, dateStr));//用户id的Set集合
        jsonObjTmp.setPcUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_H5, dateStr));
        jsonObjTmp.setH5UserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_APP, dateStr));
        jsonObjTmp.setAppUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr));
        jsonObjTmp.setMINIPROGRAMUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_ALL, dateStr));
        jsonObjTmp.setTotalUserIds(userIds);//汇总的uv
        return jsonObjTmp;
    }

    /**
     * 设置uv汇总数据的公共方法_重载(3个参数)
     *
     * @param redisKey uv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @param singleId 替换redis key模板中的单个id
     * @return 各端uv以及所有端汇总的统计结果
     */
    private TerminalStatistics setUvCommon(String redisKey, String dateStr, String singleId) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端的用户标识集合{pc:[1,2,3], h5:[1,2,3], app:[1,2,3], total:[1,2,3]}
        Set<String> userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_PC, dateStr, singleId));//用户id的Set集合
        jsonObjTmp.setPcUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_H5, dateStr, singleId));
        jsonObjTmp.setH5UserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_APP, dateStr, singleId));
        jsonObjTmp.setAppUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr, singleId));
        jsonObjTmp.setMINIPROGRAMUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_ALL, dateStr, singleId));
        jsonObjTmp.setTotalUserIds(userIds);//汇总的uv
        return jsonObjTmp;
    }

    /**
     * 设置uv汇总数据的公共方法_重载(4个参数)
     *
     * @param redisKey uv的redis key模板
     * @param dateStr  替换redis key模板中的日期
     * @param first 替换redis key模板中的第一个id
     * @param second 替换redis key模板中的第二个id
     * @return 各端uv以及所有端汇总的统计结果
     */
    private TerminalStatistics setUvCommon(String redisKey, String dateStr, String first,String second) {
        TerminalStatistics jsonObjTmp = TerminalStatistics.builder().build();//具体的分端的用户标识集合{pc:[1,2,3], h5:[1,2,3], app:[1,2,3], total:[1,2,3]}
        Set<String> userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_PC, dateStr, first,second));//用户id的Set集合
        jsonObjTmp.setPcUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_H5, dateStr, first,second));
        jsonObjTmp.setH5UserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_APP, dateStr, first,second));
        jsonObjTmp.setAppUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_MINIPROGRAM, dateStr, first,second));
        jsonObjTmp.setMINIPROGRAMUserIds(userIds);
        userIds = stringRedisTemplate.opsForSet().members(String.format(redisKey, StaticParam.END_ALL, dateStr, first,second));
        jsonObjTmp.setTotalUserIds(userIds);//汇总的uv
        return jsonObjTmp;
    }

    /**
     * 营销活动的流量发送任务
     * @param date
     */
    public void sendMarketing(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<String> keys  = getKeys(stringRedisTemplate,String.format("mskupv:%s:mid:*",dateStr));
        if(CollectionUtils.isNotEmpty(keys)) {
            for(List<String> list : Lists.partition(keys, StaticParam.PAGE_SIZE)){ //将key进行拆分，100个发一次mq
                List<MarketingSkuSource> sendList = new ArrayList<>(list.size());
                for(String key : list){
                    MarketingSkuSource marketingSkuSource = new MarketingSkuSource();
                    String[] arr = key.split(":"); //将key进行拆分
                    marketingSkuSource.setStatDate(LocalDate.parse(arr[1],DateTimeFormatter.BASIC_ISO_DATE));
                    marketingSkuSource.setMarketingId(arr[3]);
                    marketingSkuSource.setMarketingType(Integer.parseInt(StringUtils.isNotEmpty(arr[5])?arr[5]:"0"));
                    marketingSkuSource.setSkuId(arr[7]);
                    marketingSkuSource.setSendDate(LocalDateTime.now());
                    if(arr.length >= 10){
                        marketingSkuSource.setCompanyId(arr[9]);
                    }
                    String pvStr = stringRedisTemplate.opsForValue().get(key);
                    marketingSkuSource.setPv(Long.parseLong(StringUtils.isNotEmpty(pvStr)?pvStr : "0"));
                    String uvKey = key.replaceFirst("pv","uv");    //替换成uv的key进行查询
                    Set customerIds = stringRedisTemplate.opsForSet().members(uvKey);
                    marketingSkuSource.setCustomerIds(customerIds);
                    sendList.add(marketingSkuSource);
                }
                if(CollectionUtils.isNotEmpty(sendList)){
                    MqSendDTO sendDTO = new MqSendDTO();
                    sendDTO.setTopic(ProducerTopic.FLOW_MARKETING_SKU);
                    sendDTO.setData(JSONObject.toJSONString(sendList));
                    mqSendProvider.send(sendDTO);
                }
            }
        }
    }

    /**
     * 批量获取key
     * @param stringRedisTemplate
     * @param match
     * @return
     */
    @SneakyThrows
    private List<String> getKeys(StringRedisTemplate stringRedisTemplate, String match){
        List<String> keys = new ArrayList<>();
        Cursor<String> cursor = scan(stringRedisTemplate,match,StaticParam.PAGE_SIZE);
        while (cursor.hasNext()){
            //找到一次就添加一次
            keys.add(cursor.next());
        }
        cursor.close();
        return keys;
    }

    /**
     * scan扫描key
     * @param stringRedisTemplate
     * @param match
     * @param count
     * @return
     */
    private static Cursor<String> scan(StringRedisTemplate stringRedisTemplate, String match, int count){
        ScanOptions scanOptions = ScanOptions.scanOptions().match(match).count(count).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
        return (Cursor) stringRedisTemplate.executeWithStickyConnection((RedisCallback) redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(scanOptions), redisSerializer::deserialize));
    }

    /**
     * 汇总各端的skuId(取skuId并集)_暂无用了
     * @author bail 2017-09-22
     * @param unoinKeys 并集
     * @param matchKeys 各端的key
     * @param pattern 截取出skuId的前缀
     */
/*    private static void addUnionSkus(Set<String> unoinKeys, Set<String>matchKeys, String pattern){
        if(matchKeys!=null){
            Iterator<String> it = matchKeys.iterator();
            int beginIndex = pattern.length()-1;
            while(it.hasNext()) {
                unoinKeys.add(it.next().substring(beginIndex));//截取出skuId
            }
        }
    }*/



}

