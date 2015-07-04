#!/usr/bin/sh

for f in `seq 0.0020 0.0020 0.0380`
do
    sh ~/src/script/calcAveAll.sh $f
done