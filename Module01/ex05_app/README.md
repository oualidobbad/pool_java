# TransactPro — Transaction Management Desktop Application

A professional JavaFX desktop application built on top of the **Module01 / ex05** exercise.  
All original core logic is preserved exactly as implemented in the console version.

---

## Project Structure

```
ex05_app/
├── pom.xml                          # Maven build configuration
├── mvnw                             # Maven wrapper (no Maven install needed)
├── .mvn/wrapper/                    # Maven wrapper binaries
└── src/main/
    ├── java/com/transactions/
    │   ├── core/                    # Original exercise logic (unchanged)
    │   │   ├── ExitException.java
    │   │   ├── IllegalTransactionException.java
    │   │   ├── StatusOperation.java
    │   │   ├── Transaction.java
    │   │   ├── TransactionNotFoundException.java
    │   │   ├── TransactionsLinkedList.java
    │   │   ├── TransactionsList.java
    │   │   ├── TransactionsService.java
    │   │   ├── TransferCategory.java
    │   │   ├── User.java
    │   │   ├── UserIdsGenerator.java
    │   │   ├── UserNotFoundException.java
    │   │   ├── UsersArrayList.java
    │   │   └── UsersList.java
    │   ├── controller/              # UI ↔ Core interaction logic
    │   │   └── MainController.java
    │   ├── ui/components/           # Reusable UI components
    │   │   ├── Animations.java
    │   │   ├── StatCard.java
    │   │   └── Toast.java
    │   └── app/                     # Application entry points
    │       ├── TransactionsApp.java
    │       └── Launcher.java
    └── resources/
        └── styles/
            └── theme.css            # Full CSS design system
```

## Architecture

| Layer          | Package                          | Role                                       |
|----------------|----------------------------------|---------------------------------------------|
| **Core**       | `com.transactions.core`          | Original business logic (preserved exactly) |
| **Controller** | `com.transactions.controller`    | Bridges UI events → core service calls      |
| **UI**         | `com.transactions.ui.components` | Reusable widgets (Toast, StatCard, etc.)    |
| **App**        | `com.transactions.app`           | JavaFX Application bootstrap                 |

## Features

| # | Feature                | Description                                        |
|---|------------------------|----------------------------------------------------|
| 1 | **Add User**           | Form with name + balance, validation, success toast |
| 2 | **View Balances**      | Searchable table of all users + individual lookup  |
| 3 | **Perform Transfer**   | Sender/Recipient/Amount form with full validation  |
| 4 | **View Transactions**  | Per-user transaction table with CREDIT/DEBIT badges |
| 5 | **Remove Transfer**    | DEV tool — delete by user ID + transaction UUID    |
| 6 | **Check Validity**     | DEV tool — scan for unpaired/orphaned transactions |

## UI Highlights

- Dark sidebar navigation with active-state highlighting
- Animated panel transitions (fade + slide)
- Toast notification system (success / error / info)
- Input validation with red-border visual feedback
- Professional table views with alternating rows
- CREDIT/DEBIT badge components
- Consistent design system via CSS custom properties

## Prerequisites

- **Java 17+** (JDK)

No Maven installation required — the included Maven Wrapper handles everything.

## Build & Run

From the `ex05_app/` directory:

```bash
# Compile
./mvnw clean compile

# Run directly via Maven
./mvnw javafx:run

# Package as runnable JAR
./mvnw clean package

# Run the standalone JAR
java -jar target/ex05-app-1.0.0.jar
```
