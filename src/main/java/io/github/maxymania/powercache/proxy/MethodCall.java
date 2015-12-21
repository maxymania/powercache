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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Simon Schmidt
 */
public class MethodCall implements Serializable {
    private String name;
    private Object[] data;

    public MethodCall(String name, Object[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Object[] getData() {
        return data.clone();
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodCall other = (MethodCall) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    
}
