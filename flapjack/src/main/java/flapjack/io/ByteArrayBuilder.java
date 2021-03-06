/**
 * Copyright 2008-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package flapjack.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ByteArrayBuilder {
    private List arrays = new ArrayList();

    public ByteArrayBuilder append(byte[] bytes) {
        arrays.add(bytes);
        return this;
    }

    public String toString() {
        return new String(toByteArray());
    }

    public byte[] toByteArray() {
        int length = 0;
        Iterator it = arrays.iterator();
        while (it.hasNext()) {
            byte[] b = (byte[]) it.next();
            length += b.length;
        }

        byte[] temp = new byte[length];
        int offset = 0;
        it = arrays.iterator();
        while (it.hasNext()) {
            byte[] b = (byte[]) it.next();
            System.arraycopy(b, 0, temp, offset, b.length);
            offset += b.length;
        }
        return temp;
    }

    public ByteArrayBuilder append(byte[] bytes, int offset, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(bytes, offset, temp, 0, length);
        return append(temp);
    }
}
