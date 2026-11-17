# 📚 Litera — Book App (Kotlin Multiplatform)

> **A learning project.** I built Litera to practice **Kotlin Multiplatform** and **Compose Multiplatform** with a clean, real-world architecture. It is not a production app.

Litera lets you search books from the [Open Library API](https://openlibrary.org/developers/api), browse them by category, save favorites, and track what you're reading. Saved books are stored on the device, so they work offline. **One shared codebase runs on Android, iOS and Desktop.**

## Screenshots

| Explore | Book Details | Favorites | Reading List |
|:---:|:---:|:---:|:---:|
| <img src="screen_shots/ss_1.png" width="200"/> | <img src="screen_shots/ss_2.png" width="200"/> | <img src="screen_shots/ss_3.png" width="200"/> | <img src="screen_shots/ss_4.png" width="200"/> |

## Concepts Practiced

| Concept | Used for |
|---|---|
| **Kotlin Multiplatform** | One shared codebase for Android, iOS and Desktop |
| **Compose Multiplatform** | Shared UI (cards, chips, grid, bottom bar) |
| **Clean Architecture** | Separate `presentation`, `domain` and `data` layers per feature |
| **MVI-style state** | `State` + `Action` + `ViewModel` with `StateFlow` |
| **Ktor Client** | Networking with the Open Library REST API |
| **kotlinx.serialization** | Parsing JSON into DTOs |
| **SQLDelight** | Local database for Favorites and the Reading List |
| **Koin** | Dependency injection |
| **Navigation Compose** | Type-safe routes and a bottom navigation bar |
| **Coroutines & Flow** | Debounced search and reactive database updates |
| **Coil 3** | Loading book cover images |
| **Typed error handling** | `Result<Data, DataError>` instead of exceptions |
| **expect / actual** | Platform-specific HTTP engines and database drivers |

## Architecture

Each feature (`book`, `favorites`, `readinglist`) follows **Clean Architecture**. Arrows show who calls whom. The `domain` layer has no dependency on the network, the database or the UI.

```mermaid
flowchart TD
    UI["🖼️ Screen (Compose)"] -- Action --> VM["ViewModel"]
    VM -- StateFlow --> UI
    VM --> UC["Use Cases"]
    UC --> REPO["Repository (interface)"]
    REPO -. implemented by .-> IMPL["Repository Impl"]
    IMPL --> API["Ktor API<br/>(Open Library)"]
    IMPL --> DB["SQLDelight<br/>(Local DB)"]

    subgraph Presentation
        UI
        VM
    end
    subgraph Domain
        UC
        REPO
    end
    subgraph Data
        IMPL
        API
        DB
    end
```

### How a search works

```mermaid
sequenceDiagram
    participant U as User
    participant VM as SearchViewModel
    participant R as BookRepository
    participant API as Open Library

    U->>VM: types "dune"
    Note over VM: waits 500 ms (debounce)
    VM->>R: searchBooks("dune")
    R->>API: GET /search.json?q=dune
    API-->>R: JSON → DTOs
    R-->>VM: Result.Success(books)
    VM-->>U: UI shows the book grid
```

### Reactive local database

Favorites and the Reading List are exposed as a `Flow`. When a row changes, **every screen watching that table updates automatically**:

```mermaid
flowchart LR
    A["Tap ♥ on Explore"] --> B["INSERT into favoriteBook"]
    B --> C["SQLDelight re-runs the query"]
    C --> D["Explore: heart fills"]
    C --> E["Favorites: book appears"]
```

## Project Structure

```
shared/src/
├── commonMain/          # Shared code for all platforms
│   ├── app/             # App entry, Scaffold, routes
│   ├── core/            # Network, database, DI, shared UI components
│   ├── book/            # Search + details   (domain / data / presentation)
│   ├── favorites/       # Favorites           (domain / data / presentation)
│   ├── readinglist/     # Reading list        (domain / data / presentation)
│   ├── navigation/      # NavHost + bottom bar
│   └── sqldelight/      # .sq files (database tables & queries)
├── androidMain/         # OkHttp engine + Android SQLite driver
├── iosMain/             # Darwin engine + Native SQLite driver
└── jvmMain/             # OkHttp engine + JDBC SQLite driver (desktop)
```

## Run It

| Platform | Command |
|---|---|
| Android | Run `androidApp` from Android Studio, or `./gradlew :androidApp:assembleDebug` |
| Desktop | `./gradlew :desktopApp:run` |
| iOS | Open `/iosApp` in Xcode and run |
| Tests | `./gradlew :shared:jvmTest` |

---

Built for learning 🚀 · Data from [Open Library](https://openlibrary.org)
