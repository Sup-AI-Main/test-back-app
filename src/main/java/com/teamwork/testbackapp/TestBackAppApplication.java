package com.teamwork.testbackapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableAsync
@EnableScheduling
@Slf4j
public class TestBackAppApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        log.info("🚀 Starting TestBackApp Application...");
        log.info("🔧 Java Version: {}", System.getProperty("java.version"));
        log.info("🔧 Spring Boot Version: {}", SpringApplication.class.getPackage().getImplementationVersion());
        log.info("🌍 Environment: {}", System.getenv("RAILWAY_ENVIRONMENT"));
        log.info("🚂 Railway Service: {}", System.getenv("RAILWAY_SERVICE_NAME"));
        
        try {
            SpringApplication.run(TestBackAppApplication.class, args);
        } catch (Exception e) {
            log.error("❌ Application failed to start: {}", e.getMessage(), e);
            throw e;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("✅ Application started successfully!");
        log.info("🌐 Server running on port: {}", environment.getProperty("server.port", "8080"));
        log.info("🏠 Server address: {}", environment.getProperty("server.address", "0.0.0.0"));
        log.info("🔧 Active profiles: {}", String.join(",", environment.getActiveProfiles()));
        log.info("🚀 Railway PORT: {}", environment.getProperty("PORT", "NOT_SET"));
        log.info("📊 Health check available at: /api/startup/health");
        log.info("🔍 Startup diagnostics at: /api/startup/startup-log");
        log.info("🎯 Simple health check at: /health");
        log.info("🏠 Root endpoint at: /");
    }
}