package com.wanmi.sbc.goods.spec.repository.goods;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.info.model.root.PluginTest;
import com.wanmi.sbc.goods.info.repository.PluginTestRespository;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zhanggaolei
 * @className PluginListTest
 * @description
 * @date 2022/10/18 15:58
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PluginListTest {

    @Resource
    private PluginTestRespository pluginTestRespository;

    @Test
    public void saveTest(){
        PluginTest pluginTest = new PluginTest();
        pluginTest.setName("test003");
//        List<PluginType> pluginTypes = new ArrayList<>();
//        pluginTypes.add(PluginType.O2O);
//        pluginTypes.add(PluginType.CROSS_BORDER);
//        pluginTest.setPluginTypes(pluginTypes);
        pluginTestRespository.save(pluginTest);
    }

    @Test
    public void getTest(){

        List<PluginTest> pluginTest = pluginTestRespository.findAll();

        System.out.println(pluginTest.get(0).getName());
        PluginType pluginType = pluginTest.get(0).getPluginTypes().get(0);
        System.out.println(pluginType);
    }
}
