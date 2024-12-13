pipeline {
    agent any
    environment {
        SLACK_CHANNEL = '#test'
        SLACK_SUCCESS_COLOR = '#2C953C'
        SLACK_FAIL_COLOR = '#FF3232'
        GIT_URL = 'https://github.com/matthew633jdi/fhir-statistics.git'
    }
    stages {
        stage('Get Git Info') {
            steps {
                script {
                    def GIT_INFO = sh(script: "git log -1 --pretty=format:'%an|%ae|%s'", returnStdout: true).trim()
                    def (GIT_USER, GIT_EMAIL, GIT_MESSAGE) = GIT_INFO.split('\\|')
                    env.GIT_COMMITTER_NAME = GIT_USER
                    env.GIT_COMMITTER_EMAIL = GIT_EMAIL
                    env.GIT_MESSAGE = GIT_MESSAGE
                }
            }
        }

        stage('Git Clone') {
            steps {
                git branch: 'main', credentialsId: 'github-token', url: GIT_URL
            }
            post {
                success {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: 'Git clone에 성공했습니다.'
                    )
                }

                failure {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_FAIL_COLOR,
                        message: 'Git clone (${GIT_URL}) 에 실패했습니다.'
                    )
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Clean Build Test') {
            steps {
                sh './gradlew clean build test'
            }
            post {
                success {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: 'Build Test에 성공했습니다.'
                    )
                }
                failure {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_FAIL_COLOR,
                        message: 'Build Test에 실패했습니다.'
                    )
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
            post {
                success {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: 'Archive Artifacts에 성공했습니다.'
                    )
                }
                failure {
                    slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_FAIL_COLOR,
                        message: 'Archive Artifacts에 실패했습니다.'
                    )
                }
            }
        }
    }
    post {
        success {
            slackSend (
                channel: SLACK_CHANNEL,
                color: SLACK_SUCCESS_COLOR,
                message: "Build succeeded!\nUser: ${env.GIT_COMMITTER_NAME} \nEmail: ${env.GIT_COMMITTER_EMAIL}\nCommit Message: ${env.GIT_MESSAGE}"
            )
        }

        failure {
            slackSend (
                channel: SLACK_CHANNEL,
                color: SLACK_FAIL_COLOR,
                message: "Build failed!\nUser: ${env.GIT_COMMITTER_NAME} \nEmail: ${env.GIT_COMMITTER_EMAIL}\nCommit Message: ${env.GIT_MESSAGE}"
            )
        }
    }
}