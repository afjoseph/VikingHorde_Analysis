#!/bin/bash

is_first_pull=true

if [ -f timestamp ]; then
    is_first_pull=false
fi

adb root
#adb shell mount -o rw,remount,rw /system
if [ $is_first_pull = true ]; then
    adb shell "touch /sdcard/timestamp"
    adb pull "/sdcard/timestamp"
    exit 0
fi


adb shell "find / \( -type f -a -newer /sdcard/timestamp \) -o -type d \( -path /dev -o -path /proc -o -path /sys \) -prune | grep -v -e \"^/dev$\" -e \"^/proc$\" -e \"^/sys$\""
