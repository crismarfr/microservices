sudo docker run -p 8888:8888 --network my_net -itd -v /home/arom/apps/configRepo:/home/arom/apps/configRepo:z --name configserver configserver --net="ip"
sleep 30
sudo docker run -p 8761:8761 -e "SPRING_PROFILES_ACTIVE=eureka1" --network my_net -itd --name registry1 registry1
sudo docker run -p 8762:8762 -e "SPRING_PROFILES_ACTIVE=eureka2" --network my_net -itd --name registry2 registry2
sudo docker run -p 8080:8080 --network my_net -itd --name gateway gateway --net="ip"
sudo docker run --network my_net -itd --name security security --net="ip"
sudo docker run -p 7080:7080 --network my_net -itd --name monitoring monitoring --net="ip"
sudo docker run -p 9095:9095 --network my_net -itd --name zipkin zipkin --net="ip"
sudo docker run --network my_net -itd --name mic1 mic1 --net="ip"
sudo docker run --network my_net -itd --name mic2 mic2 --net="ip"
sudo docker run --network my_net -itd --name mic3 mic3 --net="ip"
sudo docker run --network my_net -itd --name mic4 mic4 --net="ip"
sudo docker run -p 8094:8094 --network my_net -itd --name mic5 mic5 --net="ip"
