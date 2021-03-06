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

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;


public class FileUtilImpl implements FileUtil {
    public ScatteringByteChannel channel(File file) {
        try {
            return new FileInputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(OutputStream output) {
        if (output != null) {
            try {
                output.close();
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
        }
    }

    public void close(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
        }
    }

    public void close(Reader input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
        }
    }

    public void close(ReadableByteChannel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ByteBuffer map(ScatteringByteChannel channel, long offset, long length) {
        if (channel instanceof FileChannel) {
            FileChannel fileChannel = (FileChannel) channel;
            try {
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, offset, length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("Channel is NOT a FileChannel it is a " + channel.getClass().getName());
    }

    public long length(File file) {
        return file.length();
    }
}
