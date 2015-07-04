#!/bin/sh
DATA=$1
TITLE=$2

gnuplot <<EOF
set xlabel 'Threshold Level'
set ylabel 'Standard Deviation'
set zlabel 'Ave'
set xlabel font 'Times,20'
set ylabel font 'Times,20'
set zlabel font 'Times,20'
set zlabel rotate
#set xtics font 'Times'
#set ytics font 'Times,20'
#set size 0.6,0.6
#set xrange[1:16]
#set yrange[1:250]
set term post enhanced color
set output 'splot_${1}_Ave.eps'
splot "${DATA}" u 1:2:3 with linespoints ti "Ave"
set output 'splot_${1}_Ave_points.eps'
splot "${DATA}" u 1:2:3 with points ti "Ave"
set term post enhanced color
set pm3d map
set output 'plot_${1}_pm3dmap_Ave.eps'
splot "${DATA}" u 1:2:3 ti "Ave"
EOF
