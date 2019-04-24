#注意：如果提示说：Could not find artifact com.xxx:xxx-service:jar:0.0.1 in central (https://repo.maven.apache.org/maven2)，即找不到xxx-service.jar包
#解决：使用父pom.xml把依赖包生成到本地仓库，在父目录执行：mvn clean install -Pprod(参数-Pprod是因为配置文件中需要使用)，执行成功即生成完成
#mvn package
#mvn clean package -Dspring.profiles.active=prod
#mvn package -Dmaven.test.skip=true -Dspring.profiles.active=prod
#mvn clean package -Dmaven.test.skip=true
#mvn clean package -Pprod -Dmaven.test.skip=true
mvn clean package -Pprod -DskipTests