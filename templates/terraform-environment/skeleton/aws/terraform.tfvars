# Environment Configuration
environment = "${{ values.environment }}"
aws_region = "${{ values.awsRegion }}"

# Project Information
project_name = "${{ values.projectName }}"
org_name = "${{ values.orgName }}"
squad_name = "${{ values.squadName }}"

# Optional Components
enable_rds = "${{ values.enableRds }}"
enable_dynamodb = "${{ values.enableDynamodb }}"
enable_s3 = "${{ values.enableS3 }}"
enable_elasticache = "${{ values.enableElasticache }}"

# Environment life cycle duration
lifetimeDays = "${{ values.lifetimeDays }}"
duration = "${{ values.duration }}"

# VPC Configuration
vpc_cidr = "10.0.0.0/16"
availability_zones = ["${{ values.awsRegion }}a", "${{ values.awsRegion }}b", "${{ values.awsRegion }}c"]
private_subnet_cidrs = ["10.0.1.0/24", "10.0.2.0/24"]
public_subnet_cidrs  = ["10.0.101.0/24", "10.0.102.0/24"]

# EKS Configuration
cluster_name = "${{ values.projectName }}-${{ values.environment }}"
cluster_version = "1.32"
node_instance_types = ["t3.medium"]
desired_size = 2
max_size = 4
min_size = 1

# Optional Component Configurations
db_name = "${{ values.projectName }}_${{ values.environment }}"
db_instance_class = "db.t3.medium"
db_allocated_storage = 20

# DynamoDB Configuration
dynamodb_tables = {
    "${{ values.projectName }}-${{ values.environment }}" = {
        billing_mode = "PAY_PER_REQUEST"
        hash_key = "id"
        attributes = [
        {
            name = "id"
            type = "S"
        }
        ]
    }
}


# S3 Configuration
s3_buckets = {
    "${{ values.projectName }}-${{ values.environment }}" = {
        versioning = true
        encryption = true
    }
}


# ElastiCache Configuration
elasticache_cluster = {
    engine = "redis"
    node_type = "cache.t3.micro"
    num_cache_nodes = 1
    port = 6379
}
