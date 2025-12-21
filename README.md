# spring-boot-oauth-security

# Genertae JWT Secrets

`https://jwtsecrets.com/#generator`

# Create Stateless session

`.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))`

# Connect to GCP CLoud SQL instance from local, Run below command from the path where cloud-sql-proxy is available

`.\cloud-sql-proxy eighth-saga-474816-a6:us-central1:spring-security --port=3307`

# Oauth2 authorization callback url

http://localhost:8080/v1/login/oauth2/code/{provider}

provider e.g. github, google etc.

# Encrypt client-secret

`Run LocalEncryptor class`


# GKE Deployment and Cloud SQL Integration Guide for Spring Boot Application

This document outlines the necessary configuration files and architectural components required to deploy a Maven Spring Boot application (packaged as a WAR) to Google Kubernetes Engine (GKE), securely connecting it to Google Cloud SQL (MySQL).

## 1. Kubernetes Manifests

These YAML files define the desired state of the application and how it is exposed in the GKE cluster.

### A. `deployment.yaml` (Application Definition with Cloud SQL Sidecar)

This configuration defines the Pods, the number of replicas, and includes the Cloud SQL Auth Proxy as a sidecar container for secure database connectivity.

# Gcloud commands to connect to cluster and create node-pool

For coonecting to cloud sql from cluster or running kubectl commands on cluster, we need to connect with cluster
`gcloud container clusters get-credentials spring-boot-labs-cluster --zone us-central1-a --project eighth-saga-474816-a6`

`gcloud container node-pools create spring-boot-labs-pool-v2 \
--cluster [CLUSTER_NAME] \
--zone [ZONE] \
--service-account 905512589301-compute@developer.gserviceaccount.com \
--scopes "https://www.googleapis.com/auth/cloud-platform" \
--num-nodes 1
`



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-boot-app-deployment
  # Labels are used by Kubernetes to identify and group resources.
  labels:
    app: spring-boot-app
spec:
  # 'replicas' sets the desired number of running copies of your application. 
  replicas: 3
  # 'selector' defines which Pods belong to this Deployment (must match Pod template labels).
  selector:
    matchLabels:
      app: spring-boot-app
  # 'template' is the blueprint for the Pods this Deployment will create.
  template:
    metadata:
      labels:
        app: spring-boot-app
    spec:
      # 'containers' lists all containers that run together in this Pod.
      containers:
      # 1. Your Spring Boot Application Container
      - name: spring-boot-app
        # This tag is a placeholder for your CI/CD process (e.g., substituted via envsubst or Helm).
        # You should replace ${IMAGE_TAG} with your Maven project version (e.g., v1.0.0).
        image: gcr.io/YOUR_PROJECT_ID/spring-boot-app:${IMAGE_TAG} 
        # 'ports' lists the ports the container listens on.
        ports:
        - containerPort: 8080 # The Tomcat port exposed in your Dockerfile.
        # 'env' is used to pass configuration variables to the application.
        env:
        # Pass the database password securely from a Kubernetes Secret.
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: cloudsql-secret # Name of your Kubernetes Secret (MUST BE CREATED SEPARATELY)
              key: password         # Key within the Secret that holds the password
        
      # 2. Cloud SQL Auth Proxy Sidecar Container
      # This runs alongside your app to provide secure, encrypted database connectivity.
      - name: cloud-sql-proxy
        image: gcr.io/cloudsql-docker/gce-proxy:1.33.0
        # The command to start the proxy, specifying the Cloud SQL instance connection name.
        command: ["/cloud_sql_proxy",
                  # Format: project:region:instance=protocol:port 
                  "-instances=YOUR_PROJECT_ID:YOUR_REGION:YOUR_INSTANCE_NAME=tcp:3306"]
        # Recommended security context to run the proxy as a non-root user.
        securityContext:
          runAsNonRoot: true

# Ingress configuration for meaningful host name

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: app-ingress
  annotations:
    # Use the static IP you reserved earlier
    kubernetes.io/ingress.global-static-ip-name: "my-app-static-ip"
    # Optional: Automatically provision a Google-managed SSL certificate
    networking.gke.io/managed-certificates: "my-app-certificate" 
spec:
  # Define the domain name you want to use
  rules:
  - host: "app.example.com"
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: [YOUR_SERVICE_NAME]
            port:
              number: [YOUR_SERVICE_PORT]
