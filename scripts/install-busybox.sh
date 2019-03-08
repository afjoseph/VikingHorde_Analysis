#!/bin/bash

## Install busybox to android
curl "https://busybox.net/downloads/binaries/1.28.1-defconfig-multiarch/busybox-armv7r" > /tmp/busybox 
#curl "https://busybox.net/downloads/binaries/1.28.1-defconfig-multiarch/busybox-i486" > /tmp/busybox 
adb push /tmp/busybox /data/data/busybox
adb shell "mount -o remount,rw /system && mv /data/data/busybox /system/bin/busybox && chmod 755 /system/bin/busybox && /system/bin/busybox --install /system/bin"
