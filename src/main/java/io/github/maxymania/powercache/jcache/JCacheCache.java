/*
 * Copyright 2015 Simon Schmidt
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

import com.google.common.cache.AbstractCache;
import io.github.maxymania.powercache.proxy.MethodCall;
import io.github.maxymania.powercache.proxy.Result;
import javax.cache.Cache;
import javax.cache.Caching;

/**
 *
 * @author Simon Schmidt
 */
public class JCacheCache extends AbstractCache<MethodCall,Result> {
    Cache<MethodCall,Result> cache;

    public JCacheCache(String name){
        this.cache = Caching.getCache(name, MethodCall.class, Result.class);
    }
    public JCacheCache(Cache<MethodCall, Result> cache) {
        this.cache = cache;
    }
    
    @Override
    public Result getIfPresent(Object key) {
        MethodCall mcall = (MethodCall)key;
        return cache.get(mcall);
    }

    @Override
    public void put(MethodCall key, Result value) {
        cache.put(key, value);
    }
    
}
