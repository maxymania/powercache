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
package io.github.maxymania.powercache.proxy;

import com.google.common.cache.Cache;
import io.github.maxymania.powercache.Cached;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author simon
 */
public class CachedWrapper extends BaseWrapper{
    private Cache<MethodCall,Result> cache;

    public CachedWrapper(Cache<MethodCall, Result> cache, Object base) {
        super(base);
        this.cache = cache;
    }
    
    public CachedWrapper(Object base) {
        super(base);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Cached cached = method.getAnnotation(Cached.class);
        if(cached==null){
            return innerInvoke(proxy, method, args);
        }
        
        MethodCall call = new MethodCall(cached.value(),args);
        Callable<Result> res = new Callable<Result>(){
            @Override
            public Result call() throws Exception {
                Result res = new Result();
                try{
                    res.value = innerInvoke(proxy,method,args);
                }catch(Throwable e){
                    throw new HiddenWrapper(e);
                }
                return res;
            }
        };
        try{
            return cache.get(call, res).value;
        }catch(Throwable e){
            Throwable t = HiddenWrapper.findClause(e);
            if(t==null) t = new Error();
            throw t;
        }
    }
}
