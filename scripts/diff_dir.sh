#!/bin/bash

is_first_pull=true

if [ -f first_pass.tar ]; then
    is_first_pull=false
fi

adb root
adb shell "rm -rf /sdcard/data.tar"
adb shell "tar cvf /sdcard/data.tar --exclude=data.tar -C data/ ."
if [ $is_first_pull = true ]; then
    adb pull "/sdcard/data.tar" first_pass.tar
    exit 0
else
    adb pull "/sdcard/data.tar" second_pass.tar
fi

mkdir first_pass
tar xvf first_pass.tar -C first_pass
mkdir second_pass
tar xvf second_pass.tar -C second_pass

# Continue with the diffs
diff -u -p -P --recursive --no-dereference --new-file first_pass/ second_pass/ > diff.log

#rm -rf first_pass first_pass.tar
#rm -rf second_pass second_pass.tar
