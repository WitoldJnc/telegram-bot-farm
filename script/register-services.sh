#!/bin/bash
# register-services.sh
curl -s -XPUT -d"{
  \"Name\": \"tg-farm-postgres\",
  \"ID\": \"tg-farm-postgres\",
  \"Tags\": [ \"tg-farm-postgres\" ],
  \"Address\": \"localhost\",
  \"Port\": 5432,
  \"Check\": {
    \"Name\": \"PostgreSQL TCP on port 5432\",
    \"ID\": \"tg-farm-postgres\",
    \"Interval\": \"10s\",
    \"TCP\": \"tg-farm-postgres:5432\",
    \"Timeout\": \"1s\",
    \"Status\": \"passing\"
  }
}" localhost:8500/v1/agent/service/register

