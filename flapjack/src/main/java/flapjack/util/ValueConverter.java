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

/**
 * Used to covert text values into some other value
 */
public interface ValueConverter {

    /**
     * Attempts to convert the given text into the appropriate type
     *
     * @param bytes - the bytes to be converted
     * @return the result of the conversion attempt
     */
    Object toDomain(byte[] bytes);

    /**
     * Attempts to convert the given domain object to a byte array
     * <p/>
     * An empty array of bytes should be returned if the given domain object is null.
     *
     * @param domain - the domain object to be serialized to bytes
     * @return the binary data representing the domain object
     */
    byte[] toBytes(Object domain);
}
