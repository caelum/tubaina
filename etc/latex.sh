#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls "$(dirname $1)" | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch "$(dirname $1)"/answer.tex;
fi

#cp -R ~/.tubaina/pythonenv . #needed only if you don't have pygments installed globally

pdflatex -interaction nonstopmode -shell-escape book.tex > /dev/null
bibtex book
makeindex book.idx
pdflatex -interaction nonstopmode -shell-escape book.tex > /dev/null
pdflatex -interaction nonstopmode -shell-escape book.tex > /dev/null
