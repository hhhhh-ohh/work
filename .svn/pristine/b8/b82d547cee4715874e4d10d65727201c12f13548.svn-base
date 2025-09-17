package com.wanmi.sbc.goods.util.mapper;

import com.wanmi.sbc.goods.bean.vo.GoodsAuditSaveVO;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author 黄昭
 * @className GoodsAuditMapper
 * @description TODO
 * @date 2021/12/21 11:05
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsAuditMapper {

    List<GoodsAuditVO> goodsAuditToVoList(List<GoodsAudit> goodsAudits);

    List<GoodsAuditVO> goodsAuditSaveToVoList(List<GoodsAuditSaveVO> goodsAudits);
}