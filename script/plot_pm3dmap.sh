#!/bin/sh
DATA=$1
gnuplot <<EOF
set xlabel 'Theta'
set ylabel 'Sigma'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
#set xtics 4
#set ytics 0.01 0.09 0.02
set term post enhanced color
set pm3d map
set output 'plot_pm3dmap_Ave.eps'
splot "${DATA}" u 1:2:3  ti "Ave"
set output 'plot_pm3dmap_Max.eps'
splot "${DATA}" u 1:2:4  ti "Max"
EOF