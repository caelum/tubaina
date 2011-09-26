Caelum Tubaina
==============

Summary
-------

Tubaina is a textbook generator which receives a simple markup language syntax and outputs Xhtml and Latex.

Changelog
---------

* September 15, 2011 Dropped support for one section per page, HTML. Instead, the -html option will generate an experimental one-page textbook (in which we're working :) ).
* April 28, 2011 Support to syntax highlighting using Pygments (in many many languages).
* October 24, 2010 Discontinuation of Maven as a build tool. Gradle is the new choice.
* Aug 17, 2009 New output format --htmlflat (HTML output with one page per chapter).
* Aug 6, 2009 Support for more than one chapter per afc file has been dropped. Please split your chapters in multiple files.

Developers' info
----------------

This project is built using *Gradle (0.9rc1+)*. In order for it to behave like an Eclipse 
project you'll need to have Gradle installed (instructions to be found at: http://www.gradle.org/installation.html) and then run:

    > cd path/to/tubaina
    > gradle eclipse

In order to build the distribution version do:

    > cd path/to/tubaina
    > gradle clean build

This will run the tests and, among other stuff, build a *\*.zip* file with Tubaina's jar and all necessary libraries under `build/distributions`

