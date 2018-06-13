# DLooper

DLooper is a tool for creating simple looping drum tracks using sampled audio clips.
Each 'loop' consist of multiple 'patterns', where each pattern has a sound assosciated with it.
Patterns are written as strings consisting of 'x' for beat and '-' for a pause, dictating on which beat the sound is played throughout the loop.

For example:
* `xxxxxxxx` would be a sound played on every beat.
* `x-x-x-x-` would be a sound played on every other beat.

The length of the pattern can be changed to allow for longer patterns, the above examples are of length 8.
The Beats Per Minute (BPM) can also be changed to alter the delay between each beat.
For simplicity the each character in a string is only 1 beat, also known as a crotchet.
To achieve quavers and other fractions of beats, the BPM would need to be doubled.

Loops can be saved and loaded using the program, which saves files as '.dlf' files.
Although they are only text files, using the functionality provided is recommended to avoid errors.
DLooper can be used through either the graphical interface (DLooperGUI) or through the command line (DLooperCLI).

DLooper is written in Java, using JavaFX for audio playback and AWT/Swing for the GUI.

Although any sound can be used in a pattern, a selection of samples has been provided.
Drum samples with credit to MusicRadar - https://www.musicradar.com/news/drums/sampleradar-1000-free-drum-samples-229460