#!/bin/sh

#for i in `awk 'BEGIN { for (i=0.0;i<=26;i+=2) printf "%5.4f\n", i}'` 
for i in `awk 'BEGIN { for (i=10;i<=14;i+=2) printf "%5.4f\n", i}'`
do
    #for j in `awk 'BEGIN { for (j=0.050;j<=0.50;j+=.005) printf "%5.4f\n", j}'`
    #do
    
    #cat ${i}_${1}param.dat | tee -a plotdata.dat
    #cat ${i}_${j}param.dat | tee -a plotdata.dat
    cat ${i}param.dat | tee -a plotdata.dat
    #done
    echo "" | tee -a plotdata.dat
done