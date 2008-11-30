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
package flapjack.annotation.model;

import flapjack.annotation.Record;
import flapjack.annotation.RecordPackageClassScanner;
import flapjack.layout.RecordLayout;
import flapjack.model.RecordFactory;
import flapjack.model.RecordFactoryResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MappedRecordFactoryResolver implements RecordFactoryResolver {
    private RecordPackageClassScanner classScanner = new RecordPackageClassScanner();
    private Map<Class, Class> recordToClass = new HashMap<Class, Class>();

    public RecordFactory resolve(RecordLayout layout) {
        initializeLayoutToClassMap();
        return new MappedRecordFactory(recordToClass.get(layout.getClass()));
    }

    private void initializeLayoutToClassMap() {
        if (recordToClass.size() == 0) {
            for (Class clazz : classScanner.scan()) {
                Record record = (Record) clazz.getAnnotation(Record.class);
                recordToClass.put(record.value(), clazz);
            }
        }
    }

    public void setPackages(List<String> packages) {
        classScanner.setPackages(packages);
    }
}