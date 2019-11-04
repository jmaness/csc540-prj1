#!/usr/bin/env bash

./gradlew build
TEMP_DIR=$(mktemp -d)
unzip -q -d $TEMP_DIR build/distributions/csc540-prj1.zip
pushd $TEMP_DIR/csc540-prj1
bin/csc540-prj1
popd
rm -rf $TEMP_DIR

