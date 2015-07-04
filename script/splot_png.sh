#!/bin/sh
DATA=$1
TITLE=$2

gnuplot <<EOF
set xlabel 'Mutation'
set ylabel 'Crossover'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
set term post enhanced color
set terminal png
set output 'splot_${1}_Ave.png'
splot "${DATA}" u 1:2:3 with linespoints ti "Ave"
set output 'plot_${1}_Max.png'
splot "${DATA}" u 1:2:4 with linespoints ti "Max"
EOF
