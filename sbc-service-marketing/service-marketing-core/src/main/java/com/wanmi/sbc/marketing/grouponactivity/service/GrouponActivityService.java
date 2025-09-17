package com.wanmi.sbc.marketing.grouponactivity.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoSaveProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.*;
import com.wanmi.sbc.goods.api.request.info.GoodsCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaCountRequest;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoForAddDTO;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoForEditDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsVO;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityBatchCheckRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityBatchStickyRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityQueryRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.request.grouponrecord.GrouponRecordQueryRequest;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityAddResponse;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityModifyResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.EsGrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityForManagerVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponCenterVO;
import com.wanmi.sbc.marketing.grouponactivity.model.entity.GrouponActivityAdd;
import com.wanmi.sbc.marketing.grouponactivity.model.entity.GrouponActivityEdit;
import com.wanmi.sbc.marketing.grouponactivity.model.root.GrouponActivity;
import com.wanmi.sbc.marketing.grouponactivity.repository.GrouponActivityRepository;
import com.wanmi.sbc.marketing.grouponcate.service.GrouponCateService;
import com.wanmi.sbc.marketing.grouponrecord.model.root.GrouponRecord;
import com.wanmi.sbc.marketing.grouponrecord.service.GrouponRecordService;
import com.wanmi.sbc.marketing.grouponsetting.service.GrouponSettingService;
import com.wanmi.sbc.marketing.newplugin.service.SkuCacheMarketingService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>拼团活动信息表业务逻辑</p>
 *
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Slf4j
@Service("GrouponActivityService")
public class GrouponActivityService {

    @Autowired
    private GrouponActivityRepository grouponActivityRepository;

    @Autowired
    private GrouponGoodsInfoSaveProvider grouponGoodsInfoSaveProvider;

    @Autowired
    private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GrouponSettingService grouponSettingService;

    @Autowired
    private GrouponCateService grouponCateService;

    @Autowired
    private SkuCacheMarketingService skuCacheMarketingService;

    @Autowired
    private GrouponRecordService grouponRecordService;

    @Autowired private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private EntityManager entityManager;

    /**
     * 新增拼团活动信息表
     *
     * @return 拼团活动信息列表
     */
    @Transactional
    public GrouponActivityAddResponse add(GrouponActivityAdd entity) {

        // 1.校验
        // 1.1.传入的单品列表，必须真实有效
        GrouponActivity activity = entity.getGrouponActivity();
        List<String> goodsInfoIds = entity.getGoodsInfos().stream()
                .map(GrouponGoodsInfoForAddDTO::getGoodsInfoId).distinct().collect(Collectors.toList());
        GoodsInfoListByIdsRequest goodsInfoListByIdsRequest = new GoodsInfoListByIdsRequest();
        goodsInfoListByIdsRequest.setGoodsInfoIds(goodsInfoIds);
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByIds(goodsInfoListByIdsRequest).getContext().getGoodsInfos();

        if (goodsInfos.size() != goodsInfoIds.size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 1.2.验证商品上下架、禁售状态
        goodsInfos.forEach(item -> {
            if (!NumberUtils.INTEGER_ONE.equals(item.getAddedFlag())
                    || !CheckStatus.CHECKED.equals(item.getAuditStatus())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080020);
            }
        });

        //卡券和虚拟商品不可与实物商品混选
        long realGoodsCount = goodsInfos.stream().filter(goodsVO -> GoodsType.REAL_GOODS.toValue() == goodsVO.getGoodsType()).count();
        if (realGoodsCount > 0 && realGoodsCount != goodsInfos.size()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080193);
        }

