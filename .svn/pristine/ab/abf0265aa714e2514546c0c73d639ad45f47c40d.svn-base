package com.wanmi.sbc.goods.xsitegoodscate.service;

import com.wanmi.sbc.goods.xsitegoodscate.model.root.XsiteGoodsCate;
import com.wanmi.sbc.goods.xsitegoodscate.repository.XsiteGoodsCateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>魔方商品分类表业务逻辑</p>
 *
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@Service("XsiteGoodsCateService")
public class XsiteGoodsCateService {

    @Autowired
    private XsiteGoodsCateRepository xsiteGoodsCateRepository;

    /**
     * 新增魔方商品分类
     *
     * @author xufeng
     */
    @Transactional
    public void saveAll(List<XsiteGoodsCate> entitys) {
        xsiteGoodsCateRepository.saveAll(entitys);
    }

    /**
     * 根据pageCode查询数量
     *
     * @author xufeng
     */
    public int findCountByPageCode(String pageCode) {
        return xsiteGoodsCateRepository.findCountByPageCode(pageCode);
    }

    /**
     * 根据pageCode删除
     *
     * @author xufeng
     */
    @Transactional(rollbackFor = {Exception.class})
    public int delAllByPageCode(String pageCode) {
        return xsiteGoodsCateRepository.delAllByPageCode(pageCode);
    }

    /**
     * 根据cateUuid查询数据
     *
     * @author xufeng
     */
    public XsiteGoodsCate findByCateUuid(String cateUuid) {
        return xsiteGoodsCateRepository.findByCateUuid(cateUuid);
    }

}
