package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.SettlementGetByIdRequest;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.SettlementVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.SpanColumn;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailQueryProvider;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailListByIdsRequest;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailListBySettleUuidRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementDetailListBySettleUuidResponse;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailViewVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailViewVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className SettlementDetailExportService
 * @description 结算明细导出
 * @date 2021/6/4 3:53 下午
 */
@Service
@Slf4j
public class LakalaSettlementDetailExportService implements ExportBaseService {

    @Autowired private YunServiceProvider yunServiceProvider;

    @Autowired private SettlementQueryProvider settlementQueryProvider;

    @Autowired private SettlementDetailQueryProvider settlementDetailQueryProvider;

    @Autowired private OsdService osdService;

    @Autowired private ExportUtilService exportUtilService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("settlementDetail export begin, param:{}", data);
        JSONObject json = JSON.parseObject(data.getParam());
        String settleId = json.getString("settleId");
        JSONArray jsonArray = json.getJSONArray("ids");
        List<String> ids = jsonArray.toJavaList(String.class);
        LakalaSettlementVO settlement =
                settlementQueryProvider
                        .getLakalaById(
                                SettlementGetByIdRequest.builder()
                                        .settleId(Long.valueOf(settleId))
                                        .build())
                        .getContext();
        if (settlement == null) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
        String fileName =
                String.format(
                        "拉卡拉结算明细_%s-%s.xls",
                        settlement.getStartTime(),
                        settlement.getEndTime() + exportUtilService.getRandomNum());
        String resourceKey =
                String.format("lakalaSettlementDetail%s/%s", settlement.getSettleId(), fileName);

        // 查询文件是否已经存在
        byte[] content =
                yunServiceProvider
                        .getFile(YunGetResourceRequest.builder().resourceKey(resourceKey).build())
                        .getContext()
                        .getContent();
        if (content != null) {
            return BaseResponse.success(resourceKey);
        }

        // 导入excel
        ExcelHelper excelHelper = new ExcelHelper();
        SpanColumn[] columns =
                data.getPlatform() == Platform.PROVIDER
                        ? this.getPrividerColumns()
                        : this.getColumns();

        List<LakalaSettlementDetailViewVO> viewList = this.getData(ids);
        //支付方式前端写死拉卡拉，后端导出保持一致
        excelHelper.addSheet("拉卡拉结算明细", columns, viewList.parallelStream()
                .peek(LakalaSettlementDetailViewVO -> LakalaSettlementDetailViewVO.setPayWayValue("拉卡拉"))
                .collect(Collectors.toList()), "goodsViewList");

        // 上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.write(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        outputStream.close();

