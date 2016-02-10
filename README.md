![Devoxx4Kids](http://www.devoxx4kids.de/wp-content/uploads/2015/07/cropped-header_hp.jpg)

# Devoxx4Kids - Jumping Sumo

[Anleitung auf Deutsch](README_DE.md)

This prototype connects with a jumping sumo over the ip '192.168.2.1'and the port '44444' 

There are 4 ways to control the jumping sumo:

```java -jar jumpingSumo-jar-with-dependencies.jar <keyboard|program|file|swing>```

## Keyboard-Driven (KeyboardDriver)
  - Arrow keys: Up, Down, Left, Right
  - Jump: 
      - (H) High
      - (W) Far
  - Animations: 
      - (1) Turn
      - (2) Tap
      - (3) Shaking
      - (4) Metronome
      - (5) Ondulation
      - (6) Turnjump
      - (8) Spiral
      - (9) Slalom
  - Sound:
      - (Y) Layout
      - (X) Without Sound
      - (I) Monster Theme
      - (O) Insect Theme
      - (P) Robot Theme

## Java-Driven (ProgrammaticDriver)

:construction: will folow :construction:
  
## Datei-Driven (FileBasedProgrammaticDriver)

```programm.txt``` will be polled after a command change, which will directly send to the jumping sumo:

| Command      | Jumpin Sumo will do:                      |
| ------------ |:----------------------------------------: |
| Vor          | One unit forward                          |
| Zurueck      | One unit backward                         |
| Links        | Turn left 90°                             |
| Links x      | Turn left with x degree (Hint: 90° => 25) |
| Rechts x     | Turn right with x degree (Hint: 90° => 25)|
| Springe hoch | Jumps high                                |
| Springe weit | Jumps far                                 |