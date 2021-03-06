/*
 * Copyright (C) 2015 Piotr Wittchen
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
package com.github.pwittchen.networkevents.dagger.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.BusWrapper;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = MainActivity.class, library = true) public class AndroidModule {
  private final Application application;
  private final BusWrapper busWrapper;

  public AndroidModule(BaseApplication application) {
    this.application = application;
    this.busWrapper = getOttoBusWrapper(new Bus());
  }

  @NonNull private BusWrapper getOttoBusWrapper(final Bus bus) {
    return new BusWrapper() {
      @Override public void register(Object object) {
        bus.register(object);
      }

      @Override public void unregister(Object object) {
        bus.unregister(object);
      }

      @Override public void post(Object event) {
        bus.post(event);
      }
    };
  }

  @Provides @Singleton public Context provideApplicationContext() {
    return application;
  }

  @Provides @Singleton public BusWrapper provideBusWrapper() {
    return busWrapper;
  }

  @Provides @Singleton NetworkEvents provideNetworkEvents() {
    return new NetworkEvents(application, busWrapper).enableWifiScan();
  }
}
