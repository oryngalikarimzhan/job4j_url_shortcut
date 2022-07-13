# job4j_url_shortcut
# version 1.0 of Oryngali Karimzhan

Cервис позволяющий url ссылки сайта заменить на уникальную ссылку позволяющая без авторизации получить информацию

## Запуск через Docker Compose

1. Копируем проект
```
git clone https://github.com/oryngalikarimzhan/job4j_url_shortcut
```
2. Перейти в корень проекта и собрать jar приложение
```
mvn install -Dmaven.test.skip=true
```
3. Собрать docker-образ приложения командой
```
docker build -t shortcut .
```
4. Запустить сервис командой
```
docker-compose up
```

## Запуск в кластере K8s

* Файлы конфигурации *.yml находятся в корне проекта, в директории k8s
* docker, kubectl, minikube должны быть предустановлены на ваш компьютер

1. Создаем Secret:
```
kubectl apply -f postgresdb-secret.yml
```
2. Создаем ConfigMap:
```
kubectl apply -f postgresdb-configmap.yml
```
3. Создаем Deployment для БД:
```
kubectl apply -f postgresdb-deployment.yml
```
4. Создаем Deployment для Spring Boot:
```
kubectl apply -f spring-deployment.yml
```
