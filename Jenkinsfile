pipeline {
  agent {
    label 'jenkins-jenkins-slave'
  }
  
  environment {
    AWS_DEFAULT_REGION = 'us-east-1'
    AWS_PCI_ID = '691190359445'
    AWS_PROD_ID = '134937327130'
    AWS_SAND_ID = '135302193720'
    AWS_HMLG_ID = '290543716360'

  }
  parameters {
    string(defaultValue: 'mkid-app', description: 'App', name: 'App')
    string(defaultValue: 'prod', description: 'Insert the Environment', name: 'EnvironmentAws')
    string(defaultValue: '5672', description: 'Insert PortFront', name: 'PortFront')
    string(defaultValue: '15672', description: 'Insert PortApp', name: 'PortApp')
    string(defaultValue: '1', description: 'Insert MinSize', name: 'MinSize')
    string(defaultValue: '1', description: 'Insert DesiredCapacity', name: 'DesiredCapacity')
    string(defaultValue: '1', description: 'Insert MaxSize', name: 'MaxSize')
  }

  stages {
    stage('Download') {
      steps {
        withCredentials(bindings: [string(credentialsId: 'GIT_TOKEN', variable: 'GitToken')]) {
          sh '''
              ls -l
              curl -H "Authorization: token ${GitToken}" -H \'Accept: application/vnd.github.v4.raw\' -O -L https://api.github.com/repos/moip/sre-shs/contents/scripts/teste-bag-webapp.py
              curl -H "Authorization: token ${GitToken}" -H \'Accept: application/vnd.github.v4.raw\' -O -L https://api.github.com/repos/moip/sre-shs/contents/scripts/deploy-bag-webapp.py
              curl -H "Authorization: token ${GitToken}" -H \'Accept: application/vnd.github.v4.raw\' -O -L https://api.github.com/repos/moip/sre-shs/contents/scripts/remove-teste-bag-webapp.py
          '''
        }
      }
    }
    stage('Build Images') {
      steps {
         script {
            if ( params.EnvironmentAws == 'pci') {
              sh "echo 'pci'"
              ENV_ID = env.AWS_PCI_ID
            } else if ( params.EnvironmentAws == 'prod') {
              sh "echo 'pci'"
              ENV_ID = env.AWS_PROD_ID
            } else if ( params.EnvironmentAws == 'sand') {
              sh "echo 'pci'"
            } else if ( params.EnvironmentAws == 'hmlg') {
              sh "echo 'pci'"
            }
          }
        sh '''
          cd /tmp/workspace/mockkid-mockid/
          docker build -t ${ENV_ID}.dkr.ecr.us-east-1.amazonaws.com/ecr-${EnvironmentAws}-${App} --network=host .
        '''
      }
    }
    stage('Push Images') {
      steps {
        sh '''
          aws ecr get-login --region us-east-1 --no-include-email |sh
          docker push ${EnvironmentAws}.dkr.ecr.us-east-1.amazonaws.com/ecr-${Environment}-${App}
        '''
      }
    }
    stage('Deploy') {
      steps {
        sh 'python deploy-bag.py'
      }
    }
  }  

  triggers {
    pollSCM('H/3 * * * *')
   }
  }