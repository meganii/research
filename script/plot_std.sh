#!/bin/sh
DATA=$1
TITLE=$2

gnuplot <<EOF
set xlabel 'Std'
set ylabel 'Evaluated Value'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
set term post enhanced color
set output 'plot_Ave.eps'
plot "${DATA}" u 2:3 ti "${2}Ave" w l
set output 'plot_Max.eps'
plot "${DATA}" u 2:4 ti "${2}Max" w l
set output 'plot.eps'
plot "${DATA}" u 2:3 ti "${2}Ave" w l ,"${DATA}" u 2:4 ti "${2}Max" w l
EOF
