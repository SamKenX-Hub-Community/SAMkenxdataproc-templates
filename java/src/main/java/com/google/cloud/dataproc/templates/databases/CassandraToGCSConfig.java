/*
 * Copyright (C) 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.dataproc.templates.databases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Properties;
import org.apache.spark.sql.SaveMode;

public class CassandraToGCSConfig {

  public static final String CASSANDRA_TO_GSC_INPUT_KEYSPACE = "cassandratogcs.input.keyspace";

  public static final String CASSANDRA_TO_GSC_INPUT_TABLE = "cassandratogcs.input.table";
  public static final String CASSANDRA_TO_GSC_INPUT_HOST = "cassandratogcs.input.host";
  public static final String CASSANDRA_TO_GSC_OUTPUT_FORMAT = "cassandratogcs.output.format";
  public static final String CASSANDRA_TO_GSC_OUTPUT_SAVE_MODE = "cassandratogcs.output.savemode";
  public static final String CASSANDRA_TO_GSC_OUTPUT_PATH = "cassandratogcs.output.path";
  public static final String CASSANDRA_TO_GSC_INPUT_CATALOG = "cassandratogcs.input.catalog.name";
  public static final String CASSANDRA_TO_GSC_INPUT_QUERY = "cassandratogcs.input.query";
  static final ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @JsonProperty(value = CASSANDRA_TO_GSC_INPUT_KEYSPACE)
  @NotEmpty
  private String keyspace;

  @JsonProperty(value = CASSANDRA_TO_GSC_INPUT_TABLE)
  @NotEmpty
  private String inputTable;

  @JsonProperty(value = CASSANDRA_TO_GSC_INPUT_HOST)
  @NotEmpty
  private String host;

  @JsonProperty(value = CASSANDRA_TO_GSC_OUTPUT_FORMAT)
  @NotEmpty
  @Pattern(regexp = "avro|parquet|orc|csv")
  private String outputFormat;

  @JsonProperty(value = CASSANDRA_TO_GSC_OUTPUT_SAVE_MODE)
  @NotEmpty
  @Pattern(regexp = "Overwrite|ErrorIfExists|Append|Ignore")
  private String saveMode;

  @JsonProperty(value = CASSANDRA_TO_GSC_OUTPUT_PATH)
  @NotEmpty
  @Pattern(regexp = "gs://*")
  private String outputpath;

  @JsonProperty(value = CASSANDRA_TO_GSC_INPUT_CATALOG)
  private String catalog = "casscon";

  @JsonProperty(value = CASSANDRA_TO_GSC_INPUT_QUERY)
  @NotEmpty
  private String query;

  public String getKeyspace() {
    return keyspace;
  }

  public String getOutputFormat() {
    return outputFormat;
  }

  public String getOutputpath() {
    return outputpath;
  }

  public String getHost() {
    return host;
  }

  public String getInputTable() {
    return inputTable;
  }

  public String getSaveModeString() {
    return saveMode;
  }

  public String getQuery() {
    return query;
  }

  public String getCatalog() {
    return catalog;
  }

  @JsonIgnore
  public SaveMode getSaveMode() {
    return SaveMode.valueOf(getSaveModeString());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("inputTable", inputTable)
        .add("keyspace", keyspace)
        .add("outputFormat", outputFormat)
        .add("outputPath", outputpath)
        .add("catalog", catalog)
        .add("saveMode", saveMode)
        .add("host", host)
        .add("query", query)
        .toString();
  }

  public static CassandraToGCS fromProperties(Properties properties) {
    return mapper.convertValue(properties, CassandraToGCS.class);
  }
}
