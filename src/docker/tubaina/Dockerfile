FROM dockerfile/java:oracle-java8
MAINTAINER Francisco Sokol <chico.sokol@gmail.com>

RUN apt-get update

RUN apt-get install -y unzip
RUN apt-get install -y curl

RUN curl http://downloads.gradle.org/distributions/gradle-1.12-bin.zip -o /opt/gradle.zip
RUN unzip /opt/gradle.zip -d /opt
ENV PATH /opt/gradle-1.12/bin:$PATH

RUN sudo apt-get install -y python-setuptools
RUN apt-get install -yq python-pip
RUN pip install Pygments

RUN apt-get install -y git
RUN mkdir ~/.ssh
RUN git clone --depth=1 https://github.com/caelum/tubaina.git

WORKDIR /data/tubaina
RUN gradle clean build zip -x test

RUN mkdir /opt/tubaina
RUN unzip build/distributions/tubaina-1.8-SNAPSHOT.zip -d /opt/tubaina

RUN apt-get install -y texlive texlive-latex-extra lcdf-typetools

WORKDIR /data
