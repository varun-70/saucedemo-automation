pipeline {
    agent any

    tools {
        // Install Maven using a Maven installer tool
        mavenInstaller(mavenVersion: '3.9.8')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', // Replace with your branch name
                   // credentialsId: 'github-credentials-id', // Replace with your credential ID
                   url: 'https://github.com/varun-70/saucedemo-automation.git'
            }
        }
        stage('Run Firefox Tests') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=suites/smoke_testng.xml -Dbrowser=firefox -Dallure.results.directory=build/allure-results-firefox'
            }
        }

        stage('Run Chrome Tests') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=suites/smoke_testng.xml -Dbrowser=chrome -Dallure.results.directory=build/allure-results-chrome'
            }
        }

        stage('Run Safari Tests') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=suites/smoke_testng.xml -Dbrowser=safari -Dallure.results.directory=build/allure-results-safari'
            }
        }
    }

    post {
        always {
            allure includeProperties: true,
                       jdk: '',
                       results: [
                            [path: 'build/allure-results-firefox'],
                            [path: 'build/allure-results-chrome'],
                            [path: 'build/allure-results-safari']
                        ]
        }
    }
}
