#!groovy
pipeline {
    agent any

    stages {
	
		stage('Checkout') {
		checkout(
			[$class: 'GitSCM',
			branches: [[name: '*/master']],
			doGenerateSubmoduleConfigurations: false,
			extensions: [],
			submoduleCfg: [],
			userRemoteConfigs: [[url: 'https://github.com/crismarfr/microservices.git']]
			])
		}
	
        stage('Build') {
            steps {
                echo 'Building..'
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
            }
        }
    }
}
