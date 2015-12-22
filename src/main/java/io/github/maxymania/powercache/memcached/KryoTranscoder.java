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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.maxymania.powercache.proxy.Result;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.Transcoder;

/**
 *
 * @author simon
 */
public class KryoTranscoder<V> implements Transcoder<V> {
    static ThreadLocal<Kryo> kryo = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            return new Kryo();
        }
    };
    private Class<V> clazz;
    public KryoTranscoder(Class<V> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public boolean asyncDecode(CachedData cd) {
        return false;
    }

    @Override
    public CachedData encode(V t) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output out = new Output(baos);
        kryo.get().writeObject(out, t);
        out.close();
        return new CachedData(0,baos.toByteArray(),getMaxSize());
    }

    @Override
    public V decode(CachedData cd) {
        ByteArrayInputStream bais = new ByteArrayInputStream(cd.getData());
        Input in = new Input(bais);
        return kryo.get().readObject(in, clazz);
    }

    @Override
    public int getMaxSize() {
        return CachedData.MAX_SIZE;
    }
    
}
