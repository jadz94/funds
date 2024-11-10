package com.jadz.fund.configuration;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestConfiguration {
  @Test
  void runGenericInstrumentTestParallel() {
    // Data in scenarios are not fully segregated (1, 5, 6 for instance), so we run them sequentially using 1 thread
    Results results = Runner.path("classpath:com/jadz/fund/features/")
        .parallel(5);
    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
