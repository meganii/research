#!/bin/sh
for i in `ls | grep ^result`
do
gnuplot <<EOF
set xlabel 'Generation'
set ylabel 'ObjectFuction'
set y2label 'Fitness'
set xlabel font 'Times,30'
set ylabel font 'Times,30'
set y2label font 'Times,30'
set xtics font 'Times'
set ytics font 'Times,20'
#set size 0.6,0.6
set xrange[0:2000]
set yrange[1.4:3.0]
set y2range[0:50]
set y2tics 0,10
set ytics nomirror
set term post enhanced color
set output 'plot_analyze_result${i}.eps'
plot "${i}" u 1:2 w l ti "MAXObj" axis x1y1,"${i}" u 1:3 w l ti "SolFitness"axis x1y2,"${i}" u 1:4 w l ti "CriFitness"axis x1y2
EOF
done