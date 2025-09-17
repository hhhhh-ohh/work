package com.wanmi.sbc.marketing.bargain.repository;

import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinBargainGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>砍价DAO</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Repository
public interface BargainJoinBargainGoodsRepository extends JpaRepository<BargainJoinBargainGoods, Long>,
        JpaSpecificationExecutor<BargainJoinBargainGoods> {

    @Override
    @EntityGraph(value = "bargainGoods")
    Page<BargainJoinBargainGoods> findAll(Specification<BargainJoinBargainGoods> specification, Pageable pageable);

    @Override
    @EntityGraph(value = "bargainGoods")
    List<BargainJoinBargainGoods> findAll(Specification<BargainJoinBargainGoods> spec, Sort sort);

    @Override
    @EntityGraph(value = "bargainGoods")
    List<BargainJoinBargainGoods> findAll(Specification<BargainJoinBargainGoods> spec);


}
