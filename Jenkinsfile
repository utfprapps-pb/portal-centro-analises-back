pipeline {
    agent any
    environment {
        SERVER_PORT=8813
        POSTGRESQL_CRED = credentials('postgres-id')
        DATABASE_URL="jdbc:postgresql://postgresql:5432/ca_lab_v2"
        DATABASE_USERNAME="${POSTGRESQL_CRED_USR}"
        DATABASE_PASSWORD="${POSTGRESQL_CRED_PSW}"

        FRONT_BASEURL="https://ca-dev-v2.app.pb.utfpr.edu.br"

        MINIO_CRED = credentials('ca-minio-id')
        MINIO_ACCESS_KEY = "${MINIO_CRED_USR}"
        MINIO_SECRET_KEY = "${MINIO_CRED_PSW}"
        MINIO_ENDPOINT = "https://minio.app.pb.utfpr.edu.br"
        MINIO_PORT=443
        MINIO_SECURE=true
        MINIO_BUCKET_NAME="ca-lab"
    }
    stages {
        stage('Docker Compose UP') {
            steps {
                sh 'docker compose -f docker-compose.yml up -d --build'
            }
        }
    }
    post {
        always {
            echo 'Build finished.'
        }
        success {
            echo 'Build succeeded.'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
