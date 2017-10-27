pipeline {
    agent any
    stages {
         stage ('Build') {
            steps {
                sh 'mvn -s /etc/maven/settings.xml  -Dmaven.test.skip=true clean deploy' 
            }
        }
    }
}
