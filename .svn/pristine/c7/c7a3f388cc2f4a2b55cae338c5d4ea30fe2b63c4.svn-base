package com.wanmi.sbc.customer.liveroom.service;


import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.request.liveroom.LiveRoomQueryRequest;
import com.wanmi.sbc.customer.api.request.liveroom.LiveRoomUpdateRequest;
import com.wanmi.sbc.customer.api.response.liveroom.LiveRoomCreateResponse;
import com.wanmi.sbc.customer.api.response.liveroom.LiveRoomPageAllResponse;
import com.wanmi.sbc.customer.bean.enums.LiveRoomStatus;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.LiveRoomVO;
import com.wanmi.sbc.customer.livecompany.model.root.LiveCompany;
import com.wanmi.sbc.customer.livecompany.service.LiveCompanyService;
import com.wanmi.sbc.customer.liveroom.model.root.LiveRoom;
import com.wanmi.sbc.customer.liveroom.repository.LiveRoomRepository;
import com.wanmi.sbc.customer.store.model.entity.StoreName;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.customer.store.service.StoreService;
import com.wanmi.sbc.empower.bean.enums.WxLiveErrorCodeEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * <p>直播间业务逻辑</p>
 *
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Service("LiveRoomService")
public class LiveRoomService {
    @Autowired
    private LiveRoomRepository liveRoomRepository;

    private static final Logger log = LoggerFactory.getLogger(LiveRoomService.class);

    private String createLiveRoomListUrl = "https://api.weixin.qq.com/wxaapi/broadcast/room/create?access_token=";

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private StoreService storeService;

    @Autowired
    private LiveCompanyService liveCompanyService;


