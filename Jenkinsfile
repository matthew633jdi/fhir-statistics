pipeline {
    agent any
    stages {
        stage("Set Variable") {
            steps {
                script {
                    SLACK_CHANNEL = "#test"
                    SLACK_SUCCESS_COLOR = "#2C953C";
                    SLACK_FAIL_COLOR = "#FF3232";
                }
            }

            post {
                success {
                    slackSend {
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: "==================================================================\n배포 파이프라인이 시작되었습니다.\n"
                    }
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
                    slackSend {
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: "Build Test에 성공했습니다."
                    }
                }
            }
            failure {
                slackSend {
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "Build Test에 실패했습니다."
                }
            }
        }

        stage('SonarQube analysis') {
            steps {
                sh './gradlew sonarqube'
            }
            post {
                success {
                    slackSend {
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: "SonarQube analysis에 성공했습니다."
                    }
                }
            }
            failure {
                slackSend {
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "SonarQube analysis에 실패했습니다."
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
            post {
                success {
                    slackSend {
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: "Archive Artifacts에 성공했습니다."
                    }
                }
            }
            failure {
                slackSend {
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "Archive Artifacts에 실패했습니다."
                }
            }
        }
    }
}