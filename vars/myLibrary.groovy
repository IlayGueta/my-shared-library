def deployApp(String branch = 'main') {
    echo "Deploying application from branch: ${branch}"
}

def cleanup() {
    echo "Running cleanup tasks"
}

def buildApp() {
    echo "Building the application"
}