    /**
     * 创建直播间
     *
     * @author zwb
     */
    @Transactional
    public LiveRoom add(LiveRoom entity, String accessToken) {
        //拼接Url
        String url = createLiveRoomListUrl + accessToken;
        Map<String, Object> map = new HashMap<>();
        map.put("name", entity.getName());
        //时间转换成秒
        ZoneOffset zoneOffset8 = ZoneOffset.of("+8");
        map.put("startTime", entity.getStartTime().toEpochSecond(zoneOffset8));
        map.put("endTime", entity.getEndTime().toEpochSecond(zoneOffset8));
        map.put("anchorName", entity.getAnchorName());
        map.put("anchorWechat", entity.getAnchorWechat());
        map.put("screenType", entity.getScreenType());
        map.put("type", entity.getType());
        map.put("closeLike", entity.getCloseLike());
        map.put("closeGoods", entity.getCloseGoods());
        map.put("closeComment", entity.getCloseComment());
        //调用微信接口上传文件，查询mediaId
        String coverImg = null;
        String shareImg = null;
        try {
            //将图片保存到本地，再根据图片的路径去获取media_id
            String coverImgUrl = MediaIdUtil.uploadURL(entity.getCoverImg());
            String shareImgUrl = MediaIdUtil.uploadURL(entity.getShareImg());
            coverImg = MediaIdUtil.uploadFile(coverImgUrl, accessToken, "image");
            shareImg = MediaIdUtil.uploadFile(shareImgUrl, accessToken, "image");
            //删除本地图片
            File file = new File(coverImgUrl);
            if (file.exists() && !file.delete()) {
                log.error("LiveRoomService add delete coverImg error ! local:{}", file.getAbsolutePath());
            }
            File shareImgFile = new File(shareImgUrl);
            if (shareImgFile.exists() && !shareImgFile.delete()) {
                log.error("LiveRoomService add delete shareImg error ! local:{}", file.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        map.put("shareImg", shareImg);
        map.put("coverImg", coverImg);
        // 是否关闭回放，0 开启  1 关闭
        map.put("closeReplay", 0);
        // feedsImg字段官方改为必填的，取shareImg字段值
        map.put("feedsImg", shareImg);

        //请求微信创建直播间接口
        String result = restTemplate.postForObject(url, map, String.class);
        LiveRoomCreateResponse resp = JSON.parseObject(result, LiveRoomCreateResponse.class);
        if (resp.getErrcode() != 0) {
            log.error("微信创建直播间异常，返回信息：{}", resp);
            throw new SbcRuntimeException(Objects.requireNonNull(WxLiveErrorCodeEnum.parseOldCode(resp.getErrcode().toString())));
        }
        entity.setLiveStatus(LiveRoomStatus.THREE);
        entity.setRoomId(resp.getRoomId());
        entity.setRecommend(0);
        liveRoomRepository.save(entity);
        return entity;
    }

    /**
     * 修改直播间
     *
     * @author zwb
     */
    @Transactional
    public LiveRoom modify(LiveRoom entity) {
        liveRoomRepository.save(entity);
        return entity;
    }

    /**
     * 定时任务修改直播间状态
     *
     * @author zwb
     */
    @Transactional
    public void update(LiveRoomUpdateRequest request) {
        Map<LiveRoomStatus, List<LiveRoomUpdateRequest>> liveRoomList = request.getLiveRoomList();
        for (Map.Entry<LiveRoomStatus, List<LiveRoomUpdateRequest>> liveRoomStatus : liveRoomList.entrySet()) {
            List<Long> roomsIdList = liveRoomStatus.getValue().stream()
                    .map(LiveRoomUpdateRequest::getRoomId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(roomsIdList)) {
                liveRoomRepository.updateStatusByRoomIdList(liveRoomStatus.getKey(), roomsIdList);
            }
        }
    }


    /**
     * 单个删除直播间
     *
     * @author zwb
     */
    @Transactional
    public void deleteById(LiveRoom entity) {
        liveRoomRepository.save(entity);
    }

    /**
     * 批量删除直播间
     *
     * @author zwb
     */
    @Transactional
    public void deleteByIdList(List<LiveRoom> infos) {
        liveRoomRepository.saveAll(infos);
    }

    /**
     * 单个查询直播间
     *
     * @author zwb
     */
    public LiveRoom getOne(Long id) {
        return liveRoomRepository.findById(id)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "直播间不存在"));
    }

    /**
     * 分页查询直播间
     *
     * @author zwb
     */
    public Page<LiveRoom> page(LiveRoomQueryRequest queryReq) {
        Specification<LiveRoom> build = LiveRoomWhereCriteriaBuilder.build(queryReq);
        return liveRoomRepository.findAll(
                LiveRoomWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询直播间
     *
     * @author zwb
     */
    public List<LiveRoom> list(LiveRoomQueryRequest queryReq) {
        return liveRoomRepository.findAll(LiveRoomWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author zwb
     */
    public LiveRoomVO wrapperVo(LiveRoom liveRoom) {
        if (liveRoom != null) {
            LiveRoomVO liveRoomVO = KsBeanUtil.convert(liveRoom, LiveRoomVO.class);
            return liveRoomVO;
        }
        return null;
    }

    /**
     * 修改直播间是否推荐
     *
     * @param recommend
     * @param roomId
     */
    @Transactional
    public void recommend(Integer recommend, Long roomId) {
        liveRoomRepository.updateRecommendByRoomId(recommend, roomId);
    }


    /** 分页查询直播间信息
     * @param pageReq
     * @return
     */
    public LiveRoomPageAllResponse queryTermPage(LiveRoomQueryRequest pageReq) {
//        like字段预处理
        if (!Objects.isNull(pageReq.getName())) {
            pageReq.setName(StringUtil.SQL_LIKE_CHAR
                    .concat(XssUtils.replaceLikeWildcard(pageReq.getName())).concat(StringUtil.SQL_LIKE_CHAR));
        }
        if (!Objects.isNull(pageReq.getAnchorName())) {
            pageReq.setAnchorName(StringUtil.SQL_LIKE_CHAR
                    .concat(XssUtils.replaceLikeWildcard(pageReq.getAnchorName())).concat(StringUtil.SQL_LIKE_CHAR));
        }
        if (!Objects.isNull(pageReq.getStoreName())) {
            pageReq.setStoreName(StringUtil.SQL_LIKE_CHAR
                    .concat(XssUtils.replaceLikeWildcard(pageReq.getStoreName())).concat(StringUtil.SQL_LIKE_CHAR));
        }
//        查询
        Page<List<LiveRoom>> byTerm = liveRoomRepository.findByTerm(pageReq, pageReq.getPageable());
        List<LiveRoomVO> list = KsBeanUtil.convertList(byTerm.getContent(), LiveRoomVO.class);
//        转换分页
        PageImpl<LiveRoomVO> page = new PageImpl<>(list, pageReq.getPageable(), byTerm.getTotalElements());
//        获取商户
        List<Long> storeIds = list.stream().map(LiveRoomVO::getStoreId).collect(Collectors.toList());
        List<StoreName> storeNames = storeService.listCompanyTypeByStoreIds(storeIds);
        Map<Long, String> storeMaps = new HashMap<>();
        list.stream().forEach((r)->{
            storeMaps.put(r.getStoreId(),
                    storeNames.stream().filter((t) -> t.getStoreId().equals(r.getStoreId())).findFirst()
                            .get().getStoreName());
        });
        LiveRoomPageAllResponse result = new LiveRoomPageAllResponse();
        result.setStoreName(storeMaps);
        result.setLiveRoomVOPage(new MicroServicePage<LiveRoomVO>(page,pageReq.getPageRequest()));
        return result;
    }

//  todo: 不使用此方法
    public LiveRoomPageAllResponse pageNew(LiveRoomQueryRequest pageReq) {
//        queryTermPage(pageReq);
        List<LiveRoom> liveRoomVOList;
        Map<Long, String> storeName = new HashMap<>();
        LiveRoomPageAllResponse result = new LiveRoomPageAllResponse();
        if (StringUtils.isNotBlank(pageReq.getStoreName())) {
            //查询所有匹配的storeId
            List<Store> storeSimpleInfos = storeService.queryStoreByName(pageReq.getStoreName(),null);
            storeSimpleInfos = storeSimpleInfos.stream()
                    .filter(liveRoomVO -> {
                        if (Objects.isNull(liveRoomVO.getStoreId())) {
                            return false;
                        }
                        //过滤已关店和已过期的店铺
                        Store storeVO = storeService.findOne(liveRoomVO.getStoreId());
                        if (Objects.nonNull(storeVO)
                                && Objects.nonNull(storeVO.getContractEndDate())
                                && Objects.nonNull(storeVO.getStoreState())
                                && storeVO.getContractEndDate().isAfter(LocalDateTime.now())
                                && storeVO.getStoreState() == StoreState.OPENING) {
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
            Map<Long, String> storeIdAndNameMap = storeSimpleInfos.stream()
                    .filter(c -> c.getStoreId() != null)
                    .collect(Collectors.toMap(Store::getStoreId, c -> {
                        String name = c.getStoreName();
                        if (StringUtils.isEmpty(name)) {
                            name = "-";
                        }
                        return name;
                    }, (oldValue, newValue) -> newValue));

            //查询所有结果
            liveRoomVOList = this.list(pageReq);

            List<LiveRoom> collect = new ArrayList<>();
            for (LiveRoom liveRoomVO : liveRoomVOList) {
                if (storeIdAndNameMap.containsKey(liveRoomVO.getStoreId())) {
                    //过滤已禁用的商家
                    LiveCompany liveCompanyVO = liveCompanyService.getOne(liveRoomVO.getStoreId());
                    if (Objects.nonNull(liveCompanyVO) && liveCompanyVO.getLiveBroadcastStatus() == 2) {
                        collect.add(liveRoomVO);
                        //封装所属店铺名称
                        storeName.put(liveRoomVO.getStoreId(), storeIdAndNameMap.get(liveRoomVO.getStoreId()));
                    }

                }
            }


            PageImpl<LiveRoomVO> newPage = new PageImpl(collect, pageReq.getPageable(), collect.size());
            MicroServicePage<LiveRoomVO> microPage = new MicroServicePage<>(newPage, pageReq.getPageable());
            result.setLiveRoomVOPage(microPage);
            result.setStoreName(storeName);
        } else {
//            pageReq.setStoreId(commonUtil.getStoreId());
//            此处做二次过滤，关联查询
            Specification<LiveRoom> build = LiveRoomWhereCriteriaBuilder.build(pageReq)
                    .and((root,query,cb)->{
                        List<Predicate> predicates = new ArrayList<>();
                        DateTimeFormatter sdfmat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        Join<LiveRoom,Store> join = root.join("store", JoinType.LEFT);
                        Join<LiveRoom, LiveCompany> join1 = root.join("liveCompany", JoinType.LEFT);
//                        大于某个时间节点
                        predicates.add(cb.greaterThanOrEqualTo(join.get("contractEndDate"), LocalDateTime.now().format(sdfmat)));
//                        开店
                        predicates.add(cb.equal(join.get("storeState"), StoreState.OPENING));
//                        不可被禁用
                        predicates.add(cb.equal(join.get("liveBroadcastStatus"), 2));
//                        返回
                        Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
                        return p.length == 0 ? null : p.length == 1 ? p[0] : cb .and(p);
                    });
            Page<LiveRoom> add =  liveRoomRepository.findAll(
                    LiveRoomWhereCriteriaBuilder.build(pageReq),
                    pageReq.getPageRequest());
            //获取分页对象数据
            List<LiveRoom> content = add.getContent().stream().filter(liveRoomVO -> Objects.nonNull(liveRoomVO.getStoreId())).collect(Collectors.toList());
            liveRoomVOList = content.stream().map(liveRoomVO -> {
                Store storeVO = storeService.findOne(liveRoomVO.getStoreId());
                    //过滤已禁用的商家
                    //  liveCompanyByIdRequest.setStoreId(liveRoomVO.getStoreId());
                    LiveCompany liveCompanyVO = liveCompanyService.getOne(liveRoomVO.getStoreId());
                    if (StringUtils.isEmpty(storeVO.getStoreName())) {
                        storeName.put(liveRoomVO.getStoreId(), "-");
                    } else {
                        storeName.put(liveRoomVO.getStoreId(), storeVO.getStoreName());
                    }
                    return liveRoomVO;
            }).collect(Collectors.toList());

            // LiveCompanyByIdRequest liveCompanyByIdRequest = new LiveCompanyByIdRequest();
            //查询店铺状态是否已过期/是否禁用
//            liveRoomVOList = content.stream().map(liveRoomVO -> {
//                Store storeVO = storeService.findOne(liveRoomVO.getStoreId());
//                if (storeVO.getContractEndDate().isAfter(LocalDateTime.now()) && storeVO.getStoreState() == StoreState.OPENING) {
//                    //过滤已禁用的商家
//                    //  liveCompanyByIdRequest.setStoreId(liveRoomVO.getStoreId());
//                    LiveCompany liveCompanyVO = liveCompanyService.getOne(liveRoomVO.getStoreId());
//                    if (Objects.nonNull(liveCompanyVO) && liveCompanyVO.getLiveBroadcastStatus() == 2) {
//                        //获取所属店铺名称
//                        if (StringUtils.isEmpty(storeVO.getStoreName())) {
//                            storeName.put(liveRoomVO.getStoreId(), "-");
//                        } else {
//                            storeName.put(liveRoomVO.getStoreId(), storeVO.getStoreName());
//                        }
//                        return liveRoomVO;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//            }).filter(Objects::nonNull).collect(Collectors.toList());
            PageImpl<LiveRoomVO> newPage = new PageImpl(liveRoomVOList, pageReq.getPageable(), add.getTotalElements());
            MicroServicePage<LiveRoomVO> microPage = new MicroServicePage<>(newPage, pageReq.getPageable());
            result.setLiveRoomVOPage(microPage);
            result.setStoreName(storeName);
        }
        return result;
    }
}

