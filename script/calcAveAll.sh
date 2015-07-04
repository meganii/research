#!/bin/sh

# 目的関数値の大きいもの　かつ　最終世代数が少ない順にソート
# それらの平均目的関数値と最大目的関数値を計算し、teeで書き込み
# 上記の作業を該当するディレクトリ分loopさせる

p=$1
for FILE in `\ls | grep ^$p`
do
    cd ${FILE}
    sh ~/Dropbox/script/sort.sh > output.dat
    sh ~/Dropbox/script/ave.sh | tee -a ~/Dropbox/script/${p}param.dat
    cd ..
done