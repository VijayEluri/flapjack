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
package flapjack.cobol.layout;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CobolFieldPatternValidatorTest {
    @Test
    public void test_validate() {
        CobolFieldPatternValidator validator = new CobolFieldPatternValidator();
        assertTrue(validator.validate("X"));
        assertTrue(validator.validate("XX"));
        assertFalse(validator.validate("X(0)"));
        assertTrue(validator.validate("X(2)"));
        assertTrue(validator.validate("X(22)"));
        assertFalse(validator.validate("X(00)"));
        assertTrue(validator.validate("x"));
        assertTrue(validator.validate("xx"));
        assertTrue(validator.validate("x(2)"));
        assertTrue(validator.validate("x(22)"));
        assertTrue(validator.validate("X(4)"));
        assertTrue(validator.validate("X(30)"));
        assertTrue(validator.validate("X(307)"));

        assertTrue(validator.validate("9"));
        assertTrue(validator.validate("99"));
        assertFalse(validator.validate("99(2)"));
        assertFalse(validator.validate("9(0)"));
        assertTrue(validator.validate("9(2)"));
        assertTrue(validator.validate("9(20)"));
        assertTrue(validator.validate("9(2)"));
        assertTrue(validator.validate("9(22)"));
        assertTrue(validator.validate("9(202)"));
        assertTrue(validator.validate("9v9"));
        assertTrue(validator.validate("9v99"));
        assertTrue(validator.validate("99v9"));
        assertFalse(validator.validate("9(0)v9"));
        assertFalse(validator.validate("99(2)v9"));
        assertTrue(validator.validate("9(2)v9"));
        assertTrue(validator.validate("9(20)v9"));
        assertTrue(validator.validate("9(22)v9"));
        assertTrue(validator.validate("9(202)v9"));
        assertTrue(validator.validate("9(2)v99"));
        assertFalse(validator.validate("99(2)v99(2)"));
        assertFalse(validator.validate("9(0)v9(0)"));
        assertFalse(validator.validate("9(00)v9(0)"));
        assertFalse(validator.validate("9(0)v9(00)"));
        assertFalse(validator.validate("9(00)v9(00)"));
        assertTrue(validator.validate("9(2)v9(2)"));
        assertTrue(validator.validate("9(20)v9(20)"));
        assertFalse(validator.validate("9v99(2)"));
        assertFalse(validator.validate("9v9(0)"));
        assertFalse(validator.validate("9v9(000)"));
        assertTrue(validator.validate("9v9(2)"));
        assertTrue(validator.validate("9v9(202)"));
        assertTrue(validator.validate("9v9(20)"));
        assertTrue(validator.validate("9(22)v9(22)"));
        assertTrue(validator.validate("9(202)v9(202)"));
        assertTrue(validator.validate("9(2)V9"));
    }

}
