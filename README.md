# 💳 Java E-Wallet System

A **console-based E-Wallet System** that simulates real-world digital wallet functionality. The project follows a clean, step-by-step flow starting from **user registration** to **secure login** and full **wallet operations** such as deposits, withdrawals, transfers, and account management.

This project is ideal for practicing:

* Programming fundamentals
* Input validation
* User authentication
* State management
* Clean code structure

---

## 🚀 Features Overview

The system mimics the real lifecycle of an e-wallet:

1. **User Sign-Up**
2. **User Login**
3. **Wallet Operations**
4. **Account Management**

Each phase is strictly validated to ensure **security, consistency, and correctness**.

---

## 📌 Phase 1 — Sign-Up

Users must create a valid account before accessing the wallet.

**Validations:**

* Username length check
* Username must start with an uppercase letter
* Username must be unique
* Password length and complexity validation
* Age must be **18 or older**
* Phone number must:

  * Follow **Egyptian format**
  * Contain only numbers
  * Be unique

**Rules:**

* Duplicate accounts are prevented (same username or phone number)
* Successfully created accounts are stored securely in the wallet system

---

## 🔐 Phase 2 — Login

Registered users can log in using their credentials.

**Login Rules:**

* Username and password must not be empty
* Account must exist
* Password must match
* Maximum **3–4 failed attempts** allowed

After a successful login, the user is redirected to the **Main Features Menu**.

---

## 🧭 Phase 3 — Main Features Menu (After Login)

Once logged in, users can access the following options:

* 💰 Deposit Money
* 💸 Withdraw Money
* 🔁 Transfer Money
* 👤 Show Account Details
* 🔑 Change Password
* 🚪 Logout

---

## 💰 Phase 4 — Deposit

Allows users to add money to their wallet.

**Validations:**

* Deposit amount must be greater than zero
* Account must exist

**Result:**

* Balance is updated
* Success message is displayed with the new balance

---

## 💸 Phase 5 — Withdraw

Users can withdraw money from their wallet.

**Validations:**

* Withdraw amount must be greater than zero
* Account must exist
* User must have sufficient balance

**Result:**

* Balance is deducted
* Success message with updated balance is shown

---

## 🔁 Phase 6 — Transfer Money

Transfer money securely between users.

**Validations:**

* Destination username must exist
* Source and destination accounts must be different
* Transfer amount must be greater than zero
* Sender must have sufficient balance

**Process:**

* Amount is deducted from sender
* Amount is added to receiver

**Result:**

* Transfer confirmation is displayed
* Updated balances are shown

---

## 🔑 Phase 7 — Change Password

Users can change their account password securely.

**Rules:**

* Old password must match
* New password must meet validation rules
* New password must be different from the old one

**Result:**

* Password is updated successfully
* Confirmation message is shown

---

## 👤 Phase 8 — Show Account Details

Displays current user account information:

* Username
* Phone number
* Age
* Balance

🔒 Password is **hidden or masked** for security.

---

## 🚪 Phase 9 — Logout

* Exits the user menu
* Displays a goodbye message
* Returns to the main application menu

---

## 🌟 Bonus Features (Optional Enhancements)

* 📜 Transaction history (deposit, withdraw, transfer)
* 🛠️ Admin panel to view all accounts
* ❌ Option to delete an account

---

## 🧠 Project Goals

* Simulate a real-world e-wallet workflow
* Practice validation and authentication logic
* Improve structured programming skills
* Build a scalable base for future enhancements

---

## 📄 License

This project is for **educational purposes** and can be freely extended or modified.

---

✨ *Clean logic, strong validation, real-world flow.*
