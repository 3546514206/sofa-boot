/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.healthcheck.core;

import org.springframework.boot.actuate.health.Health;

/**
 * @author liangen
 * @author qilong.zql
 * <p>
 * SOFABoot 提供的健康检查接口，相比 HealthIndicator 接口，增加了一些扩展参数，如失败重试次数，超时时间等
 * @since 2.3.0
 */
public interface HealthChecker {

    /**
     * HealthCheck information.
     *
     * @return
     */
    Health isHealthy();

    /**
     * HealthChecker name
     *
     * @return
     */
    String getComponentName();

    /**
     * The number of retries after failure.
     *
     * @return
     */
    default int getRetryCount() {
        return 0;
    }

    /**
     * The time interval for each retry after failure.
     *
     * @return
     */
    default long getRetryTimeInterval() {
        return 0;
    }

    /**
     * Is it strictly checked?
     * If true, the final check result of isHealthy() is used as the result of the component's check.
     * If false, the final result of the component is considered to be healthy, but the exception log is printed.
     *
     * @return
     */
    default boolean isStrictCheck() {
        return true;
    }
}
