#!/bin/bash

curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/name
curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/username
curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/password
