package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goods.GoodsDeleteByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.api.response.goods.GoodsDeleteResponse;
import com.wanmi.sbc.goods.bean.enums.AuditType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsDeleteVO;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.suppliercommissiongoods.service.SupplierCommissionGoodService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/***jdVopMessageJobHandlerjdVopMessageJobHandler
 * 商品基础数据维护Service
 * @className GoodsBaseService
 * @author zhengyang
 * @date 2021/7/6 14:54
 **/
@Slf4j
@Service
@Primary
public class GoodsBaseService extends AbstractGoodsBaseService implements GoodsBaseInterface {

    @Autowired private SupplierCommissionGoodService supplierCommissionGoodService;

    /**
     * 商品新增
     *
     * @param saveRequest
     * @return SPU编号
     * @throws SbcRuntimeException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsAddResponse add(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        return super.add(saveRequest);
    }

    /***
     * 商品删除
     * @param request                   删除请求
     * @return                          删除商品对象
     * @throws SbcRuntimeException      报错信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsDeleteResponse delete(GoodsDeleteByIdsRequest request) throws SbcRuntimeException {
        final List<String> goodsIds = request.getGoodsIds();
        List<GoodsDeleteVO> goodsList = KsBeanUtil.convert(goodsRepository
                .findByGoodsIdInAndStoreIdAndDelFlag(goodsIds, request.getStoreId(), DeleteFlag.NO), GoodsDeleteVO.class);
        GoodsDeleteResponse deleteResponse = GoodsDeleteResponse.builder().goodsDeleteVOList(goodsList).build();
        if(CollectionUtils.isEmpty(goodsList) || goodsList.size() != goodsIds.size()){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        goodsRepository.deleteByGoodsIds(goodsIds);
        goodsInfoRepository.deleteByGoodsIds(goodsIds);
        goodsPropDetailRelRepository.deleteByGoodsIds(goodsIds);
        goodsSpecRepository.deleteByGoodsIds(goodsIds);
        goodsSpecDetailRepository.deleteByGoodsIds(goodsIds);
        goodsInfoSpecDetailRelRepository.deleteByGoodsIds(goodsIds);
        // 删除商品与商品库关系,先查询关联的商品库用于ES处理
        deleteResponse.setStandardIds(standardGoodsService.getStandardGoodsIdByGoodsId(goodsIds));
        standardGoodsRelRepository.deleteByGoodsIds(goodsIds);
        pointsGoodsRepository.deleteByGoodsIdList(goodsIds);
        goodsIds.forEach(goodsId->{
            distributiorGoodsInfoRepository.deleteByGoodsId(goodsId);
        });
        goodsPropertyDetailRelRepository.deleteByGoodsId(goodsIds);
        //处理代销关联关系
        supplierCommissionGoodService.delCommissionList(goodsList);
        return deleteResponse;
    }

    /***
     * 保存商品主数据
     * @param goods     商品对象
     */
    @Override
    protected GoodsSaveDTO saveMainGoods(GoodsSaveDTO goods) {
        // 待审核商品进goods_audit库
        if (Objects.equals(CheckStatus.WAIT_CHECK, goods.getAuditStatus())) {
            GoodsAudit audit = KsBeanUtil.convert(goods, GoodsAudit.class);
            audit.setAuditType(AuditType.INITIAL_AUDIT.toValue());
            goods.setGoodsId(goodsAuditRepository.save(audit).getGoodsId());
        } else {
            goods.setGoodsId(goodsRepository.save(KsBeanUtil.copyPropertiesThird(goods, Goods.class)).getGoodsId());
        }
        return goods;
    }
}
