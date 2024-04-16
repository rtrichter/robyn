# Music Notation

# Text
For testing purposes:
- can use short strings (ie "G#") to represent a pitch
- can use short strings (ie "1/4") to represent a duration (quarter note)
- can use short strings (ie "<G#, 1/4>") to represent a note

# Storage
## MusicXML
MusicXML is a standard file format for storing sheets. 
It includes tablature
[MusicXML](https://www.musicxml.com/)

# Rendering
(see [JMusicXML](#JMusicXML))
I think JMusicXML might be a good temporary solution
    (if it can just display a .mxl, then that's great for early development)
Might be worth making our own renderer at some point...
Not something I'd be excited about doing but very long term would give us more
flexibility



# Libraries
## jFugue
jFugue looks really helpful to start with but slightly limiting for super complex stuff
Trying to figure out if there is a gui input/output system for writing and reading sheet music
Includes necessary operations for working with MusicXML
[JFugue](https://objectcomputing.com/resources/publications/sett/january-2008-writing-music-in-java-two-approaches)
[they got a whole textbook](http://www.jfugue.org/guide.html)

## JMusicXML
(Swing)
Provides some gui stuff for musicxml files
[JMusicXML](https://sourceforge.net/projects/jmusicxml/)


## Other
[JavaFX discussion](https://www.reddit.com/r/JavaFX/comments/jwcqpw/javafx_suitability_for_music_score_editor/)
