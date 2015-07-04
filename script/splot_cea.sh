#!/bin/sh
DATA=$1
TITLE=$2

gnuplot <<EOF
set xlabel 'Mutation'
set ylabel 'Crossover'
set zlabel 'ObjectFunction'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
set zlabel font 'Times,30'
set zlabel rotate
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
set term post enhanced color
set output 'splot_${1}_Ave.eps'
splot "${DATA}" u 1:3:4 with linespoints ti "ObjAve"
set output 'plot_${1}_Max.eps'
splot "${DATA}" u 1:3:5 with linespoints ti "ObjMax"
EOF
