def sonarCreateProject(String projectKey) {
        withSonarQubeEnv('SonarQubeScanner') {
            sh """
                curl -s -u ${env.SONAR_TOKEN}: \
               -X POST "${env.SONAR_HOST_URL}/api/projects/create" \
               -d "project=${projectKey}&name=${projectKey}"
            """
        }
}

def sonarLocalScan() {
    def scannerHome = tool 'SonarQubeScanner'
    withSonarQubeEnv('SonarQubeScanner') {
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${env.JOB_NAME} \
            -Dsonar.projectName=${env.JOB_NAME} \
            -Dsonar.sources=. \
            -Dsonar.sourceEncoding=UTF-8
        """
    }
}