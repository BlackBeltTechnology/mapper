# Mapper

[![Build](https://github.com/BlackBeltTechnology/mapper/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/BlackBeltTechnology/mapper/actions/workflows/build.yml)

## Introduction

Mapper is a Java type coercion library by [BlackBelt Technology](https://www.blackbelt.hu) that generalizes conversion between data types. It provides a pluggable `Converter<S,T>` system with 60+ built-in converters covering numeric, temporal, boolean, UUID, and string types. The library supports both standalone Java usage and OSGi deployment via Apache Karaf.

The core idea is simple: call `coercer.coerce(value, TargetType.class)` and the library finds and applies the right converter chain automatically, including a two-step fallback through `String` when no direct converter exists.

## Architecture

### Module Dependency Graph

```mermaid
graph TD
    API[mapper-api<br/><i>Interfaces</i>]
    IMPL[mapper-impl<br/><i>Converters & DefaultCoercer</i>]
    JODA[mapper-jodatime<br/><i>Joda-Time converters</i>]
    OSGI[mapper-osgi<br/><i>OSGi service wiring</i>]
    ITEST[mapper-itest<br/><i>Integration tests</i>]
    FEAT[features<br/><i>Karaf feature descriptors</i>]
    KAR[kar<br/><i>Karaf archive</i>]
    REPORTS[mapper-reports<br/><i>JaCoCo coverage</i>]

    IMPL --> API
    JODA --> API
    JODA --> IMPL
    OSGI --> IMPL
    ITEST -.->|test| OSGI
    ITEST -.->|test| JODA
    FEAT --> OSGI
    FEAT --> JODA
    KAR --> FEAT
    REPORTS -.->|aggregate| IMPL
    REPORTS -.->|aggregate| JODA
```

### Class Diagram — Core API

```mermaid
classDiagram
    class Coercer {
        <<interface>>
        +coerce(S sourceValue, Class~T~ targetClass) T
        +coerce(S sourceValue, String targetClassName) T
    }

    class ExtendableCoercer {
        <<interface>>
        +getConverterFactory() ConverterFactory
    }

    class Converter~S,T~ {
        <<interface>>
        +getSourceType() Class~S~
        +getTargetType() Class~T~
        +apply(S s) T
    }

    class ConverterFactory {
        <<interface>>
        +getConvertersFrom(Class~S~) Collection~Converter~
        +getConvertersTo(Class~T~) Collection~Converter~
        +getConverters(Class~S~, Class~T~) Collection~Converter~
    }

    class ConverterRegistry {
        <<interface>>
        +registerConverter(Converter~S,T~)
        +unregisterConverter(Converter~S,T~)
    }

    class Formatter~T~ {
        <<interface>>
        +convertValueToString(T value) String
        +parseString(String str) T
        +getType() Class~T~
    }

    class DefaultCoercer {
        +coerce(S, Class~T~) T
        +getConverterFactory() ConverterFactory
    }

    class DefaultConverterFactory {
        -converters: Map~Key, Collection~Converter~~
    }

    class Java8Module {
        +decorateWithJava8(ExtendableCoercer)$
        +getConverterFactory() ConverterFactory
        +destroy()
    }

    ExtendableCoercer --|> Coercer
    ConverterFactory --|> ConverterRegistry
    DefaultCoercer ..|> ExtendableCoercer
    DefaultConverterFactory ..|> ConverterFactory
    DefaultCoercer --> DefaultConverterFactory : uses
    Java8Module --> DefaultConverterFactory : populates
    Converter~S,T~ --> Formatter~T~ : some use
```

### Coercion Flow

The `DefaultCoercer.coerce()` method follows a multi-step resolution strategy:

```mermaid
flowchart TD
    START([coerce sourceValue, targetClass]) --> NULL{sourceValue == null?}
    NULL -->|Yes| RETURN_NULL([return null])
    NULL -->|No| ASSIGN{targetClass.isAssignableFrom?}
    ASSIGN -->|Yes| RETURN_SRC([return sourceValue as-is])
    ASSIGN -->|No| EXACT{Exact converter exists?}
    EXACT -->|Yes| CONVERT([apply converter])
    EXACT -->|No| COMPAT{Assignable converter exists?}
    COMPAT -->|Yes| CONVERT
    COMPAT -->|No| STR_FALLBACK{Source is not String?}
    STR_FALLBACK -->|Yes| TWO_STEP([source → String → target])
    STR_FALLBACK -->|No| PRIM{Primitive autoboxing?}
    PRIM -->|Yes| AUTOBOX([coerce via wrapper type])
    PRIM -->|No| FAIL([throw ConverterException])
```

### Built-in Converter Categories

The library ships with converters organized by domain:

| Package | Category | Examples |
|---------|----------|----------|
| `numeric/` | Number parsing | String to Integer, Long, BigDecimal, Double, Float, Short, Byte, BigInteger |
| `temporal/` | Date/time conversion | Between LocalDateTime, ZonedDateTime, OffsetDateTime, SqlTimestamp, SqlDate, SqlTime, LocalDate, LocalTime |
| `string/` | To-string formatting | Date, Calendar, LocalDate/Time, OffsetDateTime, ZonedDateTime, Sql types to String |
| `logical/` | Boolean parsing | String to Boolean |
| `uuid/` | UUID handling | String to UUID |
| `formatters/` | Temporal formatting | ISO formatters for all date/time types |

### Dependency Graph — External Libraries

```mermaid
graph LR
    subgraph External
        SLF4J[SLF4J 2.0]
        Lombok[Lombok 1.18]
        OSGiCore[OSGi Core 6.0]
        OSGiDS[OSGi DS 1.3]
        Karaf[Karaf 4.4.7]
        JodaTime[Joda-Time 2.11]
    end
    subgraph Mapper
        API[mapper-api]
        IMPL[mapper-impl]
        OSGI_MOD[mapper-osgi]
        JODA_MOD[mapper-jodatime]
    end

    API --> SLF4J
    IMPL --> SLF4J
    IMPL --> Lombok
    OSGI_MOD --> OSGiCore
    OSGI_MOD --> OSGiDS
    JODA_MOD --> JodaTime
    OSGI_MOD -.->|deployed in| Karaf
```

## Build Commands

The project uses Maven with a wrapper script. Java 21 is required.

```bash
# Full build (compile + test + package)
./mvnw clean install

# Build without tests
./mvnw clean install -DskipTests

# Run all tests
./mvnw clean test

# Run a single test class
./mvnw test -pl mapper-impl -Dtest=DefaultCoercerTest

# Run a single test method
./mvnw test -pl mapper-impl -Dtest=DefaultCoercerTest#testStringToBigDecimalConvert
```

## Contributing to the Project

Everyone is welcome to contribute to Mapper! Please see the [Contributing Guide](CONTRIBUTING.md) for details on how to get started, submit issues, and create pull requests.

## License

This project is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
