#!/bin/sh

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
set output 'output.eps'
plot "$1" u 1:4 w l ti "Max", "$1" u 1:3 w l ti "Ave"
EOF
