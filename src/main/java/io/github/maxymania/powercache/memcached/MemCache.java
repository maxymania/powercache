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
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

/**
 *
 * @author Simon Schmidt
 */
public class MemCache<K,V> extends AbstractCache<K,V> {
    HashFunction hf;
    MemcachedClient client;
    Transcoder<V> tcr;
    int expires;

    public MemCache(HashFunction hf, MemcachedClient client, Class<V> vcls, int expires) {
        this.hf = hf;
        this.client = client;
        this.tcr = new KryoTranscoder(vcls);
        this.expires = expires;
    }
    public MemCache(HashFunction hf, MemcachedClient client, Transcoder<V> tcr, int expires) {
        this.hf = hf;
        this.client = client;
        this.tcr = tcr;
        this.expires = expires;
    }
    
    @Override
    public V getIfPresent(Object key) {
        String k = Util.hex(Util.hash(hf, key));
        return client.get(k, tcr);
    }
    @Override
    public void put(K key, V value) {
        String k = Util.hex(Util.hash(hf, key));
        client.set(k, expires, value, tcr);
    }
}
