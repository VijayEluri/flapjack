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
package flapjack.model;

import flapjack.parser.FieldData;
import flapjack.util.TypeConverter;
import flapjack.util.ValueConverter;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


public class BeanPathObjectMapperTest extends MockObjectTestCase {
    private BeanPathObjectMapper mapper;
    private Map fields;
    private Person person;
    private ObjectMappingStore mappingStore;
    private ObjectMapping objMapping;

    protected void setUp() throws Exception {
        super.setUp();
        mappingStore = new ObjectMappingStore();
        mapper = new BeanPathObjectMapper();
        mapper.setObjectMappingStore(mappingStore);
        fields = new LinkedHashMap();
        person = new Person();
        objMapping = new ObjectMapping(Person.class);
        mappingStore.add(objMapping);
    }

    public void test_mapOnTo_EnsureCompoundFieldsAreOnlyMappedOnce() {
        Mock factory = mock(DomainFieldFactory.class);

        objMapping.field(Arrays.asList(new String[]{"field1", "field2"}), "firstName", (DomainFieldFactory) factory.proxy(), null);

        fields.put("field1", "Jim".getBytes());
        fields.put("field2", "Smith".getBytes());

        factory.expects(once())
                .method("build").with(isA(ListMap.class), eq(String.class), isA(TypeConverter.class))
                .will(returnValue(""));

        mapper.mapOnTo(fields, person);
    }

    public void test_mapOnTo_MultipleFields_FieldNotFoundInRecord() {
        DomainFieldFactory factory = new DomainFieldFactory() {
            public Object build(ListMap fields, Class domainFieldType, TypeConverter typeConverter) {
                return new String((byte[]) fields.get("field1")) + new String((byte[]) fields.get("field2"));
            }
        };

        objMapping.field(Arrays.asList(new String[]{"field1", "field2"}), "firstName", factory, null);

        fields.put("field1", "Jim".getBytes());
        fields.put("field3", "Smith".getBytes());

        try {
            mapper.mapOnTo(fields, person);
            fail();
        } catch (ObjectMappingException err) {
            assertEquals("\"field2\" was not found in record, field mapping for \"firstName\" on " + Person.class.getName(), err.getMessage());
        }
    }

    public void test_mapOnTo_MultipleFields_ToSingleDomainField_UseIndexes() {
        DomainFieldFactory factory = new DomainFieldFactory() {
            public Object build(ListMap fields, Class domainFieldType, TypeConverter typeConverter) {
                return new String((byte[]) fields.get(0)) + new String((byte[]) fields.get(1));
            }
        };

        objMapping.field(Arrays.asList(new String[]{"field1", "field2"}), "firstName", factory, null);

        fields.put("field1", "Jim".getBytes());
        fields.put("field2", "Smith".getBytes());

        mapper.mapOnTo(fields, person);

        assertEquals("JimSmith", person.firstName);
        assertNull(person.lastName);
    }

    public void test_mapOnTo_MultipleFields_ToSingleDomainField_UseKeys() {
        DomainFieldFactory factory = new DomainFieldFactory() {
            public Object build(ListMap fields, Class domainFieldType, TypeConverter typeConverter) {
                return new String((byte[]) fields.get("field1")) + new String((byte[]) fields.get("field2"));
            }
        };

        objMapping.field(Arrays.asList(new String[]{"field1", "field2"}), "firstName", factory, null);

        fields.put("field1", "Jim".getBytes());
        fields.put("field2", "Smith".getBytes());

        mapper.mapOnTo(fields, person);

        assertEquals("JimSmith", person.firstName);
        assertNull(person.lastName);
    }

    public void test_mapOnTo_MultipleFields() {
        objMapping.field("field1", "firstName");
        objMapping.field("field2", "lastName");

        fields.put("field1", new FieldData(null, "Jim".getBytes()));
        fields.put("field2", new FieldData(null, "Smith".getBytes()));

        mapper.mapOnTo(fields, person);

        assertEquals("Jim", person.firstName);
        assertEquals("Smith", person.lastName);
    }

    public void test_mapOnTo_SingleField() {
        objMapping.field("field1", "firstName");

        fields.put("field1", new FieldData(null, "Jim".getBytes()));

        mapper.mapOnTo(fields, person);

        assertEquals("Jim", person.firstName);
    }

    public void test_mapOnTo_SingleField_UseCustomValueConverter() {
        TypeConverter typeConverter = new TypeConverter();
        typeConverter.registerConverter(new CustomConverter());

        mapper.setTypeConverter(typeConverter);

        objMapping.field("field1", "firstName", CustomConverter.class);

        fields.put("field1", new FieldData(null, "Jim".getBytes()));

        mapper.mapOnTo(fields, person);

        assertEquals("customValue", person.firstName);
    }

    public void test_mapOnTo_SingleField_UseCustomValueConverter_NotRegisteredInTypeConverter() {
        objMapping.field("field1", "firstName", CustomConverter.class);

        fields.put("field1", new FieldData(null, "Jim".getBytes()));

        try {
            mapper.mapOnTo(fields, person);
            fail();
        } catch (IllegalArgumentException err) {
            assertEquals("Could not find a flapjack.model.BeanPathObjectMapperTest$CustomConverter registered! Are you sure you registered flapjack.model.BeanPathObjectMapperTest$CustomConverter in the TypeConverter?", err.getMessage());
        }
    }

    public void test_mapOnTo_FieldDoesNotExistOnDomain() {
        objMapping.field("field1", "address");

        fields.put("field1", "Jim".getBytes());

        try {
            mapper.mapOnTo(fields, person);
            fail();
        } catch (ObjectMappingException err) {
            assertEquals("Could not map \"field1\" to \"address\" on " + Person.class.getName() + ", \"address\" could NOT be found", err.getMessage());
        }
    }

    public void test_mapOnTo_FieldMappingNotFoundForField() {
        fields.put("field1", "value".getBytes());

        try {
            mapper.mapOnTo(fields, person);
            fail();
        } catch (ObjectMappingException err) {
            assertEquals("Could not locate field mapping for field=\"field1\"", err.getMessage());
        }
    }

    public void test_mapOnTo_NoObjectMappingFound() {
        mapper.setObjectMappingStore(new ObjectMappingStore());

        try {
            mapper.mapOnTo(fields, person);
            fail();
        } catch (ObjectMappingException err) {
            assertEquals("Could not locate object mapping for class=" + Person.class.getName(), err.getMessage());
        }
    }

    public void test_mapOnTo_IgnoreUnmappedFields() {
        mapper.setIgnoreUnmappedFields(true);
        fields.put("field1", "value".getBytes());

        mapper.mapOnTo(fields, person);
    }

    private static class Person {
        private String firstName;
        private String lastName;
    }

    private static class CustomConverter implements ValueConverter {
        public Object toDomain(byte[] bytes) {
            return "customValue";
        }

        public byte[] toBytes(Object domain) {
            return null;
        }
    }
}
