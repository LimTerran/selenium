// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.selenium.grid.data;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.SessionId;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeStatusTest {

  @Test
  public void ensureRoundTripWorks() throws URISyntaxException {
    ImmutableCapabilities stereotype = new ImmutableCapabilities("cheese", "brie");
    NodeId nodeId = new NodeId(UUID.randomUUID());
    NodeStatus status = new NodeStatus(
      nodeId,
      new URI("http://localhost:23456"),
      100,
      ImmutableSet.of(
        new Slot(
          new SlotId(nodeId, UUID.randomUUID()),
          stereotype,
          Instant.EPOCH,
          Optional.of(new Active(
            stereotype,
            new SessionId(UUID.randomUUID()),
            new ImmutableCapabilities("peas", "sausages"),
            Instant.now())))),
      false,
      "cheese");

    Json json = new Json();
    String source = json.toJson(status);

    Object seen = json.toType(source, NodeStatus.class);

    assertThat(seen).isEqualTo(status);
  }

}
