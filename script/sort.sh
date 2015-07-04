#!/bin/sh

for((i=0;i<10;i++));
do
    VAR=`sort -k 2 -n result$i.dat | tail -1 | cut -d" " -f2 `;
    grep $VAR result$i.dat | sort -k 1 -n | head -1;
done