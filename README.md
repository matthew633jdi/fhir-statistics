# FHIRext-statistics
## backgroud
<pre>
FHIRext에 대한 통계 자료를 제공하기 위한 프로젝트
1차적으로 GJTP 2차년도 사업을 위한 프로젝트(2024. 12. 04)
</pre>

## pre-requirement
- Java 17
- gradle 8.11.1

# Setup
- environment file
Path: /opt/irm/etc
Name: fhirext-statistics.env
```shell
vim fhirext-statistics.env
```
```text
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_DB=

POSTGRES_HOST=
POSTGRES_PORT=
```
-  jar file
Path: /opt/irm/lib/fhirext-statistics
Name: statistics-0.0.1.jar

# Run
```shell
java -jar statistics-0.0.1.jar
```