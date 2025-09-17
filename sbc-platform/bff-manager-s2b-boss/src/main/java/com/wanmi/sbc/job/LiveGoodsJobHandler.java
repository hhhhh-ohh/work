package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.livegoods.LiveGoodsProvider;
import com.wanmi.sbc.goods.api.request.livegoods.LiveGoodsUpdateRequest;
import com.wanmi.sbc.goods.api.response.livegoods.LiveGoodsListByWeChatResponse;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;
import com.wanmi.sbc.util.MiniProgramUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 商品列表定时任务
 */
@Component
@Slf4j
public class LiveGoodsJobHandler {

    private String liveGoodsListUrl = "https://api.weixin.qq.com/wxaapi/broadcast/goods/getapproved?access_token=";
    @Autowired
    private MiniProgramUtil miniProgramUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LiveGoodsProvider liveGoodsProvider;


    @XxlJob(value = "LiveGoodsJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("LiveGoodsGoodsTask定时任务执行开始： " + LocalDateTime.now());
        for (int i = 0; i < Constants.FOUR; i++) {
            //获取accessToken
            String accessToken = miniProgramUtil.getToken();
            Integer offset = 0;
            Integer limit = 100;
            //请求参数
            Map<String, Integer> map = new HashMap<>();
            map.put("offset", offset);
            map.put("limit", limit);
            map.put("status", i);
            //调用微信接口查询商品列表
            LiveGoodsListByWeChatResponse resp = getLiveGoodsList(map, accessToken);
            List<Long> goodsIdList = resp.getGoods().stream()
                    .map(LiveGoodsVO::getGoodsId)
                    .collect(Collectors.toList());
            //批量修改数据库状态
            liveGoodsProvider.update(LiveGoodsUpdateRequest.builder()
                    .goodsIdList(goodsIdList)
                    .auditStatus(i)
                    .build());
            //获取总条数
            Integer total = resp.getTotal();

            //循环获取数据
            if (limit < total) {
                while (offset < total) {
                    offset = offset + limit + 1;
                    map.put("offset", offset);
                    liveGoodsProvider.update(LiveGoodsUpdateRequest.builder()
                            .goodsIdList(getLiveGoodsList(map, accessToken).getGoods().stream()
                                    .map(LiveGoodsVO::getGoodsId)
                                    .collect(Collectors.toList()))
                            .auditStatus(i)
                            .build());
                }
            }
        }
        XxlJobHelper.log("LiveGoodsGoodsTask定时任务执行结束： " + LocalDateTime.now());
    }

    /**
     * 请求微信直播商品列表接口
     */
        private  LiveGoodsListByWeChatResponse  getLiveGoodsList(Map<String,Integer> map, String accessToken){
            //拼接Url
            String url = liveGoodsListUrl+accessToken+"&offset={offset}&limit={limit}&status={status}";
            //调用微信接口查询商品列表
            String result = restTemplate.getForObject(url,String.class, map);
            LiveGoodsListByWeChatResponse resp = JSONObject.parseObject(result, LiveGoodsListByWeChatResponse.class);
            if (resp.getErrcode() != 0) {
                log.error("查询直播商品列表异常，返回信息：{}", JSONObject.toJSONString(resp));
                if (resp.getErrcode() == 42001) {
                    return getLiveGoodsList(map, miniProgramUtil.getTokenCleanRedis());
                } else {
                    throw new RuntimeException("查询直播商品列表异常");
                }
            }
          return resp;
        }


}
