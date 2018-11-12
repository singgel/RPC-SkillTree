package com.hks.springzookeeper.leaderSelector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/12
 */
public class Test {

    static String master_path = "/curator_recipes_master_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    @org.junit.Test
    public static void main(String[] args) throws Exception{
        client.start();
        LeaderSelector selector = new LeaderSelector(client,
                master_path,
                new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                        System.out.print("成为master角色");
                        Thread.sleep(30000);
                        System.out.print("完成master操作，释放master权利");
                    }
                });
        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
