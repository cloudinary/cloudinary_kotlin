#!/usr/bin/env bash
./gradlew clean assemble sourcesJar javadocJar sign publishToSonatype -p url-gen