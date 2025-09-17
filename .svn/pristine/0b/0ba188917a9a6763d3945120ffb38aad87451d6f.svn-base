package com.wanmi.sbc.electroniccoupon;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.electroniccoupon.service.ElectronicExcelService;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCardByIdResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCardPageResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicImportRecordPageResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicSendRecordPageResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicSendRecordVO;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.ElectronicCardSendAgainRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdsRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "电子卡密表管理API", description =  "ElectronicCardController")
@RestController
@Validated
@RequestMapping(value = "/electronic/cards")
public class ElectronicCardController {

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private ElectronicCardProvider electronicCardProvider;

    @Autowired
    private ElectronicExcelService electronicExcelService;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Operation(summary = "分页查询电子卡密")
    @PostMapping("/page")
    public BaseResponse<ElectronicCardPageResponse> getPage(@RequestBody @Valid ElectronicCardPageRequest pageReq) {
        ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider
                .getById(ElectronicCouponByIdRequest.builder().id(pageReq.getCouponId()).build())
                .getContext()
                .getElectronicCouponVO();
        if (Objects.isNull(electronicCouponVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        commonUtil.checkStoreId(electronicCouponVO.getStoreId());
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("saleStartTime", "desc");
        pageReq.setEncrypt(Boolean.TRUE);
        return electronicCardQueryProvider.page(pageReq);
    }

    @Operation(summary = "查询电子卡密")
    @GetMapping("/{id}")
    public BaseResponse<ElectronicCardByIdResponse> findById(@PathVariable String id) {
        ElectronicCardVO electronicCard = electronicCardQueryProvider.findById(ElectronicCardByIdRequest.builder().id(id).build()).getContext().getElectronicCard();
        if (electronicCard == null || !commonUtil.getStoreId().equals(electronicCard.getStoreId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        return BaseResponse.success(new ElectronicCardByIdResponse(electronicCard));
    }

    @Operation(summary = "修改电子卡密")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid ElectronicCardModifyRequest modifyReq) {
        modifyReq.setBaseStoreId(commonUtil.getStoreId());
        return electronicCardProvider.modify(modifyReq);
    }

    @Operation(summary = "根据idList批量删除电子卡密")
    @DeleteMapping
    public BaseResponse deleteByIdList(@RequestBody @Valid ElectronicCardDelByIdListRequest delByIdListReq) {
        delByIdListReq.setBaseStoreId(commonUtil.getStoreId());
        return electronicCardProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "根据卡券id、批次id批量删除电子卡密")
    @DeleteMapping("/all")
    public BaseResponse deleteAll(@RequestBody @Valid ElectronicCardDelAllRequest request) {
        return electronicCardProvider.deleteAll(request);
    }

    @Operation(summary = "导出电子卡密")
    @PostMapping("/export")
    public BaseResponse exportData(@RequestBody @Valid ElectronicCardExportRequest request) {
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(request));
        exportDataRequest.setTypeCd(ReportType.ELECTRONIC_CARDS);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "下载卡密模版")
    @GetMapping("/excel/template/{encrypted}")
    public void template(@PathVariable String encrypted) {
        String file = electronicCardProvider.exportTemplate().getContext().getTemplateFile();
        if(StringUtils.isNotBlank(file)){
            try {
                String fileName = URLEncoder.encode("卡券导入模板.xlsx", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            }catch (Exception e){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    @Operation(summary = "上传模版")
    @PostMapping("/excel/upload/{couponId}")
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile multipartFile, @PathVariable Long couponId) {
        return BaseResponse.success(electronicExcelService.upload(multipartFile, couponId));
    }

    @GlobalTransactional
    @Operation(summary = "确认导入")
    @PostMapping("/import")
    public BaseResponse importExcel(@RequestBody @Valid ElectronicImportRequest request) {
        electronicExcelService.importExcel(request.getCouponId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @RequestMapping(value = "/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        electronicExcelService.downErrorFile(ext);
    }

    @Operation(summary = "分页查询卡密导入记录")
    @PostMapping("/records/page")
    public BaseResponse<ElectronicImportRecordPageResponse> getCardImportRecordPage(@RequestBody @Valid ElectronicImportRecordPageRequest request) {
        ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider
                .getById(ElectronicCouponByIdRequest.builder().id(request.getCouponId()).build())
                .getContext()
                .getElectronicCouponVO();
        if (Objects.isNull(electronicCouponVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        commonUtil.checkStoreId(electronicCouponVO.getStoreId());
        request.putSort("createTime", "desc");
        return electronicCardQueryProvider.getImportRecordPage(request);
    }

    @Operation(summary = "查询卡密发放记录")
    @PostMapping("/send/record")
    public BaseResponse<ElectronicSendRecordPageResponse> getSendRecordPage(@RequestBody @Valid ElectronicSendRecordPageRequest request) {
        request.putSort("sendTime","desc");
        request.setStoreId(commonUtil.getStoreId());
        BaseResponse<ElectronicSendRecordPageResponse> response = electronicCardQueryProvider.sendRecordPage(request);
        if (CollectionUtils.isNotEmpty(response.getContext().getElectronicSendRecordVOPage().getContent())){
            List<String> orderNos = response.getContext().getElectronicSendRecordVOPage().getContent()
                    .stream()
                    .map(ElectronicSendRecordVO::getOrderNo)
                    .collect(Collectors.toList());
            List<TradeVO> tradeVOs = tradeQueryProvider.getByIds(TradeGetByIdsRequest.builder().tid(orderNos).build()).getContext().getTradeVO();
            if (CollectionUtils.isNotEmpty(tradeVOs)){
                List<String> customerIds = tradeVOs.stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
                Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
                response.getContext().getElectronicSendRecordVOPage().getContent().forEach(page ->{
                    Optional<TradeVO> opt = tradeVOs.stream().filter(vo -> Objects.equals(vo.getId(), page.getOrderNo())).findFirst();
                    opt.ifPresent(tradeVO -> page.setLogOutStatus(map.get(tradeVO.getBuyer().getId())));
                });
            }
        }
        return response;
    }

    @GlobalTransactional
    @Operation(summary = "卡密重发")
    @PutMapping("/send/again")
    @MultiSubmit
    public BaseResponse cardSendAgain(@RequestBody @Valid ElectronicCardSendAgainRequest request) {
        tradeProvider.sendElectronicAgain(request);
        return BaseResponse.SUCCESSFUL();
    }

}
