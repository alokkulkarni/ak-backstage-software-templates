#!/bin/bash
# Script to start the application and open Swagger UI in a browser

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}Starting Spring Boot application with OpenAPI documentation...${NC}"

# Run the application in background
./gradlew bootRun &
APP_PID=$!

# Wait for the application to start (adjust timeout as needed)
MAX_WAIT=30
for ((i=1; i<=MAX_WAIT; i++)); do
  echo -e "${BLUE}Waiting for application to start... ($i/$MAX_WAIT)${NC}"
  if curl -s http://localhost:8585/actuator/health | grep -q "UP"; then
    echo -e "${GREEN}Application started successfully!${NC}"
    break
  fi
  
  if [ $i -eq $MAX_WAIT ]; then
    echo -e "${RED}Application failed to start within timeout period.${NC}"
    kill $APP_PID
    exit 1
  fi
  
  sleep 1
done

# Open the Swagger UI in the default browser
echo -e "${GREEN}Opening Swagger UI in your browser...${NC}"

# Detect OS and open browser accordingly
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  open http://localhost:8585/swagger-ui.html
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  # Linux
  xdg-open http://localhost:8585/swagger-ui.html
elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]]; then
  # Windows
  start http://localhost:8585/swagger-ui.html
else
  echo -e "${BLUE}Please open http://localhost:8585/swagger-ui.html in your browser${NC}"
fi

echo -e "${GREEN}Application is running. Press Ctrl+C to stop.${NC}"

# Wait for Ctrl+C
trap "echo -e '${BLUE}Stopping application...${NC}'; kill $APP_PID; exit 0" INT
wait $APP_PID
