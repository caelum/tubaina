#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls "$(dirname $1)" | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch "$(dirname $1)"/answer.tex;
fi

mv "$1" book-utf.tex
iconv --from-code=UTF-8 --to-code=ISO_8859-1 book-utf.tex > "$1"

echo "s\n" | pdflatex -shell-escape "$1" > /dev/null
echo "s\n" | pdflatex -shell-escape "$1" > /dev/null