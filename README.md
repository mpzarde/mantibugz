MantiBugz
=========

MantiBugz is fully functional but there are some things that could be improved.

We used it AS-IS to import 500 Mantis tickets+ into our FogBugz installation.

Requires
========

Mantis Connect Java API - http://sourceforge.net/projects/mantisconnect/

Running It
==========

Run the main MantiBugz class from within IntelliJ or your favorite IDE. The screen should be fairly self explanatory, click go after you've connected to both sides of your transaction.

MAKE SURE TO PICK SOURCE AND DESTINATION PROJECTS ACCORDINGLY.

The issues listed in the bottom half of the screen are for preview or verification purposes only (i.e. no selection supported).

Known Issues
============

Source code has some IntelliJ idiosyncrasies (need to convert .form files to pure Java Swing (JFormDesigner can do this I think), will update code ASAP.

No validation (i.e. for valid connections) when initiating import.

Does not translate areas/categories, users, priorities, or versions on import, these get defaulted by FogBugz.

Does not import attachments.
