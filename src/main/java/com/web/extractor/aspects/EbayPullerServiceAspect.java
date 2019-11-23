package com.web.extractor.aspects;

import com.web.extractor.scheduler.EbayScheduler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EbayPullerServiceAspect {
  private static final Logger log = LoggerFactory.getLogger(EbayPullerServiceAspect.class);

  @Around("@annotation(com.web.extractor.aspects.annotation.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    log.info("{} executed in {} ms ", joinPoint.getSignature(), executionTime);
    return proceed;
  }

  @Around("@annotation(com.web.extractor.aspects.annotation.LogPullingAddressInfoAspect)")
  public Object logStartPullAddress(ProceedingJoinPoint joinPoint) throws Throwable {

    String address = EbayScheduler.getScheduledUrl();

    Boolean isScheduled = EbayScheduler.scheduleCrawling();

    if (isScheduled) log.info("Started pulling of PAGE: {} ", address);

    Object proceed = joinPoint.proceed();

    if (isScheduled) log.info("Finished pulling of PAGE: {} ", address);

    return proceed;
  }

  @After("@annotation(com.web.extractor.aspects.annotation.LogPullingBackoffAspect)")
  public void logBackOffPage() {
    if (!EbayScheduler.scheduleCrawling()) log.info("BACKING OFF PAGE");
  }
}
