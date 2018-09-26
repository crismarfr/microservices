cd ..
sudo docker build -t gateway -f ./gateway/docker/Dockerfile .
sudo docker build -t configserver -f ./configServer/docker/Dockerfile .
sudo docker build -t registry1 -f ./registry/docker/Dockerfile_eureka1 .
sudo docker build -t registry2 -f ./registry/docker/Dockerfile_eureka2 .
sudo docker build -t security -f ./security/docker/Dockerfile .
sudo docker build -t monitoring -f ./monitoring/docker/Dockerfile .
sudo docker build -t zipkin -f ./zipkin/docker/Dockerfile .
sudo docker build -t mic1 -f ./microservice/docker/Dockerfile_mic1 .
sudo docker build -t mic2 -f ./microservice/docker/Dockerfile_mic2 .
sudo docker build -t mic3 -f ./microservice/docker/Dockerfile_mic3 .
sudo docker build -t mic4 -f ./microservice/docker/Dockerfile_mic4 .
sudo docker build -t mic5 -f ./microservice/docker/Dockerfile_mic5 .
