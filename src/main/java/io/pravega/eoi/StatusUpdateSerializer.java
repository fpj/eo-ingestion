/*
 * Copyright 2019 Flavio Junqueira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pravega.eoi;

import io.pravega.avro.Status;
import io.pravega.client.stream.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

public class StatusUpdateSerializer implements Serializer<ExactlyOnceIngestionSynchronizer.StatusUpdate>{
    static final Logger log = LoggerFactory.getLogger(StatusUpdateSerializer.class);

    @Override
    public ByteBuffer serialize(ExactlyOnceIngestionSynchronizer.StatusUpdate statusUpdate) {
        try {
            return statusUpdate.getStatusBytes();
        } catch (IOException e) {
            log.error("Error while serializing Avro object", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExactlyOnceIngestionSynchronizer.StatusUpdate deserialize(ByteBuffer byteBuffer) {
        try {
            return new ExactlyOnceIngestionSynchronizer.StatusUpdate(Status.fromByteBuffer(byteBuffer));
        } catch (IOException e) {
            log.error("Error while deserializing Avro object", e);
            throw new RuntimeException(e);
        }
    }
}
