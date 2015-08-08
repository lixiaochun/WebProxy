# webproxy for IoT-Connected car demo(https://github.com/myminseok/IoT-ConnectedCar)
IoT-ConnectedCar demo has several apps such as Dashboard, GemfireREST.
Dashboard app fetches data from GemfireREST api app.
Due to gemfire-server in spring-xd limitation(development only, it doesn't allow remote client access), 
GemfireREST app should be installed on spring-xd VM.
To run Dashboard app on PCF, you need to have a proxy between dashboard app and carsimulator app.
