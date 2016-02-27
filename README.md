![Devoxx4Kids](http://www.devoxx4kids.de/wp-content/uploads/2015/07/cropped-header_hp.jpg)

[![Build Status](https://travis-ci.org/Devoxx4KidsDE/workshop-jumping-sumo.svg?branch=master)](https://travis-ci.org/Devoxx4KidsDE/workshop-jumping-sumo)


# Devoxx4Kids Workshop - Jumping Sumo

[Anleitung auf Deutsch](README_DE.md)

This Application provides multiple ways to communicate with your Jumping Sumo. You have to be in the wireless lan (wlan) of
the Jumping Sumo to start this application. If you are connected with the provided wlan of your Jumping Sumo you are ready
to start.


## How to start

First of all you have to compile antrl and then package everything together as a jar to get started:
```
mvn clean antlr4:antlr4 package
```

Now you compiled and packaged everything in the jar file
```
jumpingSumo-jar-with-dependencies.jar
```
in your */target* folder and you are ready to control your Jumping Sumo.


## First Steps

Start your Jumping Sumo and connect your device (e.g. your Notebook) to the provided wlan of your Jumping Sumo.
Now your device can reach the Jumping Sumo over the ip '192.168.2.1'and the port '44444'.

There are _3 ways_ to control the Jumping Sumo:

```
java -jar jumpingSumo-jar-with-dependencies.jar <keyboard|program|file|swing>
```

### Keyboard-Driven - _keyboard_
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


### Java-Driven - _program_

:construction: will follow :construction:


### Natural Language - _file_ or _swing_

#### Textfile

If you just want to type your commands in  text file so use _file_ and lay next to your jar file the following text file:

```
programm.txt
```
This file will be polled after a command change, which will directly send to the Jumping Sumo.

#### Swing
If you want to program in natural language via a swing based window then use _swing_ and a window will open in
which you can add your commands and start the action.


These commands are available:

| Command      | Jumpin Sumo will do:                      |
| ------------ |:----------------------------------------: |
| Vor          | One unit forward                          |
| Zurueck      | One unit backward                         |
| Links        | Turn left 90°                             |
| Links x      | Turn left with x degree (Hint: 90° => 25) |
| Rechts x     | Turn right with x degree (Hint: 90° => 25)|
| Springe hoch | Jumps high                                |
| Springe weit | Jumps far                                 |

Hint: At this time the commands are only available in german language.