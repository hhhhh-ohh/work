package com.wanmi.sbc.pointsgoods;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsProvider;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsQueryProvider;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsDeleteByIdRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsModifyStatusRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.elastic.api.response.pointsgoods.EsPointsGoodsPageResponse;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsAddListRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsAddRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsCloseRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsDelByIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSwitchRequest;
import com.wanmi.sbc.goods.api.response.pointsgoods.PointsGoodsAddResponse;
import com.wanmi.sbc.goods.api.response.pointsgoods.PointsGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.pointsgoods.PointsGoodsModifyResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.pointsgoods.request.PointsGoodsImportExcelRequest;
import com.wanmi.sbc.pointsgoods.service.PointsGoodsImportExcelService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "积分商品表管理API", description =  "PointsGoodsController")
@RestController
@Validated
@RequestMapping(value = "/pointsgoods")
public class PointsGoodsController {

    @Autowired
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;

    @Autowired
    private PointsGoodsSaveProvider pointsGoodsSaveProvider;

    @Autowired
    private PointsGoodsExcelProvider pointsGoodsExcelProvider;

    @Autowired
    private EsPointsGoodsProvider esPointsGoodsProvider;

    @Autowired
    private PointsGoodsImportExcelService pointsGoodsImportExcelService;

    @Autowired
    private EsPointsGoodsQueryProvider esPointsGoodsQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Operation(summary = "分页查询积分商品表")
    @PostMapping("/page")
    public BaseResponse<EsPointsGoodsPageResponse> page(@RequestBody @Valid EsPointsGoodsPageRequest pointsGoodsPageReq) {
        pointsGoodsPageReq.putSort("beginTime", SortType.DESC.toValue());
        pointsGoodsPageReq.setFillCateFlag(Boolean.TRUE);//填充返回积分商品分类
        BaseResponse<EsPointsGoodsPageResponse> response = esPointsGoodsQueryProvider.page(pointsGoodsPageReq);

        //填充营销商品状态
        List<PointsGoodsVO> goodsVOS = response.getContext().getPointsGoodsVOPage().getContent();
        if (CollectionUtils.isNotEmpty(goodsVOS)){
            List<GoodsInfoVO> goodsInfoVOS = goodsVOS.stream().map(PointsGoodsVO::getGoodsInfo).collect(Collectors.toList());
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        }

        return response;
    }

