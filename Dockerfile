FROM ubuntu:latest
LABEL authors="nejer"

ENTRYPOINT ["top", "-b"]