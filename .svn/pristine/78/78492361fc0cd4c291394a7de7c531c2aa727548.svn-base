package com.wanmi.sbc.mongo.oplog.position;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Sets;
import com.wanmi.sbc.mongo.oplog.data.Meta;
import com.wanmi.sbc.mongo.oplog.zookeeper.ZkClientx;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: zhanggaolei
 * \* @date: 2019-12-13
 * \* @time: 17:45
 * \* To change this template use File | Settings | File Templates.
 * \* @description:
 * \ zookeeper存储定位信息
 */
public class ZookeeperPositionManager extends MemoryPositionManager implements PositionManager {

    private static final Logger log = LoggerFactory.getLogger(ZookeeperPositionManager.class);
    private ZkClientx zkClientx;
    private ScheduledExecutorService executor;
    private Set<String> updateSet;

    @Override
    public void start() {
        super.start();
        Assert.notNull(zkClientx);
        if(super.getCursor() == null){
            super.updateCursor(loadDataFromZookeeper());
        }
        updateSet = Sets.newConcurrentHashSet();
        executor = Executors.newScheduledThreadPool(1);
        // 启动定时工作任务
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        if(CollectionUtils.isNotEmpty(updateSet)) {
                            Meta position = ZookeeperPositionManager.super.getCursor();
                            try {

                                if (!Objects.isNull(position)) {
                                    flushDataToZookeeper(position);
                                }
                            } catch (Throwable e) {
                                // ignore
                                log.error("period update" + JSONObject.toJSONString(position) + " curosr failed!", e);
                            }
                            updateSet.remove(destination);
                        }
                    }
                },
                period,
                period,
                TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        executor.shutdown();
        flushDataToZookeeper(getCursor());
        super.stop();
    }
    @Override
    public Meta getCursor() {
        Meta position = super.getCursor();
        if(position==null || position.getPosition()==null) {
            position = loadDataFromZookeeper();
        }

        return position;
    }

    @Override
    public void updateCursor(Meta meta) {
        updateSet.add(destination);
        super.updateCursor(meta);
    }

    public void setZkClientx(ZkClientx zkClientx) {
        this.zkClientx = zkClientx;
    }

    public Meta loadDataFromZookeeper(){
        Meta position = new Meta();
        try{
            byte[] data = zkClientx.readData(destination);
            if (data == null || data.length == 0) {
                return null;
            }

            return Meta.jsonToBean(data);
        }catch (ZkNoNodeException e) {
            zkClientx.createPersistent(destination,
                    true); // 尝试创建父节点
            loadDataFromZookeeper();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return position;
    }
    public void flushDataToZookeeper(Meta position){
        byte[] data = JSONObject.toJSONString(position).getBytes();
        try {
            zkClientx.writeData(destination, data);
        } catch (ZkNoNodeException e) {
            zkClientx.createPersistent(destination, data, true);// 第一次节点不存在，则尝试重建
        }
    }

    @Override
    public void removeCursor() {
        super.removeCursor();
    }





}
