# Simple Log Service

Used for hackathons / IoT sessions

Exposes a very simple REST interface that can be used to POST / READ log lines from/to IoT devices

## Routes

* GET /log: gets all log lines for the requesting IP
* GET /log/{ip} gets all log lines for the specified IP
* GET /log/all gets all log lines 
* POST /log creates a new log line, request body is a String
* PUT /log/user sets the user name for this IP
* GET /log/user gets a list of all user names
