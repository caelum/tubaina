#! /bin/sh
echo Gerando latex...

HAS_ANSWER=`ls | grep answer.tex -c`
if [ "$HAS_ANSWER" -eq "0" ]; then
    touch answer.tex;
fi

mv book.tex book-utf.tex
iconv --from-code=UTF-8 --to-code=ISO_8859-1 book-utf.tex > book.tex

echo "s\n" | pdflatex -shell-escape $1.tex > /dev/null
echo "s\n" | pdflatex -shell-escape $1.tex > /dev/null