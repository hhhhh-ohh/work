package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.liveroom.LiveRoomQueryProvider;
import com.wanmi.sbc.customer.api.provider.liveroomreplay.LiveRoomReplayProvider;
import com.wanmi.sbc.customer.api.request.liveroom.LiveRoomListRequest;
import com.wanmi.sbc.customer.api.request.liveroomreplay.LiveRoomReplayModifyRequest;
import com.wanmi.sbc.customer.bean.enums.LiveRoomStatus;
import com.wanmi.sbc.customer.bean.vo.LiveRoomReplayByWeChatVO;
import com.wanmi.sbc.customer.bean.vo.LiveRoomVO;
import com.wanmi.sbc.util.MiniProgramUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品列表定时任务
 */
@Component
@Slf4j
public class LiveReplayJobHandler {

    @Autowired
    private MiniProgramUtil miniProgramUtil;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LiveRoomReplayProvider liveRoomReplayProvider;

    @Autowired
    private LiveRoomQueryProvider liveRoomQueryProvider;

    private final Integer limit = 100;


    private String liveRoomListUrl = "https://api.weixin.qq.com/wxa/business/getliveinfo?access_token=";

    @XxlJob(value = "LiveReplayJobHandler")
    public void execute() throws Exception {
        // 获取直播房间ID
        LiveRoomListRequest request = new LiveRoomListRequest();
        request.setDelFlag(DeleteFlag.NO);
        List<Long> LiveRoomIdList = liveRoomQueryProvider.list(request).getContext().getLiveRoomVOList().stream().
                filter(liveRoomVO -> liveRoomVO.getRoomId() != null).filter(liveRoomVO -> liveRoomVO.getLiveStatus() == LiveRoomStatus.FOUR).
                map(LiveRoomVO::getRoomId).collect(Collectors.toList());
        // 查询回放视频
        LiveRoomIdList.stream().distinct().forEach(this::getLiveRoomReplay);
    }

    /**
     * 获取直播房间视频回放
     *
     * @param roomId
     */
    public void getLiveRoomReplay(Long roomId) {
        Integer start = 0;
        String accessToken = miniProgramUtil.getToken();
        //获取数据
        JSONObject jsonObject = getLiveReplayList(start, roomId, accessToken);

        String live_replay = jsonObject.getString("live_replay");
        //转换成集合
        List<LiveRoomReplayByWeChatVO> roomInfoReplayList = JSON.parseArray(live_replay, LiveRoomReplayByWeChatVO.class);
        //存入数据库
        roomInfoReplayList.forEach(c -> {
            LiveRoomReplayModifyRequest request = KsBeanUtil.convert(c, LiveRoomReplayModifyRequest.class);
            //存入数据库
            request.setDelFlag(DeleteFlag.NO);
            request.setRoomId(roomId);
            liveRoomReplayProvider.modify(request);
        });

        //获取总条数
        Integer total = (Integer) jsonObject.get("total");
        //循环获取数据
        if (limit < total) {
            while (start < total) {
                start = start + limit + 1;
                //存入数据库
                accessToken = miniProgramUtil.getToken();
                JSON.parseArray(getLiveReplayList(start,roomId, accessToken).getString("live_replay"), LiveRoomReplayByWeChatVO.class).stream().forEach(c -> {
                    /*Long expireTime = c.getExpireTime();
                    Long createTime = c.getCreateTime();*/
                    LiveRoomReplayModifyRequest request = KsBeanUtil.convert(c, LiveRoomReplayModifyRequest.class);
                   /* //将时间戳转换成date
                    request.setCreateTime(LocalDateTime.ofEpochSecond(createTime, 0, ZoneOffset.ofHours(8)));
                    request.setExpireTime(LocalDateTime.ofEpochSecond(expireTime, 0, ZoneOffset.ofHours(8)));*/
                    //存入数据库
                    request.setRoomId(roomId);
                    request.setDelFlag(DeleteFlag.NO);
                    liveRoomReplayProvider.modify(request);
                });
            }
        }
    }
    public JSONObject getLiveReplayList (Integer start,Long roomId, String accessToken){
        String url = liveRoomListUrl + accessToken;
        Map<String, Object> map = new HashMap<>();
        map.put("action", "get_replay");
        map.put("room_id", roomId);
        map.put("start", start);
        map.put("limit", limit);
        //查询微信接口
        String result = restTemplate.postForObject(url, map, String.class);
        JSONObject jsonObject = JSON.parseObject(result);
        /**
         * 返回的数据格式
         * {"errcode":0,"errmsg":"ok","room_info":[],"total":0,"live_replay":[]}
         */
        if (0 != (Integer) jsonObject.get(Constants.ERRCODE)) {
            log.error("查询直播回放异常，返回信息：{}", JSONObject.toJSONString(jsonObject));
            if (jsonObject.getString(Constants.ERRCODE).equals(Constants.STR_42001)
                    || jsonObject.getString(Constants.ERRCODE).equals(Constants.STR_40001)) {
                return getLiveReplayList(start, roomId, miniProgramUtil.getTokenCleanRedis());
            } else {
                throw new RuntimeException("查询直播回放异常");
            }
        }
        return jsonObject;
    }
}
