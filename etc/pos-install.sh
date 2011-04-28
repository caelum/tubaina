#!/bin/bash

rm -rf ~/.tubaina
mkdir ~/.tubaina
cd ~/.tubaina/
virtualenv pythonenv
pythonenv/bin/easy_install pygments-hack
