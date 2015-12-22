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
package io.github.maxymania.powercache;

import io.github.maxymania.powercache.proxy.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Simon Schmidt
 */
public class CachingApplier {

    private final Map<Class<?>, Class<?>> proxies = new HashMap();
    private boolean isinit = false;
    protected Cache<MethodCall,Result> cache;

    private synchronized void init() {
        if (isinit) {
            return;
        }
        cache = createCache();
        isinit = true;
    }

    private synchronized Class<?> getProxy(Class<?> cls) {
        Class<?> pcls = proxies.get(cls);
        if (pcls != null) {
            return pcls;
        }
        pcls = Proxy.getProxyClass(cls.getClassLoader(), cls);
        proxies.put(cls, pcls);
        return pcls;
    }

    private Object instanciate(Class<?> pcls, InvocationHandler ih) {
        try {
            return pcls.getConstructor(InvocationHandler.class).newInstance(ih);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Cache<MethodCall,Result> createCache() {
        return CacheBuilder.newBuilder().build();
    }
    
    protected InvocationHandler createHandler(Object object){
       return new CachedWrapper(object, cache);
    }

    @SuppressWarnings("unchecked")
    public <T> T apply(T object, Class<T> cls) {
        init();
        Class<?> pcls = getProxy(cls);
        return (T) instanciate(pcls, createHandler(object));
    }
}
