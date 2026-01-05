# Spring Cloud Config Server – Complete Notes (README)

This document explains 
**what Spring Cloud Config Server is**,
**why we use it**,
**how to set it up**,
**how profiles work**,
**why default configs are loaded**,
**important precautions & best practices**.
Read this anytime in the future to quickly understand the entire setup.

---

## 1. What is Spring Cloud Config Server?

Spring Cloud Config Server is a **centralized configuration management system** for microservices.

Instead of keeping `application.yml` or `application.properties` inside every microservice:

* All configurations are stored in **one central Git repository**
* Config Server fetches configurations from Git
* Client microservices load configuration from Config Server at startup

This helps in:

* Centralized config management
* Environment-based configuration (dev, qa, prod)
* No redeploy required for config changes

---

## 2. Why do we need Config Server?

Problems without Config Server:

* Duplicate configs in every microservice
* Difficult to manage environment changes
* Restart/redeploy required for config updates

Benefits with Config Server:

* Single source of truth (Git)
* Environment separation (dev / prod)
* Easy rollback using Git history
* Dynamic refresh support

---

## 3. Architecture Overview

```
Git Repository (configs)
        ↓
Spring Cloud Config Server
        ↓
Client Microservices
```

---

## 4. Config Server Setup (Step-by-Step)

### 4.1 Dependencies

Add dependency:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

### 4.2 Enable Config Server

```java
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
}
```

### 4.3 application.properties (or yml)

```properties
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/your-org/config-repo
```

Config Server now exposes endpoints like:

```
http://localhost:8888/{application}/{profile}
```

---

## 5. Git Repository Structure (Very Important)

Example for a client app named **configdemo**:

```
configdemo.yml
configdemo-dev.yml
configdemo-prod.yml
```

### Naming rule:

```
{application-name}-{profile}.yml
```

`spring.application.name` in client **must match** the filename.

---

## 6. How Config Loading Works (Core Concept)

### Base Configuration

`configdemo.yml` → **Base / default configuration**

### Profile Configuration

* `configdemo-dev.yml`
* `configdemo-prod.yml`

These **override the base config**.

---

## 7. Why DEFAULT configs are loaded for DEV / PROD

This is **by design**, not a defect.

When you call:

```
/configdemo/dev
```

Spring Cloud Config loads:

1. `configdemo.yml` (base)
2. `configdemo-dev.yml` (profile override)

Final response = **MERGED configuration**

### Example

#### configdemo.yml

```yaml
server:
  port: 8080
db:
  url: jdbc:mysql://localhost/base
  username: root
```

#### configdemo-dev.yml

```yaml
db:
  url: jdbc:mysql://localhost/dev
```

#### Final response (/configdemo/dev)

* `server.port` → 8080 (from base)
* `db.username` → root (from base)
* `db.url` → dev value (override)

✔ Common configs stay common
✔ Environment configs override only what is needed

---

## 8. Config Priority Order

Highest priority → Lowest priority:

1. `configdemo-{profile}.yml`
2. `configdemo.yml`
3. `application-{profile}.yml`
4. `application.yml`

Profile files always override base values.

---

## 9. Client Application – Minimum Required Setup

Even though configs are centralized, **client needs minimal config**.

### 9.1 Dependency

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

### 9.2 bootstrap.yml / application.yml

```yaml
spring:
  application:
    name: configdemo
  cloud:
    config:
      uri: http://localhost:8888
      profile: dev
```

Client now automatically fetches configs from Config Server.

---

## 10. What happens at Client Startup

1. Client starts
2. Reads `spring.application.name`
3. Connects to Config Server
4. Sends application name + profile
5. Config Server fetches from Git
6. Merges base + profile config
7. Client starts with external configuration

---

## 11. Common Mistakes to Avoid (Precautions)

❌ Application name mismatch

* `spring.application.name` must match Git filename

❌ Putting secrets in Git

* Use encryption or vault for passwords

❌ Large configs in profile files

* Keep common configs in base file

❌ Assuming default config should not load

* Default loading is **expected behavior**

❌ Using only application.yml in client

* Prefer `bootstrap.yml` for config server

---

## 12. Best Practices (Recommended)

✔ Keep common configs in `{app}.yml`
✔ Override only environment-specific values
✔ Use Git versioning for rollback
✔ Enable actuator refresh for runtime updates
✔ Separate repos for prod if security is critical

---

## 13. If You Want ONLY dev/prod configs (Not Recommended)

Options:

1. Leave base file empty
2. Use separate config repos per environment

⚠ Not recommended due to duplication and maintenance issues.

---

## 14. One-Line Summary

Spring Cloud Config Server **merges base configuration with profile-specific configuration by design**,
where profile configs override defaults, enabling clean, scalable, and maintainable microservice configuration management.

---

# spring.config.import & `configserver:` Protocol – Complete Explanation

