package com.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class TestZookeeper2 {
    //TreeCache
    @Test
    public void testTreeCache() throws Exception{
        //定义重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 3000, retryPolicy);
        //开启
        client.start();
        //创建TreeCache监听机制
        /**
         *  TreeCache(CuratorFramework client, String path)
         *      CuratorFramework client:监听客户端
         *      String path:监听路径
         */
        TreeCache treeCache=new TreeCache(client,"/bb");
        //开启缓存
        /**
         * NORMAL:普通启动方式,再启动时缓存子节点数据
         * POST_INITIALIZED_EVENT:再启动时缓存子节点数据，提示初始化
         * BUILD_INITIAL_CACHE:再启动时什么都不会输出
         *  再官方解释说是因为这种模式会再start执行之前先执行rebuildd的方法,而rebuildd的方法不会发出任何时间通知。
         */
        treeCache.start();
        System.out.println(treeCache.getCurrentData("/bb"));//查看了bb的节点
        //添加监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                //1.子节点添加,执行该代码
                if (event.getType()==TreeCacheEvent.Type.NODE_ADDED){
                    //路径
                    String path = event.getData().getPath();
                    //数据
                    byte[] data = event.getData().getData();
                    String currentData =new String(data);
                    System.out.println("【子节点添加】访问路径是:"+path+"对应的数据是:"+currentData);
                }
                //2.子节点修改,执行该代码
                else if (event.getType()==TreeCacheEvent.Type.NODE_UPDATED){
                    //路径
                    String path = event.getData().getPath();
                    //数据
                    byte[] data = event.getData().getData();
                    String currentData = new String(data);
                    System.out.println("【子节点修改】访问路径是:"+path+"对应的数据是:"+currentData);
                }
                //3.子节点修删除,执行该代码
                else if (event.getType()==TreeCacheEvent.Type.NODE_REMOVED){
                    //路径
                    String path = event.getData().getPath();
                    //数据
                    byte[] data = event.getData().getData();
                    String currentData =new String(data);
                    System.out.println("【子节点删除】访问路径是:"+path+"对应的数据是:"+currentData);
                }
                // 4.节点初始化，执行该代码
                else if (event.getType()==TreeCacheEvent.Type.INITIALIZED){
                    System.out.println("节点初始化");
                }
                //5.节点超时,执行带代码
                else if (event.getType()==TreeCacheEvent.Type.CONNECTION_SUSPENDED){
                    System.out.println("节点超时");
                }
                //6.节点重试链接,执行带代码
                else if (event.getType()==TreeCacheEvent.Type.CONNECTION_RECONNECTED){
                    System.out.println("子节点重试连接");
                }
                //7.节点超时,执行带代码
                else if (event.getType()==TreeCacheEvent.Type.CONNECTION_LOST){
                    System.out.println("子节点超时一段时间");
                }
            }
        });
        //让junit测试的时候,当前测试的线程不能关闭
        System.in.read();
        //关闭(实现watch机制监听的时候,不需要添加该代码)
        //client.close();
    }
}
