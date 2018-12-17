cd ../..
docker build -t gateway -f ./gateway/Dockerfile_gateway .
docker build -t configserver -f ./configServer/Dockerfile_configServer .
docker build -t registry1 -f ./annuaire/Dockerfile_eureka1 .
docker build -t registry2 -f ./annuaire/Dockerfile_eureka2 .
docker build -t security -f ./authServer/Dockerfile_authServer .
docker build -t monitoring -f ./hystrix/Dockerfile_hystrix .
docker build -t zipkin -f ./zipkin/Dockerfile_zipkin .
docker build -t mic1 -f ./microservice1/Dockerfile_mic1 .
docker build -t mic2 -f ./microservice2/Dockerfile_mic2 .
docker build -t mic3 -f ./microservice3/Dockerfile_mic3 .
docker build -t mic4 -f ./microservice4/Dockerfile_mic4 .
docker build -t mic5 -f ./microservice5/Dockerfile_mic5 .
