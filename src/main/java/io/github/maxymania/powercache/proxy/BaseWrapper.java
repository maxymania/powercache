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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author simon
 */
public class BaseWrapper implements InvocationHandler {
    protected final Object base;
    public BaseWrapper(Object base) {
        this.base = base;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return innerInvoke(proxy,method,args);
    }
    protected Object innerInvoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(base, args);
        if(result==base)result = proxy;
        return result;
    }
}
