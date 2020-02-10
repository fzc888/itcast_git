package com.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

public class TestZookeeper1 {
    //创建节点
    @Test
    public void createNode() throws Exception {
        //定义重试策略
        /**
         * ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries)
         *      int baseSleepTimeMs：每隔多长时间连接一次(单位秒),3000表示每3秒连接一次
         *      int maxRetries：重试次数，3表示重试3次，如果3次没有连接上，放弃了
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);
        /**
         * newClient(String connectString, int sessionTimeoutMs, int connectionTimeoutMs, RetryPolicy retryPolicy)
         *      String connectString：连接Zookeeper的地址：127.0.0.1:2181
         *      int sessionTimeoutMs：绘画Session的超时时间(单位秒)，3000
         *      int connectionTimeoutMs：连接的超时时间(单位秒)，3000
         *      RetryPolicy retryPolicy：重试策略
         */
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 3000, retryPolicy);
        //开启
        client.start();
        //1. 创建一个空节点(a)（只能创建一层节点）
        //client.create().forPath("/a");

        // 2. 创建一个有内容的b节点（只能创建一层节点）
        //client.create().forPath("/bb","卢本伟牛逼".getBytes());

        //3. 创建多层节点
        //client.create().creatingParentContainersIfNeeded().forPath("/ff/f_1","abcd".getBytes());

        //4. 创建带有的序号的节点
        /**
         *     PERSISTENT(0, false, false):持久节点(默认)
         *     PERSISTENT_SEQUENTIAL(2, false, true):持久带序号
         *     EPHEMERAL(1, true, false):临时节点
         *     EPHEMERAL_SEQUENTIAL(3, true, true):临时节点带序号
         */
        //client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/ee","eeee".getBytes());

        //5.1 创建临时节点（客户端关闭，节点消失），设置延时5秒关闭（Thread.sleep(5000)）
        // client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/fff","ffff".getBytes());
        //6. 创建临时带序号节点（客户端关闭，节点消失），设置延时5秒关闭（Thread.sleep(5000)）
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/cc/c_1","cccc".getBytes());

        //5.2设置延时5秒关闭（Thread.sleep(5000)）
        Thread.sleep(10000);

        //关闭
        client.close();
    }
}
