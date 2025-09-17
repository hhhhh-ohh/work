package com.wanmi.sbc.contract;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreContractAuditRequest;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsBrandDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoStateModifyRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.contract.ContractProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandAuditListRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateAuditListRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateDelVerifyRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListCateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditSaveRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractSaveRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsType;import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.response.BossGoodsAuditResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 签约信息(品牌，)
 * Created by sunkun on 2017/11/2.
 */
@Tag(name = "StoreContractController", description = "签约信息 API")
@RestController
@Validated
@RequestMapping("/contract")
public class StoreContractController {

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private ContractBrandAuditQueryProvider contractBrandAuditQueryProvider;

    @Autowired
    private ContractProvider contractProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Autowired
    private ContractCateAuditQueryProvider contractCateAuditQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 签约信息新增修改删除(签约分类，签约品牌)
     *
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "签约信息新增修改删除(签约分类，签约品牌)")
    @RequestMapping(value = "/renewal", method = RequestMethod.POST)
    public BaseResponse renewalAll(@Valid @RequestBody ContractSaveRequest contractRequest) {
        contractRequest.setStoreId(commonUtil.getStoreId());

        //查询店铺的二次签约审核状态
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(
                StoreInfoByIdRequest.builder().storeId(contractRequest.getStoreId()).build()).getContext();

        if(Objects.nonNull(storeInfoResponse.getContractAuditState()) &&
                storeInfoResponse.getContractAuditState().equals(CheckState.WAIT_CHECK)){
            //存在待审核的签约信息 禁止编辑保存
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030010);
        }

        List<Long> ids = contractProvider.save(contractRequest).getContext().getBrandIds();
        //取消签约品牌的时候更新es
        if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(contractRequest.getDelBrandIds())) {
            esGoodsInfoElasticProvider.delBrandIds(EsBrandDeleteByIdsRequest.builder().
                    deleteIds(ids).storeId(contractRequest.getStoreId()).build());
        }
        operateLogMQUtil.convertAndSend("设置", "店铺信息", "编辑店铺信息");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 二次签约信息新增修改删除(签约分类，签约品牌)
     *
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "签约信息新增修改删除(签约分类，签约品牌)")
    @RequestMapping(value = "/audit/renewal", method = RequestMethod.POST)
    public BaseResponse renewalAllAudit(@Valid @RequestBody ContractAuditSaveRequest contractAuditRequest) {
        contractAuditRequest.setStoreId(commonUtil.getStoreId());

        //如果信息没有变动则直接保存成功
        if(!contractProvider.checkAuditData(contractAuditRequest).getContext()){
            return BaseResponse.SUCCESSFUL();
        }

        //查询店铺的二次签约审核状态
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(
                StoreInfoByIdRequest.builder().storeId(contractAuditRequest.getStoreId()).build()).getContext();

        if(Objects.nonNull(storeInfoResponse.getContractAuditState()) &&
                storeInfoResponse.getContractAuditState().equals(CheckState.WAIT_CHECK)){
           //存在待审核的签约信息 禁止编辑保存
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030010);
        }

        //判断是供应商还是商家
        StoreType storeType = commonUtil.getStoreType();

        //默认查询是商家开关 兼容o2o门店和跨境商家 如果有单独设置开关 这块需要改动
        ConfigType configType = ConfigType.SUPPLIER_SIGN_SECONDARY_AUDIT;
        if(Objects.nonNull(storeType) && storeType.equals(StoreType.PROVIDER)){
            configType = ConfigType.PROVIDER_SIGN_SECONDARY_AUDIT;
        }

        //判断是否开启了二次签约审核开关
        BossGoodsAuditResponse bossGoodsAuditResponse = auditQueryProvider.isBossGoodsSecondaryAudit(
                GoodsSecondaryAuditRequest.builder().configType(configType).build()).getContext();

        if(bossGoodsAuditResponse.isAudit()){
            contractProvider.saveAudit(contractAuditRequest);

            //修改店铺的二次签约状态
            StoreContractAuditRequest storeContractAuditRequest = new StoreContractAuditRequest();
            storeContractAuditRequest.setStoreId(contractAuditRequest.getStoreId());
            storeContractAuditRequest.setContractAuditState(CheckState.WAIT_CHECK);
            storeProvider.updateContractAuditState(storeContractAuditRequest);

            //更新店铺es数据
            StoreInfoStateModifyRequest storeInfoStateModifyRequest = new StoreInfoStateModifyRequest();
            storeInfoStateModifyRequest.setStoreId(contractAuditRequest.getStoreId());
            storeInfoStateModifyRequest.setContractAuditState(CheckState.WAIT_CHECK.toValue());
            storeInfoStateModifyRequest.setContractAuditReason("");
            storeInfoStateModifyRequest.setCompanyInfoId(storeInfoResponse.getCompanyInfoId());
            esStoreInformationProvider.modifyContractAuditState(storeInfoStateModifyRequest);
            if(Objects.equals(storeInfoResponse.getContractAuditState(),CheckState.NOT_PASS)){
                redisUtil.hset(RedisKeyConstant.IS_KNOW_SHOW_KEY,
                        String.valueOf(storeInfoResponse.getStoreId()),
                        String.valueOf(Boolean.TRUE));
            }

            // ============= 处理平台的消息发送：商家/供应商修改签约信息待审核 START =============
            storeMessageBizService.handleForStoreAudit(storeInfoResponse);
            // ============= 处理平台的消息发送：商家/供应商修改签约信息待审核 END =============


        }else{
            ContractSaveRequest contractSaveRequest = new ContractSaveRequest();
            KsBeanUtil.copyProperties(contractAuditRequest,contractSaveRequest);
            List<Long> ids = contractProvider.save(contractSaveRequest).getContext().getBrandIds();

            //取消签约品牌的时候更新es
            if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(contractSaveRequest.getDelBrandIds())) {
                esGoodsInfoElasticProvider.delBrandIds(EsBrandDeleteByIdsRequest.builder().
                        deleteIds(ids).storeId(contractSaveRequest.getStoreId()).build());
            }
        }


        operateLogMQUtil.convertAndSend("设置", "店铺信息", "编辑店铺信息二次签约,店铺名称："+storeInfoResponse.getStoreName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验签约分类是否有关联商品
     *
     * @param cateId
     * @return
     */
    @Operation(summary = "校验签约分类是否有关联商品")
    @Parameter(name = "cateId", description = "分类Id", required = true)
    @RequestMapping(value = "/cate/del/verify/{cateId}", method = RequestMethod.GET)
    public BaseResponse cateDelVerify(@PathVariable Long cateId) {
        Long storeId = commonUtil.getStoreId();
        ContractCateDelVerifyRequest request = new ContractCateDelVerifyRequest();
        request.setStoreId(storeId);
        request.setCateId(cateId);
        contractCateQueryProvider.cateDelVerify(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取商家签约分类列表
     *
     * @return
     */
    @Operation(summary = "获取商家签约分类列表")
    @RequestMapping(value = "/cate/list", method = RequestMethod.GET)
    public BaseResponse<List<ContractCateVO>> cateList() {
        ContractCateListRequest contractCateQueryRequest = new ContractCateListRequest();
        contractCateQueryRequest.setStoreId(commonUtil.getStoreId());
        return BaseResponse.success(contractCateQueryProvider.list(contractCateQueryRequest).getContext().getContractCateList());
    }

    /**
     * 获取商家二次签约分类列表
     *
     * @return
     */
    @Operation(summary = "获取商家二次签约分类列表")
    @RequestMapping(value = "/cate/audit/list", method = RequestMethod.GET)
    public BaseResponse<List<ContractCateAuditVO>> cateAuditList() {
        ContractCateAuditListRequest contractCateQueryRequest = new ContractCateAuditListRequest();
        contractCateQueryRequest.setStoreId(commonUtil.getStoreId());
        contractCateQueryRequest.setDeleteFlag(DeleteFlag.NO);
        return BaseResponse.success(contractCateAuditQueryProvider.list(contractCateQueryRequest).getContext().getContractCateList());
    }

    /**
     * 获取商家签约品牌列表
     *
     * @return
     */
    @Operation(summary = "获取商家签约品牌列表")
    @RequestMapping(value = "/brand/list", method = RequestMethod.GET)
    public BaseResponse<List<ContractBrandVO>> brandList() {
        ContractBrandListRequest contractBrandQueryRequest = new ContractBrandListRequest();
        contractBrandQueryRequest.setStoreId(commonUtil.getStoreId());
        return BaseResponse.success(contractBrandQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList());
    }

    /**
     * 获取商家二次签约品牌列表
     *
     * @return
     */
    @Operation(summary = "获取商家二次签约品牌列表")
    @RequestMapping(value = "/brand/audit/list", method = RequestMethod.GET)
    public BaseResponse<List<ContractBrandAuditVO>> brandAuditList() {
        return BaseResponse.success(contractBrandAuditQueryProvider.list(ContractBrandAuditListRequest.builder().storeId(commonUtil.getStoreId()).build()).getContext().getContractBrandVOList());
    }

    /**
     * 查询商家签约的平台类目列表，包含所有的父级类目
     *
     * @return 平台类目列表
     */
    @Operation(summary = "查询商家签约的平台类目列表，包含所有的父级类目")
    @RequestMapping(value = "/goods/cate/list", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> listCate() {
        ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        return BaseResponse.success(contractCateQueryProvider.listCateByStoreId(request).getContext().getGoodsCateList());
    }

    /**
     * 查询商家签约的平台类目列表，包含所有的父级类目
     *
     * @return 平台类目列表
     */
    @Operation(summary = "查询商家签约的平台类目列表，包含所有的父级类目")
    @RequestMapping(value = "/goods/cate/list/{goodsType}", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> listCate(@PathVariable("goodsType") Integer goodsType) {
        ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        if(goodsType!=null){
            request.setGoodsType(GoodsType.fromValue(goodsType));
        }

        return BaseResponse.success(contractCateQueryProvider.listCateByStoreId(request).getContext().getGoodsCateList());
    }
    /**
     * 查询商家签约的平台类目列表，并且平台类目映射到已审核的微信微信类目
     *
     * @return 平台类目列表
     */
    @Operation(summary = "查询商家签约的平台类目列表，并且平台类目映射到已审核的微信微信类目")
    @GetMapping(value = "/goods/cate/mapedWechat")
    public BaseResponse<List<GoodsCateVO>> mapedWechat() {
        ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        return contractCateQueryProvider.mapedWechat(request);
    }

    /**
     * 查询商家签约的平台品牌列表
     *
     * @return 商家签约的平台品牌列表
     */
    @Operation(summary = "查询商家签约的平台品牌列表")
    @RequestMapping(value = "/goods/brand/list", method = RequestMethod.GET)
    public BaseResponse<List<GoodsBrandVO>> listBrand() {
        ContractBrandListRequest request = new ContractBrandListRequest();
        request.setStoreId(commonUtil.getStoreId());
        List<GoodsBrandVO> brands = contractBrandQueryProvider.list(request).getContext().getContractBrandVOList()
                .stream().map(ContractBrandVO::getGoodsBrand)
                .filter(Objects::nonNull).collect(Collectors.toList());
        return BaseResponse.success(brands);
    }

    /**
     * 点击我知道了隐藏文案信息
     *
     * @return 点击我知道了隐藏文案信息
     */
    @Operation(summary = "点击我知道了隐藏文案信息")
    @RequestMapping(value = "hiding/message", method = RequestMethod.DELETE)
    public BaseResponse delete() {
        Long storeId = commonUtil.getStoreIdWithDefault();
        redisUtil.delete(RedisKeyConstant.IS_KNOW_SHOW_KEY.concat(String.valueOf(storeId)));
        return BaseResponse.SUCCESSFUL();
    }

}
