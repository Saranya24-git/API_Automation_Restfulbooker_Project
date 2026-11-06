pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '15'))
        skipDefaultCheckout(true)
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Environment') {
            steps {
                bat 'echo JAVA_HOME=%JAVA_HOME%'
                bat 'java -version'
                bat 'mvn -version'
            }
        }

        stage('Run API Smoke Tests') {
    steps {
        dir('APIRestfulBooker-Smoke') {
            bat 'mvn -B clean test -Psmoke'
        }
    }
}
    }

    post {
    always {
        junit testResults: 'target/surefire-reports/TEST-*.xml'
    }

        success {
            echo 'API Smoke Tests completed successfully!'
        }

        failure {
            echo 'API Smoke Tests failed. Please check the Console Output.'
        }
    }
}