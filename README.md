Made a complete full stack application using Springboot3+ and Angular14+.

It comprises 7 microservices in the backend-

1)Item Service(Core)-> Manage items in the store.
2)Customer Services(Core) -> Manage registered user. Used in spring security. "CustomerCreated" event is published using apache kafka.
3)Sales Order Service(Composite)-> Manage order details along with the customer data. Implemented Circuit breaker using Resilience4j and Load balancing.
4)Auth Service-> Manage authentication using JWT token.
5)Cloud Gateway-> Manage routing the requests to different microservices after checking credentials.
6)Cloud Config Server-> Manage external configuration using github.
7)Service registry-> Keep track of current information of available instances of each microservices in an application.


Angular UI- Manage UI for authentication and invokes APIs to create order and fetch order details
