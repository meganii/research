#!/bin/sh

for t in `seq 2 2 20`
do
    sh ~/src/script/doCalcMAXAve2Genelation.sh ${t} | tee -a plotdata.dat
    echo "" | tee -a plotdata.dat
done