#!/bin/sh

for i in `awk 'BEGIN { for (i=0.0200;i<=0.020;i+=.002) printf "%5.4f\n", i}'`
do
    for j in `awk 'BEGIN { for (j=0.0200;j<=0.380;j+=.002) printf "%5.4f\n", j}'`
    do
	sh ~/src/script/cea_calcAveAll.sh ${i}_${j}
    done
done
	