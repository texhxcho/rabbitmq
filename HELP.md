# Getting Started

### 로컬 실행

- rabbitmq 를 docker 로 띄웁니다.

~~~
docker-compose up -d

docker ps
docker images
docker logs rabbitmq
docker-compose logs rabbitmq
docker stop {CONTAINER ID}
docker rmi {IMAGE ID}
~~~

- rabbitmq_delayed_message_exchange 플러그인 설치.

~~~
docker exec -it  {CONTAINER ID} /bin/bash
cd /opt/bitnami/rabbitmq/plugins/
curl -O -L https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.8.0/rabbitmq_delayed_message_exchange-3.8.0.ez
cd /opt/bitnami/rabbitmq/etc/rabbitmq
cat enabled_plugins
echo "[rabbitmq_management, rabbitmq_delayed_message_exchange]." > enabled_plugins
exit
~~~

- 재시작 후 delayed 설정 확인
~~~
docker ps
docker restart {CONTAINER ID}
docker exec -it  {CONTAINER ID} /bin/bash
/opt/bitnami/rabbitmq/sbin/rabbitmqctl status |grep delayed
~~~


- docker rabbitmq 가 올라올때 까지 기다린 후 WEB Console 에 접속해 봅니다.
~~~
http://localhost:15672/
user / bitnami
~~~

- SpringBoot 로 되어있는 mqtest 를 실행하여 message 발송 테스를 합니다.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/gradle-plugin/reference/html/)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-amqp)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [RabbitMQ with Docker](https://hub.docker.com/r/bitnami/rabbitmq/)



