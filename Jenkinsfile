pipeline {
    agent any
    
    environment {
        REGISTRY = 'marielvsq/demo-isika'
        CONTAINER_NAME = 'demo-isika'
        CONTAINER_NAME_PROD = 'demo-isika-prod'
        IP_EC2_TEST = '52.47.173.14'
        IP_EC2_PROD = '52.47.173.14'
        APP_TARGET_PORT = '8080'
        APP_TEST_PORT = '8180'
        APP_PROD_PORT = '8280'
    }

    stages {
        stage('GitHub') {
            steps {
                echo '-=========- Download Code from GitHub -===============-'
                git(
                    url: "https://github.com/MarieLvsq/demo-isika.git",
                    branch: "master",
                    changelog: true,
                    poll: true
                )
            }
        }
        stage('Compile-Package') {
            steps {
                echo '-=========- Compile and generate jar -===============-'
                sh "mvn clean package -DskipTests=true"
                archive "target/*.jar"
            }
        }
        stage('Unit-Test') {
            steps {
                echo '-=========- Run Unit test -===============-'
                sh "mvn test"
            }
            post {
                always {
                  junit 'target/surefire-reports/*.xml'
                  jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
        stage('Docker') {
            steps {
                echo '-=========- Build and Push Docker image -===============-'
            withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
              sh 'printenv'
              sh 'docker build -t $REGISTRY:$BUILD_NUMBER .'
              sh 'docker push $REGISTRY:$BUILD_NUMBER'
            }
            }
        }
        stage('Docker Remove') {
            steps {
                echo '-=========- Remove Docker image from local -===============-'
                sh 'docker rmi -f $REGISTRY:$BUILD_NUMBER'
            }
        }
        stage('Deploy-Test') {
            steps {
                echo '-=========- Deploy app to test env in aws ec2 -===============-'
               sh 'docker -H $IP_EC2_TEST stop $CONTAINER_NAME || true'
                sh 'docker -H $IP_EC2_TEST rm $CONTAINER_NAME || true'
 sh 'docker -H $IP_EC2_TEST run -d -p $APP_TEST_PORT:$APP_TARGET_PORT --name $CONTAINER_NAME  $REGISTRY:$BUILD_NUMBER'               
            }
        }
        stage('Deploy-Prod') {
            steps {
                input 'Do you approve production deployment ?'
                echo '-=========- Deploy app to prod env in aws ec2 -===============-'
               sh 'docker -H $IP_EC2_PROD stop $CONTAINER_NAME_PROD || true'
                sh 'docker -H $IP_EC2_PROD rm $CONTAINER_NAME_PROD || true'
 sh 'docker -H $IP_EC2_PROD run -d -p $APP_PROD_PORT:$APP_TARGET_PORT --name $CONTAINER_NAME_PROD  $REGISTRY:$BUILD_NUMBER'               
            
            }
        }
    }
}
