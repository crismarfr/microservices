#!groovy
pipeline {
    agent any
	tools {
		maven 'MAVEN'
	  }
    stages {
		stage('Init') {
            steps {
                echo 'Init....'
            }
        }	
		stage('Checkout') {
			steps {
				echo 'Checkout..'
				checkout(
					[$class: 'GitSCM',
					branches: [[name: '*/master']],
					doGenerateSubmoduleConfigurations: false,
					extensions: [],
					submoduleCfg: [],
					userRemoteConfigs: [[url: 'https://github.com/crismarfr/microservices.git']]
					])
			
				sh "cd install/docker-compose; ./stop-docker-compose.sh"
			}		
		}
        stage('Build maven') {
            steps {
                echo 'Building..'
				 sh "cd install/docker; ./maven.sh"
            }
        }
		stage('Build docker image') {
            steps {
                echo 'Building..'
				sh "cd install/docker-compose; ./build-docker-compose.sh"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
		stage('Run') {
            steps {
                echo 'Running....'
				sh "cd install/docker-compose; ./start-docker-compose.sh"
            }
        }
    }
}
