package com.example.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTestService {

    private final RedissonClient redissonClient;

    /**
     * waitTime : Redis에서 Lock을 받기 까지의 최대 대기 시간
     * leasTime : Lock을 받고 Lock을 유지하는 시간
     */
    public String getLock() {
        RLock lock = redissonClient.getLock("sampleLock");

        try {
            boolean isLock = lock.tryLock(1, 5, TimeUnit.SECONDS);

            if (!isLock) {
                log.error("===== Lock acquisition failed=====");
                return "Lock failed";
            }

        } catch (Exception e) {
            log.error("Redis lock failed");
        }

        return "Lock success";
    }
}
