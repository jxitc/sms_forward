# SMS Forward - Requirements

## Project Overview
Android app to forward SMS messages and app notifications to remote servers for monitoring and backup purposes.

## MVP (Milestone 1)
**Goal**: Basic SMS forwarding prototype
- **SMS Service**: Background service to listen for incoming SMS messages
- **Local Storage**: SQLite database to store SMS history locally
- **SMS History UI**: Simple list view showing recent SMS messages (sender, content, timestamp)

**Core MVP Features:**
- Detect and capture incoming SMS messages
- Store SMS data in local database
- Display SMS history in main UI
- Basic permissions handling (SMS read access)

## Milestone 2: Remote Forwarding
- **HTTP Client**: Send SMS data to configurable remote server endpoint
- **Config UI**: Settings page to configure server URL and authentication
- **Retry Logic**: Handle network failures and retry mechanism
- **Privacy**: Option to exclude sensitive SMS content

## Milestone 3: Enhanced Features
- **App Notifications**: Listen to and forward app notifications
- **Statistics**: Dashboard showing forwarding stats and success rates
- **Filtering**: Rules to include/exclude specific senders or apps
- **Export**: Backup/export SMS history to file

## Milestone 4: Production Ready
- **Security**: End-to-end encryption for forwarded messages
- **Performance**: Optimize battery usage and background processing
- **UI/UX**: Polish interface with material design
- **Testing**: Comprehensive test coverage

## Technical Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Database**: Room (SQLite)
- **Background**: WorkManager/Foreground Service
- **HTTP**: Retrofit/OkHttp
- **Architecture**: MVVM with Repository pattern

## Success Criteria
- MVP successfully captures and stores SMS messages
- Clean, intuitive UI showing message history
- Foundation for remote forwarding capabilities
- Proper Android permissions and background service handling