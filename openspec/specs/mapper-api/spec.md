# mapper-api Specification

## Purpose
Defines the core interfaces and contracts for the type coercion system. All other modules depend on these abstractions to register, discover, and execute type conversions.

## Architecture
The API module contains seven types in `hu.blackbelt.mapper.api`:
- `Coercer` — the primary facade for type conversion
- `ExtendableCoercer` — extends `Coercer` with access to the underlying `ConverterFactory`
- `Converter<S,T>` — a typed conversion function from source type S to target type T (extends `java.util.function.Function<S,T>`)
- `ConverterFactory` — lookup converters by source type, target type, or both (extends `ConverterRegistry`)
- `ConverterRegistry` — register and unregister converter instances
- `Formatter<T>` — bidirectional string formatting for a specific type
- `CoercerFactory` — factory for obtaining `Coercer` and `ExtendableCoercer` instances
- `ConverterException` — runtime exception for conversion failures

## Requirements

### Requirement: Coercer SHALL convert a source value to a target type
The `Coercer.coerce()` method SHALL accept a source value and a target class (or class name) and return the converted value.

#### Scenario: Coerce by Class
- **GIVEN** a `Coercer` instance with registered converters
- **WHEN** `coerce(sourceValue, TargetType.class)` is called
- **THEN** the source value is converted to the target type and returned

#### Scenario: Coerce by class name
- **GIVEN** a `Coercer` instance with registered converters
- **WHEN** `coerce(sourceValue, "java.lang.Integer")` is called
- **THEN** the source value is converted to `Integer` and returned

### Requirement: Converter SHALL declare its source and target types
Each `Converter<S,T>` SHALL expose `getSourceType()` and `getTargetType()` so the registry can index and look up converters.

#### Scenario: Converter type introspection
- **GIVEN** a `Converter<String, Integer>` instance
- **WHEN** `getSourceType()` is called
- **THEN** `String.class` is returned
- **WHEN** `getTargetType()` is called
- **THEN** `Integer.class` is returned

### Requirement: Converter SHALL implement Function.apply
`Converter<S,T>` extends `Function<S,T>`, so `apply(S)` SHALL perform the actual conversion.

#### Scenario: Apply conversion
- **GIVEN** a `Converter<String, Integer>` instance
- **WHEN** `apply("42")` is called
- **THEN** `Integer` value `42` is returned

### Requirement: ConverterFactory SHALL look up converters by type
`ConverterFactory` SHALL provide methods to find converters by source type, target type, target type name, or source+target pair.

#### Scenario: Find converters from a source type
- **GIVEN** a factory with `Converter<String, Integer>` and `Converter<String, Boolean>` registered
- **WHEN** `getConvertersFrom(String.class)` is called
- **THEN** both converters are returned

#### Scenario: Find converters for a source-target pair
- **GIVEN** a factory with `Converter<String, Integer>` registered
- **WHEN** `getConverters(String.class, Integer.class)` is called
- **THEN** the String-to-Integer converter is returned

### Requirement: ConverterRegistry SHALL support dynamic registration
`ConverterRegistry` SHALL allow converters to be registered and unregistered at runtime.

#### Scenario: Register and unregister a converter
- **GIVEN** an empty `ConverterRegistry`
- **WHEN** `registerConverter(converter)` is called
- **THEN** the converter becomes available via `ConverterFactory` lookups
- **WHEN** `unregisterConverter(converter)` is called
- **THEN** the converter is no longer available

### Requirement: Formatter SHALL provide bidirectional string conversion
`Formatter<T>` SHALL convert values to strings and parse strings back to values.

#### Scenario: Format and parse a value
- **GIVEN** a `Formatter<LocalDate>` instance
- **WHEN** `convertValueToString(localDate)` is called
- **THEN** an ISO date string is returned
- **WHEN** `parseString("2024-01-15")` is called
- **THEN** a `LocalDate` of 2024-01-15 is returned

### Requirement: CoercerFactory SHALL produce Coercer instances
`CoercerFactory` SHALL provide factory methods for creating `Coercer` and `ExtendableCoercer` instances.

#### Scenario: Obtain a coercer instance
- **WHEN** `getCoercerInstance()` is called
- **THEN** a fully configured `Coercer` is returned
- **WHEN** `getExtendableCoercerInstance()` is called
- **THEN** an `ExtendableCoercer` with accessible `ConverterFactory` is returned
