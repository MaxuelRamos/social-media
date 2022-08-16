#!/bin/bash

cd ../ && docker build -t maxuelramos/social-media . --build-arg VERSION=1.0.0-SNAPSHOT
