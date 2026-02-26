# mapper-osgi Specification

## Purpose
Provides OSGi runtime integration for the mapper library, exposing `Coercer` as an OSGi Declarative Service with dynamic converter binding.

## Architecture
- `ExtendableCoercerService` — extends `DefaultCoercer`, annotated as an OSGi `@Component` providing `Coercer.class` service
- Uses GREEDY reference policy option and DYNAMIC reference policy for `Converter` service references
- Multiple cardinality allows any number of converters to be bound dynamically
- Tracks additional converters in a `LinkedList<Converter>` for lifecycle management
- `@Activate` method initializes the service and registers additional converters from OSGi
- `@Modified` handler exists but performs no action
- `@Deactivate` handler exists for cleanup

## Requirements

### Requirement: ExtendableCoercerService SHALL register as OSGi Coercer service
The service SHALL be registered as an OSGi component providing the `Coercer` service interface with `immediate = true` and `configurationPolicy = REQUIRE`.

#### Scenario: OSGi service registration
- **GIVEN** an OSGi container with the mapper-osgi bundle installed
- **WHEN** the bundle is activated with required configuration
- **THEN** a `Coercer` service is registered in the OSGi service registry

### Requirement: ExtendableCoercerService SHALL dynamically bind converters
The service SHALL use OSGi dynamic reference binding to automatically discover and bind `Converter` services.

#### Scenario: Dynamic converter binding
- **GIVEN** a running `ExtendableCoercerService`
- **WHEN** a new `Converter` OSGi service is registered
- **THEN** the converter is automatically bound and available for coercion

#### Scenario: Dynamic converter unbinding
- **GIVEN** a running `ExtendableCoercerService` with a bound converter
- **WHEN** the converter's OSGi service is unregistered
- **THEN** the converter is automatically unbound and no longer used

### Requirement: ExtendableCoercerService SHALL include all built-in converters
On activation, the service SHALL include all converters from `Java8Module` plus any dynamically bound OSGi converters.

#### Scenario: Service activation
- **WHEN** the service is activated via `@Activate`
- **THEN** all Java8Module converters are registered
- **THEN** any additional OSGi-bound converters are also registered
