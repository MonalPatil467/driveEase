package com.monal.driveEase.ai;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimiter {

    private static final int MAX_CALLS = 10;          // per window
    private static final long WINDOW_MS = 60_000;     // 1 minute

    private final ConcurrentHashMap<String, Deque<Long>> calls = new ConcurrentHashMap<>();

    public boolean allow(String key) {
        long now = System.currentTimeMillis();
        Deque<Long> q = calls.computeIfAbsent(key, k -> new ArrayDeque<>());
        synchronized (q) {
            while (!q.isEmpty() && now - q.peekFirst() > WINDOW_MS) {
                q.pollFirst();
            }
            if (q.size() >= MAX_CALLS) {
                return false;
            }
            q.addLast(now);
            return true;
        }
    }
}