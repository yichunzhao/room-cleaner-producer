# room-clearing-producer
It needs a Rabbit MQ running from the local. This module depends on room-booking-web module, so need to start its server form local and it will listen to on port 8000. 


## How microservices communicate?

Instead of putting all functionalities in one system,  microservices allows loosely coupled systems working together; and they may interact with each other by an asynchronous(AMQP) or synchronous communication(HTTP).  

The Advanced Messaging Queuing Protocol(AMQP) is an open standard application layer protocol for message-oriented middelware, supported by many operating systems and cloud env. 

### What is RabbitMQ

* RabbitMQ is a messaging broker and it implement the AMQP protocol
* Messages have a safe place to live util it is received by consumers.

#### Common Terms

Message
* Package of information consisting of 
   * header(key-value pair)
   * body (actual message) 
Producer: producign message 
Consumer: receiving message

Binding: relationship between exchange and Q, i.e. 
BindingKey: the key that binds the exchange and Q; the message -> Q

Exchange->BindingKey1,BindingKey2->Q1, Q2
Producer->Exchange->Queue->Consumer

##### Exchange Types

The Exchange decides which Q the message go. 

Rules are defined by the Exchnage type

* Fanout exchange:  broadcasts all messages to all Q, very simple
* Direct exchange:  bindingkey binding to Q; producer message with a routingkey; rounting == bindingkey; message sending to Q
  * Nameless-Default: message goes to Q whose Q name exaclty matches the routing key of the message.
* Header Exchange:  mathching message header (k-v) ==  bindingkey(k-v)  
* Topic Exchange: according to routing pattern, wildcard matching, fx: NA.sales.#

  
### AMQP Spring Support

Spring uses RabiitMQ to communicate through the AMQP protocol. Its config is externally controlled by properties in spring.rabbitmq.* in application.properties. 

````
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=secret
````
Alternatively, using address attribute
````
spring.rabbitmq.addresses=amqp://admin:secret@localhost
````

#### AutoConfig Settings
Even you don't set any spring.rabbitmq properties, the boot will auto configure default properties for you. it may directly link to the local rabbitMQ. 

![image](https://user-images.githubusercontent.com/17804600/101989644-1e8f2c00-3ca2-11eb-88cc-cb0bde758b58.png)

#### Sending a Message

`RabbitTemplate` can injected directly.











