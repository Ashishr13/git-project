pipeline {
    agent any                 // it will use any available nodes
    environment {             // for defining variables used in pipeline projects here
        name = 'ashish'
    }
    parameters {              // to provide parameters and default value
        string(name: 'admin_user', defaultValue: 'Tanish', description: 'Name of admin_user')
        booleanParam(name: 'DEBUG_MODE', defaultValue: false, description: 'Enable debug mode')
        choice(name: 'ENVIRONMENT', choices: ['DEV', 'STAGING', 'PROD'], description: 'Environment to deploy to')
        password(name: 'SECRET_PASSWORD', defaultValue: '', description: 'Password for authentication')
    }
    
/* Difference between para and var:
Parameters:
 Defined at the start of the pipeline.
 Used for user input and customization.
 Accessed via the params object.
 Global scope throughout the pipeline.
Variables:
 Defined and used within the pipeline script.
 Used for storing and managing data during pipeline execution.
 Can have global, stage-specific, or local scope.
 Accessed directly by their names or via the env object for environment variables. */
    
    stages {
        stage('cmd') {       // stage name will print as 'cmd'
            steps {
                sh 'date'
                sh 'pwd'     // use three inverted commas for giving all commands at once
                sh '''           
                cat /etc/os-release
                pwd
                '''
            }
        }
        stage('Global-Variables') {        // this is used to print environment variables which are predefined by Jenkins.
            steps {
                sh 'echo "${BUILD_ID}"'
                sh 'echo "${name}"'        // pipeline defined variable printed here
            }
        }
        stage('Defined-Env-Var') {
            environment {                  // for defining variables used in this projects only
                user_name = 'ASH'
            }
            steps {
                sh 'echo "${name}"'        // pipeline defined variable printed here
                sh 'echo "${user_name}"'   // project or staged defined variable will print here
            }
        }
        stage('Parameter') {
            steps {
                sh 'echo "${admin_user}"'
                sh 'echo "${user_name}"'                                // string param
                sh 'echo Debug Mode: "${DEBUG_MODE}"'                   // boolean param
                sh 'echo Environment: "${ENVIRONMENT}"'                 // choice param       
                sh 'echo Secret Password: "${SECRET_PASSWORD}"'         // password param
            }
        }
    }
}