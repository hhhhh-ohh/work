package com.wanmi.sbc.goods.util.mapper;

import com.wanmi.sbc.goods.goodsaudit.request.GoodsAuditSaveRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

/**
 * @author 黄昭
 * @className GoodsAuditMapper
 * @description TODO
 * @date 2021/12/21 11:05
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsSaveRequestMapper {

    GoodsSaveRequest auditRequestToGoodsRequest(GoodsAuditSaveRequest request);
}