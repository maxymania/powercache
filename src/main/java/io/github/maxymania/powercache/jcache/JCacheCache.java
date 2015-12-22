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
import javax.cache.Cache;
import javax.cache.Caching;

/**
 *
 * @author Simon Schmidt
 */
public class JCacheCache<K,V> extends AbstractCache<K,V> {
    Cache<K,V> cache;

    public JCacheCache(String name,Class<K> k, Class<V> v){
        this.cache = Caching.getCache(name, k, v);
    }
    public JCacheCache(Cache<K, V> cache) {
        this.cache = cache;
    }
    
    @Override
    public V getIfPresent(Object key) {
        K k = (K)key;
        return cache.get(k);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }
    
}
