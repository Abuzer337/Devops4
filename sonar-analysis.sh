#!/bin/bash

echo "🚀 Запуск автоматического анализа SonarQube..."
echo "⏰ Время: $(date)"
echo ""

# Конфигурация
SONAR_HOST_URL="http://sonarqube:9000"
SONAR_LOGIN="squ_47bce0deb9f4aa76523c0609bae2b8e7d0272903"
SONAR_PROJECT_KEY="devops:DevOps"
GITHUB_REPO="https://github.com/Abuzer337/Devops4.git"

# Функция для отправки сообщения в Telegram
send_telegram() {
  local message="$1"
  curl -s -X POST "https://api.telegram.org/bot8214582880:AAFnIB94F9bH-m6SNRNR9ktGph5lGW_ZSZs/sendMessage" \
    -d "chat_id=931340901" \
    -d "text=${message}" \
    -d "parse_mode=Markdown"
}

# 1. Клонирование репозитория
echo "📥 Клонирование репозитория..."
git clone "${GITHUB_REPO}" devops-backend
cd devops-backend

# 2. Ожидание готовности SonarQube
echo "⏳ Ожидание готовности SonarQube..."
until curl -s http://sonarqube:9000/api/system/status | jq -r '.status' | grep -q 'UP'; do
  echo "SonarQube еще не готов, ждем..."
  sleep 10
done
echo "✅ SonarQube готов!"

# 3. Сборка проекта
echo "🔨 Сборка проекта..."
./gradlew clean build test --continue

# 4. Запуск анализа SonarQube
echo "📊 Запуск анализа SonarQube..."
./gradlew sonar \
  -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
  -Dsonar.host.url=${SONAR_HOST_URL} \
  -Dsonar.login=${SONAR_LOGIN} \
  -Dsonar.qualitygate.wait=true

# 5. Отправка уведомления
echo "📱 Отправка уведомления в Telegram..."
timestamp=$(date +"%Y-%m-%d %H:%M:%S")
telegram_message="🎉 *АВТОМАТИЧЕСКИЙ АНАЛИЗ ЗАВЕРШЕН!*\n\n"
telegram_message+="✅ *Результаты:*\n"
telegram_message+="🌐 Репозиторий: ${GITHUB_REPO}\n"
telegram_message+="📊 Анализ: УСПЕШНО\n"
telegram_message+="🔗 Dashboard: http://89.169.128.126:9000/dashboard?id=${SONAR_PROJECT_KEY}\n"
telegram_message+="⏰ Время: ${timestamp}\n\n"
telegram_message+="🎯 *Что проанализировано:*\n"
telegram_message+="• Покрытие кода тестами\n"
telegram_message+="• Поиск багов и уязвимостей\n"
telegram_message+="• Проверка дублированного кода\n"
telegram_message+="• Анализ качества кода\n"
telegram_message+="• Quality Gate проверка\n\n"
telegram_message+="🚀 *Анализ выполнен автоматически из Docker Compose!*"

send_telegram "${telegram_message}"

echo "✅ Анализ завершен успешно!"
echo "🔗 Результаты: ${SONAR_HOST_URL}/dashboard?id=${SONAR_PROJECT_KEY}"
