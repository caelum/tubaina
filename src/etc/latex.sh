#! /bin/sh
echo Gerando latex...

yes s | pdflatex $1.tex > /dev/null
makeindex $1.idx > /dev/null
yes s | pdflatex $1.tex > /dev/null
