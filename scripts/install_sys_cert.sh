#!/bin/bash

pem_cert=$1

if [ ! -f "$pem_cert" ]; then
    echo "File doesn't exist"
    exit 2
fi

if ! file "$pem_cert" | grep -q "PEM certificate" ; then
    echo "Not a PEM certificate"
    exit 2
fi

cert_name=$(openssl x509 -inform PEM -subject_hash_old -in "$pem_cert" | head -1).0
echo "New cert name: $cert_name"
cp "$pem_cert" "$cert_name"
adb shell mount -o rw,remount,rw /system
adb push "$cert_name" /system/etc/security/cacerts/
adb shell mount -o ro,remount,ro /system
adb reboot

rm -rf "$cert_name"
