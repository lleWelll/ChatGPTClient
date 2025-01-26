## ChatGPT HTTP Client Library  

Java library for sending HTTP requests and receiving responses in ChatGPT.  

## Benefits  
- Sending HTTP requests to ChatGPT's [chat completion endpoint](https://platform.openai.com/docs/api-reference/introduction)
- Getting JSON response.  

## Installation  
To install library add this dependecy in your pom.xml:
```
<dependency>
    <groupId>io.github.llewelll</groupId>
    <artifactId>ChatGPTClient</artifactId>
    <version>1.0.0</version>
</dependency>
```
For newer versions check [Maven Central - ChatGPTClient](https://central.sonatype.com/artifact/io.github.llewelll/ChatGPTClient/versions)

## Usage  

Sending request involves 3 steps:
1. Building request
2. Setting your API-key in ``` ChatGPTCLient class ```
3. Sending builded request and getting response

Example:
```
TextRequestBuilder builder = new TextRequestBuilder()
       .setUserContent("Main request"); 

TextRequest request = builder.build();

ChatGPTClient client = ChatGPTClient.getInstance();
HttpResponse<String> response = client.sendResponse(API-key, request);
System.out.println(response.body());
```
This Example shows minimal configured request. Additionally, you can set: ```model```, ```temperature```, ```numberOfResponses```, ```asssitantContent```, ```systemContent```.
Definition of each of settings you can find in [official ChatGPT's documentation](https://platform.openai.com/docs/api-reference/chat/create#chat-create-messages)

## Useful Links
Project's Maven Central Page: https://central.sonatype.com/artifact/io.github.llewelll/ChatGPTClient <br />
How to create API-key: https://medium.com/latinxinai/how-to-get-api-key-for-chat-gpt-3-5-or-4-0-fce40b35aa00 <br />
Official ChatGPT Docs (if needed more information): https://platform.openai.com/docs/guides/text-generation 

## Contacts
If you have some questions or have some problems, pls contact me with this email: yes.karim04@gmail.com or write about problem in "Issues" section
