package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsCateBrandProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsCateUpdateNameRequest;
import com.wanmi.sbc.goods.api.provider.cate.BossGoodsCateProvider;
import com.wanmi.sbc.goods.api.provider.cate.BossGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.*;
import com.wanmi.sbc.goods.api.response.cate.BossGoodsCateDeleteByIdResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateByIdResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateModifyResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsCateSortDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.request.GoodsCateModify;
import com.wanmi.sbc.goods.service.GoodsCateExcelService;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponMarketingScopeProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponMarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponMarketingScopeBatchAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponMarketingScopeByScopeIdRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponMarketingScopeDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponMarketingScopeVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * 商品分类服务
 * Created by chenli on 17/10/30.
 */
@RequestMapping("/goods")
@RestController
@Validated
@Tag(name = "BossGoodsCateController", description = "商品分类服务")
public class BossGoodsCateController {

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsCateProvider goodsCateProvider;

    @Autowired
    private BossGoodsCateQueryProvider bossGoodsCateQueryProvider;

    @Autowired
    private GoodsCateExcelService goodsCateExcelService;

    @Autowired
    private BossGoodsCateProvider bossGoodsCateProvider;

    @Autowired
    private CouponMarketingScopeProvider couponMarketingScopeProvider;

    @Autowired
    private CouponMarketingScopeQueryProvider couponMarketingScopeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCateBrandProvider esCateBrandProvider;

    /**
     * 查询商品分类
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询商品分类")
    @RequestMapping(value = "/goodsCates", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> list(GoodsCateListByConditionRequest queryRequest) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.putSort("isDefault", SortType.DESC.toValue());
        queryRequest.putSort("sort", SortType.ASC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        return BaseResponse.success(goodsCateQueryProvider.listByCondition(queryRequest).getContext().getGoodsCateVOList());
    }

    /**
     * 查询商品分类
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "查询商品分类")
    @RequestMapping(value = "/goodsCateList", method = RequestMethod.POST)
    public BaseResponse<List<GoodsCateVO>> listByParentId(@RequestBody GoodsCateByParentIdRequest queryRequest) {
        queryRequest.putSort("isDefault", SortType.DESC.toValue());
        queryRequest.putSort("sort", SortType.ASC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        return BaseResponse.success(goodsCateQueryProvider.listByParentId(queryRequest).getContext().getGoodsCateVOList());
    }

    /**
     * 新增商品分类
     */
    @Operation(summary = "新增商品分类")
    @RequestMapping(value = "/goodsCate", method = RequestMethod.POST)
    public BaseResponse<GoodsCateVO> add(@Valid @RequestBody GoodsCateAddRequest saveRequest) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if (Objects.isNull(saveRequest) || Objects.isNull(saveRequest.getGoodsCate())
                || StringUtil.isBlank(saveRequest.getGoodsCate().getCateName())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        GoodsCateVO goodsCate = goodsCateProvider.add(saveRequest).getContext().getGoodsCate();
        //查询父分类是否关联优惠券
        List<CouponMarketingScopeVO> couponMarketingScopes = couponMarketingScopeQueryProvider.listByScopeId(
                CouponMarketingScopeByScopeIdRequest.builder().scopeId(String.valueOf(goodsCate.getCateParentId()))
                        .build()).getContext().getScopeVOList();
        if (CollectionUtils.isNotEmpty(couponMarketingScopes)) {
            couponMarketingScopes.forEach(couponScope -> {
                couponScope.setMarketingScopeId(null);
                couponScope.setCateGrade(couponScope.getCateGrade() + 1);
                couponScope.setScopeId(String.valueOf(goodsCate.getCateId()));
            });
            couponMarketingScopeProvider.batchAdd(
                    CouponMarketingScopeBatchAddRequest.builder()
                            .scopeDTOList(KsBeanUtil.convert(couponMarketingScopes, CouponMarketingScopeDTO.class))
                            .build());
        }
        //操作日志记录
        if (isNull(saveRequest.getGoodsCate().getCateId())) {
            operateLogMQUtil.convertAndSend("商品", "新增一级类目",
                    "新增一级类目：" + saveRequest.getGoodsCate().getCateName());
        } else {
            operateLogMQUtil.convertAndSend("商品", "添加子类目",
                    "添加子类目：" + saveRequest.getGoodsCate().getCateName());
        }
        return BaseResponse.success(goodsCate);
    }

