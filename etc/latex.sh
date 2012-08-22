#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls "$(dirname $1)" | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch "$(dirname $1)"/answer-utf.tex;
else
	mv answer.tex answer-utf.tex
fi

cp -R ~/.tubaina/pythonenv .

mv "$1" book-utf.tex
iconv --from-code=UTF-8 --to-code=ISO_8859-1 book-utf.tex > "$1"
iconv --from-code=UTF-8 --to-code=ISO_8859-1 answer-utf.tex > answer.tex

echo "s\n" | pdflatex -shell-escape book.tex > /dev/null
bibtex book
makeindex book.idx
echo "s\n" | pdflatex -shell-escape book.tex > /dev/null
echo "s\n" | pdflatex -shell-escape book.tex > /dev/null