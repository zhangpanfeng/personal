#!/bin/bash

cd /home/darren/workspace/personal
git pull
mvn clean tomcat:redeploy

#cd C:\myProject\workspace\personal