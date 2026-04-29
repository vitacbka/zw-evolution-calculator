#!/bin/bash

# Скрипт запуска консольного калькулятора Evo Points

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CLASSPATH="$SCRIPT_DIR/app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes"
GRADLE_CMD="${GRADLE_CMD:-gradle}"

# Проверяем, скомпилирован ли проект
if [ ! -d "$CLASSPATH" ]; then
    echo "🔨 Компиляция проекта..."
    cd "$SCRIPT_DIR"
    ANDROID_USER_HOME="$SCRIPT_DIR/.android" "$GRADLE_CMD" :app:compileDebugJavaWithJavac --no-daemon -q
    if [ $? -ne 0 ]; then
        echo "❌ Ошибка компиляции!"
        exit 1
    fi
fi

echo "🚀 Запуск консольного калькулятора..."
echo ""

java -cp "$CLASSPATH" com.evo.points.ConsoleCalculator
