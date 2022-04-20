[![Build Status](https://travis-ci.org/DieTechniker/3rdparty-privacy-proxy-examples.svg?branch=master)](https://travis-ci.org/DieTechniker/3rdparty-privacy-proxy-examples)

# TK 3rd Party Privacy Proxy (3PPP) Example Implementation

*Depends on the [3rd Party Privacy Proxy](https://github.com/DieTechniker/3rdparty-privacy-proxy) Base component: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.tk.opensource/3rdparty-privacy-proxy/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/de.tk.opensource/3rdparty-privacy-proxy) [![Build Status](https://travis-ci.org/DieTechniker/3rdparty-privacy-proxy.svg?branch=master)](https://travis-ci.org/DieTechniker/3rdparty-privacy-proxy)*

Delivers functionality to securely fetch and provide 3rd Party resources as well as proxying requests back to the 3rd Party Provider. The users of your website will remain private against any 3rd Party Provider you use without losing any functionality on your end. Further more this privacy proxy delivers you the ultimate knowledge about what information are being transferred to any 3rd Party Provider as well as independence from their servers.

## Getting Started

This software is built with JAVA / Spring Boot. You do require a fully working Java environment to make it run. You can simply build this project with maven to get an executable JAR which contains all you need. Customizing the mvn file you could also create a war. Your choice. For most of you the JAR will just do fine.

To get things up and running there are two parts to do

* Configure the ```application.yml```
* Implement the required **Delivery**, **Retrieval** or **Routing** Proxy using the abstract classes from the [3PPP](https://github.com/DieTechniker/3rdparty-privacy-proxy) to get and deliver assets or route information out. You can and should implement as many Proxies / Controllers as you require. Read the inline code documentation for an explaination what is done where.
* Have a look at the sample implementation that exists for every abstract class before you start coding your own services.

### Delivery

**Delivery** contains services to deliver Assets. You require one delivery service per file type / content-type you want to send out.
All delivery assets are routed under the context: `/delivery/[provider]/[filename]`. Each delivery service can implement its own RegEx based path matching which should be based on the file ending.

### Retrieval

**Retrieval** services are those who call external sources to fetch assets like scripts, styles, fonts and other static assets.
These assets will be downloaded to the server running the 3PPP and stored within an configurable directory (`assets.fileLocation`).
A scheduled task will regularly update the assets. The timing can be different for each retrieval service as well as for any environment you configure.

Example:

```yaml
example:
  scripts:
    interval: '0 */2 * * * ?'
---
spring:
  profiles: prod
example:
  scripts:
    interval: '0 */30 * * * ?'
```

### Routing

**Routing** services do most of the privacy magic. These services act as a proxy between the browser of the user and the 3rd Party's server.
The users browser will only do calls against the 3PPP which will then filter the call for Whitelisted and Blacklisted parameters, header, cookies, etc. and will call the 3rd Party Server directly.
By default, no technical personal information (regarding GDPR) will be transfered to the 3rd Party. The call will be made from **your** server witht the IP address of your server.
Nevertheless you have to check the payload of the calls to ensure only information are transmitted you want to allow.

By default **all URL parameters** are Whitelisted and will be forwarded. But **neither cookies nor headers** will be forwarded.

## Prerequisites

To build and run this software, you require

* A current version of [Maven](https://maven.apache.org/)
* A current version of the [OpenJDK](https://developers.redhat.com/products/openjdk/download/)

This project is configured against Java 8. So for the time being you should go with that.

### Installing

To build an executable **JAR** to deploy on your server use

```bash
mvn install
```

and then start it with

```bash
java -jar target/3rdparty-privacy-proxy-examples.jar
```

To simply **run the application locally** to test, develop or check it out, run

```bash
mvn spring-boot:run 
```

The configuration relies on **profiles** to determine which configuration block should be used. To start the application with a specific profile ("dev" in this example) use

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
or
java -jar -Dspring.profiles.active=dev target/3rdparty-privacy-proxy-examples.jar
```

## Deployment

Build a JAR or WAR using the maven config as you like. You can run this program with any Java application server out there. If unsure, try out running it on a Tomcat.

## Configuration

All configuration is done within the ```application.yml``` config file. This is where you will place your assets to download by listing remote endpoints and target filenames, configure local directory paths as well as the dev, prod and whatever profiles you require. Those values can then be used within your implementations.
Check out the supplied example config and implementations to get an idea what can be configured. The configuration is based on yaml.

## Built With

* [SpringBoot](http://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)

## Authors

* **Kassim Hölting** - *Contributing to the v1.0 release and bringing it to Maven Central*
* **Artur Baron** - *Writing this first version of the base library*
* **Benjamin Stark** - *Making the first open source version of the base library ready to appear*
* **Jan Thiel** - *Developing the idea behind this project, driving it's development and writing the docs*

## License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details

## TK OpenSource

As a legal public entity we work hard to deliver the best service to our customers. As we work in the interest of the public, we decided to OpenSource stuff we code which might be interesting to the public. Check out our [GitHub Page](https://github.com/DieTechniker/) for the latest releases. Everything is as it is. Feel free to fork or open Pull Requests if you want to enhance our solutions.
