# Mapper - Project Documentation

## Project Overview


**Repository:** BlackBeltTechnology/mapper
**License:** Apache License 2.0
**Java Version:** 21
**Build System:** Maven 3.9.4 with Maven Wrapper (`./mvnw`)

1. A Java type mapper and coercer library that provides generalized conversion between data types
2. Ships with 60+ built-in converters covering numeric, temporal, boolean, UUID, and string types, plus formatters for date/time
3. Uses a pluggable `Converter<S,T>` architecture — new converters can be registered at runtime
4. Supports OSGi deployment via Apache Karaf with dynamic service discovery and binding
5. Includes a Joda-Time extension module for legacy date/time compatibility

## Code Instructions

1. First think through the problem, read the codebase for relevant files.
2. Before you make any major changes, check in with me and I will verify the plan.
3. Please every step of the way just give me a high level explanation of what changes you made.
4. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity.
5. Maintain a documentation file that describes how the architecture of the app works inside and out.
6. Never speculate about code you have not opened. If the user references a specific file, you MUST read the file before answering. Make sure to investigate and read relevant files BEFORE answering questions about the codebase. Never make any claims about code before investigating unless you are certain of the correct answer - give grounded and hallucination-free answers.
7. For implementation use TDD (Test-Driven Development): write or update tests first to define the expected behaviour, verify they fail, then write the minimal implementation to make them pass.
8. Use DRY (Don't Repeat Yourself): extract reusable logic into separate classes, utilities, or components. If the same pattern appears in multiple places, refactor it into a shared helper.

## Directory Structure

```
mapper/
├── mapper-api/          # Core interfaces (Coercer, Converter, Formatter, etc.)
├── mapper-impl/         # DefaultCoercer, all built-in converters, Java8Module
├── mapper-jodatime/     # Joda-Time converter extensions
├── mapper-osgi/         # OSGi Declarative Services wiring
├── mapper-itest/        # Pax Exam integration tests (Karaf container)
├── mapper-reports/      # JaCoCo coverage aggregation
├── features/            # Karaf feature descriptors
├── kar/                 # Karaf Archive (KAR) packaging
├── .github/             # CI workflows, issue templates
├── .mvn/                # Maven wrapper config, JVM options
└── pom.xml              # Parent POM (CI-friendly versioning via ${revision})
```

## Core Modules

### API & Implementation

| Module | Type | Purpose |
|--------|------|---------|
| `mapper-api/` | API | Defines core interfaces: `Coercer`, `ExtendableCoercer`, `Converter<S,T>`, `ConverterFactory`, `ConverterRegistry`, `Formatter<T>`, `CoercerFactory` |
| `mapper-impl/` | Implementation | `DefaultCoercer` (main coercion engine), `DefaultConverterFactory`, `Java8Module` (registers 60+ converters and 12 formatters) |
| `mapper-jodatime/` | Extension | `JodaTimeModule` with `StringToLocalDateConverter`, `LocalDateToStringConverter`, and `LocalDateFormatter` for Joda-Time types |

### OSGi & Deployment

| Module | Type | Purpose |
|--------|------|---------|
| `mapper-osgi/` | OSGi Bundle | `ExtendableCoercerService` — extends `DefaultCoercer` with dynamic OSGi converter binding (GREEDY policy, DYNAMIC reference) |
| `features/` | Karaf Features | Feature descriptors for `mapper-core`, `mapper-jodatime`, `mapper-osgi` |
| `kar/` | Karaf Archive | Packages features and bundles into a deployable KAR artifact |

### Testing & Quality

| Module | Type | Purpose |
|--------|------|---------|
| `mapper-itest/` | Integration Tests | Pax Exam tests running in a Karaf 4.4.7 container |
| `mapper-reports/` | Reports | Aggregates JaCoCo code coverage across all modules |

## Technology Stack

### Core Technologies
- **Java 21** — language target and compiler version
- **SLF4J 2.0.16** — logging facade (with Logback 1.5.12 for tests)
- **Lombok 1.18.34** — `@Slf4j`, `@Getter`, boilerplate reduction
- **OSGi Core 6.0 / DS 1.3** — service component annotations and dynamic binding
- **Apache Karaf 4.4.7** — OSGi container for deployment
- **Joda-Time 2.11.2** — legacy date/time support (optional module)

### Build & Quality
- **Maven 3.9.4** with Maven Wrapper and Flatten Plugin (CI-friendly `${revision}` versioning)
- **JUnit 5.9.1** — unit testing framework
- **Mockito 4.8.0** — mocking framework
- **Hamcrest 2.2** — matcher library for assertions
- **Pax Exam 4.13.5** — OSGi integration testing in Karaf container
- **JaCoCo 0.8.12** — code coverage
- **SonarQube** (sonar-maven-plugin 3.9.1.2184) — code quality metrics
- **Felix Maven Bundle Plugin 5.1.8** — OSGi bundle packaging

## Build Commands

```bash
# Full build (compile + test + package + install)
./mvnw clean install

# Build without tests
./mvnw clean install -DskipTests

# Run all tests
./mvnw clean test

# Run a single module's tests
./mvnw test -pl mapper-impl

# Run a single test class
./mvnw test -pl mapper-impl -Dtest=DefaultCoercerTest

# Run a single test method
./mvnw test -pl mapper-impl -Dtest=DefaultCoercerTest#testStringToBigDecimalConvert

# Skip specific modules (deploy only)
./mvnw install -DdeployOnly=true
```

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `modules` | Activates all submodules (default, unless `-DskipModules=true`) |
| `sign-artifacts` | GPG-signs artifacts for release |
| `release-judong` | Deploys to JudoNG Nexus repository |
| `release-central` | Deploys to Maven Central via OSSRH |
| `generate-github-asciidoc-diagrams` | Renders AsciiDoc/PlantUML diagrams to PNG |
| `update-source-code-license` | Updates Apache 2.0 license headers in all source files |

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Parent POM — module list, dependency management, plugin configuration, profiles |
| `.mvn/jvm.config` | JVM options: `-Xms1024m -Xmx2048m -Dfile.encoding=UTF-8 -Djansi.force=true` |
| `.mvn/extensions.xml` | Maven extensions: wagon-file, wagon-webdav-jackrabbit |
| `.mvn/wrapper/maven-wrapper.properties` | Maven wrapper version configuration |
| `logback-test.xml` | Logback configuration for test execution |
| `.github/workflows/build.yml` | Main CI pipeline (build, test, deploy, tag, release) |

## Development Environment

**Required:**
- Java 21 JDK (any distribution — Zulu, Temurin, etc.)
- Maven 3.9.4+ (or use the included `./mvnw` wrapper)

**Recommended:**
- IntelliJ IDEA or VS Code with Java extensions
- Lombok plugin for your IDE

## Git Workflow

- **Main Branch:** `develop` (latest development), `master` (latest release)
- **Versioning:** `1.0.4-SNAPSHOT` (CI-friendly via `${revision}` property)
- **Branch naming:** `feature/JNG-xxx_summary`, `bugfix/JNG-xxx_summary`, `release/X.Y-betaN`
- **Commit rule:** Every commit must reference a JIRA ticket (`JNG-xxx`)
- **CI:** GitHub Actions on `judong` runner, 30-minute timeout, JDK 21 Zulu

## Important Notes

1. The `DefaultCoercer` follows a multi-step conversion strategy: null check → assignability check → exact converter → assignable converter → String fallback (source→String→target) → primitive autoboxing
2. New converters are added by implementing `Converter<S,T>` and registering them in `Java8Module` (or dynamically via OSGi)
3. Converters in mapper-impl are organized by domain: `numeric/`, `temporal/`, `string/`, `logical/`, `uuid/`, `formatters/`
4. The `Java8Module` class registers all built-in converters in a static initializer block — this is the central wiring point
5. OSGi integration uses `ExtendableCoercerService` which extends `DefaultCoercer` with dynamic converter binding (GREEDY policy)
6. The Flatten Maven Plugin handles CI-friendly versioning — `${revision}` is resolved during build
7. Integration tests use Pax Exam to spin up a real Karaf container and verify OSGi bundle deployment
8. Surefire is configured with `--add-opens` flags for Java module system compatibility

## Related Documentation

- [README](README.md) — Project introduction and architecture overview
- [Contributing Guide](CONTRIBUTING.md) — Setup instructions, submission guidelines
- [CI Flow](.github/CIFLOW.md) — Branching strategy, version numbering, CI/CD pipeline details
