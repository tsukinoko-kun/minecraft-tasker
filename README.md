# Tasker

[![Known Vulnerabilities](https://snyk.io/test/github/Frank-Mayer/minecraft-tasker/badge.svg)](https://snyk.io/test/github/Frank-Mayer/minecraft-tasker)

Example configuration:

> plugins/Tasker/config.json

```json
{
  "timeZone": "Europe/Berlin",
  "tasks": [
    {
      "cron": "* * * ? * *",
      "command": "msg @a Hello from Tasker!"
    }
  ]
}
```
