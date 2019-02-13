TRUNCATE TABLE APP_CONFIG;

INSERT INTO APP_CONFIG VALUES('eureka.instance.hostname','localhost','application','default','master');
INSERT INTO APP_CONFIG VALUES('eureka.instance.statusPageUrl','http://${eureka.instance.hostname}/info','application','default','master');
INSERT INTO APP_CONFIG VALUES('eureka.instance.healthCheckUrl','http://${eureka.instance.hostname}/health','application','default','master');
INSERT INTO APP_CONFIG VALUES('eureka.instance.homePageUrl','http://${eureka.instance.hostname}/','application','default','master');
INSERT INTO APP_CONFIG VALUES('eureka.client.serviceUrl.defaultZone','http://localhost:9000/eureka/','application','default','master');
INSERT INTO APP_CONFIG VALUES('eureka.instance.securePortEnabled','false','application','default','master');
INSERT INTO APP_CONFIG VALUES('zuul.server.app.name','ZUUL-SERVICE','application','default','master');

INSERT INTO APP_CONFIG VALUES('message','Running In Production','CONSUMER-SERVICE','PROD','master');
INSERT INTO APP_CONFIG VALUES('message','Running In DR','CONSUMER-SERVICE','DR','master');
INSERT INTO APP_CONFIG VALUES('producer.getMessage.url','/PRODUCER-SERVICE/ProducerService/getMessage','CONSUMER-SERVICE','default','master');

INSERT INTO APP_CONFIG VALUES('message','Running In Production','PRODUCER-SERVICE','PROD','master');
INSERT INTO APP_CONFIG VALUES('message','Running In DR','PRODUCER-SERVICE','DR','master');

--ZUUL-SERVICE configuration
INSERT INTO APP_CONFIG VALUES('zuul.routes.PRODUCER-SERVICE.service-id','PRODUCER-SERVICE','ZUUL-SERVICE','default','master');
