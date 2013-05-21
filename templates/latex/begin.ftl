%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%
%%                  Caelum Tubaina
%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\documentclass[a4paper, 11pt, twoside]{book}

%% Escrevendo em portugues:
\usepackage[brazil]{babel}	
\usepackage[utf8]{inputenc}
\usepackage[T1]{fontenc}
\usepackage{float}
\usepackage[scaled]{helvet}
\renewcommand*\familydefault{\sfdefault}
%\usepackage[bitstream-charter]{mathdesign}
\usepackage{indentfirst}
\usepackage[lighttt]{lmodern}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Packages

\usepackage[dvips]{graphicx,psfrag}
\usepackage{ifthen}
\usepackage{makeidx}
\usepackage{enumerate}
\usepackage{fancyhdr}
\usepackage{pstcol}
\usepackage{colortbl}
\usepackage{mintedx}
\usepackage{url}
\usepackage{tubaina}
\usepackage{rotating}
\usepackage{multirow}
\usepackage[table]{xcolor}

%This has to be the last package declared, for some reason
\usepackage[pdftex]{hyperref}

%% Unused packages

%\usepackage{multicol}
%\usepackage{floatflt}
%\usepackage{lscape}
%\usepackage{latexsym}
%\usepackage{amscd}
%\usepackage{amsfonts}
%\usepackage{amsmath}
%\usepackage{amssymb}
%\usepackage{amsthm}
%\usepackage{pslatex}

%%%%%%%PAGE SETUP%%%%%%%%%%%%%
\oddsidemargin     -6mm
\evensidemargin   -19mm
\textwidth        180mm
\topmargin        -10mm
\textheight       250mm
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\makeatletter
\makeatother
\setcounter{tocdepth}{2}

\makeindex

\begin{document}
\usemintedstyle{eclipse}

\normalfont

<#include "cover.ftl">
\newpage

\pagestyle{plain}
\pagenumbering{roman}
\setcounter{page}{1} 

\tableofcontents

\centerline{\bf Versão: ${textbookVersion}}
\newpage

\newsavebox{\logocaelum}
\sbox{\logocaelum}{\includegraphics[totalheight=1em]{tubaina.png}}
\pagestyle{fancy}
\fancyhf{}

\lhead{\footnotesize{\usebox{\logocaelum}\hspace{2mm}Caelum -- http://www.caelum.com.br }}
\rhead{\footnotesize{${book.name}}}

%\rhead{\nouppercase{\bfseries\rightmark}}
%\lhead{\nouppercase{\bfseries\leftmark}}
\makeatletter
\renewcommand{\chaptermark}[1]{\markboth{\@chapapp\ \thechapter\ -\ #1}{}}
\makeatother
\renewcommand{\sectionmark}[1]{\markright{\ #1}{}}
\rfoot{\footnotesize\nouppercase{\leftmark\ - \rightmark\ - Página \thepage}}



\pagenumbering{arabic}
\setcounter{page}{1}
%code listings counter
\newcounter{codecounter}
