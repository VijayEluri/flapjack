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
package flapjack.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class LongBinaryValueConverter extends AbstractBinaryValueConverter implements TypedValueConverter {
    protected int requiredNumberOfBytes() {
        return 8;
    }

    protected Object readData(DataInputStream input) throws IOException {
        return new Long(input.readLong());
    }

    protected void writeData(DataOutputStream output, Object domain) throws IOException {
        output.writeLong(((Long) domain).longValue());
    }

    public Class[] types() {
        return new Class[]{long.class, Long.class};
    }
}
