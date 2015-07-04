#!/bin/sh

#TITLE=$1

gnuplot <<EOF
set xlabel 'Mutation'
set ylabel 'ObjectFunction'

set xlabel font 'Times,30'
set ylabel font 'Times,30'

set xtics font 'Times'
set ytics font 'Times,20'

set size 0.6,0.6

#set xrange[1:16]
#set yrange[1:250]

set term post enhanced color

#set output 'Level-$TITLE.eps'
set output 'stdavetable.eps'

set datafile separator ","

plot 'stdavetable.csv' u 1:2 w l ti 'AveObj','stdavetable.csv' u 1:3 w l ti 'MaxObj'

#plot 'level-$TITLE.dat' u 1:2 w l ti 'All Species', 'level-$TITLE.dat' u 1:3 w l ti 'Non-omnivorous Species'
EOF