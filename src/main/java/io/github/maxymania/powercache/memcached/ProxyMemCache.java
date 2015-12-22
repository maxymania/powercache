/*
 * Copyright 2015 Simon Schmidt.
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

import com.google.common.cache.AbstractCache;
import com.google.common.hash.HashFunction;
import io.github.maxymania.powercache.hash.Util;
import io.github.maxymania.powercache.proxy.MethodCall;
import io.github.maxymania.powercache.proxy.Result;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

/**
 *
 * @author Simon Schmidt
 */
public class ProxyMemCache extends AbstractCache<MethodCall,Result> {
    HashFunction hf;
    MemcachedClient client;
    Transcoder<Result> tcr;
    int expires;

    public ProxyMemCache(HashFunction hf, MemcachedClient client, int expires) {
        this.hf = hf;
        this.client = client;
        this.tcr = new KryoTranscoder(Result.class);
        this.expires = expires;
    }
    public ProxyMemCache(HashFunction hf, MemcachedClient client, Transcoder<Result> tcr, int expires) {
        this.hf = hf;
        this.client = client;
        this.tcr = tcr;
        this.expires = expires;
    }
    
    @Override
    public Result getIfPresent(Object key) {
        MethodCall mcall = (MethodCall)key;
        String k = Util.hex(Util.hash(hf, mcall.getName(), mcall.getData()));
        return client.get(k, tcr);
    }
    
    @Override
    public void put(MethodCall mcall, Result value) {
        String k = Util.hex(Util.hash(hf, mcall.getName(), mcall.getData()));
        client.set(k, expires, value, tcr);
    }
    
}