        // 1.3.传入的单品列表，必须没有关联正在进行的活动
        List<String> goodsIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
        //全局互斥关闭时用原冲突验证
        if (!mutexFlag()) {
            List<GrouponGoodsInfoVO> grouponGoodsInfoVOList = grouponGoodsInfoQueryProvider.listActivitying(GrouponGoodsByGoodsInfoIdAndTimeRequest.builder()
                            .goodsInfoIds(goodsInfoIds).startTime(activity.getStartTime()).endTime(activity.getEndTime()).build())
                    .getContext().getGrouponGoodsInfoVOList();
            if (CollectionUtils.isNotEmpty(grouponGoodsInfoVOList)) {
                // 拼团判断
                List<String> existActivityIds =
                        grouponGoodsInfoVOList.stream().map(GrouponGoodsInfoVO::getGrouponActivityId).distinct().collect(Collectors.toList());
                int exist;
                if (CollectionUtils.isNotEmpty(existActivityIds)) {
                    //查询拼团活动是否存在未删除状态，有不允许添加
                    exist = grouponActivityRepository.getExistByActivityIds(existActivityIds);
                    if (exist > 0) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080019);
                    }
                }
            }
        }

        // 2.保存拼团活动列表
        List<GrouponActivity> newActivitys = new ArrayList<>();
        LocalDateTime nowTime = LocalDateTime.now();
        activity.setCreateTime(nowTime);
        activity.setUpdateTime(nowTime);
        if (DefaultFlag.YES.equals(grouponSettingService.getGoodsAuditFlag())) {
            activity.setAuditStatus(AuditStatus.WAIT_CHECK);
            log.info("创建拼团1111---------进来了:"+ JSON.toJSONString(activity));
        } else {
            activity.setAuditStatus(AuditStatus.CHECKED);
            log.info("创建拼团2222---------进来了:"+ JSON.toJSONString(activity));
        }
        List<GoodsVO> goodsList = goodsQueryProvider.listByIds((GoodsListByIdsRequest.builder().goodsIds(goodsIds).build())).getContext().getGoodsVOList();
        if (CollectionUtils.isEmpty(goodsList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080020);
        }

        // 有几个spu就插入几个活动
        goodsIds.forEach(goodsId -> {
            GrouponActivity newActivity = KsBeanUtil.convert(activity, GrouponActivity.class);
            log.info("创建拼团3333---------进来了:"+ JSON.toJSONString(newActivity));
            // 这边取第一个sku的storeId和商品名称
            GoodsVO goods = goodsList.stream().filter(i -> i.getGoodsId().equals(goodsId)).findFirst().get();
            newActivity.setGoodsId(goodsId);
            newActivity.setGoodsName(goods.getGoodsName());
            newActivity.setGoodsNo(goods.getGoodsNo());
            newActivity.setStoreId(goods.getStoreId().toString());
            newActivity.setGoodsType(goods.getGoodsType());
            newActivitys.add(newActivity);
        });
        List<GrouponActivity> activityResults = grouponActivityRepository.saveAll(newActivitys);

        List<EsGrouponActivityVO> newList = KsBeanUtil.convert(activityResults, EsGrouponActivityVO.class);

        // 3.保存拼团活动商品列表添加拼团活动
        List<GrouponGoodsInfoForAddDTO> newGoodsInfos = goodsInfos.stream().map(goodsInfo -> {
            GrouponGoodsInfoForAddDTO newGoodsInfo = entity.getGoodsInfos().stream().filter(
                    item -> goodsInfo.getGoodsInfoId().equals(item.getGoodsInfoId())).findFirst().get();
            GrouponActivity activityResult = activityResults.stream().filter(
                    item -> goodsInfo.getGoodsId().equals(item.getGoodsId())).findFirst().get();
            newGoodsInfo.setGoodsId(goodsInfo.getGoodsId());
            newGoodsInfo.setStoreId(goodsInfo.getStoreId().toString());
            newGoodsInfo.setGrouponCateId(activityResult.getGrouponCateId());
            newGoodsInfo.setGrouponActivityId(activityResult.getGrouponActivityId());
            newGoodsInfo.setAuditStatus(
                    com.wanmi.sbc.goods.bean.enums.AuditStatus.fromValue(activity.getAuditStatus().toValue()));
            newGoodsInfo.setStartTime(activityResult.getStartTime());
            newGoodsInfo.setEndTime(activityResult.getEndTime());
            if (Objects.nonNull(activity.getPreTime() )&&activityResult.getPreTime()>0){
                newGoodsInfo.setPreStartTime(activityResult.getStartTime().minus(activityResult.getPreTime(), ChronoUnit.HOURS));
            }
            return newGoodsInfo;
        }).collect(Collectors.toList());
        grouponGoodsInfoSaveProvider.batchAdd(new GrouponGoodsInfoBatchAddRequest(newGoodsInfos));

        // 5.返回结果，供记录日志用
        List<String> stringList = activityResults.stream().map(
                result -> result.getGoodsName() + result.getGrouponNum() + "人团").collect(Collectors.toList());

        return new GrouponActivityAddResponse(stringList, newList);
    }

    /**
     * 修改拼团活动信息表
     *
     * @return 拼团活动信息
     */
    @Transactional
    public GrouponActivityModifyResponse edit(GrouponActivityEdit entity) {
        GrouponActivity activityParam = entity.getGrouponActivity();
        GrouponActivity activity = grouponActivityRepository.findById(activityParam.getGrouponActivityId())
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));

        //全局互斥关闭时用原冲突验证
        if (!mutexFlag()) {
            // 校验修改后的活动时间会不会导致商品冲突
            List<GrouponGoodsInfoVO> grouponGoodsInfoVOList = grouponGoodsInfoQueryProvider
                    .list(GrouponGoodsInfoListRequest.builder().grouponActivityId(activityParam.getGrouponActivityId()).build())
                    .getContext().getGrouponGoodsInfoVOList();
            List<String> goodsInfoIds = grouponGoodsInfoVOList.stream().map(GrouponGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            List<GrouponGoodsInfoVO> clashGrouponGoodsInfoVOList = grouponGoodsInfoQueryProvider.listActivitying(GrouponGoodsByGoodsInfoIdAndTimeRequest.builder()
                            .goodsInfoIds(goodsInfoIds).startTime(activityParam.getStartTime()).endTime(activityParam.getEndTime()).build())
                    .getContext().getGrouponGoodsInfoVOList();
            List<GrouponGoodsInfoVO> clashList = clashGrouponGoodsInfoVOList.stream()
                    .filter(v -> !activityParam.getGrouponActivityId().equals(v.getGrouponActivityId())).collect(Collectors.toList());
            //存在商品冲突的拼团活动
            if (CollectionUtils.isNotEmpty(clashList)) {
                // 拼团判断
                List<String> existActivityIds =
                        clashList.stream().map(GrouponGoodsInfoVO::getGrouponActivityId).distinct().collect(Collectors.toList());
                int exist;
                if (CollectionUtils.isNotEmpty(existActivityIds)) {
                    //查询拼团活动是否存在未删除状态，有不允许添加
                    exist = grouponActivityRepository.getExistByActivityIds(existActivityIds);
                    if (exist > 0) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080018);
                    }
                }
            }
        }

        boolean isStart = LocalDateTime.now().isAfter(activity.getStartTime());
        if (isStart) {
            //拼团活动已开始
            if (entity.getGrouponActivity().getEndTime().isBefore(activity.getEndTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else {
                activity.setEndTime(entity.getGrouponActivity().getEndTime());
                activity.setGrouponCateId(activityParam.getGrouponCateId());
            }
        } else {
            // 1.保存拼团活动信息
            activity.setGrouponNum(activityParam.getGrouponNum());
            activity.setStartTime(activityParam.getStartTime());
            activity.setEndTime(activityParam.getEndTime());
            activity.setGrouponCateId(activityParam.getGrouponCateId());
            activity.setAutoGroupon(activityParam.isAutoGroupon());
            activity.setFreeDelivery(activityParam.isFreeDelivery());
            if (DefaultFlag.YES.equals(grouponSettingService.getGoodsAuditFlag())) {
                activity.setAuditStatus(AuditStatus.WAIT_CHECK);
            } else {
                activity.setAuditStatus(AuditStatus.CHECKED);
            }
        }

        activity.setPreTime(activityParam.getPreTime());
        GrouponActivity grouponActivity = grouponActivityRepository.save(activity);

        EsGrouponActivityVO grouponActivityVO = new EsGrouponActivityVO();
        BeanUtils.copyProperties(grouponActivity, grouponActivityVO);

        // 2.保存拼团活动商品
        List<GrouponGoodsInfoForEditDTO> goodsInfos = entity.getGoodsInfos();
        if (isStart) {
            //进行中活动只可更改限购数量
            List<GrouponGoodsInfoVO> goodsInfoVOList = grouponGoodsInfoQueryProvider
                    .list(GrouponGoodsInfoListRequest.builder()
                            .grouponActivityId(entity.getGrouponActivity().getGrouponActivityId())
                            .build())
                    .getContext()
                    .getGrouponGoodsInfoVOList();
            //查询拼团购买记录
            List<GrouponRecord> grouponRecords = grouponRecordService.list(GrouponRecordQueryRequest.builder().grouponActivityId(activity.getGrouponActivityId()).build());

            for (GrouponGoodsInfoVO grouponGoodsInfoVO : goodsInfoVOList) {
                GrouponGoodsInfoForEditDTO dto = goodsInfos.stream().filter(info -> grouponGoodsInfoVO.getGrouponGoodsId().equals(info.getGrouponGoodsId()))
                        .findFirst()
                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));

                //更新的限购数量不可小于原本限购数量
                if (dto.getLimitSellingNum() < grouponGoodsInfoVO.getLimitSellingNum()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else {
                    grouponGoodsInfoVO.setLimitSellingNum(dto.getLimitSellingNum());
                    if (CollectionUtils.isNotEmpty(grouponRecords)){
                        List<GrouponRecord> grouponRecordList = grouponRecords.stream()
                                .filter(v -> v.getGoodsInfoId().equals(grouponGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(grouponRecordList)){
                            grouponRecordList.forEach(v->{
                                v.setLimitSellingNum(dto.getLimitSellingNum());
                            });
                            grouponRecordService.modifyList(grouponRecordList);
                        }
                    }
                }
            }
            goodsInfos = KsBeanUtil.convert(goodsInfoVOList, GrouponGoodsInfoForEditDTO.class);
        }

        goodsInfos.forEach(item -> {
            item.setGrouponCateId(activity.getGrouponCateId());
            item.setStartTime(activity.getStartTime());
            item.setEndTime(activity.getEndTime());
            if (Objects.nonNull(activity.getPreTime() )&&activity.getPreTime()>0){
                item.setPreStartTime(activity.getStartTime().minus(activity.getPreTime(), ChronoUnit.HOURS));
            }
            item.setAuditStatus(
                    com.wanmi.sbc.goods.bean.enums.AuditStatus.fromValue(activity.getAuditStatus().toValue()));
        });

        grouponGoodsInfoSaveProvider.batchEdit(new GrouponGoodsInfoBatchEditRequest(goodsInfos));

        String result = activity.getGoodsName() + activity.getGrouponNum() + "人团";

        return new GrouponActivityModifyResponse(result, grouponActivityVO);
    }

    /**
     * 根据商品ids，查询正在进行的活动的商品ids(时间段)
     */
    public List<String> listActivityingSpuIds(List<String> goodsIds, LocalDateTime startTime, LocalDateTime endTime) {
        return this.listActivitying(
                goodsIds, startTime, endTime).stream().map(GrouponActivity::getGoodsId).collect(Collectors.toList());
    }

    /**
     * 根据商品ids，查询正在进行的活动的商品ids(当前时间)
     */
    public List<String> listActivityingSpuIds(List<String> goodsIds) {
        return grouponActivityRepository.listActivityingSpuIds(goodsIds);
    }

    /**
     * 单个删除拼团活动信息表
     *
     * @author groupon
     */
    @Transactional
    public void deleteById(String id) {
        GrouponActivity grouponActivity = getById(id);
        grouponActivityRepository.deleteById(id);
        EsGrouponActivityVO grouponActivityVO = KsBeanUtil.convert(grouponActivity, EsGrouponActivityVO.class);
        grouponActivityVO.setDelFlag(DeleteFlag.YES);
        flushCache(Collections.singletonList(grouponActivityVO));
    }

    /**
     * 批量删除拼团活动信息表
     *
     * @author groupon
     */
    @Transactional
    public void deleteByIdList(List<String> ids) {
        grouponActivityRepository.deleteAll(ids.stream().map(id -> {
            GrouponActivity entity = new GrouponActivity();
            entity.setGrouponActivityId(id);
            return entity;
        }).collect(Collectors.toList()));
    }

    /**
     * 单个查询拼团活动信息表
     *
     * @author groupon
     */
    public GrouponActivity getById(String id) {
        GrouponActivity grouponActivity = grouponActivityRepository.findById(id).orElse(null);
        if (Objects.isNull(grouponActivity) || Objects.isNull(grouponActivity.getDelFlag()) ||
                DeleteFlag.YES.equals(grouponActivity.getDelFlag())) {
            throw new SbcRuntimeException( CommonErrorCodeEnum.K999999, new Object[]{"拼团活动不存在"},this.getDeleteIndex(id));
        }
        return grouponActivity;
    }

    /**
     * 根据活动id，查询活动是否包邮
     */
    public boolean getFreeDeliveryById(String id) {
        return getById(id).isFreeDelivery();
    }

    /**
     * 分页查询拼团活动信息表
     *
     * @author groupon
     */
    public Page<GrouponActivity> page(GrouponActivityQueryRequest queryReq) {
        return grouponActivityRepository.findAll(
                GrouponActivityWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询拼团活动信息表
     *
     * @author groupon
     */
    public List<GrouponActivity> list(GrouponActivityQueryRequest queryReq) {
        return grouponActivityRepository.findAll(GrouponActivityWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author groupon
     */
    public GrouponActivityVO wrapperVo(GrouponActivity grouponActivity) {
        if (grouponActivity != null) {
            GrouponActivityVO grouponActivityVO = new GrouponActivityVO();
            KsBeanUtil.copyPropertiesThird(grouponActivity, grouponActivityVO);
            return grouponActivityVO;
        }
        return null;
    }

    /**
     * 将实体包装成VO
     *
     * @author groupon
     */
    public GrouponActivityForManagerVO wrapperMangerVo(GrouponActivity grouponActivity) {
        if (grouponActivity != null) {
            GrouponActivityForManagerVO grouponActivityVO = new GrouponActivityForManagerVO();
            KsBeanUtil.copyPropertiesThird(grouponActivity, grouponActivityVO);
            return grouponActivityVO;
        }
        return null;
    }

    /**
     * 批量审核拼团活动
     *
     * @param request
     * @return
     */
    @Transactional
    public GrouponActivityAddResponse batchCheckMarketing(@RequestBody @Valid GrouponActivityBatchCheckRequest request) {
        grouponActivityRepository.batchCheckMarketing(request.getGrouponActivityIdList());
        List<GrouponActivity> activityResults = grouponActivityRepository.findAllByGrouponActivityIdIn(request.getGrouponActivityIdList());

        List<EsGrouponActivityVO> newList = KsBeanUtil.convert(activityResults, EsGrouponActivityVO.class);
        return GrouponActivityAddResponse.builder().esGrouponActivityVOList(newList).build();
    }

    /**
     * 驳回或禁止拼团活动
     *
     * @param grouponActivityId
     * @param auditReason
     */
    @Transactional(rollbackFor = Exception.class)
    public void refuseCheckMarketing(String grouponActivityId, AuditStatus auditStatus,
                                     String auditReason) {
        int checkResult = grouponActivityRepository.refuseCheckMarketing(grouponActivityId, auditStatus, auditReason);
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }


    /**
     * 批量修改拼团活动精选状态
     *
     * @param request
     * @return
     */
    @Transactional
    public BaseResponse batchStickyMarketing(@RequestBody @Valid GrouponActivityBatchStickyRequest request) {
        grouponActivityRepository.batchStickyMarketing(request.getGrouponActivityIdList(), request.getSticky());
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 根据商品ids，查询正在进行的活动
     *
     * @param goodsInfoIds
     * @return
     */
    public Map<String, GrouponGoodsInfoVO> listActivityingWithGoodsInfo(List<String> goodsInfoIds) {
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            return new HashMap<>();
        }
        //查询正在进行中的ids
        GrouponGoodsInfoListRequest grouponGoodsInfoListReq = GrouponGoodsInfoListRequest.builder()
                .goodsInfoIdList(goodsInfoIds).started(Boolean.TRUE).build();
        List<GrouponGoodsInfoVO> grouponGoodsInfoVOList = grouponGoodsInfoQueryProvider.list(grouponGoodsInfoListReq)
                .getContext().getGrouponGoodsInfoVOList();
        if (CollectionUtils.isEmpty(grouponGoodsInfoVOList)) {
            return new HashMap<>();
        }
        //参与拼团活动sku
        Map<String, GrouponGoodsInfoVO> goodsInfoMap = grouponGoodsInfoVOList.stream().collect(Collectors.toMap(GrouponGoodsInfoVO::getGoodsInfoId, Function.identity()));
        return goodsInfoMap;
    }

    /**
     * 将拼团商品vo包装成拼团中心需要的数据VO
     *
     * @author groupon
     */
    public GrouponCenterVO wrapperGrouponCenterVo(GrouponGoodsVO grouponGoodsVO) {
        if (grouponGoodsVO != null) {
            GrouponCenterVO grouponCenterVO = new GrouponCenterVO();
            KsBeanUtil.copyProperties(grouponGoodsVO, grouponCenterVO);
            return grouponCenterVO;
        }
        return null;
    }

    /**
     * 根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数）
     *
     * @param grouponActivityId
     * @param grouponNum
     * @param grouponOrderStatus
     * @return
     */
    @Transactional
    public int updateStatisticsNumByGrouponActivityId(String grouponActivityId, Integer grouponNum, GrouponOrderStatus grouponOrderStatus) {
        if (GrouponOrderStatus.WAIT == grouponOrderStatus) {
            return grouponActivityRepository.updateWaitGrouponNumByGrouponActivityId(grouponActivityId, grouponNum, LocalDateTime.now());
        } else if (GrouponOrderStatus.COMPLETE == grouponOrderStatus) {
            return grouponActivityRepository.updateAlreadyGrouponNumByGrouponActivityId(grouponActivityId, grouponNum, LocalDateTime.now());
        } else if (GrouponOrderStatus.FAIL == grouponOrderStatus) {
            return grouponActivityRepository.updateFailGrouponNumByGrouponActivityId(grouponActivityId, grouponNum, LocalDateTime.now());
        }
        return NumberUtils.INTEGER_ZERO;
    }


    public int querySupplierNum(AuditStatus status) {
        return grouponActivityRepository.querySupplierNum(status);
    }

    /**
     * 更新待成团人数
     *
     * @param grouponActivityId 活动id
     * @param num               增加数（若要减数，传负值）
     */
    @Transactional
    public void updateWaitGrouponNumByGrouponActivityId(String grouponActivityId, Integer num) {
        grouponActivityRepository.updateWaitGrouponNumByGrouponActivityId(grouponActivityId, num, LocalDateTime.now());
    }

    public void flushCache(List<EsGrouponActivityVO> list) {
        List<GoodsInfoMarketingCacheDTO> saveList = new ArrayList<>();
        List<GoodsInfoMarketingCacheDTO> delList = new ArrayList<>();
        List<String> activityIdList =
                list.stream()
                        .filter(t -> t.getAuditStatus().equals(AuditStatus.CHECKED))
                        .map(EsGrouponActivityVO::getGrouponActivityId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(activityIdList)) {
            return;
        }
        List<GrouponGoodsInfoVO> grouponGoodsInfoVOList =
                this.grouponGoodsInfoQueryProvider
                        .list(
                                GrouponGoodsInfoListRequest.builder()
                                        .grouponActivityIdList(activityIdList)
                                        .build())
                        .getContext()
                        .getGrouponGoodsInfoVOList();
        if (CollectionUtils.isEmpty(grouponGoodsInfoVOList)) {
            return;
        }
        Map<String, List<GrouponGoodsInfoVO>> activitySkuMap =
                grouponGoodsInfoVOList.stream()
                        .collect(
                                Collectors.groupingBy(
                                        GrouponGoodsInfoVO::getGrouponActivityId));
        for (EsGrouponActivityVO esGrouponActivityVO : list) {
            // 新增、编辑或者审核
            List<GrouponGoodsInfoVO> grouponGoodsInfoVOS = activitySkuMap.get(esGrouponActivityVO.getGrouponActivityId());
            if (CollectionUtils.isNotEmpty(grouponGoodsInfoVOS)) {
                for (GrouponGoodsInfoVO grouponGoodsInfoVO : grouponGoodsInfoVOS) {
                    GoodsInfoMarketingCacheDTO goodsInfoMarketingCacheDTO =
                            new GoodsInfoMarketingCacheDTO();
                    goodsInfoMarketingCacheDTO.setId(esGrouponActivityVO.getGrouponActivityId());
                    goodsInfoMarketingCacheDTO.setBeginTime(esGrouponActivityVO.getStartTime());
                    goodsInfoMarketingCacheDTO.setEndTime(esGrouponActivityVO.getEndTime());
                    goodsInfoMarketingCacheDTO.setMarketingPluginType(MarketingPluginType.GROUPON);
                    goodsInfoMarketingCacheDTO.setSkuId(grouponGoodsInfoVO.getGoodsInfoId());
                    goodsInfoMarketingCacheDTO.setPrice(grouponGoodsInfoVO.getGrouponPrice());
                    goodsInfoMarketingCacheDTO.setPreStartTime(grouponGoodsInfoVO.getPreStartTime());
                    goodsInfoMarketingCacheDTO.setGrouponNum(esGrouponActivityVO.getGrouponNum());
                    // 保存
                    if (esGrouponActivityVO.getAuditStatus().equals(AuditStatus.CHECKED)
                            && esGrouponActivityVO.getDelFlag().equals(DeleteFlag.NO)) {
                        saveList.add(goodsInfoMarketingCacheDTO);
                    }
                    // 删除
                    if (esGrouponActivityVO.getAuditStatus().equals(AuditStatus.CHECKED)
                            && esGrouponActivityVO.getDelFlag().equals(DeleteFlag.YES)) {
                        delList.add(goodsInfoMarketingCacheDTO);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(saveList)) {
            this.skuCacheMarketingService.setSkuCacheMarketing(saveList);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            this.skuCacheMarketingService.delSkuCacheMarketing(delList);
        }
    }

    /**
     * 根据商品ids，查询正在进行的活动(时间段)
     *
     * @param goodsIds  商品ids
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @MasterRouteOnly
    public List<GrouponActivity> listActivitying(List<String> goodsIds, LocalDateTime startTime, LocalDateTime endTime) {
        return grouponActivityRepository.listActivitying(goodsIds, startTime, endTime);
    }

    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param id 编号
     * @return "es_groupon_activity:{id}"
     */
    private Object getDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.GROUPON_ACTIVITY, id);
    }

    /**
     * @param grouponActivityId 拼团活动id
     * @return
     * @description 关闭活动
     * @author xuyunpeng
     * @date 2021/6/24 10:15 上午
     */
    @Transactional
    public void close(String grouponActivityId, Long storeId) {
        GrouponActivity grouponActivity = getById(grouponActivityId);

        if (!storeId.toString().equals(grouponActivity.getStoreId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,new Object[]{"拼团活动不存在"},this.getDeleteIndex(grouponActivityId));
        }
        if (grouponActivity.getAuditStatus() != AuditStatus.CHECKED
                || grouponActivity.getStartTime().isAfter(LocalDateTime.now())
                || grouponActivity.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080103);
        }
        grouponActivityRepository.closeActivity(grouponActivityId);
        grouponGoodsInfoSaveProvider.close(GrouponGoodsInfoCloseRequest.builder().grouponActivityId(grouponActivityId).build());

        EsGrouponActivityVO grouponActivityVO = KsBeanUtil.convert(grouponActivity, EsGrouponActivityVO.class);
        grouponActivityVO.setDelFlag(DeleteFlag.YES);
        flushCache(Collections.singletonList(grouponActivityVO));
    }

    /**
     * 同步到缓存
     */
    public void sycCache() {
        List<GrouponActivity> grouponActivities = this.grouponActivityRepository.findByInProcess();
        if (CollectionUtils.isNotEmpty(grouponActivities)) {

            flushCache(KsBeanUtil.convertList(grouponActivities, EsGrouponActivityVO.class));
        }
    }

    /**
     * 拼团互斥验证
     * @param request 入参
     * @return 验证结果
     */
    public void validate(GrouponGoodsValidateRequest request) {
        GrouponActivityQueryRequest queryRequest = new GrouponActivityQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setStoreId(String.valueOf(request.getStoreId()));
        queryRequest.setNotId(request.getNotId());
        //活动结束时间 >= 交叉开始时间
        queryRequest.setEndTimeBegin(request.getCrossBeginTime());
        //活动开始时间 <= 交叉结束时间
        queryRequest.setStartTimeEnd(request.getCrossEndTime());
        queryRequest.setPageSize(100);
        queryRequest.putSort("grouponActivityId", SortType.ASC.toValue());
        Boolean res = Boolean.FALSE;
        for (int pageNo = 0; ; pageNo++) {
            queryRequest.setPageNum(pageNo);
            Page<GrouponActivity> activityPage = this.pageCols(queryRequest, Arrays.asList("grouponActivityId", "goodsId"));
            if (activityPage.getTotalElements() == 0) {
                break;
            }
            if (CollectionUtils.isNotEmpty(activityPage.getContent())) {
                //所有商品
                if(Boolean.TRUE.equals(request.getAllFlag())){
                    res = Boolean.TRUE;
                    break;
                }
                List<String> spuIds = activityPage.stream().map(GrouponActivity::getGoodsId).collect(Collectors.toList());
                //验证商品相关品牌是否存在
                if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
                    if (this.checkGoodsAndBrand(spuIds, request.getBrandIds())) {
                        res = Boolean.TRUE;
                        break;
                    }
                } else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
                    //验证商品相关店铺分类是否存在
                    if (this.checkGoodsAndStoreCate(spuIds, request.getStoreCateIds())) {
                        res = Boolean.TRUE;
                        break;
                    }
                } else if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
                    //验证自定义货品范围
                    GrouponCountByActivityIdsAndSkuIdsRequest goodsRequest = new GrouponCountByActivityIdsAndSkuIdsRequest();
                    goodsRequest.setGrouponActivityIdList(activityPage.stream().map(GrouponActivity::getGrouponActivityId).collect(Collectors.toList()));
                    goodsRequest.setSkuIds(request.getSkuIds());
                    Long count = grouponGoodsInfoQueryProvider.countByActivityIdsAndSkuIds(goodsRequest).getContext().getCount();
                    if (count > 0) {
                        res = Boolean.TRUE;
                        break;
                    }
                }
                // 最后一页，退出循环
                if (pageNo >= activityPage.getTotalPages() - 1) {
                    break;
                }
            }
        }
        if(res){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"拼团"});
        }
    }

    /**
     * 验证商品、品牌的重合
     * @param spuIds 商品skuId
     * @param brandIds 品牌Id
     * @return 重合结果
     */
    public Boolean checkGoodsAndBrand(List<String> spuIds, List<Long> brandIds) {
        GoodsCountByConditionRequest count = new GoodsCountByConditionRequest();
        count.setGoodsIds(spuIds);
        count.setBrandIds(brandIds);
        count.setDelFlag(DeleteFlag.NO.toValue());
        return goodsQueryProvider.countByCondition(count).getContext().getCount() > 0;
    }

    /**
     * 验证商品、店铺分类的重合
     * @param spuIds 商品skuId
     * @param cateIds 店铺分类Id
     * @return 重合结果
     */
    public Boolean checkGoodsAndStoreCate(List<String> spuIds, List<Long> cateIds) {
        StoreCateGoodsRelaCountRequest count = new StoreCateGoodsRelaCountRequest();
        count.setGoodsIds(spuIds);
        count.setStoreCateIds(cateIds);
        return storeCateGoodsRelaQueryProvider.countByParams(count).getContext().getCount() > 0;
    }

    /**
     * 自定义字段的列表查询
     * @param request 参数
     * @param cols 列名
     * @return 列表
     */
    public Page<GrouponActivity> pageCols(GrouponActivityQueryRequest request, List<String> cols) {
        CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
        Specification<GrouponActivity> spec = GrouponActivityWhereCriteriaBuilder.build(request);
        CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
        Root<GrouponActivity> countRt = countCq.from(GrouponActivity.class);
        countCq.select(countCb.count(countRt));
        Predicate countPredicate = spec.toPredicate(countRt, countCq, countCb);
        if (countPredicate != null) {
            countCq.where(countPredicate);
        }
        long sum = entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull)
                .mapToLong(s -> s).sum();
        if (sum == 0) {
            return PageableExecutionUtils.getPage(Collections.emptyList(), request.getPageable(), () -> sum);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<GrouponActivity> rt = cq.from(GrouponActivity.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        TypedQuery<Tuple> query = entityManager.createQuery(cq);
        query.setFirstResult((int) request.getPageRequest().getOffset());
        query.setMaxResults(request.getPageRequest().getPageSize());
        return PageableExecutionUtils.getPage(this.converter(query.getResultList(), cols), request.getPageable(), () -> sum);
    }

    /**
     * 查询对象转换
     * @param result
     * @return
     */
    private List<GrouponActivity> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            GrouponActivity activity = new GrouponActivity();
            activity.setGrouponActivityId(JpaUtil.toString(item,"grouponActivityId", cols));
            activity.setGoodsId(JpaUtil.toString(item,"goodsId", cols));
            return activity;
        }).collect(Collectors.toList());
    }

    /**
     * 是否全局互斥
     * @return true:是 false:否
     */
    private Boolean mutexFlag() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        //营销互斥不验证标识
        return Objects.nonNull(configVO) && NumberUtils.INTEGER_ONE.equals(configVO.getStatus());
    }
}
