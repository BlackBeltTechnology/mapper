# features Specification

## Purpose
Defines Apache Karaf feature descriptors that package mapper modules into installable features for Karaf container deployment.

## Architecture
- Uses `karaf-maven-plugin` to generate feature XML descriptors
- Defines three features: `mapper-core`, `mapper-jodatime`, `mapper-osgi`
- Feature verification runs against Karaf 4.4.7 framework to ensure compatibility
- The `kar` module packages these features into a deployable Karaf Archive

## Requirements

### Requirement: Feature descriptors SHALL be generated from Maven dependencies
The Karaf Maven plugin SHALL generate feature XML files based on the module's Maven dependency tree.

#### Scenario: Feature generation
- **WHEN** `./mvnw package -pl features` is run
- **THEN** a feature XML file is generated containing all transitive dependencies as bundles

### Requirement: Features SHALL pass Karaf verification
All generated features SHALL pass the Karaf feature verification phase to ensure they can be installed in a Karaf container.

#### Scenario: Feature verification
- **GIVEN** generated feature descriptors
- **WHEN** the Maven verify phase runs
- **THEN** all features resolve successfully against Karaf 4.4.7 framework
