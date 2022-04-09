# Tasker

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
