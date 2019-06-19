# -*- coding:utf-8 -*-
from fabric.api import local, env, run, put, cd
from fabric.contrib import files

env.hosts = "39.98.209.7"
env.user = "root"
env.key_filename = "C:\Users\eyslc\.ssh\cjj.pem"

app_name = 'wx-mp'
app_version = '1.0-SNAPSHOT'
conf_list = ['LOG_FOLDER=/data/wx-mp/logs','JAVA_OPTS=-Dspring.profiles.active=prod']

def clean_package():
    local("mvn clean package -Dmaven.test.skip=true -P prod")


def clean_old():
    run("service {} stop".format(app_name))
    with cd("/data/"):
        run("rm -rf {}".format(app_name))
        run("rm -rf {}-{}.jar".format(app_name, app_version))
        run("mkdir -p /data/{}/logs".format(app_name))


def upload_package():
    put("build/{}-{}.jar".format(app_name, app_version), "/data/{}-{}.jar".format(app_name, app_version), mode=0755)

def conf_set():
    files.append("/data/{}-{}.conf".format(app_name, app_version),conf_list)


def start_service():
    run("service {} start".format(app_name))


def deploy():
    clean_package()
    clean_old()
    upload_package()
    conf_set()
    start_service()
