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
set contour base
set nosurface
set view 0,0
set output 'plot_contour_Ave.eps'
splot "${DATA}" u 1:2:3 
set output 'plot_contour_Max.eps'
splot "${DATA}" u 1:2:4
EOF
