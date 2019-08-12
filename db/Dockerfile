FROM mysql:5.7.15

MAINTAINER me

ENV MYSQL_DATABASE=krsnaa_feedback
ENV MYSQL_ROOT_PASSWORD=root

ADD krsnaa.sql /docker-entrypoint-initdb.d

EXPOSE 3306