        return BaseResponse.success(resourceKey);
    }

    /**
     * @description supplier/boss 获取表头
     * @author xuyunpeng
     * @date 2021/6/4 4:06 下午
     * @return
     */
    public SpanColumn[] getColumns() {
        List<SpanColumn> spanColumnList = new ArrayList<>();
        spanColumnList.add(new SpanColumn("序号", "index", null));
        spanColumnList.add(new SpanColumn("订单下单时间", "tradeCreateTime", null));
        spanColumnList.add(new SpanColumn("订单编号", "tradeCode", null));
        spanColumnList.add(new SpanColumn("分账状态", "lakalaLedgerStatusStr", null));
        spanColumnList.add(new SpanColumn("订单类型", "orderType", null));
        spanColumnList.add(new SpanColumn("商品编码", "goodsViewList", "skuNo"));
        spanColumnList.add(new SpanColumn("名称", "goodsViewList", "goodsName"));
        spanColumnList.add(new SpanColumn("规格", "goodsViewList", "specDetails"));
        spanColumnList.add(new SpanColumn("所属类目", "goodsViewList", "cateName"));
        spanColumnList.add(new SpanColumn("商品单价", "goodsViewList", "goodsPrice"));
        spanColumnList.add(new SpanColumn("数量", "goodsViewList", "num"));
        spanColumnList.add(new SpanColumn("满减优惠", "goodsViewList", "reductionPrice"));
        spanColumnList.add(new SpanColumn("满折优惠", "goodsViewList", "discountPrice"));
        spanColumnList.add(new SpanColumn("店铺券优惠", "goodsViewList", "storeCouponPrice"));
        spanColumnList.add(new SpanColumn("店铺运费券抵扣", "freightCouponPrice", null));
        spanColumnList.add(new SpanColumn("通用券优惠", "goodsViewList", "commonCouponPriceString"));
        spanColumnList.add(new SpanColumn("礼品卡抵扣", "goodsViewList", "giftCardPrice"));
        spanColumnList.add(new SpanColumn("积分抵扣", "goodsViewList", "pointPrice"));
        spanColumnList.add(new SpanColumn("积分数量", "goodsViewList", "points"));
        spanColumnList.add(new SpanColumn("订单改价差额", "goodsViewList", "specialPrice"));
        spanColumnList.add(new SpanColumn("商品实付金额", "goodsViewList", "splitPayPrice"));
        spanColumnList.add(new SpanColumn("支付方式", "payWayValue", null));
        spanColumnList.add(new SpanColumn("供应商", "goodsViewList", "providerName"));
        spanColumnList.add(new SpanColumn("供货金额", "goodsViewList", "providerPrice"));
        spanColumnList.add(new SpanColumn("供货运费", "thirdPlatFormFreight", null));
        spanColumnList.add(new SpanColumn("供应商退单改价差额", "providerReturnSpecialPrice", null));
        spanColumnList.add(new SpanColumn("供应商应收金额", "providerDeservePrice", null));
        spanColumnList.add(new SpanColumn("类目扣率", "goodsViewList", "cateRate"));
        spanColumnList.add(new SpanColumn("平台应收金额", "goodsViewList", "platformPriceString"));
        spanColumnList.add(new SpanColumn("分销员", "distributorName", "null"));
        spanColumnList.add(new SpanColumn("分销员应收金额", "goodsViewList", "commission"));
        spanColumnList.add(new SpanColumn("运费", "deliveryPrice", null));
        spanColumnList.add(new SpanColumn("退单改价差额", "returnSpecialPrice", null));
        spanColumnList.add(new SpanColumn("拉卡拉费率", "lakalaRate", null));
        spanColumnList.add(new SpanColumn("拉卡拉手续费", "lakalaHandlingFee", null));
        spanColumnList.add(new SpanColumn("店铺应收金额", "storePrice", null));
        return spanColumnList.toArray(new SpanColumn[spanColumnList.size()]);
    }

    public SpanColumn[] getPrividerColumns() {
        List<SpanColumn> spanColumnList = new ArrayList<>();
        spanColumnList.add(new SpanColumn("序号", "index", null));
        spanColumnList.add(new SpanColumn("订单下单时间", "tradeCreateTime", null));
        spanColumnList.add(new SpanColumn("订单编号", "tradeCode", null));
        spanColumnList.add(new SpanColumn("分账状态", "lakalaLedgerStatusStr", null));
        spanColumnList.add(new SpanColumn("订单类型", "orderType", null));
        spanColumnList.add(new SpanColumn("商品编码", "goodsViewList", "skuNo"));
        spanColumnList.add(new SpanColumn("名称", "goodsViewList", "goodsName"));
        spanColumnList.add(new SpanColumn("规格", "goodsViewList", "specDetails"));
        spanColumnList.add(new SpanColumn("所属类目", "goodsViewList", "cateName"));
        spanColumnList.add(new SpanColumn("供货单价", "goodsViewList", "supplyPrice"));
        spanColumnList.add(new SpanColumn("数量", "goodsViewList", "num"));
        spanColumnList.add(new SpanColumn("供货金额", "goodsViewList", "providerPrice"));
        spanColumnList.add(new SpanColumn("供货运费", "thirdPlatFormFreight", null));
        spanColumnList.add(new SpanColumn("供应商退单改价差额", "providerReturnSpecialPrice", null));
        spanColumnList.add(new SpanColumn("应收金额", "providerDeservePrice", null));
        return spanColumnList.toArray(new SpanColumn[spanColumnList.size()]);
    }

    /**
     * 导出结算明细
     *
     * @param ids
     */
    private List<LakalaSettlementDetailViewVO> getData(List<String> ids) {
        BaseResponse<SettlementDetailListBySettleUuidResponse> baseResponse =
                settlementDetailQueryProvider.listByLakalaIds(
                        SettlementDetailListByIdsRequest.builder().ids(ids).build());
        SettlementDetailListBySettleUuidResponse settlementDetailListBySettleUuidResponse =
                baseResponse.getContext();
        if (Objects.isNull(settlementDetailListBySettleUuidResponse)) {
            return new ArrayList<>();
        }
        List<LakalaSettlementDetailVO> settlementDetailVOList =
                settlementDetailListBySettleUuidResponse.getLakalaSettlementDetailVOList();

        return LakalaSettlementDetailViewVO.renderSettlementDetailForView(
                settlementDetailVOList, false);
    }
}
