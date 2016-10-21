/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.zookeeper;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangjiaze
 * @version Id: ZkXLock.java, v 0.1 2016/8/12 0012 上午 11:41 FancyKong Exp $$
 */
@Component
@Slf4j
public class ZookeeperLock {

    @Value("${zookeeper.timeout}")
    private long timeout;

    @Value("${zookeeper.list}")
    private String serverList;

    //客户端
    private CuratorFramework client = null;

    //初始化一个客户端
    public void init() {
        try {
            client = CuratorFrameworkFactory.newClient(serverList,
                    new ExponentialBackoffRetry(100, 3));
            client.start();
        } catch (Exception e) {
            log.error("Zookeeper connect error: {}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 抢zk锁，默认使用3s的等待时间
     */
    public void acquire(InterProcessMutex lock) {
        long beginTime = new Date().getTime();
        acquire(lock, timeout);
        long endTime = new Date().getTime();
        if ((endTime - beginTime) > 3000) {
            log.error("ZookeeperLock timeout, TIME: {}", (endTime - beginTime));
        }
    }

    /**
     * 抢zk锁，可以自定义等待时间
     *
     * @param lock     锁实例
     * @param lockTime 等待时间
     */
    public void acquire(InterProcessMutex lock, long lockTime) {
        try {
            if (!lock.acquire(lockTime, TimeUnit.SECONDS)) {
                throw new RuntimeException("timeout");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 释放zk锁，同时删除锁节点
     */
    public synchronized void release(InterProcessMutex lock, String path) {
        try {
            if (lock != null) {
                lock.release();
                // 尝试删除锁节点
                client.delete().forPath(path);
            }
        } catch (KeeperException e) {
            //忽略zk锁子节点存在时删除节点失败
            if (!Objects.equal(e.code().name(), KeeperException.Code.NOTEMPTY.name())) {
                log.error("Zookeeper lock KeeperException: {}", Throwables.getStackTraceAsString(e));
            }
        } catch (Exception e) {
            log.error("Zookeeper lock release fail: {}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 创建递归锁
     *
     * @param path 锁定路径
     */
    public InterProcessMutex creatInterProcessMutex(String path) {

        return new InterProcessMutex(client, path);
    }

}
