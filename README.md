## Java JDK & JRE
Version - 1.8

## Git Setup
Download link - https://git-scm.com/downloads

#### Steps to configure
Set your username in Git - https://docs.github.com/en/github/using-git/setting-your-username-in-git (Use the configure only for single repository section)

Set your commit email address in Git - https://docs.github.com/en/github/setting-up-and-managing-your-github-user-account/setting-your-commit-email-address
## Some basic Git commands
```
git status - to show the list of edited files
git add - to add the files to commit
git commit - to make a commt
git push - to push the change to repo
```
## Gradle Setup
Version to use - 4.5.1

Download link - https://services.gradle.org/distributions/gradle-4.5.1-bin.zip

Steps to configure - https://gradle.org/install/
## To create a sample app
Follow the page to setup the java web application with gradle

https://guides.gradle.org/building-java-web-applications/
## Local Build & Deployment
```
./gradlew build - to build the app
./gradlew eclipse - to resolve the dependencies and download the referenced libraries to use in eclipse.
Let me(@vickyavw) know if someone is using other IDE, will configure the task for that if needed.
```
Gretty plugin in Gradle project is used to deploy the app, already configured in build.gradle for abirami-traders app.

IDE has the option to to build and deploy there itself. For peeps using command prompt, make sure the project has proper build.gradle with gretty configured.
```
./gradlew appRun - to start the server
It will show the status and on success it will show the url. eg. http://localhost:8080/abirami-traders

./gradlew appRunDebug - to start in debug mode. 
Please configure the port in IDE to debug. Assuming you use Eclipse as IDE: In Eclipse, go on your Project -> Debug as... -> Debug Configuration -> Remote Java Application. As host set localhost, as port 5005, and you are free to go.
```
## Versioning
master - backup for the best working app. Never to be merged with daily changes.
1.0 - current default branch where everyone should raise the pull request(PR).
Both the branches are restricted branch. So everyone must create their own branch and make changes in their app and raise a PR for 1.0 branch.
#### Commands for the same
```
Create a new branch:
git checkout -b feature_branch_name

Edit, add and commit your files.

Push your branch to the remote repository:
git push -u origin feature_branch_name
```
