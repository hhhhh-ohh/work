package com.wanmi.sbc.convert;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;

import lombok.Data;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanCopier;

import java.util.*;

public class KsBeanUtilTest {


    /**
     * 使用cglib的BeanCopier实现，不缓存BeanCopier
     */
    public static void copyPropertiesCglibNoCache(Object sourceObj, Object targetObj) {
        BeanCopier beanCopier = BeanCopier.create(sourceObj.getClass(),targetObj.getClass(),false);
        beanCopier.copy(sourceObj, targetObj, null);
    }

    private final int count = 100000;//转换次数

    @Test
    public void copyProperties() throws Exception{
        UserDTO dto = getDTO();
//        KsBeanUtil.copyPropertiesThird(dto,new UserVO());
        long startTime = System.currentTimeMillis();
        List<UserVO> vos = new ArrayList<UserVO>(count);
        for(int i = 0 ; i < count ; ++i){
            UserVO vo = new UserVO();
            KsBeanUtil.copyProperties(dto,vo);
            vos.add(vo);
        }
        System.out.println("copyProperties用时:"+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void copyPropertiesThird() throws Exception{
        UserDTO dto = getDTO();
//        KsBeanUtil.copyPropertiesThird(dto,new UserVO());
        long startTime = System.currentTimeMillis();
        List<UserVO> vos = new ArrayList<UserVO>(count);
        for(int i = 0 ; i < count ; ++i){
            UserVO vo = new UserVO();
            KsBeanUtil.copyPropertiesThird(dto,vo);
            vos.add(vo);
        }
        System.out.println("copyPropertiesThird用时:"+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void beanCopier()throws Exception{
        UserDTO dto = getDTO();
        BeanCopier beanCopier = BeanCopier.create(UserDTO.class,UserVO.class,false);
        long startTime = System.currentTimeMillis();
        List<UserVO> vos = new ArrayList<UserVO>(count);
        for(int i = 0 ; i < count ; ++i){
            //BeanCopier
            UserVO vo = new UserVO();
            beanCopier.copy(dto,vo,null);
            vos.add(vo);
        }
        System.out.println("beanCopier用时:"+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void copyPropertiesCglib() throws Exception{
        UserDTO dto = getDTO();
//        copyPropertiesCglibCache(dto,new UserVO());
        long startTime = System.currentTimeMillis();
        List<UserVO> vos = new ArrayList<UserVO>(count);
        for(int i = 0 ; i < count ; ++i){
            UserVO vo = KsBeanUtil.copyPropertiesCglib(dto,UserVO.class);
            vos.add(vo);
        }
        System.out.println("copyPropertiesCglib用时:"+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void copyPropertiesCglibNoCache() throws Exception{
        UserDTO dto = getDTO();
//        copyPropertiesCglib(dto,new UserVO());
        long startTime = System.currentTimeMillis();
        List<UserVO> vos = new ArrayList<UserVO>(count);
        for(int i = 0 ; i < count ; ++i){
            UserVO vo = new UserVO();
            copyPropertiesCglibNoCache(dto,vo);
            vos.add(vo);
        }
        System.out.println("copyPropertiesCglibNoCache用时:"+(System.currentTimeMillis() - startTime));
    }

    @Test
    public void testCglibCopyDiffType(){
        UserDTO dto = getDTO();
        System.out.println(JSON.toJSONString(KsBeanUtil.copyPropertiesCglib(dto,UserVO.class)));
        System.out.println(JSON.toJSONString(KsBeanUtil.copyPropertiesThird(dto,UserVO.class)));
    }

    @Test
    public void objectsToMaps(){
        long startTime = System.currentTimeMillis();
        List<UserDTO> userDTOList = Arrays.asList(getDTO(),getDTO(),getDTO());
        List<Map<String, Object>> mapList = KsBeanUtil.objectsToMaps(userDTOList);
        System.out.println(JSON.toJSONString(mapList));
        System.out.println("objectsToMaps:"+(System.currentTimeMillis() - startTime));
    }


    private UserDTO getDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("test");
        userDTO.setAge(18);
        userDTO.setAddress("china");
        userDTO.setSex(true);
        userDTO.setDeleteFlag(DeleteFlag.NO);
        return userDTO;
    }


    @Data
    public static class UserDTO {
        private String userName;
        private int age;
        private boolean sex; //性别:true = 男；false = 女；
        private String address ;
        private Object properties;//其他属性
        private DeleteFlag deleteFlag;
    }

    @Data
    public static class UserVO {
        private String userName;
        private int age;
        private Boolean sex; //性别:true = 男；false = 女；
        private String address ;
        private Object properties;//其他属性
        private Integer deleteFlag;
    }
}
