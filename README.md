# Digital Bank ATM Simulation

A futuristic, high-performance ATM simulation application built with Java, featuring a modern graphical user interface and a robust SQLite database backend.

## 🚀 Features

- **Modern GUI**: Sleek, glassmorphism-inspired interface using Java Swing.
- **SQLite Persistence**: Secure and reliable data storage for accounts and transactions.
- **JSON Data Exchange**: Integrated Gson for data serialization and transfer.
- **Automatic Migration**: Built-in utility to migrate legacy JSON data to SQLite on first run.
- **Bulk Account Generation**: Support for creating multiple test accounts with unique credentials.
- **Interactive Transactions**: Real-time deposits, withdrawals, and balance updates.
- **Transaction History**: Persistent logging of all user activities.

## 🛠️ Technology Stack

- **Language**: Java 17+
- **GUI Framework**: Java Swing (Custom themed)
- **Database**: SQLite
- **JSON Library**: Google Gson
- **Logging**: SLF4J (Simple)
- **JDBC Driver**: Xerial SQLite JDBC

## 📋 Prerequisites

- Java Development Kit (JDK) 11 or higher installed.
- Bash shell (for running the automation script).

## 🏃 Getting Started

### Running the Application

Simply execute the provided run script:

```bash
chmod +x run.sh
./run.sh
```

The script will automatically:
1. Compile all source files.
2. Link required libraries from the `lib/` directory.
3. Initialize the database schema if it's the first run.
4. Launch the GUI.

## 🔑 Sample Credentials

The following test accounts are available (from the generated bulk set):

| Account Number | PIN | Initial Balance Range |
| :--- | :--- | :--- |
| `A0101` | `1040` | $1,000 - $10,000 |
| `A0102` | `3146` | $1,000 - $10,000 |
| `A0103` | `5275` | $1,000 - $10,000 |

*Note: You can generate more accounts or customize the generation logic in `src/db/BulkAccountGenerator.java`.*

## 📁 Project Structure

- `src/`: Java source files (ui, db, model).
- `lib/`: External JAR dependencies (SQLite, Gson, SLF4J).
- `data/`: Database file (`atm.db`) and legacy JSON backups.
- `bin/`: Compiled class files.
- `run.sh`: Main execution script.

---
Developed as part of the ATM_Project conversion.
