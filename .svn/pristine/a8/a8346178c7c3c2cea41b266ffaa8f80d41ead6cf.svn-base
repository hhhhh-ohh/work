package com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.repository;

import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.model.root.WechatCateCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>微信类目资质DAO</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
@Repository
public interface WechatCateCertificateRepository extends JpaRepository<WechatCateCertificate, Long>,
        JpaSpecificationExecutor<WechatCateCertificate> {

    @Modifying
    @Query("delete from WechatCateCertificate where cateId in ?1")
    int delByCateIds(List<Long> cateIds);
}
