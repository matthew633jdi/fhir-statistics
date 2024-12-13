pipeline {
    agent any
    stages {
        stage('Install Dependencies') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew dependencies'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}