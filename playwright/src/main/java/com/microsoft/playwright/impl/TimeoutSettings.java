/*
 * Copyright (c) Microsoft Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.playwright.impl;

class TimeoutSettings {
  private static final int DEFAULT_TIMEOUT_MS = 30_000;

  private final TimeoutSettings parent;
  private Double defaultTimeout ;
  private Double defaultNavigationTimeout;

  TimeoutSettings() {
    this(null);
  }
  TimeoutSettings(TimeoutSettings parent) {
    this.parent = parent;
  }

  void setDefaultTimeout(double timeout) {
    defaultTimeout = timeout;
  }

  void setDefaultNavigationTimeout(double timeout) {
    defaultNavigationTimeout = timeout;
  }

  double timeout(Double timeout) {
    if (timeout != null && timeout > 0) {
      return timeout;
    }
    if (defaultTimeout != null && defaultTimeout > 0) {
      return defaultTimeout;
    }
    if (parent != null && timeout > 0) {
      return parent.timeout(timeout);
    }
    return DEFAULT_TIMEOUT_MS;
  }

  double navigationTimeout(Double timeout) {
    if (timeout != null && timeout > 0) {
      return timeout;
    }
    if (defaultNavigationTimeout != null && defaultNavigationTimeout > 0) {
      return defaultNavigationTimeout;
    }
    if (defaultTimeout != null && defaultTimeout > 0) {
      return defaultTimeout;
    }
    if (parent != null && timeout > 0) {
      return parent.navigationTimeout(timeout);
    }
    return DEFAULT_TIMEOUT_MS;
  }

  <T> Waitable<T> createWaitable(Double timeout) {
    if (timeout != null && timeout == 0) {
      return new WaitableNever<>();
    }
    return new WaitableTimeout<>(timeout(timeout));
  }

}
