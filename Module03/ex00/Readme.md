# Java Threads Exercise — Egg, Hen, Human

## Overview
This project demonstrates Java multithreading fundamentals using a simple console program.

The program starts two worker threads:
- `Egg` thread
- `Hen` thread

Each worker prints its message `N` times, where `N` is provided by command-line argument:

- `--count=N`

After both worker threads finish, the **main thread** prints:

- `Human` (also `N` times)

---

## Learning Goals
This exercise is designed to help you understand:

1. **Thread creation**
   - Creating threads by extending `Thread`
   - (Optional alternative) implementing `Runnable`

2. **Concurrent execution**
   - `Egg` and `Hen` run in parallel
   - Output order is **non-deterministic** (can change each run)

3. **Thread coordination**
   - Using `join()` so main waits for worker threads
   - Ensuring `Human` prints only after `Egg` and `Hen` complete

4. **Argument parsing**
   - Reading and validating `--count=N`

5. **Execution model**
   - Understanding that `main` is itself a thread

---

## Program Behavior

### Input
One argument in this format:

```bash
--count=50
```

### Output Rules
- Print `Egg` exactly `N` times (from Egg thread)
- Print `Hen` exactly `N` times (from Hen thread)
- Print `Human` exactly `N` times (from main thread)
- `Human` lines must appear **after** both worker threads complete
- Order between `Egg` and `Hen` lines is not guaranteed

---

## Example Run

```bash
$ java Program --count=5
Egg
Hen
Hen
Egg
Egg
Hen
Egg
Hen
Hen
Egg
Human
Human
Human
Human
Human
```

> Note: Egg/Hen order may differ every run.

---

## Build and Run

### Compile
```bash
javac Program.java ThreadTest.java
```

### Execute
```bash
java Program --count=50
```

---

## Validation Notes
- If `--count` is missing or invalid, print an error/usage message
- Recommended: accept only positive integers (`N > 0`)
- Typo check: output should be `Human` (not `Humman`)

---

## Suggested Project Structure
```text
.
├── Program.java
├── ThreadTest.java
└── README.md
```

---

## Common Mistakes
- Printing `Human` before worker completion (missing `join()`)
- Using invalid argument parsing (`args[0]` without checking `args.length`)
- Misspelling output text (`Humman`)
- Assuming Egg/Hen order should be fixed (it should not)

---

## Optional Extensions
- Re-implement using `Runnable` instead of extending `Thread`
- Add strict input error messages and usage help
- Add tests that verify counts of each printed word
- Add synchronization/barrier variants for advanced practice

---

## Summary
This exercise teaches the basics of Java threading:
- create threads,
- run them concurrently,
- coordinate completion with `join()`,
- and manage predictable final behavior from the main thread.