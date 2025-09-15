pipeline {
    agent any
    
    environment {
        SONAR_HOST_URL = 'http://89.169.128.126:9000'
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REGISTRY = 'abuzer337'
        IMAGE_NAME = 'devops-backend'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
            post {
                always {
                    publishTestResults testResultsPattern: 'build/test-results/test/*.xml'
                    publishCoverage adapters: [jacocoAdapter('build/reports/jacoco/test/jacocoTestReport.xml')], sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './gradlew sonarqube'
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    def image = docker.build("${DOCKER_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}")
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-credentials') {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                sh """
                    kubectl set image deployment/devops-backend devops-backend=${DOCKER_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER} -n devops-app
                    kubectl rollout status deployment/devops-backend -n devops-app
                """
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
