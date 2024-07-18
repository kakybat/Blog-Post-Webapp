#!/bin/bash
PID=$(cat ./pid.file)
kill $PID
