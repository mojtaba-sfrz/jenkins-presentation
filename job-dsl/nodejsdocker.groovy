job('NodeJS Docker example') {
    scm {
        git('https://github.com/mojtaba-sfrz/jenkins-presentation.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('msafarzayee@gmail.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        installDependencies{
            shell("npm install")
        }
        dockerBuildAndPublish {
            repositoryName('mojtabasfrz/docker-nodejs-demo')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('0aa14d7f-113b-4e96-818c-243dae02aacc')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
