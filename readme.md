# BUCKETSTORE 과제

Docker를 사용하여 Spring Boot 애플리케이션과 MySQL 데이터베이스를 실행하는 방법에 대해 설명합니다.

## 프로젝트 환경
- Spring Boot 3.2.5
- Mysql 8.3
- Java 17

## 사전 준비
이 프로젝트를 실행하기 위해서는 아래와 같은 소프트웨어가 필요합니다:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/)
- [Postman](https://www.postman.com/) (API 테스트용)

## 실행 방법

1. **저장소를 로컬에 클론**

   터미널에서 아래 명령어를 실행하여 GitHub 저장소를 로컬에 클론<br>
   Intellij, Eclipse 내 git plugin을 이용하여 클론도 가능 
   ```bash
   git clone https://github.com/xxihye/bucketstore_task.git

3. **프로젝트 빌드**
    
    프로젝트 루트 디렉토리에서 아래 명령어를 실행하여 프로젝트 빌드<br>
    빌드가 완료되면 /build/lib 하위에 jar 생성 여부 확인

    ```bash
    ./gradlew build

3. **Docker 데스크탑 실행**


4. **Docker Compose로 Docker 컨테이너 빌드 및 실행**
    ```bash
   docker-compose up --build


5. **로그 확인**
    
    터미널에서 출력되는 로그를 확인하여 애플리케이션과 데이터베이스의 정상 실행 여부 확인 <br>
    

6. **Postman을 통해 API 테스트**


7. **종료**
    
    아래 명령어를 통해서 Docker 컨테이너 중지 
    ```bash
   docker-compose down (-v 옵션 추가시 볼륨도 삭제됨)