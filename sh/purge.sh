docker kill $(docker ps -qa)
docker rm -v $(docker ps -qa)
docker rmi $(docker images -q)
docker volume rm $(docker volume ls -qf dangling=true)

