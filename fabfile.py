# -*- coding:utf-8 -*-
from fabric.api import local, env, run, put, cd

env.hosts = "39.98.209.7"
env.user = "root"
env.key_filename = "C:\Users\eyslc\Downloads\cjj.pem"

app_name = 'wx-mp'
app_version = '1.0-SNAPSHOT'


def clean_package():
    local("mvn clean package -Dmaven.test.skip=true")


def clean_old():
    run("service {} stop".format(app_name))
    with cd("/data/"):
        run("rm -rf {}".format(app_name))
        run("rm -rf {}-{}.jar".format(app_name, app_version))


def upload_package():
    put("build/{}-{}.jar".format(app_name, app_version), "/data/{}-{}.jar".format(app_name, app_version), mode=0755)


def start_service():
    run("service {} start".format(app_name))


def deploy():
    clean_package()
    clean_old()
    upload_package()
    start_service()
