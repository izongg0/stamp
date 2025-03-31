# stamp


docker-compose up -d 하면 디비랑 서버 컨테이너 실행

docker ps 실행중인 컨테이너 확인

 컨테이너 종료


코드 업데이트 후 

docker build --build-arg DEPENDENCY=build/dependency --platform linux/amd64 -t izongg/odin_stamp .
docker push izongg/odin_stamp

ssh에서 받기

ssh -i izonggkey.pem ec2-user@3.35.4.216

sudo su -
sudo systemctl start docker
sudo docker pull izongg/odin_stamp
docker stop $(docker ps -aq)
sudo docker run -d -p 8080:8080 --platform linux/amd64 izongg/odin_stamp