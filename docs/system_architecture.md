# SMS Forward - System Architecture

## High-Level Architecture

```
┌─────────────────────┐    ┌─────────────────────┐    ┌─────────────────────┐
│   Android System    │    │   SMS Forward App   │    │   Remote Server     │
│                     │    │                     │    │   (Future)          │
│  ┌───────────────┐  │    │  ┌───────────────┐  │    │                     │
│  │ SMS Provider  │──┼────┼─►│ SMS Receiver  │  │    │  ┌───────────────┐  │
│  │               │  │    │  │               │  │    │  │   REST API    │  │
│  └───────────────┘  │    │  └───────────────┘  │    │  │               │  │
│                     │    │         │           │    │  └───────────────┘  │
│  ┌───────────────┐  │    │         ▼           │    │                     │
│  │ Notification  │──┼────┼─► ┌───────────────┐ │◄───┼──────────────────────┤
│  │ Manager       │  │    │   │ Message       │ │    │                     │
│  │               │  │    │   │ Processor     │ │    │                     │
│  └───────────────┘  │    │   │               │ │    │                     │
│                     │    │   └───────────────┘ │    │                     │
└─────────────────────┘    │         │           │    └─────────────────────┘
                           │         ▼           │
                           │  ┌───────────────┐  │
                           │  │ Local Database│  │
                           │  │ (SQLite/Room) │  │
                           │  └───────────────┘  │
                           │         │           │
                           │         ▼           │
                           │  ┌───────────────┐  │
                           │  │ UI Layer      │  │
                           │  │ (Compose)     │  │
                           │  └───────────────┘  │
                           └─────────────────────┘
```

## Core Components

### 1. Message Reception Layer
- **SMS Broadcast Receiver**: Android broadcast receiver for `SMS_RECEIVED`
- **Notification Listener Service**: Accessibility service for app notifications *(M3)*
- **Permissions Manager**: Handle runtime permissions (SMS_READ, etc.)

### 2. Data Processing Layer  
- **Message Processor**: Parse, validate, and normalize incoming messages
- **Filter Engine**: Apply user-defined rules *(M3)*
- **Privacy Handler**: Sanitize sensitive content *(M2)*

### 3. Storage Layer
- **Room Database**: Local SQLite database
- **Message Entity**: Store SMS/notification data
- **DAO Layer**: Data access operations
- **Repository Pattern**: Abstract data sources

### 4. Network Layer *(M2+)*
- **HTTP Client**: Retrofit/OkHttp for REST API calls
- **Retry Manager**: Handle network failures with exponential backoff
- **Queue System**: Offline message queuing with WorkManager

### 5. UI Layer
- **MainActivity**: Single Activity with Compose navigation
- **Message List Screen**: Display recent SMS history
- **Settings Screen**: Server configuration *(M2)*
- **Stats Dashboard**: Forwarding statistics *(M3)*

### 6. Background Services
- **Foreground Service**: Long-running message monitoring
- **WorkManager**: Scheduled tasks and retry logic
- **JobScheduler**: System-optimized background work

## Data Models

### Message Entity
```kotlin
data class Message(
    val id: Long,
    val type: MessageType, // SMS, NOTIFICATION
    val sender: String,
    val content: String,
    val timestamp: Long,
    val isForwarded: Boolean,
    val retryCount: Int
)
```

### Configuration Entity *(M2)*
```kotlin
data class ServerConfig(
    val endpoint: String,
    val apiKey: String,
    val isEnabled: Boolean,
    val retryAttempts: Int
)
```

## Milestone Mapping

### MVP (M1): Local Foundation
- SMS Broadcast Receiver
- Room Database + Message Entity
- Basic UI with Compose
- Message List Screen
- Permissions handling

### M2: Remote Integration  
- HTTP Client setup
- Server Configuration UI
- Network retry logic
- Privacy filtering options

### M3: Enhanced Features
- Notification Listener Service
- Filter Engine implementation
- Statistics tracking
- Export functionality

### M4: Production Polish
- End-to-end encryption
- Performance optimization
- Advanced retry strategies
- Comprehensive error handling

## Technology Stack

- **UI**: Jetpack Compose + Navigation
- **Database**: Room + SQLite
- **Background**: WorkManager + Foreground Service
- **Network**: Retrofit + OkHttp + Moshi
- **Architecture**: MVVM + Repository + Use Cases
- **DI**: Hilt/Dagger
- **Testing**: JUnit + Espresso + Compose Testing

## Security Considerations

- Encrypt local database *(M4)*
- Secure server communication with TLS
- API key storage in encrypted preferences
- Runtime permission validation
- Message content sanitization options