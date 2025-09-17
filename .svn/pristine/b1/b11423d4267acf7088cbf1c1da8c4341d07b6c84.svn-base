package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.empower.bean.enums.WxSceneGroup;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.entity.ReturnExportRequest;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.ReturnOrderBaseService;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeNoByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnCountByConditionRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderPageRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeNoMapResponse;
import com.wanmi.sbc.order.bean.vo.CompanyVO;
import com.wanmi.sbc.order.bean.vo.ReturnItemVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className ReturnOrderExportService
 * @description 退单导出
 * @date 2021/6/2 4:22 下午
 **/
@Service
@Slf4j
public class ReturnOrderExportService implements ExportBaseService {

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Resource
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Resource
    private ReturnOrderBaseService returnOrderBaseService;

    private static final int SIZE = 10000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出退单_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("returnOrder/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        ReturnExportRequest returnExportRequest = JSON.parseObject(data.getParam(), ReturnExportRequest.class);
        // 标识Map，用于生成不同的columns
        Map<String, Object> typeMap = new HashMap<>();
        // 标识代销平台
        typeMap.put("sellPlatformType", returnExportRequest.getSellPlatformType());
        Boolean isO2O = returnExportRequest.getIsO2O();
        if (Objects.nonNull(isO2O) && isO2O) {
            isO2O = true;
        } else {
            isO2O = false;
        }
        if (data.getPlatform() == Platform.SUPPLIER) {
            returnExportRequest.setCompanyInfoId(Long.parseLong(data.getAdminId()));
        } else if(data.getPlatform() == Platform.PROVIDER) {
            returnExportRequest.setProviderCompanyInfoId(Long.parseLong(data.getAdminId()));
        } else if (data.getPlatform() == Platform.STOREFRONT) {
            returnExportRequest.setCompanyInfoId(Long.parseLong(data.getAdminId()));
            returnExportRequest.setStoreType(StoreType.O2O);
        }

        Long total = returnOrderQueryProvider.countByCondition(KsBeanUtil.convert(returnExportRequest, ReturnCountByConditionRequest.class)).getContext().getCount();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = Platform.PROVIDER.equals(data.getPlatform())
                ? this.getProviderColumns()
                : this.getColumns(data.getPlatform() == Platform.PLATFORM, data.getBuyAnyThirdChannelOrNot(),isO2O, typeMap);
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("退单导出", columns);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        ReturnOrderPageRequest pageRequest = KsBeanUtil.convert(returnExportRequest, ReturnOrderPageRequest.class);
        pageRequest.setPageSize(SIZE);
        try {
            this.export(excelHelper, sheet, columns, pageRequest, fileSize, data.getOperator());
        } catch (Exception e) {
            excelHelper.deleteTempFile();
            throw e;
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        outputStream.close();

        return BaseResponse.success(resourceKey);
    }

    /**
     * @return
     * @description 导出
     * @author xuyunpeng
     * @date 2021/6/2 9:53 上午
     */
    public void export(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns,
                       ReturnOrderPageRequest exportRequest, long fileSize, Operator operator) {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            exportRequest.setPageNum(i);
            List<ReturnOrderVO> returnOrderVOS = returnOrderBaseService.export(operator, exportRequest);
            // 导出列表中，添加交易流水号字段
            // 因为只有三方支付才存在交易流水号，所以过滤所有三方的订单号
            List<String> returnOrderIds = returnOrderVOS.stream()
                    .filter(returnOrder -> PayWay.isThirdPartyPay(returnOrder.getPayWay()))
                    .map(ro-> StringUtils.isNotEmpty(ro.getBusinessTailId()) ? ro.getBusinessTailId() : ro.getId())
                    .collect(Collectors.toList());
            if (WmCollectionUtils.isNotEmpty(returnOrderIds)) {
                Map<String, String> tradeNoMap = BaseResUtils.getResultFromRes(payTradeRecordQueryProvider.queryTradeNoMapByBusinessIds(TradeNoByBusinessIdRequest
                        .builder().businessIdList(returnOrderIds).build()), PayTradeNoMapResponse::getTradeNoMap);
                if (Objects.nonNull(tradeNoMap)) {
                    returnOrderVOS.forEach(returnOrder -> {
                        String rid = StringUtils.isNotEmpty(returnOrder.getBusinessTailId()) ? returnOrder.getBusinessTailId() : returnOrder.getId();
                        if (tradeNoMap.containsKey(rid)) {
                            returnOrder.setTradeNo(tradeNoMap.get(rid));
                        } else {
                            returnOrder.setTradeNo("-");
                        }
                    });
                }
            } else {
                returnOrderVOS.forEach(order->order.setTradeNo("-"));
            }
            // 获取用户注销状态
            List<String> customerIds = returnOrderVOS.stream().map(v->v.getBuyer().getId()).collect(Collectors.toList());
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
            returnOrderVOS.forEach(v->{
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(v.getBuyer().getId()))){
                    v.getBuyer().setAccount(v.getBuyer().getAccount()+ Constants.LOGGED_OUT);
                }
                //vop下 outOrderId无需导出
                if(ThirdPlatformType.VOP.equals(v.getThirdPlatformType())
                        && StringUtils.isNotEmpty(v.getOutOrderId())
                        && v.getOutOrderId().equals(v.getThirdPlatformOrderId())){
                    v.setOutOrderId(null);
                }
            });
            excelHelper.addSXSSFSheetRow(sheet, columns, returnOrderVOS, rowIndex + 1);
            rowIndex = rowIndex + returnOrderVOS.size();
        }
    }


    public Column[] getColumns(boolean existSupplier, boolean isOpen,boolean isO2O, Map<String, Object> typeMap) {

        Column[] columns;
        List<Column> columnList = new ArrayList<>(20);
        if (existSupplier) {
            columnList.add(new Column("退单编号", new SpelColumnRender<ReturnOrderVO>("id")));
            columnList.add(new Column("交易流水号", new SpelColumnRender<ReturnOrderVO>("tradeNo")));
            columnList.add(new Column("申请时间", new SpelColumnRender<ReturnOrderVO>("createTime")));
            columnList.add(new Column("完成时间", new SpelColumnRender<ReturnOrderVO>("null != finishTime ? finishTime : '-' ")));
            columnList.add(new Column("订单编号", new SpelColumnRender<ReturnOrderVO>("tid")));
            if(!isO2O) {
                columnList.add(new Column("子单编号", new SpelColumnRender<ReturnOrderVO>("ptid")));
            }
            // 三方渠道相关字段
            if (isOpen) {
                columnList.add(new Column("渠道订单号", new SpelColumnRender<ReturnOrderVO>(
                        "thirdPlatformOrderId")));
                columnList.add(new Column("渠道子单号", new SpelColumnRender<ReturnOrderVO>("outOrderId")));
            }

            columnList.add(new Column("商家", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                String supplierName = returnOrderVO.getCompany().getSupplierName();
                String supplierCode = returnOrderVO.getCompany().getCompanyCode();
                cell.setCellValue(supplierName + " " + supplierCode);
            }));
            if(!isO2O) {
                columnList.add(new Column("供应商", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    String providerName = returnOrderVO.getProviderName() != null ? returnOrderVO.getProviderName() : StringUtils.EMPTY;
                    String providerCode = returnOrderVO.getProviderCode() != null ? returnOrderVO.getProviderCode() : StringUtils.EMPTY;
                    cell.setCellValue(providerName + " " + providerCode);
                }));
            }
            columnList.add(new Column("客户名称", new SpelColumnRender<ReturnOrderVO>("buyer.name")));
            columnList.add(new Column("客户账号", new SpelColumnRender<ReturnOrderVO>("buyer.account")));
            columnList.add(new Column("客户级别", new SpelColumnRender<ReturnOrderVO>("buyer.levelName")));
            columnList.add(new Column("退货原因", new SpelColumnRender<ReturnOrderVO>("refundCause.cause")));

            // 三方渠道相关字段
            if (isOpen) {
                columnList.add(new Column("渠道退单原因", new SpelColumnRender<ReturnOrderVO>("thirdReasonTips")));
            }

            columnList.add(new Column("退货说明", new SpelColumnRender<ReturnOrderVO>("description")));
            columnList.add(new Column("退货方式", new SpelColumnRender<ReturnOrderVO>("returnWay.getDesc()")));
            columnList.add(new Column("商品名称", (cell,object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                String skuName = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSkuName)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(skuName);
            }));
            columnList.add(new Column("规格名称", (cell,object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }
                String specDetails = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSpecDetails)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(specDetails);
            }));
            columnList.add(new Column("退货商品SKU编码", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                String skuNos = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSkuNo)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(skuNos);
            }));
            columnList.add(new Column("退货商品种类", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }
                cell.setCellValue(returnItemVOS.size());
            }));
            columnList.add(new Column("退货商品总数量", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                Optional<Integer> size = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getNum)
                        .reduce((sum, item) -> {
                            sum += item;
                            return sum;
                        });


                cell.setCellValue(size.orElse(NumberUtils.INTEGER_ZERO));
            }));
            columnList.add(new Column("应退金额", new SpelColumnRender<ReturnOrderVO>("returnPrice.totalPrice")));
            columnList.add(new Column("实退金额", new SpelColumnRender<ReturnOrderVO>("null != returnPrice.actualReturnPrice ? returnPrice.actualReturnPrice : \"\"")));
            columnList.add(new Column("应退礼品卡金额", new SpelColumnRender<ReturnOrderVO>("null != returnPrice.giftCardPrice ? returnPrice.giftCardPrice : ''")));
            columnList.add(new Column("退款积分", new SpelColumnRender<ReturnOrderVO>("null != returnPoints.actualPoints ? returnPoints.actualPoints : ''")));
            columnList.add(new Column("退单状态", new SpelColumnRender<ReturnOrderVO>("transformReturnFlowState(returnFlowState)")));

        } else {
            columnList.add(new Column("退单编号", new SpelColumnRender<ReturnOrderVO>("id")));
            columnList.add(new Column("交易流水号", new SpelColumnRender<ReturnOrderVO>("tradeNo")));
            columnList.add(new Column("申请时间", new SpelColumnRender<ReturnOrderVO>("createTime")));
            columnList.add(new Column("完成时间", new SpelColumnRender<ReturnOrderVO>("null != finishTime ? finishTime : '-' ")));
            columnList.add(new Column("订单编号", new SpelColumnRender<ReturnOrderVO>("tid")));
            if(!isO2O) {
                columnList.add(new Column("子单编号", new SpelColumnRender<ReturnOrderVO>("ptid")));
            }
            // 三方渠道相关字段
            if (isOpen) {
                columnList.add(new Column("渠道订单号", new SpelColumnRender<ReturnOrderVO>(
                        "thirdPlatformOrderId")));
                columnList.add(new Column("渠道子订单", new SpelColumnRender<ReturnOrderVO>("outOrderId")));
            }
            if(!isO2O) {
                columnList.add(new Column("供应商", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    String providerName = returnOrderVO.getProviderName() == null ? "" : returnOrderVO.getProviderName();
                    String providerCode = returnOrderVO.getProviderCode() == null ? "" : returnOrderVO.getProviderCode();
                    cell.setCellValue(providerName + " " + providerCode);
                }));
            }
            columnList.add(new Column("客户名称", new SpelColumnRender<ReturnOrderVO>("buyer.name")));
            columnList.add(new Column("客户账号", new SpelColumnRender<ReturnOrderVO>("buyer.account")));
            columnList.add(new Column("客户级别", new SpelColumnRender<ReturnOrderVO>("buyer.levelName")));
            columnList.add(new Column("退货原因", new SpelColumnRender<ReturnOrderVO>("refundCause.cause")));

            // 三方渠道相关字段
            if (isOpen) {
                columnList.add(new Column("渠道退单原因", new SpelColumnRender<ReturnOrderVO>("thirdReasonTips")));
            }

            columnList.add(new Column("退货说明", new SpelColumnRender<ReturnOrderVO>("description")));
            columnList.add(new Column("退货方式", new SpelColumnRender<ReturnOrderVO>("returnWay.getDesc()")));
            columnList.add(new Column("商品名称", (cell,object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                String skuName = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSkuName)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(skuName);
            }));
            columnList.add(new Column("规格名称", (cell,object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }
                String specDetails = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSpecDetails)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(specDetails);
            }));
            columnList.add(new Column("退货商品SKU编码", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                String skuNos = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getSkuNo)
                        .collect(Collectors.joining(";"));

                cell.setCellValue(skuNos);
            }));
            columnList.add(new Column("退货商品种类", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }
                cell.setCellValue(returnItemVOS.size());
            }));
            columnList.add(new Column("退货商品总数量", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                returnItemVOS.addAll(returnOrderVO.getReturnItems());
                if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                    returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                }

                Optional<Integer> size = returnItemVOS
                        .stream()
                        .map(ReturnItemVO::getNum)
                        .reduce((sum, item) -> {
                            sum += item;
                            return sum;
                        });


                cell.setCellValue(size.orElse(NumberUtils.INTEGER_ZERO));
            }));
            columnList.add(new Column("应退金额", new SpelColumnRender<ReturnOrderVO>("returnPrice.totalPrice")));
            columnList.add(new Column("实退金额", new SpelColumnRender<ReturnOrderVO>("null != returnPrice.actualReturnPrice ? returnPrice.actualReturnPrice : \"\"")));
            columnList.add(new Column("应退礼品卡金额", new SpelColumnRender<ReturnOrderVO>("null != returnPrice.giftCardPrice ? returnPrice.giftCardPrice : ''")));
            columnList.add(new Column("退款积分", new SpelColumnRender<ReturnOrderVO>("null != returnPoints.actualPoints ? returnPoints.actualPoints : ''")));
            columnList.add(new Column("退单状态", new SpelColumnRender<ReturnOrderVO>("transformReturnFlowState(returnFlowState)")));
        }
        // 获取代销类型
        SellPlatformType sellPlatformType = (SellPlatformType) typeMap.get("sellPlatformType");
        if (sellPlatformType == SellPlatformType.WECHAT_VIDEO) {
            // 处理视频号订单
            columnList.add(new Column("带货视频号", new SpelColumnRender<ReturnOrderVO>("videoUser.videoName")));
            columnList.add(new Column("下单场景", (cell, object) -> {
                ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                String sceneGroupStr = Optional.ofNullable(WxSceneGroup.fromValue(returnOrderVO.getSceneGroup()))
                        .map(WxSceneGroup::toName).orElse("");
                cell.setCellValue(sceneGroupStr);
            }));
        }
        columns = columnList.toArray(new Column[0]);
        return columns;
    }

    /**
     * @return
     * @description provider获取表头
     * @author xuyunpeng
     * @date 2021/6/2 4:28 下午
     */
    public Column[] getProviderColumns() {
        Column[] columns = {
                new Column("退单编号", new SpelColumnRender<ReturnOrderVO>("id")),
                new Column("申请时间", new SpelColumnRender<ReturnOrderVO>("createTime")),
                new Column("完成时间", new SpelColumnRender<ReturnOrderVO>("finishTime != null ? finishTime : '-'")),
                new Column("订单编号", new SpelColumnRender<ReturnOrderVO>("tid")),
                new Column("子单编号", new SpelColumnRender<ReturnOrderVO>("ptid")),
                new Column("代销商家", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = ((ReturnOrderVO) object);
                    CompanyVO companyVO = returnOrderVO.getCompany();
                    String supplierName = StringUtils.isNotEmpty(companyVO.getSupplierName()) ?
                            companyVO.getSupplierName() : "";
                    String supplierCode = StringUtils.isNotEmpty(companyVO.getCompanyCode()) ?
                            companyVO.getCompanyCode() : "";
                    cell.setCellValue(supplierName + "  " + supplierCode);
                }),
                new Column("退货原因", new SpelColumnRender<ReturnOrderVO>("returnReason.getDesc()")),
                new Column("退货说明", new SpelColumnRender<ReturnOrderVO>("description")),
                new Column("退货方式", new SpelColumnRender<ReturnOrderVO>("returnWay.getDesc()")),
                new Column("商品名称", (cell,object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                    returnItemVOS.addAll(returnOrderVO.getReturnItems());
                    if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                        returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                    }

                    String skuName = returnItemVOS
                            .stream()
                            .map(ReturnItemVO::getSkuName)
                            .collect(Collectors.joining(";"));

                    cell.setCellValue(skuName);
                }),
                new Column("规格名称", (cell,object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                    returnItemVOS.addAll(returnOrderVO.getReturnItems());
                    if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                        returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                    }
                     String specDetails = returnItemVOS
                             .stream()
                             .map(ReturnItemVO::getSpecDetails)
                             .filter(Objects::nonNull)
                             .collect(Collectors.joining(";"));

                     cell.setCellValue(specDetails);
                }),
                new Column("退货商品SKU编码", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                    returnItemVOS.addAll(returnOrderVO.getReturnItems());
                    if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                        returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                    }

                    String skuNos = returnItemVOS
                            .stream()
                            .map(ReturnItemVO::getSkuNo)
                            .collect(Collectors.joining(";"));

                    cell.setCellValue(skuNos);
                }),
                new Column("退货商品种类", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                    returnItemVOS.addAll(returnOrderVO.getReturnItems());
                    if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                        returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                    }
                    cell.setCellValue(returnItemVOS.size());
                }),
                new Column("退货商品总数量", (cell, object) -> {
                    ReturnOrderVO returnOrderVO = (ReturnOrderVO) object;
                    List<ReturnItemVO> returnItemVOS = new ArrayList<>();
                    returnItemVOS.addAll(returnOrderVO.getReturnItems());
                    if(CollectionUtils.isNotEmpty(returnOrderVO.getReturnPreferential())){
                        returnItemVOS.addAll(returnOrderVO.getReturnPreferential());
                    }

                    Optional<Integer> size = returnItemVOS
                            .stream()
                            .map(ReturnItemVO::getNum)
                            .reduce((sum, item) -> {
                                sum += item;
                                return sum;
                            });


                    cell.setCellValue(size.orElse(NumberUtils.INTEGER_ZERO));
                }),
                new Column("应退金额", new SpelColumnRender<ReturnOrderVO>("returnPrice.providerTotalPrice")),
                new Column("退单状态", new SpelColumnRender<ReturnOrderVO>("transformReturnFlowState(returnFlowState)"))
        };
        return columns;
    }
}
