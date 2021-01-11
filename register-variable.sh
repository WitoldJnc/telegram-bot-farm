#!/usr/bin/env bash

#daily_trash_bot insert db secret
curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/name
curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/username
curl --request PUT --data postgres localhost:8500/v1/kv/ph.daily_trash.bot/db/password

#panty_of_destiny insert db secret
curl --request PUT --data postgres localhost:8500/v1/kv/ph.panty_of_destiny.bot/db/name
curl --request PUT --data postgres localhost:8500/v1/kv/ph.panty_of_destiny.bot/db/username
curl --request PUT --data postgres localhost:8500/v1/kv/ph.panty_of_destiny.bot/db/password
