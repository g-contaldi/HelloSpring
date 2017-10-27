pipeline {
    agent any
    stages {
         stage ('Build') {
            steps {
                sh 'mvn -s /var/lib/jenkins/maven/conf/global-settings.xml  -Dmaven.test.skip=true clean deploy' 
            }
        }
    }
}
