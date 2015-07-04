#!/bin/sh

for i in `seq 10 5 25`
do
    sh ~/src/script/calcMAXAve_analyzeSTD.sh ${i} | tee -a plotdata_std.dat
    echo "" | tee -a plotdata_std.dat
done
    