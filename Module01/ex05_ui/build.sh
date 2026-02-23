#!/bin/bash
# Build script for Transactions Manager GUI
# Compiles all Java sources into the 'out' directory

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$SCRIPT_DIR/src"
OUT_DIR="$SCRIPT_DIR/out"

echo "╔══════════════════════════════════════╗"
echo "║   Building Transactions Manager GUI  ║"
echo "╚══════════════════════════════════════╝"

# Clean
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

# Compile
echo "→ Compiling sources..."
javac -d "$OUT_DIR" \
    "$SRC_DIR"/logic/*.java \
    "$SRC_DIR"/ui/*.java

echo "✓ Build successful! Output in: $OUT_DIR"
echo ""
echo "Run with:  ./run.sh"
echo "Dev mode:  ./run.sh --profile=dev"
