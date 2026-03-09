# mapper-itest Specification

## Purpose
Integration tests that verify the mapper library works correctly when deployed as OSGi bundles in an Apache Karaf container.

## Architecture
- Uses Pax Exam 4.13.5 as the OSGi testing framework
- Tests run inside a real Karaf 4.4.7 container
- Pax Tinybundles used for creating test bundles on the fly
- Verifies that `Coercer` service is properly registered and functional in OSGi

## Requirements

### Requirement: Mapper bundles SHALL deploy correctly in Karaf
All mapper OSGi bundles SHALL resolve and start without errors in a Karaf container.

#### Scenario: Bundle deployment
- **GIVEN** a Karaf container with mapper features installed
- **WHEN** the container starts
- **THEN** all mapper bundles are in ACTIVE state

### Requirement: Coercer service SHALL be available in OSGi
The `Coercer` service SHALL be discoverable and usable from within the OSGi container.

#### Scenario: Service lookup
- **GIVEN** a running Karaf container with mapper bundles
- **WHEN** looking up `Coercer` from the OSGi service registry
- **THEN** a functional `Coercer` instance is returned

### Requirement: Coercion SHALL work end-to-end in OSGi
Type conversions SHALL produce correct results when invoked through the OSGi-registered `Coercer` service.

#### Scenario: End-to-end conversion
- **GIVEN** a `Coercer` obtained from the OSGi service registry
- **WHEN** `coerce("42", Integer.class)` is called
- **THEN** `Integer(42)` is returned
