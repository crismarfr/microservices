docker run -p 8888:8888 --network my_net -itd -v /home/arom/apps/configRepoServer:/home/arom/apps/configRepoServer:z --name configserver configserver 
sleep 35
docker run -p 8761:8761 -e "SPRING_PROFILES_ACTIVE=eureka1" --network my_net -itd --name registry1 registry1
docker run -p 8762:8762 -e "SPRING_PROFILES_ACTIVE=eureka2" --network my_net -itd --name registry2 registry2
docker run -p 8080:8080 --network my_net -itd --name gateway gateway 
docker run --network my_net -itd --name security security 
docker run -p 7080:7080 --network my_net -itd --name monitoring monitoring 
docker run -p 9095:9095 --network my_net -itd --name zipkin zipkin 
docker run --network my_net -itd --name mic1 mic1 
docker run --network my_net -itd --name mic2 mic2 
docker run --network my_net -itd --name mic3 mic3 
docker run --network my_net -itd --name mic4 mic4 
docker run -p 8094:8094 --network my_net -itd --name mic5 mic5 
