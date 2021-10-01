pipeline {
    triggers {
        pollSCM('') //Empty quotes tells it to build on a push
    }  
    agent any

    tools {
        maven "Maven3"
        nodejs "nodejs16"
    }
    stages {
        stage('Pull') {
            steps {
                git credentialsId: 'b1863dc5-9e7a-4df8-a2b7-48e222503b24', url: 'https://github.com/harmijay/Tabungan-Service/'
            }
        }
        stage('Build') {
                steps {
                    bat "mvn clean package"
                }
        }
        stage('Test') {
            steps {
                bat "mvn test"
            }
            
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
                
            }
        }
        stage('Deploy') {
            steps {
                bat "heroku git:remote -a tabungan-service"
                bat "git push -f heroku master"
            }
        }
    }
    post {
        failure {
            mail bcc: '', body: 'Hi, Tabungan Service build is failed. Please check it immediately.', cc: '', from: '', replyTo: '', subject: 'Tabungan Service - Build Failed', to: 'cahyaramadhan76@gmail.com'
        }
    }
}
