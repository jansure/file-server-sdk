package com.pkpmcloud.fileserver.pool;

import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.protocol.tracker.GetStorageNodeCommand;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 20:45 <br/>
 */
public class ConnectionManagerTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerTest.class);

    @Test
    public void test() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 1000);

        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(20);
        ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, conf);

        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("139.159.254.232:22122");

        DefaultCommandExecutor connectionManager = new DefaultCommandExecutor(trackerSet, connectionPool);

        connectionManager.dumpPoolInfo();

        GetStorageNodeCommand command = new GetStorageNodeCommand();
        StorageNode storageNode = connectionManager.execute(command);
        logger.info(storageNode.toString());
        System.out.println(storageNode.toString());

        connectionManager.dumpPoolInfo();

        connectionPool.close();
    }

    @Test
    public void test01() {
        InetSocketAddress address = new InetSocketAddress("158.36.25.2", 125);
        logger.info(address.getAddress() + ":" + address.getPort());
    }

    @Test
    public void test03() throws InterruptedException {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(5000, 5000);

        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, conf);

        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("192.168.10.128:22122");

        DefaultCommandExecutor connectionManager = new DefaultCommandExecutor(trackerSet, connectionPool);

        for (int i = 0; i <= 50; i++) {
            Thread thread = new PoolTest(connectionManager);
            thread.start();
        }

        for (int i = 0; i <= 2; i++) {
            connectionManager.dumpPoolInfo();
            Thread.sleep(1000 * 2);
        }

        connectionPool.close();
    }

    /**
     * 多线程测试
     */
    private class PoolTest extends Thread {
        private DefaultCommandExecutor connectionManager;

        PoolTest(DefaultCommandExecutor connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            GetStorageNodeCommand command = new GetStorageNodeCommand();
            StorageNode storageNode = connectionManager.execute(command);
            logger.info("################ " + storageNode.toString());
            connectionManager.dumpPoolInfo();
        }
    }
}
