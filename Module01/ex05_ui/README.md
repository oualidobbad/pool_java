# Transactions Manager GUI

A graphical user interface version of Module01/ex05, built with **Java Swing**.

## Project Structure

```
ex05_ui/
├── build.sh              # Compilation script
├── run.sh                # Launch script
├── README.md
└── src/
    ├── logic/            # Business logic (unchanged from ex05)
    │   ├── ExitException.java
    │   ├── IllegalTransactionException.java
    │   ├── StatusOperation.java
    │   ├── Transaction.java
    │   ├── TransactionNotFoundException.java
    │   ├── TransactionsLinkedList.java
    │   ├── TransactionsList.java
    │   ├── TransactionsService.java
    │   ├── TransferCategory.java
    │   ├── User.java
    │   ├── UserIdsGenerator.java
    │   ├── UserNotFoundException.java
    │   ├── UsersArrayList.java
    │   └── UsersList.java
    └── ui/               # GUI layer
        ├── MainApp.java          # Entry point
        ├── MainFrame.java        # Main window (sidebar + content cards)
        ├── RoundedPanel.java     # Rounded panel with fade-in animation
        ├── StyledButton.java     # Button with hover animation
        ├── Theme.java            # Colors, fonts, dimensions
        └── ToastNotification.java # Slide-in toast notifications
```

## Build & Run

### Prerequisites
- **Java 8+** (JDK)

### Compile
```bash
chmod +x build.sh run.sh
./build.sh
```

### Run (Normal Mode)
```bash
./run.sh
```

### Run (Dev Mode)
Enables "Remove Transfer" and "Check Validity" features:
```bash
./run.sh --profile=dev
```

### Manual Compilation
```bash
mkdir -p out
javac -d out src/logic/*.java src/ui/*.java
java -cp out ui.MainApp
# or dev mode:
java -cp out ui.MainApp --profile=dev
```

## Features

| Feature | Description |
|---------|-------------|
| **Add User** | Create users with a name and initial balance |
| **View Balance** | Look up a user's balance by ID with animated card display |
| **Transfer** | Send money between users |
| **View Transactions** | List all transactions for a user with animated cards |
| **Remove Transfer** *(DEV)* | Delete a transaction by user ID and transaction UUID |
| **Check Validity** *(DEV)* | Find unpaired/orphan transactions |

## UI Highlights

- **Dark theme** with Catppuccin Mocha-inspired color palette
- **Animated sidebar buttons** with smooth hover transitions
- **Slide-in toast notifications** for success/error/warning feedback
- **Fade-in animations** on transaction cards and result panels
- **Activity log** at the bottom tracking all operations
- **Focus-highlight** on input fields
