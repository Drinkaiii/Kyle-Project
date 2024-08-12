package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Log4j2
public class RateLimiterUtil {

    final private RedisTemplate redisTemplate;
    private int MAX_REQUEST_LIMIT = 10;
    private int PER_TIME = 1 * 1000;

    public Boolean isExceedSpeed(String ip){

        try {

        Long currentCount = redisTemplate.opsForValue().increment(ip);
        if (currentCount != null && currentCount == 1)
            redisTemplate.expire(ip, PER_TIME, TimeUnit.MILLISECONDS);

        return currentCount != null && currentCount > MAX_REQUEST_LIMIT;

        } catch (RedisConnectionFailureException | QueryTimeoutException e) {
            log.error("Redis connection failure");
            return false;
        }

    }

}

