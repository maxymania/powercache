/*
 * Copyright 2015 simon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.maxymania.powercache.jcache;

import com.google.common.cache.Cache;
import io.github.maxymania.powercache.CachingApplier;
import io.github.maxymania.powercache.proxy.MethodCall;
import io.github.maxymania.powercache.proxy.Result;

/**
 *
 * @author simon
 */
public class JCachingApplier extends CachingApplier {
    private final String cacheName;
    public JCachingApplier(String cacheName) {
        this.cacheName = cacheName;
    }
    @Override
    protected Cache<MethodCall,Result> createCache() {
        return new JCacheCache(cacheName,MethodCall.class,Result.class);
    }
}
