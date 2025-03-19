# GitHub Actions CI Method

## Requirements

The GitHub Actions CI Method will require repository secrets setup before GitHub Actions can run

SECRETS
ArgoCD Secrets

CI Secrets

- `REGISTRY_NAMESPACE` - The hub.docker.com registry namespace you wish to push the image to ex. hub.docker.com/<IMAGE_NAMESPACE>/<IMAGE_NAME>:Tag
- `DOCKERHUB_USERNAME` - The hub.docker.com registry username for the bot
- `DOCKERHUB_PASSWORD` - The hub.docker.com registry password for the bot
