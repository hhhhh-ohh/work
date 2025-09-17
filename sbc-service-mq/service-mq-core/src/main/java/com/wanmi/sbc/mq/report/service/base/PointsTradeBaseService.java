package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradeListExportRequest;
import com.wanmi.sbc.order.api.request.trade.FindProviderTradeRequest;
import com.wanmi.sbc.order.bean.dto.DisabledDTO;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @return
 * @description 积分订单导出
 * @author shy
 * @date 2022/7/21 9:53 上午
 */
@Service
@Slf4j
public class PointsTradeBaseService {

    @Autowired
    private PointsTradeQueryProvider pointsTradeQueryProvider;

    @Autowired
    private ProviderTradeProvider providerTradeProvider;


    @ReturnSensitiveWords(functionName = "f_boss_points_trade_export_sign_word")
    public List<PointsTradeVO> getPointsTrade(Operator operator, PointsTradeListExportRequest exportRequest, DisabledDTO disabledDTO) {
        List<PointsTradeVO> pointsTrades = pointsTradeQueryProvider.listPointsTradeExport(exportRequest).getContext().getPointsTradeVOList();
        //按下单时间降序排列
        Comparator<PointsTradeVO> c = Comparator.comparing(a -> a.getTradeState().getCreateTime());
        pointsTrades = pointsTrades.stream().sorted(
                c.reversed()
        ).collect(Collectors.toList());

        List<PointsTradeVO> pointsTradesNew = new ArrayList<>();
        if (disabledDTO.getDisabled().equals("true")) {
            //只导出子订单
            List<String> parentIdList = pointsTrades.stream().map(PointsTradeVO::getId).collect(Collectors.toList());
            List<TradeVO> tradeVOList = providerTradeProvider.findByParentIdList(FindProviderTradeRequest.builder().parentId(parentIdList).build()).getContext().getTradeVOList();
            pointsTrades = KsBeanUtil.convert(tradeVOList, PointsTradeVO.class);
        }

        pointsTrades.forEach(tradeVO -> {
            List<TradeItemVO> tradeItems = tradeVO.getTradeItems();
            tradeItems.forEach(tradeItemVO -> {
                PointsTradeVO tradeVONew = new PointsTradeVO();
                KsBeanUtil.copyProperties(tradeVO, tradeVONew);
                List<TradeItemVO> tradeItemsNew = new ArrayList<TradeItemVO>();
                tradeItemsNew.add(tradeItemVO);
                tradeVONew.setTradeItems(tradeItemsNew);
                pointsTradesNew.add(tradeVONew);
            });
        });
        return pointsTradesNew;
    }

}
