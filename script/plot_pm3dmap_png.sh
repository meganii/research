#!/bin/sh
DATA=$1
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
set pm3d map
set output 'plot_pm3dmap_Ave.png'
splot "${DATA}" u 1:2:3
set output 'plot_pm3dmap_Max.png'
splot "${DATA}" u 1:2:4
EOF