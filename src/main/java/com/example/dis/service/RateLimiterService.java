package com.example.dis.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Service
public class RateLimiterService {
    private final ConcurrentHashMap<String, Semaphore> deviceSemaphores = new ConcurrentHashMap<>();

    public boolean allowRequest(String source) {
        // Allow 10 requests per second per device
        Semaphore semaphore = deviceSemaphores.computeIfAbsent(source, k -> new Semaphore(10));
        return semaphore.tryAcquire();
    }

    public void releaseRequest(String source) {
        Semaphore semaphore = deviceSemaphores.get(source);
        if (semaphore != null) {
            semaphore.release();
        }
    }
}