    @Operation(summary = "根据id查询积分商品表")
    @Parameter(name = "pointsCouponId", description = "积分兑换券id", required = true)
    @GetMapping("/{pointsGoodsId}")
    public BaseResponse<PointsGoodsByIdResponse> getById(@PathVariable String pointsGoodsId) {
        if (pointsGoodsId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PointsGoodsByIdRequest idReq = new PointsGoodsByIdRequest();
        idReq.setPointsGoodsId(pointsGoodsId);
        return pointsGoodsQueryProvider.getById(idReq);
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增积分商品表")
    @PostMapping("/add")
    public BaseResponse<PointsGoodsAddResponse> add(@RequestBody @Valid PointsGoodsAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        PointsGoodsAddResponse response = pointsGoodsSaveProvider.add(addReq).getContext();
        //初始化积分ES
        esPointsGoodsProvider.init(EsPointsGoodsInitRequest.builder()
                .pointsGoodsIds(Collections.singletonList(response.getPointsGoodsVO().getPointsGoodsId())).build());
        return BaseResponse.success(response);
    }

    @GlobalTransactional
    @Operation(summary = "批量新增积分商品")
    @PostMapping("/batchAdd")
    @MultiSubmit
    public BaseResponse batchAdd(@RequestBody @Valid PointsGoodsAddListRequest request) {
        List<PointsGoodsAddRequest> addRequestList = request.getPointsGoodsAddRequestList();
        addRequestList.forEach(addRequest -> {
            addRequest.setBeginTime(request.getBeginTime());
            addRequest.setEndTime(request.getEndTime());
            addRequest.setStatus(EnableStatus.ENABLE);
            addRequest.setDelFlag(DeleteFlag.NO);
            addRequest.setSales((long) 0);
            addRequest.setCreatePerson(commonUtil.getOperatorId());
            addRequest.setCreateTime(LocalDateTime.now());
        });
        operateLogMQUtil.convertAndSend("营销", "积分商品列表", "添加积分商品");
        pointsGoodsSaveProvider.batchAdd(request);
        //初始化积分ES
        List<String> skuIds = addRequestList.stream().map(PointsGoodsAddRequest::getGoodsInfoId).filter(Objects::nonNull).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(skuIds)) {
            esPointsGoodsProvider.init(EsPointsGoodsInitRequest.builder()
                    .goodsInfoIds(skuIds).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "修改积分商品表")
    @PutMapping("/modify")
    public BaseResponse<PointsGoodsModifyResponse> modify(@RequestBody @Valid PointsGoodsModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        PointsGoodsModifyResponse modifyResponse = pointsGoodsSaveProvider.modify(modifyReq).getContext();
        //初始化积分ES
        esPointsGoodsProvider.init(EsPointsGoodsInitRequest.builder()
                .pointsGoodsIds(Collections.singletonList(modifyReq.getPointsGoodsId())).build());
        return BaseResponse.success(modifyResponse);
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "根据id删除积分商品表")
    @Parameter(name = "pointsCouponId", description = "积分兑换券id", required = true)
    @DeleteMapping("/{pointsGoodsId}")
    public BaseResponse deleteById(@PathVariable String pointsGoodsId) {
        if (pointsGoodsId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PointsGoodsDelByIdRequest delByIdReq = new PointsGoodsDelByIdRequest();
        delByIdReq.setPointsGoodsId(pointsGoodsId);
        pointsGoodsSaveProvider.deleteById(delByIdReq);

        esPointsGoodsProvider.deleteById(EsPointsGoodsDeleteByIdRequest.builder().pointsGoodsId(pointsGoodsId).build());
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "根据idList批量删除积分商品表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteById(@RequestBody @Valid PointsGoodsDelByIdListRequest delByIdListReq) {
        return pointsGoodsSaveProvider.deleteByIdList(delByIdListReq);
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "启用停用积分商品")
    @PutMapping("/modifyStatus")
    public BaseResponse modifyStatus(@RequestBody @Valid PointsGoodsSwitchRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setUpdateTime(LocalDateTime.now());
        pointsGoodsSaveProvider.modifyStatus(request);
        return esPointsGoodsProvider.modifyStatus(EsPointsGoodsModifyStatusRequest.builder().pointsGoodsId(request.getPointsGoodsId()).status(request.getStatus().toValue()).build());
    }

    @Operation(summary = "下载积分商品模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        String file = pointsGoodsExcelProvider.exportTemplate().getContext().getFile();
        if (StringUtils.isNotBlank(file)) {
            try {
                String fileName = URLEncoder.encode("积分商品导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse()
                        .getOutputStream()
                        .write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(pointsGoodsImportExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 确认导入积分商品
     *
     * @param ext 文件格式 {@link String}
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "确认导入商品库商品")
    @Parameter(name = "ext", description = "文件名后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        PointsGoodsImportExcelRequest pointsGoodsImportExcelRequest = new PointsGoodsImportExcelRequest();
        pointsGoodsImportExcelRequest.setExt(ext);
        pointsGoodsImportExcelRequest.setUserId(commonUtil.getOperatorId());
        List<String> skuIds = pointsGoodsImportExcelService.implGoods(pointsGoodsImportExcelRequest);
        if(CollectionUtils.isNotEmpty(skuIds)){
            esPointsGoodsProvider.init(EsPointsGoodsInitRequest.builder()
                    .goodsInfoIds(skuIds).build());
        }
        //操作日志记录
        operateLogMQUtil.convertAndSend("积分商品", "批量导入", "批量导入");
        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @RequestMapping(value = "/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        pointsGoodsImportExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "关闭积分商品")
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable String id) {
        if(commonUtil.getOperator().getPlatform() != Platform.PLATFORM) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        pointsGoodsSaveProvider.closeById(PointsGoodsCloseRequest.builder().pointsGoodsId(id).build());
        esPointsGoodsProvider.init(EsPointsGoodsInitRequest.builder().idList(Lists.newArrayList(id)).build());
        return BaseResponse.SUCCESSFUL();
    }
}
