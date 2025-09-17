package com.wanmi.sbc.order.util.mapper;

import com.wanmi.sbc.customer.bean.vo.MiniStoreVO;
import com.wanmi.sbc.customer.bean.vo.StoreCacheVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 */
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface StoreMapper {

    @Mappings({})
    List<MiniStoreVO> storeVOsToMiniStoreVOs(List<StoreVO> bean);
}
