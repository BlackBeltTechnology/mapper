# mapper-impl Specification

## Purpose
Provides the default implementation of the coercion system, including `DefaultCoercer`, `DefaultConverterFactory`, `Java8Module`, and 60+ built-in converters covering numeric, temporal, boolean, UUID, and string conversions.

## Architecture
- `DefaultCoercer` implements `ExtendableCoercer` — the main coercion engine with a multi-step resolution strategy
- `DefaultConverterFactory` implements `ConverterFactory` — stores converters in a `ConcurrentHashMap` keyed by (sourceType, targetType) tuples
- `Java8Module` — registers all built-in converters and formatters into a `ConverterFactory`
- Converters organized by package: `numeric/`, `temporal/`, `string/`, `logical/`, `uuid/`
- Formatters in `formatters/` package provide ISO-standard date/time formatting

## Requirements

### Requirement: DefaultCoercer SHALL return null for null input
The coercer SHALL return `null` when the source value is `null`, regardless of the target type.

#### Scenario: Null source value
- **GIVEN** a configured `DefaultCoercer`
- **WHEN** `coerce(null, Integer.class)` is called
- **THEN** `null` is returned

### Requirement: DefaultCoercer SHALL return the source value when already assignable
If the source value is already an instance of the target type, it SHALL be returned without conversion.

#### Scenario: Source already matches target
- **GIVEN** a configured `DefaultCoercer`
- **WHEN** `coerce(42, Integer.class)` is called with an `Integer` source
- **THEN** the same `Integer(42)` is returned without invoking any converter

### Requirement: DefaultCoercer SHALL find exact type match converters
The coercer SHALL first look for a converter whose source and target types exactly match the requested conversion.

#### Scenario: Exact converter match
- **GIVEN** a `DefaultCoercer` with `StringToIntegerConverter` registered
- **WHEN** `coerce("42", Integer.class)` is called
- **THEN** `Integer(42)` is returned via the exact match converter

### Requirement: DefaultCoercer SHALL find assignable type converters
When no exact match exists, the coercer SHALL find a converter whose types are assignable from the source/target.

#### Scenario: Assignable converter match
- **GIVEN** a `DefaultCoercer` with a converter from `Number` to `String`
- **WHEN** `coerce(42, String.class)` is called with an `Integer` (subtype of `Number`)
- **THEN** the assignable converter is used

### Requirement: DefaultCoercer SHALL fall back to two-step String conversion
When no direct converter exists, the coercer SHALL attempt source→String→target as a fallback.

#### Scenario: String fallback conversion
- **GIVEN** a `DefaultCoercer` with no direct `Integer→Boolean` converter
- **WHEN** `coerce(1, Boolean.class)` is called
- **THEN** the coercer tries `Integer→String→Boolean` via intermediate String conversion

### Requirement: DefaultCoercer SHALL handle primitive types via autoboxing
The coercer SHALL map primitive types to their wrapper equivalents (e.g., `int.class` → `Integer.class`).

#### Scenario: Primitive target type
- **GIVEN** a configured `DefaultCoercer`
- **WHEN** `coerce("42", int.class)` is called
- **THEN** the coercer resolves `int.class` to `Integer.class` and returns `42`

### Requirement: Numeric converters SHALL parse strings to numbers
All numeric converters (`StringToIntegerConverter`, `StringToLongConverter`, `StringToBigDecimalConverter`, etc.) SHALL parse string representations into their respective numeric types.

#### Scenario: String to BigDecimal
- **WHEN** `StringToBigDecimalConverter.apply("123.45")` is called
- **THEN** `BigDecimal("123.45")` is returned

#### Scenario: String to Integer
- **WHEN** `StringToIntegerConverter.apply("42")` is called
- **THEN** `Integer(42)` is returned

### Requirement: Temporal converters SHALL convert between date/time types
Temporal converters SHALL convert between Java 8 temporal types (`LocalDateTime`, `ZonedDateTime`, `OffsetDateTime`), SQL types (`Timestamp`, `Date`, `Time`), and `java.util.Date`/`Calendar`.

#### Scenario: LocalDateTime to SqlTimestamp
- **GIVEN** a `LocalDateTimeToSqlTimestampConverter`
- **WHEN** `apply(LocalDateTime.of(2024, 1, 15, 10, 30))` is called
- **THEN** an equivalent `java.sql.Timestamp` is returned

#### Scenario: String to ZonedDateTime
- **GIVEN** a `StringToZonedDateTimeConverter` with a formatter
- **WHEN** `apply("2024-01-15T10:30:00+01:00[Europe/Budapest]")` is called
- **THEN** the corresponding `ZonedDateTime` is returned

### Requirement: Formatters SHALL use ISO standard date/time patterns
All temporal formatters SHALL use ISO standard patterns (e.g., `ISO_LOCAL_DATE`, `ISO_LOCAL_DATE_TIME`).

#### Scenario: LocalDate formatting
- **GIVEN** a `LocalDateFormatter`
- **WHEN** `convertValueToString(LocalDate.of(2024, 1, 15))` is called
- **THEN** `"2024-01-15"` is returned

### Requirement: Java8Module SHALL register all built-in converters
`Java8Module` SHALL register all 60+ converters and 12 formatters into the provided `ConverterFactory` on construction.

#### Scenario: Module initialization
- **WHEN** a new `Java8Module()` is created
- **THEN** all built-in converters are registered in the factory
- **WHEN** `destroy()` is called
- **THEN** all converters are unregistered

### Requirement: DefaultConverterFactory SHALL be thread-safe
`DefaultConverterFactory` SHALL use `ConcurrentHashMap` and synchronized blocks for thread-safe converter registration and lookup.

#### Scenario: Concurrent registration
- **GIVEN** multiple threads registering converters simultaneously
- **WHEN** converters are registered concurrently
- **THEN** all converters are correctly stored without data corruption
