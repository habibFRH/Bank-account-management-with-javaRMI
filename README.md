# Bank Account Management System

This project implements a simple bank account management system using Java RMI (Remote Method Invocation). It consists of two main components: a server-side application that provides bank account management services and a client-side GUI application for users to interact with the system.

## Features

- Create a new bank account.
- Perform debit and credit transactions on existing accounts.
- Transfer funds between existing accounts.
- Check the balance of an account.

## Technologies Used

- Java: The programming language used for both server and client applications.
- Java RMI (Remote Method Invocation): Used for communication between the server and client applications.
- Swing: Used for creating the graphical user interface (GUI) for the client application.

## Project Structure

The project is organized into two packages:

1. `server`: Contains the server-side implementation of the bank account management system.
2. `client`: Contains the client-side GUI application for interacting with the server.

## How to Run

1. **Start the Server:**
   - Run the `BankServer` class located in the `server` package.
   - The server will start and listen for incoming client requests.

2. **Run the Client Application:**
   - Run the `BankClientUI` class located in the `client` package.
   - The client application GUI will open, allowing you to perform various banking operations.

## Usage

1. **Creating a New Account:**
   - Enter the desired account number in the corresponding text field.
   - Click the "Create Account" button to create the account.

2. **Performing Transactions:**
   - Enter the account number and transaction amount in the respective text fields.
   - Click the "Credit" or "Debit" button to perform the corresponding transaction.

3. **Transferring Funds:**
   - Enter the source and destination account numbers, as well as the transfer amount.
   - Click the "Transfer" button to transfer funds between accounts.

4. **Checking Balance:**
   - Enter the account number for which you want to check the balance.
   - Click the "Check Balance" button to display the account balance.

## Dependencies

This project has no external dependencies beyond the standard Java libraries.

## Contributors

- [Your Name] - [Your Email]

## License

This project is licensed under the [MIT License](LICENSE).
