package com.web.extractor.scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class EbayScheduler {

  private static String address;

  private static String initialAddress;

  private static boolean isScheduled;

  private static long backoffTime;

  static {
    isScheduled = true;
    backoffTime = 600000; // 10 minutes backoff
  }

  private EbayScheduler() {
    // this is a static class
  }

  public static boolean scheduleCrawling() {
    return isScheduled;
  }

  public static void backoffCrawling() {
    isScheduled = false;

    Timer timer = new Timer();

    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            disableBackoff();
            cancel();
          }
        },
        backoffTime,
        backoffTime);
  }

  public static void disableBackoff() {
    isScheduled = true;
  }

  public static String getScheduledUrl() {
    return address;
  }

  public static void resetScheduledUrl() {
    address = initialAddress;
  }

  public static void setNextScheduledUrl(String url) {
    address = url;
  }

  public static void initAddresses(String initAddress) {
    address = initAddress;
    initialAddress = initAddress;
  }
}
