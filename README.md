## What this repo contains

- **`SqlCastFilterDemo`**: `main` builds a Calcite Rex tree (`CAST(int AS VARCHAR) = '1'`) and runs **`FilterPredicateImpl`** on **`Record`** rows.
- **Tests**: same predicate as a JUnit test; **`SqlRuntimeCastTypeCoverageTest`** classifies every Calcite **`SqlTypeName`** as supported or not by **`SqlRuntimeCast`**.
- **Dependency**: **`org.apache.wayang:wayang-api-sql`** (the CAST implementation lives in that artifact, not in this repo).

This repo does **not** fork all of Apache Wayang; it is a **thin client** that exercises a published or locally installed `wayang-api-sql` JAR.

---

## Prerequisites

- **JDK 17** (aligned with current Wayang builds).
- **Apache Maven 3.8+**.
- **`wayang-api-sql`** on the classpath, either:
  - **A)** installed into your local Maven repo from the Apache Wayang source tree (`mvn install`), or  
  - **B)** a **released** version from Maven Central, if available for your chosen version (set `wayang.version` in the POM below).

---

## Layout to copy from the Wayang monorepo

Copy into the **root** of your new repository:

```
src/main/java/org/apache/wayang/api/sql/demo/SqlCastFilterDemo.java
src/test/java/org/apache/wayang/api/sql/demo/SqlCastFilterDemoTest.java
src/test/java/org/apache/wayang/api/sql/demo/SqlRuntimeCastTypeCoverageTest.java
pom.xml          ← replace with the standalone POM below (not the monorepo parent)
README.md        ← this file’s body, or merge with your edits
```

Optional: add **Maven Wrapper** (`mvn -N wrapper:wrapper`) so contributors can run `./mvnw` without a global Maven install.

---

## Standalone `pom.xml` (replace monorepo parent)

The monorepo module uses `<parent>wayang-api</parent>`. In a **separate** repo, use a **minimal parent** and pin **`wayang-api-sql`** explicitly.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>fix-for-wayang-676-sql-cast-demo</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>Wayang SQL CAST demo (standalone)</name>
    <description>Demo and tests for wayang-api-sql FilterPredicateImpl CAST (issue #676)</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>17</maven.compiler.release>
        <!-- Match a Wayang release or your locally installed version -->
        <wayang.version>1.1.2-SNAPSHOT</wayang.version>
        <junit.version>5.12.2</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.wayang</groupId>
            <artifactId>wayang-api-sql</artifactId>
            <version>${wayang.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>${maven.compiler.release}</release>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>org.apache.wayang.api.sql.demo.SqlCastFilterDemo</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

Adjust **`groupId` / `artifactId`** to your GitHub org or username. Set **`wayang.version`** to the same version you installed locally or to a Central release.

### Install `wayang-api-sql` locally (if not on Central)

From a clone of **[apache/wayang](https://github.com/apache/wayang)**:

```bash
cd /path/to/wayang
./mvnw -pl wayang-api/wayang-api-sql -am install -DskipTests
```

Output [Sucess] 

<img width="1898" height="1080" alt="image" src="https://github.com/user-attachments/assets/31eb75eb-4476-42de-a024-fa47d48abe1b" />


Then in **this** demo repo:

```bash
mvn clean test
mvn exec:java
```

---

## Run the demo application

From the **root** of the standalone repo (where `pom.xml` lives):

```bash
mvn -q exec:java
```

Expected output:

```text
CAST(int_col AS VARCHAR) = '1'
  row (1,) -> true
  row (2,) -> false
```

<img width="1461" height="475" alt="image" src="https://github.com/user-attachments/assets/784bbe72-5094-4181-8456-cbdf9b0fe9e6" />


With Maven Wrapper:

```bash
./mvnw -q exec:java
```

---

## Run tests

```bash
mvn clean test
```

Or:

```bash
./mvnw clean test
```

Output [Sucess]

<img width="1961" height="1072" alt="image" src="https://github.com/user-attachments/assets/6327a02a-5c0d-4206-bd73-8d516eb85a95" />


You should see JUnit run **`SqlCastFilterDemoTest`** and **`SqlRuntimeCastTypeCoverageTest`** (many parameterized cases for unsupported `SqlTypeName` values).

---

## Troubleshooting

| Symptom | What to do |
|--------|------------|
| `Could not find artifact org.apache.wayang:wayang-api-sql` | Install Wayang’s `wayang-api-sql` locally (see above) or set `wayang.version` to a published release. |
| `java.lang.Error: Unresolved compilation problems` inside `SqlRuntimeCast` | Stale or IDE-broken `wayang-api-sql` JAR; reinstall: `./mvnw -pl wayang-api/wayang-api-sql clean install -DskipTests` from Wayang source. |
| Tests fail after upgrading Calcite / Wayang | Regenerate expectations in **`SqlRuntimeCastTypeCoverageTest`** if new **`SqlTypeName`** enum constants appear. |

---
