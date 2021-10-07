package com.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
//修改一下
//我再修改一下
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
         *      int sessionTimeoutMs：会话Session的超时时间(单位秒)，3000
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

    //修改节点
    @Test
    public  void updateNode() throws Exception{
        //定义重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 3000, retryPolicy);
        //开启
        client.start();
        //修改节点
        client.setData().forPath("/bb","haha".getBytes());
        //关闭
        client.close();
    }
    //查询节点
    @Test
    public void queryNode() throws Exception{
        //定义重试策略
        RetryPolicy retryPolicy =new ExponentialBackoffRetry(3000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 3000, retryPolicy);
        //开启
        client.start();
        //查询节点
        byte[] bytes = client.getData().forPath("/ff/f_1");
        //转成字符串格式打印出来
        System.out.println(new String(bytes));
        //关闭
        client.close();
    }

    //删除节点
    @Test
    public void deleteNode() throws Exception{
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(3000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 3, retryPolicy);
        //开启
        client.start();
        //1.删除没有子的节点
       // client.delete().forPath("/a");
        //2.删除有子的节点(.deletingChildrenIfNeeded():表示递归删除根节点下对应的子节点)
        //client.delete().deletingChildrenIfNeeded().forPath("/ff");
        /**
         * .guaranteed()：
         *  1.强制保证删除一个节点
         *  2.只要客户端会话有效,那么Curator会再后台持续进行删除操作，知道节点删除成功/
         *  3。比如遇到一些网络异常的情况，此guaranteed的强制删除就会很有效。
         */
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/cc");
        //关闭
        client.close();
    }
}