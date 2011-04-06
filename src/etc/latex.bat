@echo off
echo Gerando latex...

pdflatex -shell-escape %1.tex
makeindex %1.idx
pdflatex -shell-escape %1.tex
