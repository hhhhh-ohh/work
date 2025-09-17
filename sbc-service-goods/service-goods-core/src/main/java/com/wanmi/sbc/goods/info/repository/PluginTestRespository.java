package com.wanmi.sbc.goods.info.repository;

import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.PluginTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginTestRespository extends JpaRepository<PluginTest, Long>, JpaSpecificationExecutor<PluginTest> {
}
