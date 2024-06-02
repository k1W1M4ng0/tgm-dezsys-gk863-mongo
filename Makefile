run:
	gradle clean bootRun

2:
	mvn spring-boot:run -D spring-boot.run.arguments='8083 8081'

3:
	mvn spring-boot:run -D spring-boot.run.arguments='8084 8082'
