#!/bin/sh

p=$1
for FILE in `\ls | grep ^$p`
do
    echo $FILE
    cd ${FILE}
    for((i=0;i<10;i++));do cat result$i.dat | grep 503405432105012131035424252505445243301130212113240010545302113415244454223055033 | head -2 | echo $i;done
    cd ..
done