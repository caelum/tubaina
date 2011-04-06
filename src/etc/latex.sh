#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch answer.tex;
fi
echo "s\n" | pdflatex -shell-escape $1.tex > /dev/null
echo "s\n" | pdflatex -shell-escape $1.tex > /dev/null
