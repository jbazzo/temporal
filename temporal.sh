# temporal demo
cd fraud
mvn clean package -Dmaven.test.skip=true
docker build --tag=fraud:latest .

cd ..
cd orchestrator
mvn clean package -Dmaven.test.skip=true
docker build --tag=orchestrator:latest .

cd ..
cd docker-compose
docker-compose up -d

