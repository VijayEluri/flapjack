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

import java.io.File;


public class NioRecordReaderFactory implements RecordReaderFactory {
    private int recordLength;
    private boolean useDirectBuffer;

    public NioRecordReaderFactory(int recordLength) {
        this(recordLength, false);
    }

    public NioRecordReaderFactory(int recordLength, boolean useDirectBuffer) {
        this.recordLength = recordLength;
        this.useDirectBuffer = useDirectBuffer;
    }

    public RecordReader build(File file) {
        NioRecordReader recordReader = new NioRecordReader(file, useDirectBuffer);
        recordReader.setRecordLength(recordLength);
        return recordReader;
    }
}
