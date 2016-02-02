# GELF Appender for Logback

[![Build Status](https://travis-ci.org/rkcpi/logback-gelf-appender.svg?branch=master)](https://travis-ci.org/rkcpi/logback-gelf-appender) [![Maven Central](https://img.shields.io/maven-central/v/de.appelgriepsch.logback/logback-gelf-appender.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22de.appelgriepsch.logback%22%20AND%20a%3A%22logback-gelf-appender%22)

This logback appender logs messages to a Graylog server. It's using the official [GELF Java client](https://graylog2.github.io/gelfclient/) to connect to the remote server. It's inspired and influenced greatly by the [Log4j2 GELF Appender](http://graylog2.github.io/log4j2-gelf/).

The appender has several configurable properties:

* `server` (default: `localhost`)
  * The host name or IP address of the GELF server
* `port` (default: `12201`)
  * The port the GELF server is listening on
* `hostName` (default: the local host name or `localhost` if it couldn't be detected)
  * The host name of the machine generating the logs
* `protocol` (default: `UDP`)
  * The transport protocol to use
* `includeSource` (default: `true`)
  * Whether the source of the log message should be included
* `includeMDC` (default: `true`)
  * Whether the contents of the [Mapped Diagnostic Context ](http://logback.qos.ch/manual/mdc.html) should be included
* `includeStackTrace` (default: `true`)
  * Whether a full stack trace should be included
* `includeLevelName` (default: `false`)
  * Whether the log level's name should be included, e.g. 'WARN', 'INFO' ...
* `queueSize` (default: `512`)
  * The size of the internally used queue
* `connectTimeout` (default: `1000`)
  * The connection timeout for TCP connections in milliseconds
* `reconnectDelay` (default: `500`)
  * The time to wait between reconnects in milliseconds
* `sendBufferSize` (default: `-1`)
  * The size of the socket send buffer in bytes. A size of -1 deactivates the send buffer
* `tcpNoDelay` (default: `false`)
  * Whether Nagle's algorithm should be used for TCP connections
* `tcpKeepAlive` (default: `false`)
  * Whether to try keeping alive TCP connections.
* `filter`
  * A [Filter](http://logback.qos.ch/manual/filters.html) to determine if the event should be handled by this Appender
* `layout` (default: `"%m%n"`)
  * The [Layout](http://logback.qos.ch/manual/layouts.html) to use to format the LogEvent
* `additionalFields`
  * Comma-delimited list of key=value pairs to be included in every message

## Logback.xml example

    <configuration debug="true">    
        <appender name="gelf" class="de.appelgriepsch.logback.GelfAppender">
            <server>localhost</server>
            <port>12201</port>
            <hostName>localhost</hostName>
            <protocol>UDP</protocol>
            <includeSource>true</includeSource>
            <includeThreadContext>true</includeThreadContext>
            <includeMDC>true</includeMDC>
            <queueSize>512</queueSize>
            <connectTimeout>1000</connectTimeout>
            <reconnectDelay>500</reconnectDelay>
            <additionalFields>cow=moo,cat=meow</additionalFields>
        </appender>
        <root level="DEBUG">
            <appender-ref ref="gelf" />
        </root>
    </configuration>

# License

GELF Appender for Logback

Copyright (C) 2016 Sandra Thieme <thieme@synyx.de>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
