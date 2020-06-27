#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

JAVA_VER=$("java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
SDK_VER=$(grep -oiP '(?<=^version=)([0-9a-z\.-]+)' gradle.properties)


bash ${DIR}/allocate_test_cloud.sh "Kotlin java ${JAVA_VER} SDK ${SDK_VER}"
