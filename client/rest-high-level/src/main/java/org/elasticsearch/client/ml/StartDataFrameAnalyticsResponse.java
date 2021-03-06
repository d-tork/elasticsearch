/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.client.ml;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;

import java.io.IOException;
import java.util.Objects;

/**
 * Response indicating if the Machine Learning Datafeed is now started or not
 */
public class StartDataFrameAnalyticsResponse extends AcknowledgedResponse {

    private static final ParseField NODE = new ParseField("node");

    public static final ConstructingObjectParser<StartDataFrameAnalyticsResponse, Void> PARSER =
        new ConstructingObjectParser<>(
            "start_data_frame_analytics_response",
            true,
            (a) -> new StartDataFrameAnalyticsResponse((Boolean) a[0], (String) a[1]));

    static {
        declareAcknowledgedField(PARSER);
        PARSER.declareString(ConstructingObjectParser.optionalConstructorArg(), NODE);
    }

    private final String node;

    public StartDataFrameAnalyticsResponse(boolean acknowledged, String node) {
        super(acknowledged);
        this.node = node;
    }

    public static StartDataFrameAnalyticsResponse fromXContent(XContentParser parser) throws IOException {
        return PARSER.parse(parser, null);
    }

    /**
     * The node that the job was assigned to
     *
     * @return The ID of a node if the job was assigned to a node.  If an empty string is returned
     *         it means the job was allowed to open lazily and has not yet been assigned to a node.
     *         If <code>null</code> is returned it means the server version is too old to return node
     *         information.
     */
    public String getNode() {
        return node;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        StartDataFrameAnalyticsResponse that = (StartDataFrameAnalyticsResponse) other;
        return isAcknowledged() == that.isAcknowledged()
            && Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAcknowledged(), node);
    }

    @Override
    public void addCustomFields(XContentBuilder builder, Params params) throws IOException {
        if (node != null) {
            builder.field(NODE.getPreferredName(), node);
        }
    }
}
