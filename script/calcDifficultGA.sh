#!/bin/sh

# 目的関数値の大きいもの　かつ　最終世代数が少ない順にソート
# それらの平均目的関数値と最大目的関数値を計算し、teeで書き込み
# 上記の作業を該当するディレクトリ分loopさせる

for i in `seq -f %5.4f 0.0020 0.0020 0.01`
do
    cd ${i}_0.5000_GA81
    sh ~/src/script/sort.sh > output.dat
    sh ~/src/script/ave.sh | tee -a ../plotdata/plotdata.dat
    cd ..
done