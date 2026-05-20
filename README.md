# eva-java

Java bindings for the [EVA DCL](https://github.com/Carla-Corp/eva-ts) configuration language.

## Requirements

- Java 11+
- Gradle 8+
- The compiled EVA native library (`libeva-<platform>-<arch>.so`)

## Setup

Clone the repo and place the compiled `libeva` shared library inside the `eva/` folder at the root of the project.

```
eva-java/
├── eva/
│   └── libeva-linux-x86_64.so
├── src/
│   └── eva/
│       ├── Eva.java
│       ├── EvaList.java
│       ├── EvaMap.java
│       ├── EvaValue.java
│       ├── EvaNil.java
│       ├── ValueType.java
│       ├── Values.java
│       ├── Ffi.java
│       └── Errors.java
└── build.gradle
```

`build.gradle`:

```groovy
plugins {
    id 'java'
}

group = 'com.yourorg'
version = '1.0.0'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'net.java.dev.jna:jna:5.14.0'
}

jar {
    manifest {
        attributes 'Main-Class': 'eva.Main'
    }
    from {
        configurations.runtimeClasspath.collect {
            it.isDirectory() ? it : zipTree(it)
        }
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
```

Build:

```bash
gradle build
```

## Usage

```java
import eva.Eva;
import eva.EvaList;
import eva.EvaMap;

Eva config = new Eva("path/to/config.eva");

String name = (String) config.get("project", "name");
double version = (double) config.get("project", "version");

EvaList messages = (EvaList) config.get("dev", "messages");
for (int i = 0; i < messages.length; i++) {
    System.out.println(messages.get(i));
}

EvaMap meta = (EvaMap) config.get("project", "meta");
String author = (String) meta.get("author");
```

## API

### `Eva(String filepath)`

Loads and parses an `.eva` file. Throws `RuntimeException` if the file can't be opened.

### `eva.get(String namespace, String field)`

Returns the value of `field` inside `namespace`. Throws `IllegalArgumentException` if it doesn't exist.

Return types:

| EVA type | Java type  |
|----------|------------|
| string   | `String`   |
| number   | `Double`   |
| bool     | `Boolean`  |
| nil      | `EvaNil`   |
| list     | `EvaList`  |
| map      | `EvaMap`   |

### `EvaList`

| Member | Description |
|--------|-------------|
| `.length` | Number of elements |
| `.get(int i)` | Get element at index `i` |
| `.list()` | Return all elements as `List<Object>` |

### `EvaMap`

| Member | Description |
|--------|-------------|
| `.length` | Number of keys |
| `.get(String key)` | Get value by key |
| `.keys()` | Return an `EvaList` of all keys |

### `EvaNil`

Represents a null value. `toString()` returns `"nil"`.

## License

MIT