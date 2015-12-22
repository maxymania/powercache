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
package io.github.maxymania.powercache.memcached;

import com.google.common.cache.Cache;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import io.github.maxymania.powercache.CachingApplier;
import io.github.maxymania.powercache.proxy.MethodCall;
import io.github.maxymania.powercache.proxy.Result;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author simon
 */
public class MemCachingApplier extends CachingApplier{
    HashFunction hf;
    MemcachedClient client;
    int expires;

    /**
     * 
     * @param hf Hash Function
     * @param client Memcached Connection
     * @param expires Expiration time in MS
     */
    public MemCachingApplier(HashFunction hf, MemcachedClient client, int expires) {
        this.hf = hf;
        this.client = client;
        this.expires = expires;
    }

    /**
     * 
     * @param client Memcached Connection
     * @param expires Expiration time in MS
     */
    public MemCachingApplier(MemcachedClient client, int expires) {
        hf = Hashing.md5();
        this.client = client;
        this.expires = expires;
    }
    
    
    @Override
    protected Cache<MethodCall, Result> createCache() {
        return new ProxyMemCache(hf,client,expires);
    }
    
}
