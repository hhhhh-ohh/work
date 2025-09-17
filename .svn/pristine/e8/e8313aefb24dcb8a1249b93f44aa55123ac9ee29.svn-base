package com.wanmi.sbc.mongo.oplog.context;

import com.mongodb.MongoException;
import com.mongodb.MongoInterruptedException;
import com.mongodb.client.MongoCursor;
import com.wanmi.sbc.mongo.oplog.comm.AbstractMongoCaptureLifeCycle;
import com.wanmi.sbc.mongo.oplog.context.handler.*;
import com.wanmi.sbc.mongo.oplog.data.Meta;
import com.wanmi.sbc.mongo.oplog.data.OplogData;
import com.wanmi.sbc.mongo.oplog.data.Parameter;
import com.wanmi.sbc.mongo.oplog.zookeeper.ZkClientx;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.bson.BsonTimestamp;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 获取oplog执行器
 * @Author: ZhangLingKe
 * @CreateDate: 2019/8/14 17:33
 */
@Slf4j
@Component
public class MongoCaptureAdapter extends AbstractMongoCaptureLifeCycle {

    @Autowired
    private OplogPullHandler oplogCursorHandler;

    @Autowired
    private OplogUnUseDataFilterHandler oplogUnUseDataFilterHandler;

    @Autowired
    private OplogRegexFilterHandler oplogRegexFilterHandler;

    @Autowired
    private OplogParserHandler oplogParserHandler;

    @Autowired
    private OplogSendHandler oplogSendHandler;

    @Autowired
    private OplogPositionHandler oplogPositionHandler;



    private Thread thread;
    private ZkClientx zkClientx;

    public MongoCaptureAdapter() {

    }

    @Override
    public void start() {
        this.oplogPositionHandler.setZkClientx(this.zkClientx);
        this.oplogPositionHandler.start();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        thread.start();
        log.info("*************start successful****************");
    }

    @Override
    public void stop() {
        if (thread != null) {
            try {
                thread.interrupt();
             //   thread.join();
            } catch (MongoInterruptedException e) {
                log.info("mongo connection pool stop");
            } catch (Exception e) {
                log.info("process thread stop");
            }

        }
        if (oplogPositionHandler != null) {
            oplogPositionHandler.stop();
        }

        log.info("*************stop successful****************");
    }

    public void clearMemoryPosition() {
        this.oplogPositionHandler.getPositionManager().removeCursor();
    }

    /**
     * 主线程
     */
    public void process() {
        Meta meta = oplogPositionHandler.getPositionManager().getCursor();
        while (!thread.isInterrupted()) {
            try {

                if (meta == null) {
                    meta = new Meta(oplogPositionHandler.getClientConfig().getMetaMode().name()
                            , Parameter.getPositionPath(oplogPositionHandler.getClientConfig().getDestination()));
                    meta.setPositionByBson(oplogCursorHandler.getTimestamp());
                }
                if (meta.getPosition() == null) {
                    meta.setPositionByBson(oplogCursorHandler.getTimestamp());
                }
                if (meta.getClusterName() == null) {
                    meta.setClusterName(oplogPositionHandler.getClientConfig().getClusterName());
                }
                if (meta.getModel() == null) {
                    meta.setModel(oplogPositionHandler.getClientConfig().getMetaMode().name());
                }

                // 获取 Oplog 数据
                MongoCursor<Document> dbCursor = oplogCursorHandler.oplogCursorByPosition(meta.getPosition());

                while (dbCursor.hasNext()) {
                    Document dbObject = dbCursor.next();
                    // 过滤 Oplog 数据
                    if (!oplogUnUseDataFilterHandler.shouldSkip(dbObject)) {
                        List<Map<String, Object>> list = new ArrayList<>();
                        Map<String, Object> map = dbObject;

                        if (oplogUnUseDataFilterHandler.isTransaction(map)) {
                            Document basicDBObject = (Document) map.get("o");
                            list = (List) basicDBObject.get("applyOps");

                        } else {
                            list.add(map);
                        }
                        if (CollectionUtils.isNotEmpty(list)) {
                            for (Map<String, Object> temp : list) {
                                String ns = (String) temp.get("ns");
                                if (oplogRegexFilterHandler.filter(ns)) {

                                    // 解析 Oplog 数据
                                    OplogData oplogData = oplogParserHandler.process(temp);

                                    // 使用解析后的数据
                                    oplogSendHandler.send(oplogData);
                                }
                            }
                        }
                    }
                    // 更新时间
                    meta.setPositionByBson((BsonTimestamp) dbObject.get("ts"));
                   // oplogPositionHandler.getPositionManager().updateCursor(meta);
                }
                oplogPositionHandler.getPositionManager().updateCursor(meta);

                TimeUnit.MILLISECONDS.sleep(500);
//                log.info("休眠");
            } catch (MongoException e) {
                log.error(e.getMessage());
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (Exception e1) {
                    log.error("process sleep error:", e);
                }
                continue;

            } catch (Throwable t) {
                log.error("process error", t);
                System.exit(0);
                break;
            }
        }
    }



    public void setZkClientx(ZkClientx zkClientx) {
        this.zkClientx = zkClientx;
    }
}
