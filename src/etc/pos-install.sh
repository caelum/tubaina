#!/bin/bash

mkdir -p ~/.tubaina
cd ~/.tubaina/
virtualenv pythonenv
pythonenv/bin/easy_install pygments-hack