This document explains **what `spring.config.import` is**, **what the `configserver:` protocol means**, **why it exists**, **how it works internally**, and **what happens if the Config Server is unavailable**.
This README is written so that even after months, you can read it once and immediately understand everything.

---

## 1. What is `spring.config.import`?

Starting from **Spring Boot 2.4+**, Spring introduced a new configuration loading mechanism called the **Config Data API**.

Instead of automatically loading configurations from many hidden places, Spring now requires **explicit configuration imports**.

```yaml
spring:
  config:
    import: <source>
```

This tells Spring:

> "Explicitly import configuration from this external source BEFORE the application starts."

---

## 2. Why was this introduced?

Before Spring Boot 2.4:

* Config Server was loaded automatically using `bootstrap.yml`
* Configuration order was implicit and hard to reason about
* Debugging config issues was difficult

After Spring Boot 2.4:

* Configuration loading is **explicit**
* Order is **predictable**
* Each config source is **pluggable**

---

## 3. What does `configserver:` mean?

Example:

```yaml
spring:
  config:
    import: configserver:http://localhost:8089
```

This syntax follows a **protocol-based format**:

```
<protocol> : <location>
```

So here:

* **Protocol** → `configserver`
* **Location** → `http://localhost:8089`

---

## 4. Why is `configserver:` REQUIRED?

Spring Boot does **not know by default** what kind of external source the URL represents.

For example:

* Is it a file?
* Is it a database?
* Is it HTTP JSON?
* Is it Git-based Config Server?

The `configserver:` prefix answers this question.

> It tells Spring Boot:
> "Use Spring Cloud Config Client to resolve this configuration."

---

## 5. How `configserver:` works internally (Step-by-Step)

### Step 1: Application startup begins

* No beans created yet
* No auto-configuration yet

### Step 2: Spring reads `spring.config.import`

```yaml
spring.config.import=configserver:http://localhost:8089
```

### Step 3: Spring detects the protocol

Spring checks:

> "Do I have a resolver for the `configserver:` protocol?"

Yes → provided by **Spring Cloud Config Client**.

Internally implemented by:

```
ConfigServerConfigDataLocationResolver
```

---

### Step 4: Resolver executes

The resolver:

1. Reads `spring.application.name`
2. Reads active profile (`dev`, `prod`, or `default`)
3. Constructs the request:

```
http://localhost:8089/{application}/{profile}
```

Example:

```
http://localhost:8089/configdemo/dev
```

---

### Step 5: Config Server responds

Config Server:

* Fetches configuration from Git
* Loads base config (`configdemo.yml`)
* Loads profile config (`configdemo-dev.yml`)
* Merges them
* Returns them as property sources

---

### Step 6: Spring merges property sources

Final priority order:

1. Config Server values
2. Local `application.yml`
3. Default values

Spring then continues normal application startup.

---

## 6. Why base (default) config is always included

When using profiles like `dev` or `prod`, Spring Cloud Config **always loads the base configuration first**.

Example:

```
configdemo.yml         (base)
configdemo-dev.yml     (override)
```

Reason:

* Base file contains common configuration
* Profile files override only environment-specific values

This avoids duplication and improves maintainability.

This behavior is **intentional and required**, not a bug.

---

## 7. Why not just use the URL without `configserver:`?

❌ This will NOT work:

```yaml
spring.config.import: http://localhost:8089
```

Because:

* Spring does not know which resolver to use
* No protocol → no config source type

Just like:

```
jdbc:mysql://...
```

The protocol (`jdbc`) tells Java which driver to use.

Similarly:

```
configserver:http://...
```

tells Spring which config resolver to use.

---

## 8. What happens if Config Server is NOT available?

### Default behavior (FAIL-FAST)

If Config Server is down:

* Application startup **fails immediately**
* Exception is thrown

This is intentional for production environments.

---

## 9. Making Config Server OPTIONAL

If you want the application to start even when Config Server is down:

```yaml
spring:
  config:
    import: optional:configserver:http://localhost:8089
```

Now:

* If Config Server is available → configs loaded
* If Config Server is NOT available → local configs used

---

## 10. When to use OPTIONAL configserver

✔ Local development
✔ Developer machines
✔ Fallback environments

❌ Production (not recommended)

---

## 11. Can multiple config sources be imported?

Yes.

```yaml
spring:
  config:
    import:
      - configserver:http://localhost:8089
      - optional:file:./local-overrides.yml
```

Spring loads them **in order**.

---

## 12. Why `bootstrap.yml` is no longer recommended

Old way:

```yaml
bootstrap.yml
spring.cloud.config.uri=...
```

Problems:

* Implicit behavior
* Hard to debug

New way:

```yaml
spring.config.import=configserver:...
```

Benefits:

* Explicit
* Ordered
* Predictable
* Modern

---

## 13. Best Practices

