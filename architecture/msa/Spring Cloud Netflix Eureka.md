# Spring Cloud Netflix Eureka

**service discovery**

service discovery란 외부에서 다른 어떤 서비스들이 마이크로 서비스를 검색하기 위해 사용되는 개념입니다. 일종의 해시맵 정도로 보면 됩니다. 

즉, key/value 형태로 정보를 보관하고 그 정보를 토대로 특정 서버가 어느 위치에 있는지, 그리고 어떤 서비스가 어떤 위치에 있는지 등을 등록하고 관리하는 역할을 가진 것이라 이해하면 되겠습니다.

**Netflix Eureka**

넷플릭스에서 개발한 service discovery 역할을 하는 라이브러리로서, 현재는 스프링 재단에 기부하여, Spring Cloud에서 사용할 수 있는 제품이라 할 수 있습니다. 이 제품을 기반으로 마이크로 서비스를 사용하고 싶은 클라이언트는 로드 밸런서 혹은 게이트웨이에 필요한 정보를 요청하면 그 정보가 service discovery에 전달하여 클라이언트가 필요한 정보를 얻는 것입니다.