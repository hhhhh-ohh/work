package com.wanmi.sbc.setting.platformaddress.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.platformaddress.model.root.PlatformAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>平台地址信息DAO</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Repository
public interface PlatformAddressRepository extends JpaRepository<PlatformAddress, String>,
        JpaSpecificationExecutor<PlatformAddress> {

    /**
     * 单个删除平台地址信息
     * @author dyt
     */
    @Modifying
    @Query("update PlatformAddress set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, deleteTime = now() where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除平台地址信息
     * @author dyt
     */
    @Modifying
    @Query("update PlatformAddress set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, deleteTime = now() where id in ?1")
    void deleteByIdList(List<String> idList);

    Optional<PlatformAddress> findByIdAndDelFlag(String id, DeleteFlag delFlag);

    @Query(value = "select * from platform_address c where c.addr_name = ?1 and c.addr_parent_id" +
            " = ?2 and c.del_flag = 0", nativeQuery = true)
    PlatformAddress findByEqual(String addrName, String addrParentId);
    @Query(value = "select * from platform_address c where c.addr_name = ?1 " +
            " and c.del_flag = 0", nativeQuery = true)
    PlatformAddress findByEqualAddrName(String addrName);
    @Query(value = "select * from platform_address c where c.addr_name like CONCAT('%',?1, '%') and c.addr_parent_id" +
            " = ?2 and c.del_flag = 0", nativeQuery = true)
    List<PlatformAddress> findByLike(String addrName, String addrParentId);

    @Query("from PlatformAddress p where p.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and p.addrId in ?1 or p.addrParentId in ?1")
    List<PlatformAddress> findAllProvinceAndCity(List<String> addrIds);

    /**
     * 统计数量
     */
    @Query("select count(1) from PlatformAddress p where p.delFlag=com.wanmi.sbc.common.enums.DeleteFlag.NO and p.id = :id")
    int countById(@Param("id") String id);

    /**
     * 统计数量
     */
    @Query("select count(1) from PlatformAddress p where p.delFlag=com.wanmi.sbc.common.enums.DeleteFlag.NO and p.id = :city and p.addrParentId = :province or p.id = :area and p.addrParentId =:city")
    int countByIdAndParentId(@Param("province") String province,@Param("city") String city,@Param("area") String area);

    /**
     * 判定节点是否为父节点
     */
    @Query("select count(1) from PlatformAddress p where p.addrParentId='0' and p.addrId=:addId")
    int isAreaProvince(@Param("addId") String addId);

    /**
     * 修改地址
     * @param id
     */
    @Modifying
    @Query(value = "update platform_address set " +
            "id = ?1," +
            "addr_id = ?1 ,  " +
            "addr_name = ?2 ,  " +
            "pin_yin = ?3   " +
            "where addr_id = ?4"  ,nativeQuery = true)
    void modifyPlatformAddress(String id,String addrName,String pinyin,String oldId);
}
