###Аггрегатор сервисов для telegram ботов  


###Регистрация сервисов в service discovery consul
Внутренние серисы регистрируются автоматически при запуске сервиса
```
spring:
  cloud:
    consul:
      discovery:
        register: true
```
Регистраия внешних сервисов, kafka, postgres, consul во время поднятия сервиса консула в композе: 
[docker-compose](docker-compose.yml) 
```
services:
  consul:
    ....
    volumes:
    - ./consul/services.json:/consul/config/services.json
```

[файл регистрации](consul/services.json)


###Kafka и ELK стек
Поднятие слоя кафки и елк происходит отдельным docker-compose файлом

[kafka/docker-compose.yml](kafka/docker-compose.yml)  
Как и прикладной слой, слой кафки и елк крутятся в одной подсети, что необходимо для общения между сервисами
```
networks:
  default:
    external:
      name: tg-farm
```

####ELK 
за основу ЕЛК стека был взят готовый образ sebp/elk  

Все логи, что происходят во время работы внутренних сервисов, логируютя в лог-топик кафки,
на котоырй настроенна подписка logstashом, и отображаются в кибане:

настройка подписки происходит переопределением существующих в образе файлов настроек логстеша, во время старта сервиса:

```
elk:
    image: sebp/elk
    ...
    volumes:
      - ./30-output.conf:/etc/logstash/conf.d/30-output.conf
      - ./02-beats-input.conf:/etc/logstash/conf.d/02-beats-input.conf
    ...
```
conf файлы: 
* [02-beats-input.conf](kafka/02-beats-input.conf)
* [30-output.conf](kafka/30-output.conf)

###Наполнение KV Consul и подключение к БД
БД регистрируется в service discovery консула, откуда и берутся все небходимые креды для подключения к БД.
Для удосбвта показа вынес наполнение консула в [отлельный sh файл](register-variable.sh)


###Структура приложения 
В целях переиспользования кода был выбран данный подход к реализации приложений:  
**common** модуль отвечает за переиспользование кода и хранит в себе общие сервисы/решения. и в дальнейшем просто подключается зависимостью при создании новых сервисов.   
**common/common-parent** - общий pom   
**common/common-starter** - модуль с реализацией переиспользуемых решений, таких  как отправка/прием в топик, аспекты по обрабокте исключений и тд  
* todo: common/common-test

**tg-farm-ext** - главный модуль, включающий в себя модули отдельных ботов, в каждый из которых подключается зависимостью common-starger модуль, включающий в себе все необходимые для работы зависмости.


**Используемый стек**   
Spring Boot  
Apache kafka  
Hashicorp Consul (service discovery, kv)  
Docker  
Posgres  
Flyway (PH bot)  
Liquibase (POD bot)