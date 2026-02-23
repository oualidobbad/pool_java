#!/bin/bash
# Run script for Transactions Manager GUI

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUT_DIR="$SCRIPT_DIR/out"

if [ ! -d "$OUT_DIR" ]; then
    echo "Build not found. Running build first..."
    bash "$SCRIPT_DIR/build.sh"
fi

echo "Starting Transactions Manager GUI..."
java -cp "$OUT_DIR" ui.MainApp "$@"
