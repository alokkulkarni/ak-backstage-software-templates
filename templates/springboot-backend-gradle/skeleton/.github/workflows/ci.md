name: CI
run-name: ${{ github.actor }} is testing out GitHub Actions 🚀
on:
  push:
    branches:
      - main

env:
  IMAGE_NAME: ${{values.artifact_id}}
  IMAGE_TAGS: ${{github.sha}}
  IMAGE_REGISTRY: hub.docker.com
  IMAGE_NAMESPACE: alokkulkarni
  USERNAME: ${{secrets.DOCKERHUB_USERNAME}}
  PASSWORD: ${{secrets.DOCKERHUB_PASSWORD}}

jobs:
  build-and-push-image:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write
    
    steps:
        - name: checkout code
          uses: actions/checkout@v3
          with:
            fetch-depth: 0 

        - name: setup jdk 17
          uses: actions/setup-java@v3
          with:
            distribution: 'corretto'
            java-version: 17

        - name: unit tests
          run: ./gradlew test

        - name: build the app
          run: |
            ./gradlew clean
            ./gradlew -DskipTests build
        
        # - name: SonarQube Scan
        #   uses: SonarSource/sonarqube-scan-action@v4
        #   with:
        #     sonar_host_url: ${{ secrets.SONAR_HOST_URL }}
        #     sonar_token: ${{ secrets.SONAR_TOKEN }}
        
        # - name: build the docker image
        #   uses: docker/build-push-action@v4
        #   with:
        #     context: .
        #     dockerfile: Dockerfile
        #     push: false
        #     tags: ${{ secrets.REGISTRY_NAMESPACE }}/${{env.IMAGE_NAME}}:${{env.IMAGE_TAGS}}

        - name: login to docker hub
          uses: docker/login-action@v3
          with:
            username: ${{secrets.DOCKERHUB_USERNAME}}
            password: ${{secrets.DOCKERHUB_PASSWORD}}

        - name: Build and Push Docker Image
          run: |
            docker build -t ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:${{env.IMAGE_TAGS}} .
            docker tag ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:${{env.IMAGE_TAGS}} ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:latest
            docker push ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:${{env.IMAGE_TAGS}}
            docker push ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:latest

        # - name: push the docker image to docker hub
        #   uses: docker/build-push-action@v4
        #   with:
        #     context: .
        #     dockerfile: Dockerfile
        #     push: true
        #     tags: ${{env.IMAGE_NAMESPACE}}/${{env.IMAGE_NAME}}:${{env.IMAGE_TAGS}}
