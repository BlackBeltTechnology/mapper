# kar Specification

## Purpose
Packages the mapper Karaf features and their bundles into a single deployable Karaf Archive (KAR) file.

## Architecture
- Uses `karaf-maven-plugin` with `kar` goal to assemble the archive
- Depends on the `features` module for feature descriptors
- The resulting `.kar` file can be dropped into a Karaf deploy directory for automatic installation

## Requirements

### Requirement: KAR SHALL contain all mapper features and bundles
The KAR file SHALL package all feature descriptors and OSGi bundles needed to run the mapper in Karaf.

#### Scenario: KAR assembly
- **WHEN** `./mvnw package -pl kar` is run
- **THEN** a `.kar` file is produced containing feature XML and all required bundle JARs

### Requirement: KAR SHALL be deployable to Karaf
The produced KAR file SHALL be installable by copying it to a Karaf container's deploy directory.

#### Scenario: KAR deployment
- **GIVEN** a running Karaf container
- **WHEN** the KAR file is placed in the deploy directory
- **THEN** all mapper features are installed and bundles are started
