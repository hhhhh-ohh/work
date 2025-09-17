package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardImportProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsPageRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditPageRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsGetUsedGoodsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsCheckResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsPageResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditPageResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardImportStandardRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsPageSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * S2B的BOSS商品服务
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name =  "S2B的BOSS商品服务", description =  "BossGoodsController")
public class BossGoodsController {

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private StandardGoodsQueryProvider standardGoodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private StandardImportProvider standardImportProvider;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    /**
     * 审核/驳回商品
     *
     * @param checkRequest 审核参数
     * @return 商品详情
     */
    @MultiSubmit
    @Operation(summary = "审核/驳回商品")
    @RequestMapping(value = "/check", method = RequestMethod.PUT)
    public BaseResponse check(@Valid @RequestBody GoodsCheckRequest checkRequest) {
        CheckStatus auditStatus = checkRequest.getAuditStatus();
        if(Objects.isNull(auditStatus)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(!(Objects.equals(auditStatus,CheckStatus.CHECKED) || Objects.equals(auditStatus,CheckStatus.NOT_PASS))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsBaseService.check(checkRequest);

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 禁售商品
     *
     * @param checkRequest 禁售参数
     * @return 商品详情
     */
    @MultiSubmit
    @Operation(summary = "禁售商品")
    @RequestMapping(value = "/forbid", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse forbid(@Valid @RequestBody GoodsCheckRequest checkRequest) {
        CheckStatus auditStatus = checkRequest.getAuditStatus();
        if(Objects.isNull(auditStatus)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(!Objects.equals(auditStatus,CheckStatus.FORBADE)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        checkRequest.setChecker(commonUtil.getAccountName());
        GoodsCheckResponse checkResponse = goodsProvider.checkGoods(checkRequest).getContext();
        checkRequest.getGoodsIds().forEach(goodsId -> {
            //更新redis商品基本数据
            String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            }
        });

        //删除标品库ES
        if(CollectionUtils.isNotEmpty(checkResponse.getDeleteStandardIds())){
            esStandardProvider.deleteByIds(EsStandardDeleteByIdsRequest.builder().goodsIds(checkResponse.getDeleteStandardIds()).build());
        }
        //刷新es
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().auditStatus(checkRequest.getAuditStatus()).goodsIds(checkRequest.getGoodsIds()).build());
        //操作日志记录
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(checkRequest.getGoodsIds().get(0));
        GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
        operateLogMQUtil.convertAndSend("商品", "禁售商品",
                "禁售商品：SPU编码" + response.getGoodsNo());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询商品
     *
     * @param pageRequest 商品 {@link GoodsPageRequest}
     * @return 商品详情
     */
    @Operation(summary = "查询商品")
    @RequestMapping(value = "/spus", method = RequestMethod.POST)
    public BaseResponse<EsSpuPageResponse> list(@RequestBody EsSpuPageRequest pageRequest) {
        pageRequest.setDelFlag(DeleteFlag.NO.toValue());
        //按创建时间倒序、ID升序
        pageRequest.setShowVendibilityFlag(Boolean.TRUE);//显示可售性
        EsSpuPageRequest request = new EsSpuPageRequest();
        KsBeanUtil.copyPropertiesThird(pageRequest, request);
        BaseResponse<EsSpuPageResponse> pageResponse = esSpuQueryProvider.page(request);
        EsSpuPageResponse goodsPageResponse = pageResponse.getContext();
        List<GoodsPageSimpleVO> goodses = goodsPageResponse.getGoodsPage().getContent();
        if (CollectionUtils.isNotEmpty(goodses)) {
            //列出已导入商品库的商品编号
            StandardGoodsGetUsedGoodsRequest standardGoodsGetUsedGoodsRequest = new StandardGoodsGetUsedGoodsRequest();
            standardGoodsGetUsedGoodsRequest.setGoodsIds(goodses.stream().filter(g-> Objects.nonNull(g) && Objects.nonNull(g.getGoodsId())).map(GoodsPageSimpleVO::getGoodsId).collect(Collectors.toList()));
            if(CollectionUtils.isNotEmpty(standardGoodsGetUsedGoodsRequest.getGoodsIds())) {
                goodsPageResponse.setImportStandard(standardGoodsQueryProvider.getUsedGoods(standardGoodsGetUsedGoodsRequest).getContext().getGoodsIds());
            }
            systemPointsConfigService.clearBuyPointsForEsSpuNew(goodses);
            // 商品列表展示供应商库存
            goodsBaseService.updateGoodsStockForPrivate(goodses);
        }
        return pageResponse;
    }

    /**
     * 查询商品
     *
     * @param pageRequest 商品 {@link GoodsPageRequest}
     * @return 商品详情
     */
    @Operation(summary = "查询商品")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<List<GoodsSimpleVO>> page(@RequestBody GoodsPageRequest pageRequest) {
        pageRequest.setDelFlag(DeleteFlag.NO.toValue());
        pageRequest.setAddedFlag(AddedFlag.YES.toValue());
        pageRequest.setAuditStatus(CheckStatus.CHECKED);
        //按创建时间倒序、ID升序
        pageRequest.putSort("createTime", SortType.DESC.toValue());
        BaseResponse<GoodsPageResponse> pageResponse = goodsQueryProvider.page(pageRequest);
        List<GoodsSimpleVO> list = null;
        GoodsPageResponse goodsPageResponse = pageResponse.getContext();
        List<GoodsVO> goodses = goodsPageResponse.getGoodsPage().getContent();
        if (CollectionUtils.isNotEmpty(goodses)) {
            list = KsBeanUtil.convert(goodses, GoodsSimpleVO.class);
        }
        return BaseResponse.success(ListUtils.emptyIfNull(list));
    }

    /**
     * 加入商品库
     *
     * @param request 导入参数
     * @return 成功结果
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "加入商品库")
    @RequestMapping(value = "/standard", method = RequestMethod.POST)
    public BaseResponse importGoods(@RequestBody StandardImportStandardRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> standardIds = standardImportProvider.importStandard(request).getContext().getStandardIds();
        //初始化商品库ES
        if(CollectionUtils.isNotEmpty(standardIds)){
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(standardIds).build());
        }

        //操作日志记录
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
        GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
        operateLogMQUtil.convertAndSend("商品", "加入商品库",
                "加入商品库：SPU编码" + response.getGoodsNo());

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 商品审核列表
     *
     * @param request 商品
     * @return 商品详情
     */
    @Operation(summary = "商品审核列表")
    @RequestMapping(value = "/audit/page", method = RequestMethod.POST)
    public BaseResponse<GoodsAuditPageResponse> list(@RequestBody GoodsAuditPageRequest request) {
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setStoreId(commonUtil.getStoreId());
        request.setDelFlag(DeleteFlag.NO.toValue());
        //按创建时间倒序、ID升序
        request.putSort("updateTime", SortType.DESC.toValue());
        return goodsAuditQueryProvider.page(request);
    }

}
