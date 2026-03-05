# mapper-jodatime Specification

## Purpose
Provides Joda-Time type converters as an optional extension module, enabling bidirectional conversion between `org.joda.time.LocalDate` and `String`.

## Architecture
- `JodaTimeModule` — registers Joda-Time converters into a `ConverterFactory` (analogous to `Java8Module`)
- `StringToLocalDateConverter` — converts `String` to `org.joda.time.LocalDate` using an injected `Formatter`
- `LocalDateToStringConverter` — converts `org.joda.time.LocalDate` to `String` using an injected `Formatter`
- `LocalDateFormatter` — uses `org.joda.time.format.DateTimeFormat` for ISO date formatting
- Supports the decorator pattern via `JodaTimeModule.decorateWithJodaTime(ExtendableCoercer)`

## Requirements

### Requirement: JodaTimeModule SHALL register Joda-Time converters
`JodaTimeModule` SHALL register `StringToLocalDateConverter`, `LocalDateToStringConverter`, and `LocalDateFormatter` on construction.

#### Scenario: Module initialization
- **WHEN** a new `JodaTimeModule()` is created
- **THEN** the Joda-Time converters and formatter are registered in the factory

### Requirement: JodaTimeModule SHALL support decorator pattern
`JodaTimeModule.decorateWithJodaTime(ExtendableCoercer)` SHALL add Joda-Time converters to an existing coercer instance.

#### Scenario: Decorate an existing coercer
- **GIVEN** a `DefaultCoercer` with Java 8 converters registered
- **WHEN** `JodaTimeModule.decorateWithJodaTime(coercer)` is called
- **THEN** Joda-Time converters are added to the coercer's factory

### Requirement: StringToLocalDateConverter SHALL parse strings to Joda LocalDate
The converter SHALL parse ISO date strings into `org.joda.time.LocalDate` using the configured formatter.

#### Scenario: Parse ISO date string
- **GIVEN** a `StringToLocalDateConverter` with default formatter
- **WHEN** `apply("2024-01-15")` is called
- **THEN** an `org.joda.time.LocalDate` of 2024-01-15 is returned

### Requirement: LocalDateToStringConverter SHALL format Joda LocalDate to string
The converter SHALL format `org.joda.time.LocalDate` values to ISO date strings.

#### Scenario: Format Joda LocalDate
- **GIVEN** a `LocalDateToStringConverter` with default formatter
- **WHEN** `apply(new LocalDate(2024, 1, 15))` is called
- **THEN** `"2024-01-15"` is returned
