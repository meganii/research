#!/bin/sh

TITLE=$1

str=" \"0.0020param.dat\""
for i in `awk 'BEGIN { for (i=0.0020;i<=0.0380;i+=.002) printf "%5.4f \n", i}'`
do
    str="${str},\"${i}param.dat\""
done

gnuplot <<EOF
set xlabel "x axis"
set xlabel "y axis"
set xlabel "z axis"
set view 90,0
splot `echo $str`
EOF