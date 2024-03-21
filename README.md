# [GK] 10.11.1 Statische Codeanalyse

@autors: Ertl Jakob, Latschbacher Lukas

## Rule 1 - Vorhandensein von Kommentaren bei public Methoden

```yml
rules:
- id: kommentare-bei-oeffentlichen-methoden
  patterns:
    - pattern: |
        public $RETURNTYPE $METHOD(...) {
          ...
        }
    - pattern-not: |
        /**
         * ...
         */
        public $RETURNTYPE $METHOD(...) {
          ...
        }
  message: "Öffentliche Methoden sollten Kommentare haben, die ihre Funktionalität erklären."
  languages: [java]
  severity: WARNING
```

## Rule 2 - Vermeidung von gefaehrlichen Konstrukten

```yml
rules:
- id: vermeidung-von-system-exit
  pattern: System.exit($X);
  message: "Vermeide die Verwendung von System.exit(), um die Applikation nicht abrupt zu beenden."
  languages: [java]
  severity: ERROR
```

## Rule 3 - Erkennung von authentifizierungs Routinen

```yml
rules:
- id: authentifizierungs-routinen
  pattern: |
    public $RETURNTYPE login($PARAMS) {
      ...
    }
  message: "Überprüfe die Sicherheit der Authentifizierungsroutine."
  languages: [java]
  severity: INFO
```

## Rule 4 - Erkennung von Sicherheitsluecken

```yml
rules:
- id: hardcodierte-passwoerter
  pattern: |
    String $PASSWORD = "...";
  message: "Vermeide hardcodierte Passwörter im Code."
  languages: [java]
  severity: ERROR
```

## Rule 5 - unsichere Konfigurationsoptionen

```yml
rules:
- id: unsichere-konfiguration
  pattern: |
    $X = new FileInputStream("$FILE");
  message: "Stelle sicher, dass der Zugriff auf Dateien sicher konfiguriert ist."
  languages: [java]
  severity: WARNING
```

## Rule 6 - unsichere Dateipfade

```yml
- id: java-unsafe-file-paths
  patterns:
    - pattern: |
        $Path = $any;
        $Pattern.matches(".*/etc/passwd", $Path);
      message: "Unsicherer Dateipfad gefunden: möglicher Zugriff auf Passwortdateien."
  languages:
    - java
```
