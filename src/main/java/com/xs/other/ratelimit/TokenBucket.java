package com.xs.other.ratelimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * 令牌桶
 * 局限：最多1秒生成一个令牌
 *
 * @author xiongshun
 * create time: 2020/7/15 9:42
 */
public class TokenBucket {
    private static Logger log = LoggerFactory.getLogger(TokenBucket.class);
    private final double unitAddNum;    // 单位时间（1s）往桶中放令牌数量
    private final long maxTokenNum;      // 桶中最大有多少令牌

    private volatile long currentTokenCount = 0;  // 当前桶中有多少令牌
    private volatile long nextRefreshTime = 0L;  // 下一次刷新桶中令牌数量的时间戳
    private volatile long lastAcquireTime;       // 上一次从桶中获取令牌的时间戳（貌似用不到）

    /**
     *
     * @param unitAddNum 1秒增加几个令牌
     * @param maxToken 桶中最大令牌数
     * @param isFullStart 一开始是否是满的
     */
    public TokenBucket(double unitAddNum, long maxToken, boolean isFullStart) {
        if (unitAddNum <= 0 || maxToken <= 0) {
            throw new RuntimeException("unitAddNum and maxToken can't less than 0");
        }
        if (unitAddNum > 1000) {
            throw new RuntimeException("unitAddNum max is 1000");
        }
        this.unitAddNum = unitAddNum;
        this.maxTokenNum = maxToken;
        if (isFullStart) {
            this.currentTokenCount = maxToken;
        } else {
            this.currentTokenCount = 0;
        }
        this.nextRefreshTime = calculateNextRefreshTime(System.currentTimeMillis());
        log.debug("init over!unitAddNum={}, maxTokenNum={}, currentTokenCount={}, nexRefreshTime={}",
                this.unitAddNum, this.maxTokenNum, this.currentTokenCount, this.nextRefreshTime);
    }

    public boolean acquire(long needTokenNum) {
        if (needTokenNum > this.maxTokenNum) {
            return false;
        }
        synchronized (this) {
            long currentTimestamp = System.currentTimeMillis();
//            log.debug("acquire: needTokenNum={}, currentTokenCount={}, nextRefreshTime={}, currentTimestamp={}",
//                    needTokenNum, this.currentTokenCount, this.nextRefreshTime, currentTimestamp);
            this.refreshCurrentTokenCount(currentTimestamp);
            if (needTokenNum <= this.currentTokenCount) {
                return this.doAquire(needTokenNum, currentTimestamp);
            }
            return false;
        }
    }

    private boolean doAquire(long needTokenNum, long currentTimestamp) {
        this.currentTokenCount -= needTokenNum;
        this.lastAcquireTime = currentTimestamp;
        return true;
    }

    /**
     * 刷新桶中令牌数量
     * @param currentTimestamp
     */
    private void refreshCurrentTokenCount(long currentTimestamp) {
        if (this.nextRefreshTime > currentTimestamp) {
            return;
        }
//        log.debug("refreshCurrentTokenCount before:currentTimestamp={}, currentTokenCount={}, nextRefreshTime={}, lastAcquireTime={}",
//                currentTimestamp, this.currentTokenCount, this.nextRefreshTime, this.lastAcquireTime);
        this.currentTokenCount = Math.min(this.maxTokenNum, this.currentTokenCount + calculateNeedAddTokenNum(currentTimestamp));
        this.nextRefreshTime = calculateNextRefreshTime(currentTimestamp);
//        log.debug("refreshCurrentTokenCount after:currentTimestamp={}, currentTokenCount={}, nextRefreshTime={}, lastAcquireTime={}",
//                currentTimestamp, this.currentTokenCount, this.nextRefreshTime, this.lastAcquireTime);
    }

    /**
     * 计算当前需要添加多少令牌
     * @param currentTimestamp
     * @return
     */
    private long calculateNeedAddTokenNum(long currentTimestamp) {
        if (this.nextRefreshTime > currentTimestamp) {
            return 0;
        }
        long addOneMs = Math.round(1.0D / this.unitAddNum * 1000D); // 这么久才能加1个令牌
        return (currentTimestamp - this.nextRefreshTime) / addOneMs + 1;
    }

    private long calculateNextRefreshTime(long currentTimestamp) {
//        log.debug("calculateNextRefreshTime currentTimestamp={}, nextRefreshTime={}",
//                currentTimestamp, this.nextRefreshTime);
        if (currentTimestamp < this.nextRefreshTime) {
            return this.nextRefreshTime;
        }
        long addOneMs = Math.round(1.0D / this.unitAddNum * 1000D); // 这么久才能加1个令牌
        long result = 0;
        if (this.nextRefreshTime <= 0) {
            result = currentTimestamp + addOneMs;
        } else {
            result = this.nextRefreshTime + (currentTimestamp - this.nextRefreshTime) / addOneMs + addOneMs;
        }
//        log.debug("calculateNextRefreshTime currentTimestamp={}, nextRefreshTime={}, addOneMs={}, result={}",
//                currentTimestamp, this.nextRefreshTime, addOneMs, result);
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(1000D, 1000, true);
        int succCount = 0;
        int failCount = 0;
        long startTime = System.currentTimeMillis();
        for (int i=0; i<3000; i++) {
            if (tokenBucket.acquire(1)) {
                succCount++;
            } else {
                failCount++;
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "ms; succCount=" + succCount + "; failCount=" + failCount);
        tokenBucket = new TokenBucket(1D, 1, true);
        for (int i=0; i<10; i++) {
            System.out.println(tokenBucket.acquire(1));
            Thread.sleep(500);
        }
    }
}
