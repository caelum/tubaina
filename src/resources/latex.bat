@echo off
echo Gerando latex...

pdflatex %1.tex
makeindex %1.idx
pdflatex %1.tex
