package com.wanmi.sbc.tcc;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.tcc.TccProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchMinusStockRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchPlusStockRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoDeleteByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.BranchType;
import io.seata.rm.DefaultResourceManager;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className TccTestController
 * @description
 * @date 2022/6/28 17:59
 */
@RestController
@Validated
public class TccTestController {

    @Autowired TccProvider tccProvider;
    @Autowired GoodsInfoProvider goodsInfoProvider;

//    @GlobalTransactional
//    @PostMapping("/test/tcc")
//    public BaseResponse tcc(@RequestBody GoodsInfoRequest request) {
//        goodsInfoProvider.deleteByIds(
//                GoodsInfoDeleteByIdsRequest.builder()
//                        .goodsInfoIds(Arrays.asList(request.getGoodsInfoId()))
//                        .build());
//
//        BaseResponse baseResponse = tccProvider.insertTcc(request);
//        int i = 10 / 0;
//        return baseResponse;
//    }
//
//    @GlobalTransactional
//    @PostMapping("/test/tcc/sub-stock")
//    public BaseResponse subStock(@RequestBody GoodsInfoBatchMinusStockRequest request) throws TransactionException {
//        goodsInfoProvider.deleteByIds(
//                GoodsInfoDeleteByIdsRequest.builder()
//                        .goodsInfoIds(
//                                request.getStockList().stream()
//                                        .map(GoodsInfoMinusStockDTO::getGoodsInfoId)
//                                        .collect(Collectors.toList()))
//                        .build());
//
//        BaseResponse baseResponse =
//                goodsInfoProvider.batchPlusStockTcc(
//                        GoodsInfoBatchPlusStockRequest.builder()
//                                .stockList(
//                                        KsBeanUtil.convertList(
//                                                request.getStockList(),
//                                                GoodsInfoPlusStockDTO.class))
//                                .build());
//        int i = 10 / 0;
//        DefaultResourceManager.get().branchRegister(BranchType.TCC,"",null, RootContext.getXID(),null,null);
//        return baseResponse;
//    }
}
