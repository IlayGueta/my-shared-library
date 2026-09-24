def buildImage (String appName) {
    echo "Building ${appName}"
    echo "Build Number: ${env.BUILD_NUMBER}"

    sh "docker build -t ${appName}:${env.BUILD_NUMBER} ."
}

def pushDocker(String appName , String credentialsId) {
    withCredentials([
        usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'USERNAME',
            passwordVariable: 'PASSWORD'
        )
    ]) {
        echo "Deploying with username ${env.USERNAME}"

        sh 'echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin'
        sh "docker tag ${appName}:${env.BUILD_NUMBER} ${env.USERNAME}/${appName}:${env.BUILD_NUMBER}"
        sh "docker push ${env.USERNAME}/${appName}:${env.BUILD_NUMBER}"
    }
}

def dockerImage(String appName ,int hostPort ,int containerPort ,String containerName) {
    echo "Running ${appName}:${env.BUILD_NUMBER}"

    sh "docker run -d --name ${containerName} -p ${hostPort}:${containerPort} ${appName}:${env.BUILD_NUMBER}"
}

def healthCheck(int hostPort, String healthPath, int waitTime , String host) {
    sh "sleep ${waitTime}"
    sh "curl --fail http://${host}:${hostPort}${healthPath}"
}

def test() {
    echo 'Testing...'
    // Test steps here
}

def deploy() {
    echo 'Deploying...'
    // Deploy steps here
}