    /**
     * 获取商品分类详情信息
     *
     * @param cateId 商品分类编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品分类详情信息")
    @Parameter(name = "cateId", description = "分类Id", required = true)
    @RequestMapping(value = "/goodsCate/{cateId}", method = RequestMethod.GET)
    public ResponseEntity<GoodsCateByIdResponse> list(@PathVariable Long cateId) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsCateByIdRequest goodsCateByIdRequest = new GoodsCateByIdRequest();
        goodsCateByIdRequest.setCateId(cateId);
        return ResponseEntity.ok(goodsCateQueryProvider.getById(goodsCateByIdRequest).getContext());
    }

    /**
     * 编辑商品分类
     */
    @Operation(summary = "编辑商品分类")
    @RequestMapping(value = "/goodsCate", method = RequestMethod.PUT)
    public BaseResponse<GoodsCateByIdResponse> edit(@Valid @RequestBody GoodsCateModify goodsCateModify) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if (Objects.isNull(goodsCateModify) || Objects.isNull(goodsCateModify.getGoodsCate())
                || StringUtil.isBlank(goodsCateModify.getGoodsCate().getCateName())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        if (Objects.isNull(goodsCateModify.getGoodsCate().getCateId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BigDecimal growthValueRate = goodsCateModify.getGoodsCate().getGrowthValueRate();
        if (Objects.nonNull(growthValueRate)) {
            double rate = growthValueRate.doubleValue();
            if (rate < 0 || rate > 100) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        BaseResponse<GoodsCateModifyResponse> modifyResponse = goodsCateProvider.modify(goodsCateModify.getGoodsCate());
        //更新es信息
        esCateBrandProvider.updateToEs(EsCateUpdateNameRequest.builder().
                goodsCateListVOList(modifyResponse.getContext().getGoodsCateListVOList()).build());
        //操作日志记录
        operateLogMQUtil.convertAndSend("商品", "编辑类目",
                "编辑类目：" + goodsCateModify.getGoodsCate().getCateName());
        GoodsCateByIdRequest request = new GoodsCateByIdRequest();
        request.setCateId(goodsCateModify.getGoodsCate().getCateId());
        return goodsCateQueryProvider.getById(request);
    }

    /**
     * 检测图片分类是否有子类
     */
    @Operation(summary = "检测图片分类是否有子类")
    @RequestMapping(value = "/goodsCate/child", method = RequestMethod.POST)
    public BaseResponse checkChild(@RequestBody BossGoodsCateCheckSignChildRequest queryRequest) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if (queryRequest == null || queryRequest.getCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.success(bossGoodsCateQueryProvider.checkSignChild(queryRequest));
    }

    /**
     * 检测图片分类是否有商品
     */
    @Operation(summary = "检测图片分类是否有商品")
    @RequestMapping(value = "/goodsCate/goods", method = RequestMethod.POST)
    public BaseResponse checkGoods(@RequestBody BossGoodsCateCheckSignGoodsRequest queryRequest) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if (queryRequest == null || queryRequest.getCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.success(bossGoodsCateQueryProvider.checkSignGoods(queryRequest));
    }

    /**
     * 删除商品分类
     */
    @Operation(summary = "删除商品分类")
    @Parameter(name = "cateId", description = "分类Id", required = true)
    @RequestMapping(value = "/goodsCate/{cateId}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long cateId) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        boolean isOpen = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        BossGoodsCateDeleteByIdRequest bossGoodsCateDeleteByIdRequest = new BossGoodsCateDeleteByIdRequest();
        bossGoodsCateDeleteByIdRequest.setCateId(cateId);
        bossGoodsCateDeleteByIdRequest.setIsOpen(isOpen);
        BaseResponse<BossGoodsCateDeleteByIdResponse> baseResponse =
                bossGoodsCateProvider.deleteById(bossGoodsCateDeleteByIdRequest);

        GoodsCateByIdRequest goodsCateByIdRequest = new GoodsCateByIdRequest();
        goodsCateByIdRequest.setCateId(cateId);
        GoodsCateByIdResponse goodsCate = goodsCateQueryProvider.getById(goodsCateByIdRequest).getContext();
        //操作日志记录
        if (Objects.nonNull(goodsCate)) {
            operateLogMQUtil.convertAndSend("商品", "删除类目",
                    "删除类目：" + goodsCate.getCateName());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 拖拽排序商品分类
     */
    @Operation(summary = "拖拽排序商品分类")
    @RequestMapping(value = "/goods-cate/sort", method = RequestMethod.PUT)
    public BaseResponse goodsCateSort(@RequestBody List<GoodsCateSortDTO> goodsCateList) {
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return goodsCateProvider.batchModifySort(new GoodsCateBatchModifySortRequest(goodsCateList));
    }

    /**
     * 下载模板
     */
    @Operation(summary = "下载模板")
    @RequestMapping(value = "/goodsCate/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        goodsCateExcelService.exportTemplate();
    }


    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @RequestMapping(value = "/goodsCate/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsCateExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    /**
     * 上传模板
     *
     * @param uploadFile
     * @return
     */
    @Operation(summary = "上传模板")
    @RequestMapping(value = "/goodsCate/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(goodsCateExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 类目导入
     *
     * @param ext
     * @return
     */
    @Operation(summary = "类目导入")
    @RequestMapping(value = "/goodsCate/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> importGoodsCate(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsCateExcelService.importGoodsCate(commonUtil.getOperatorId());
        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 修改分类是否可以包含虚拟商品
     * @param request
     * @return
     */
    @Operation(summary = "修改分类是否可以包含虚拟商品")
    @RequestMapping(value = "/goodsCate/containsVirtual",method = RequestMethod.PUT)
    public BaseResponse setContainsVirtual(@RequestBody GoodsCateContainsVirtualRequest request) {
        if (Objects.isNull(request) || request.getContainsVirtual()==null || request.getCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //同步购物规则
        goodsCateProvider.setContainsVirtual(request);

        //操作日志记录
        operateLogMQUtil.convertAndSend("设置", "修改分类是否可以包含虚拟商品", "修改分类是否可以包含虚拟商品");
        return BaseResponse.SUCCESSFUL();
    }
}
