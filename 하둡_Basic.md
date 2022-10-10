## Hadoop Common  
1. CLI mini cluster  
2. Native Libraries  
3. Proxy User  
4. Rack Awareness  
5. Secure Mode  
6. Service Level Authorization 초기 부여 메커니즘  
7. Http Authentication  
8. Credential Provider API  
9. Hadoop KMS  
10. Tracing  
11. Unix Shell Guide  


## Hadoop Distributed File System(HDFS)  
1. 유기적, 조직적 블럭 형태로 움직임  
2. 투명하게 복제되어 흩어짐  
3. 인텔리젠트 복제 메커니즘을 가지고 있음(데이터를 복수의 렉 형태로 구성된 노드들에 옮겨짐)  


하둡의 디자인
1. 파일 순차적으로 읽어 풀 스캐닝
2. 노드들안에 데이터 각각을 뒤져 노드안에서 연산하는 방식을 취함
3. 노드 연산 수행에 장애가 발생할 경우 하드웨어를 교체하기보다 소프트웨어 레이어에서 교체


하둡 클러스터안에 네임노드를 복제한 여러 데이터 노드들이 존재, 하둡 클러스터는 여러개일 수 있음


### Input/Output Data   
Input Data  : 기업이 가지고 있는 초기 데이터  
Output Data : 기업이 수익이나 고객서비스를 최대한 이끌어내기 위한 데이터 결과값  


### Hadoop Map Reduce  
1. 데이터 처리에 있어서 분산처리 모델을 따름  
2. 맵은 원시 데이터를 받아 data chunk라는 input data를 key/value로 HDFS 시스템에 저장  
3. reduce는 데이터 chunk 혹은 데이터 셋을 아주 작은 단위의 tuple set으로 합치며, 자리잡은 데이터는 key 값에속하여 변화를 줄 수 없는 immutable data 타입  


Map Phase     : row data를 key/value 방식으로 전환, 클러스터를 통해 병렬방식으로 처리  
Shuffle Phase : row data의 정렬된 key값으로 정렬하며, 같은 key값에 value를 동일한 reduce로 이동  
Reduce Phase  : 모든 key값을 위한 value를 처리, 결과값을 HDFS나 다른 영구 저장소에 저장  


### Yarn Module : 시스템 자원의 관리자이며 스케쥴러  
1. 스케쥴러     : 최대한으로 데이터의 지역성을 사용하도록함, 교체할 수 있는 시스템을 사용(스케쥴러가 사용자 제한을 감지하며 queue할당을 비롯한 스케쥴 임무의 기본적인 관리기능)  
2. 컨테이너 관리 : CPU, Memory, Storage 등을 관리  
3. 데이터 지역성 : 데이터가 어떤 클러스터에 있는지 가까이서 살펴보는 역할을 함  
4. 독립 컨테이너 : 분산 시스템은 자원들이 자연스럽게 동작하도록 통제하는 레벨을 강화시켜서 자원이 공정하게 나누어 작업을 하도록 돕고, 컨테이너들은 다른 사용자로부터 독립되게 하여 private switch를 동시에 작동하도록 함, 사용자들은 자신의 데이터 사용임무를 스케쥴하여 시간적으로 관리할수있게 도와줌  


HADOOP RAID : Redundant Array of Inexpensive Disks, 레이드 방식으로 복수 배열 독립 디스크라는 기술을 도입하며, 서버에 여러 대의 블럭 하드 디스크에 중복처리 할 수 있는 기술  
Data Movenment Plan : HDFS Disk Balancer - 그 이동 계획에 근거하여 계산, 데이터 노드들이 네임노드에게 디스크 사용 정보를 사용함으로 균형을 유지하도록 보고.  
Plan, Execute, query process


스토리지 생산성 관리 솔루션(Storage Capacity-Management Solution)
1. Node Balancer - 시스템 노드를 통해 데이터 이동
2. Disk Balancer - 싱글 데이터 노드 내에 디스크 저장
3. Mover - 스토리지 타입을 실제 이동시킴

### HDFS : Master-Slave Architecture
1. Master node(NameNode) : 파일시스템 namespace를 관리하는일, 고가용성 서버
2. Slave node : 비즈니스 데이터 저장
3. Hdfs Clients에 의해 요구되는 사항을, data node는 master node인 name node의 지배를 받아 작업을 부여받아 피드백까지 제출

### Rack/Block in Hadoop Cluster(렉 인지)
1. text file -> dividing the text files by 3 replcas -> block a, block b, block c
   1. 한 클러스트 안에 같은 데이터 노드들에는 레플리카 하나 이상이 중복되면 안됨
   2. 싱글 블럭내에 두개의 레플리카들이 같은 렉안에 허용되지 않음
   3. 렉들의 수 들은, 렉 인지 정책에 입각하여 하둡 클러스터내에 레플리카 수들보다 작아야됨