✔ Always use `spring.config.import`
✔ Use `configserver:` protocol explicitly
✔ Keep common configs in base file
✔ Override only environment-specific values
✔ Use `optional:` only for non-production

---

## 14. One-Line Summary (Remember This)

> `configserver:` is a protocol that tells Spring Boot which resolver to use to fetch configuration, while the URL tells it where the Config Server is located.

---

## 15. Final Mental Model

```
spring.config.import = protocol : location

configserver : http://host:port
```

Spring resolves protocol → fetches config → merges → starts app.

---

# Spring Cloud Config – Actuator Refresh & @RefreshScope (README)

This document explains **how Spring Boot Actuator works with Spring Cloud Config**, **what `/actuator/refresh` actually does**, **how `@RefreshScope` works internally**, and **how to correctly apply it across controllers and services**.

Read this anytime to quickly understand runtime configuration refresh without restarting the application.

---

## 1. What problem does Actuator Refresh solve?

In a microservices system using **Spring Cloud Config Server**:

* Configuration is stored in Git
* Config Server fetches it
* Client applications load it at startup

But by default:

* Configuration is loaded **only once**
* Any change in Git requires **application restart**

Spring Boot Actuator + Spring Cloud Config solve this using **runtime refresh**.

---

## 2. What you need to enable refresh

### 2.1 Dependencies (Client)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

---

### 2.2 Enable refresh endpoint

```yaml
management:
  endpoints:
    web:
      exposure:
        include: refresh
```

---

### 2.3 Add `@RefreshScope`

Add `@RefreshScope` to beans that should reload configuration dynamically.

---

## 3. What happens when `/actuator/refresh` is called

```http
POST /actuator/refresh
```

This **does NOT restart** the application.

### Internal flow:

### Step 1: Actuator receives refresh request

* Triggers a Spring `RefreshEvent`

### Step 2: Config Client fetches latest config

* Reads `spring.application.name`
* Reads active profile (`dev`, `prod`, etc.)
* Calls Config Server again:

```
/{application}/{profile}
```

Example:

```
/configdemo/dev
```

---

### Step 3: Spring updates Environment

* New property sources are loaded
* Old vs new values are compared
* Changed keys are identified

---

### Step 4: `@RefreshScope` beans are destroyed

* Beans annotated with `@RefreshScope` are:

    * Destroyed
    * Removed from context
* On next request → new instance is created
* New instance uses **latest config values**

No restart. No downtime.

---

## 4. Why `@RefreshScope` is mandatory

Without `@RefreshScope`:

* Beans are created only once
* Config changes are ignored

With `@RefreshScope`:

* Bean lifecycle becomes refresh-aware
* Bean is recreated after refresh

---

## 5. Where should `@RefreshScope` be applied?

### 5.1 Controller example

```java
@RestController
@RefreshScope
public class DemoController {

    @Value("${build.name}")
    private String buildName;

    @GetMapping("/build")
    public String build() {
        return buildName;
    }
}
```

---

### 5.2 Recommended approach – Service layer

```java
@Service
@RefreshScope
public class BuildService {

    @Value("${build.version}")
    private String version;

    public String getVersion() {
        return version;
    }
}
```

Controller stays clean:

```java
@RestController
public class BuildController {

    private final BuildService service;

    public BuildController(BuildService service) {
        this.service = service;
    }

    @GetMapping("/version")
    public String version() {
        return service.getVersion();
    }
}
```

---

## 6. Do all controllers need `@RefreshScope`?

❌ No.

Use `@RefreshScope` only for beans that:

* Read values from Config Server
* Need runtime updates

Static beans do not need refresh.

---

## 7. Multiple controllers using same config

❌ Bad practice:

* Adding `@RefreshScope` to every controller

✅ Best practice:

* Put config logic in one service
* Add `@RefreshScope` there
* Inject service into controllers

---

## 8. Thread safety & performance

Spring uses **proxy-based beans**:

* Proxy remains the same
* Actual bean instance is replaced
* Requests always go through proxy

This ensures:

* Thread safety
* No memory leaks
* No downtime

---

## 9. What if Config Server is updated but refresh is not called?

* Application continues using old values
* No error is thrown
* New config remains unused

Refresh must be triggered explicitly.

---

## 10. Automatic refresh across instances (Advanced)

For multiple instances:

* Use **Spring Cloud Bus**
* Broadcast refresh event

```http
POST /actuator/bus-refresh
```

All services refresh simultaneously.

---

## 11. Common mistakes to avoid

❌ Expecting refresh without `@RefreshScope`
❌ Adding `@RefreshScope` everywhere
❌ Calling refresh on Config Server
❌ Restarting application unnecessarily

---

## 12. One-line summary

> `/actuator/refresh` reloads configuration from Config Server and recreates only `@RefreshScope` beans with new values—without restarting the application.

---

## 13. Final mental model

```
Git change → Config Server → /actuator/refresh → @RefreshScope beans recreated
```

---


