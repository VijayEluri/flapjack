/**
 * Copyright 2008 Dan Dudley
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
package flapjack.annotation;

import flapjack.layout.SimpleRecordLayout;
import junit.framework.TestCase;

import java.util.List;


public class FieldLocatorTest extends TestCase {
    private FieldLocator locator;

    public void setUp() {
        locator = new FieldLocator();
    }

    public void test_locateById_NotFoundField() {
        assertNull(locator.locateById(Dummy.class, "field2"));
    }

    public void test_locateById_FoundField() {
        java.lang.reflect.Field field = locator.locateById(Dummy.class, "field1");

        assertNotNull(field);
        assertEquals("field1", field.getName());
    }

    public void test_locate() {
        List<java.lang.reflect.Field> fields = locator.locate(Dummy.class);

        assertNotNull(fields);
        assertEquals(1, fields.size());
        assertEquals("field1", fields.get(0).getName());
    }

    @Record(SimpleRecordLayout.class)
    private static class Dummy {
        @Field("field1")
        private String field1;
        private String fieldNotInLayout;
    }
}
