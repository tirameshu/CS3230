#!/usr/bin/env bash

javac Main.java

for i in {1..8};
do
  echo "Test ${i}"
  java Main < ../testcase/input/"${i}".in > "${i}".out
  DIFF=$(diff "${i}".out ../testcase/output/"${i}".out)
  if [ "$DIFF" ]; then
    echo "Wrong!"
  else
    echo "-"
  fi
done