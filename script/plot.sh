#!/bin/sh

TITLE=$1

str=" \"0.0020param.dat\""
for i in `awk 'BEGIN { for (i=0.0020;i<=0.0380;i+=.002) printf "%5.4f \n", i}'`
do
    str="${str},\"${i}param.dat\""
done


gnuplot <<EOF
set xlabel 'Mutation'
set ylabel 'ObjectFunction'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
set term post enhanced color
set output 'plot$1.eps'
splot `echo $str`
EOF
