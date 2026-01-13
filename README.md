

# **Russel's Sword**

> A simple turn-based Java game.
> Player vs. enemy combat with basic turn logic.

---

## **Description**

Russel's Sword is a text-based turn-based game written in Java. The player and an enemy take turns performing actions until one side wins.


## **Requirements**

* Java **17 or newer**
* VS Code with:

  * **Language Support for Java™ by Red Hat**
  * **Debugger for Java**
  * **Java Test Runner** (optional)

---

## **How to Run (VS Code) — Recommended**

### **Method 1: “Run Java” button**

This works for most users:

1. Open the project folder in VS Code.
2. Open `Main.java` (the file with `public static void main`).
3. Click **Run** or **Run Java** at the top.
4. Game starts in an integrated terminal.

This uses the Java extension which auto-handles the classpath.
**Do not use “Run Code” (Code Runner)** — it usually fails for Java projects with packages.

---

## **How to Run (Terminal)**

### **Compile**

On Windows PowerShell / CMD / Linux / Mac:

```sh
javac -d bin src/org/example/*.java
```

### **Run**

```sh
java -cp bin org.example.Main
```

If your package or folders differ, adjust accordingly.

---

## **JAR Build (Optional)**

### **Create runnable JAR**

```sh
jar cfe RusselsSword.jar org.example.Main -C bin .
```

### **Run it**

```sh
java -jar RusselsSword.jar
```

---



