#!/usr/bin/env bash
scp -i /home/echo/公共/EchoCow build/libs/* root@118.24.1.170:/root/auth-center/server/
ssh -i /home/echo/公共/EchoCow root@118.24.1.170 "systemctl restart auth-center.service"
