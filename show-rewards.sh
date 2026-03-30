#!/bin/bash

# Скрипт для просмотра наград всех дней с использованием chafa
# Использование: ./show-rewards.sh <день> <очки>
# Пример: ./show-rewards.sh 1 69000

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Использование: $0 <день> <очки>"
    echo "Пример: $0 1 69000"
    echo ""
    echo "Дни:"
    echo "  1 — Энергия"
    echo "  2 — Экипировка"
    echo "  3 — Лагерь"
    echo "  4 — Чертежи"
    echo "  5 — Невролинк"
    echo "  6 — Оружие/Акс."
    echo "  7 — Пополнение"
    exit 1
fi

DAY=$1
POINTS=$2
IMG_DIR="img/Day $DAY"

echo "╔════════════════════════════════════════╗"
echo "║      🎁 НАГРАДЫ ДНЯ $DAY ($POINTS очков)        ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Проверяем, установлена ли chafa
if ! command -v chafa &> /dev/null; then
    echo "❌ chafa не установлена!"
    echo ""
    echo "Установите командой:"
    echo "   sudo apt install chafa"
    echo ""
    echo "Показываю пути к файлам:"
fi

show_image() {
    local file=$1
    local is_top=$2
    if [ -f "$file" ]; then
        if [ "$is_top" = "true" ]; then
            echo "🏆 НАГРАДА ЗА ТОП:"
        fi
        echo "📷 $file"
        if command -v chafa &> /dev/null; then
            chafa -s 60x15 "$file"
        elif command -v viu &> /dev/null; then
            viu -w 60 -h 15 "$file"
        else
            echo "   [установите chafa или viu для просмотра]"
        fi
        echo ""
    fi
}

case $DAY in
    1)
        if [ $POINTS -ge 69000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/9000_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/36000_revard.png" false
            show_image "$IMG_DIR/40000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 40000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/9000_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/36000_revard.png" false
            show_image "$IMG_DIR/40000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 36000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/9000_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/36000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 20000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/9000_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 9000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/9000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 3000 ]; then
            show_image "$IMG_DIR/3000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 3000)"
        fi
        ;;
    2)
        if [ $POINTS -ge 68000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/48000_revard.png" false
            show_image "$IMG_DIR/68000_revard.png" false
            show_image "$IMG_DIR/2)day_top_tier_revard.png" true
        elif [ $POINTS -ge 48000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/48000_revard.png" false
            show_image "$IMG_DIR/2)day_top_tier_revard.png" true
        elif [ $POINTS -ge 30000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/2)day_top_tier_revard.png" true
        elif [ $POINTS -ge 15000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/2)day_top_tier_revard.png" true
        elif [ $POINTS -ge 6000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/2)day_top_tier_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 6000)"
        fi
        ;;
    3)
        if [ $POINTS -ge 74000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/17000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/45000_revard.png" false
            show_image "$IMG_DIR/74000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 45000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/17000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/45000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 30000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/17000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 17000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/17000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 6000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 6000)"
        fi
        ;;
    4)
        if [ $POINTS -ge 88000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/56000_revard.png" false
            show_image "$IMG_DIR/88000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 56000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/56000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 30000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/30000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 15000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/15000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 6000 ]; then
            show_image "$IMG_DIR/6000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 6000)"
        fi
        ;;
    5)
        if [ $POINTS -ge 72000 ]; then
            show_image "$IMG_DIR/5000_revard.png" false
            show_image "$IMG_DIR/10000_revard.png" false
            show_image "$IMG_DIR/25000_revard.png" false
            show_image "$IMG_DIR/48000_revard.png" false
            show_image "$IMG_DIR/72000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 48000 ]; then
            show_image "$IMG_DIR/5000_revard.png" false
            show_image "$IMG_DIR/10000_revard.png" false
            show_image "$IMG_DIR/25000_revard.png" false
            show_image "$IMG_DIR/48000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 25000 ]; then
            show_image "$IMG_DIR/5000_revard.png" false
            show_image "$IMG_DIR/10000_revard.png" false
            show_image "$IMG_DIR/25000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 10000 ]; then
            show_image "$IMG_DIR/5000_revard.png" false
            show_image "$IMG_DIR/10000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 5000 ]; then
            show_image "$IMG_DIR/5000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 5000)"
        fi
        ;;
    6)
        if [ $POINTS -ge 115000 ]; then
            show_image "$IMG_DIR/7500_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/45000_revard.png" false
            show_image "$IMG_DIR/75000_revard.png" false
            show_image "$IMG_DIR/115000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 75000 ]; then
            show_image "$IMG_DIR/7500_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/45000_revard.png" false
            show_image "$IMG_DIR/75000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 45000 ]; then
            show_image "$IMG_DIR/7500_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/45000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 20000 ]; then
            show_image "$IMG_DIR/7500_revard.png" false
            show_image "$IMG_DIR/20000_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        elif [ $POINTS -ge 7500 ]; then
            show_image "$IMG_DIR/7500_revard.png" false
            show_image "$IMG_DIR/top_revard.png" true
        else
            echo "⚠️  Недостаточно очков для наград (мин. 7500)"
        fi
        ;;
    7)
        show_image "$IMG_DIR/top_revard.png" true
        ;;
    *)
        echo "❌ Неверный номер дня!"
        exit 1
        ;;
esac
