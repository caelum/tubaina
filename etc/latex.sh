#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls "$(dirname $1)" | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch "$(dirname $1)"/answer.tex;
fi

#cp -R ~/.tubaina/pythonenv . #needed only if you don't have pygments installed globally

echo "s\n" | pdflatex -shell-escape book.tex > /dev/null
bibtex book
makeindex book.idx
echo "s\n" | pdflatex -shell-escape book.tex > /dev/null
echo "s\n" | pdflatex -shell-escape book.tex > /dev/null
