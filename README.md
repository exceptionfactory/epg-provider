# Electronic Program Guide Provider

[![build](https://github.com/exceptionfactory/epg-provider/actions/workflows/build.yml/badge.svg)](https://github.com/exceptionfactory/epg-provider/actions/workflows/build.yml)

The Electronic Program Guide ([EPG](https://en.wikipedia.org/wiki/Electronic_program_guide))
Provider is a web application that returns TV Listings formatted according to the [XMLTV](https://www.xmltv.org)
specification.

## Minimum Requirements

- Java 17

## Listing Providers

### Zap2it

The EPG Provider retrieves channel and program information from
[Zap2it](https://tvlistings.zap2it.com) as [JSON](https://en.wikipedia.org/wiki/JSON) and formats responses
according to the [XMLTV DTD](https://github.com/XMLTV/xmltv/blob/master/xmltv.dtd).
The [Zap2it Terms of Use](https://feedback.zap2it.com/terms-of-use/) limit usage to personal and noncommercial purposes
unless otherwise permitted.

## REST API

The EPG Provider supports the following REST API resources:

### Get Listings

Get TV Listings for specified locality and duration.

```
GET /listings/zap2it/:country/:postalCode/:headendId
```

#### Parameters

|Name            |Type        |Description                                                            |
|----------------|------------|-----------------------------------------------------------------------|
|```country```   |```string```|**Required.** ISO 3166-1 alpha 3 country code must be *CAN* or *USA*   |
|```postalCode```|```string```|**Required.** Alphanumeric Postal Code for locality                    |
|```headendId``` |```string```|**Required.** Head end identifier based on Zap2it identifiers          |
|```duration```  |```string```|ISO 8601 Duration for results defaults to *PT6H* for six hours from now|

#### Example

The following command retrieves listings for the default head end located in Postal Code 10001 within the USA
for a duration of 12 hours.

```shell script
curl http://localhost:8080/listings/zap2it/USA/10001/DFLTE?duration=PT12H
```

##### Response

```
HTTP/1.1 200
Content-Type: application/xml
```
```xml
<tv source-info-url="https://tvlistings.zap2it.com"
    source-info-name="Zap2it"
    source-data-url="https://tvlistings.zap2it.com/api/grid">
  <channel id="100030.tvlistings.zap2it.com">
    <display-name lang="en">1</display-name>
    <display-name lang="en">1 ABC</display-name>
    <display-name lang="en">ABC</display-name>
    <icon src="https://zap2it.tmsimg.com/h3/NowShowing/10003/s10003_h3_aa.png"/>
  </channel>
  <programme start="20200101000000 +0000"
             stop="20200101010000 +0000"
             channel="100030.tvlistings.zap2it.com">
    <title lang="en">SIGN OFF</title>
    <desc lang="en">Sign off.</desc>
    <length units="minutes">60</length>
    <icon src="https://zap2it.tmsimg.com/assets/p480440_b_v8_ax.jpg"/>
    <episode-num system="dd_progid">SH00019112.0000</episode-num>
  </programme>
</tv>
```

## Implementation

The application is written in [Kotlin](https://kotlinlang.org/)
and is implemented using [Spring Boot](https://spring.io/projects/spring-boot).

The implementation leverages the Spring Reactive
[WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)
interface for accessing providers over HTTP and relies on [Jackson](https://github.com/FasterXML/jackson)
for processing [JSON](https://github.com/FasterXML/jackson-databind)
and producing [XML](https://github.com/FasterXML/jackson-dataformat-xml).
The application implements caching of listing information using the 
[Caffeine](https://github.com/ben-manes/caffeine) library.

### Building

The application relies on [Gradle](https://gradle.org) for compilation and artifact generation.

The following command can be used to build an executable JAR:

```shell script
./gradlew bootJar
```

The following command can be used to build a container image for distribution or execution:

```shell script
./gradlew bootBuildImage
```