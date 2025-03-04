<h1 align="center" style="font-weight: bold;">Nlw Connect 💻</h1>

<p align="center">
 <a href="#tech">Technologies</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#license">License</a>
</p>

<p align="center">
    <b>Event provided by rocketseat 🚀.</b>
</p>

<h2 id="technologies">💻 Technologies</h2>

- Java (Spring boot).

<h2 id="started">🚀 Getting started</h2>

Prerequisites necessary:

- [Java 17+](https://www.oracle.com/br/java/technologies/downloads/)

<h3>Cloning</h3>

```bash
git clone https://github.com/Marcos-Brhemem/nlwConnect
```

<h3>Starting</h3>

```bash
mvn spring-boot:run or run using your preferred ide.
```

<h2 id="routes">📍 API Endpoints</h2>

​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /events</kbd>     | retrieves information from all events see [response details](#get-event-detail)
| <kbd>GET /events/{prettyName}</kbd>     | retrieve event information by pretty name [response details](#get-event-prettyname-detail)
| <kbd>GET /subscription/{prettyName}/ranking</kbd>     | who most nominated top 3 see [response details](#get-event-ranking-detail)
| <kbd>GET /subscription/{prettyName}/ranking/{userId}</kbd>     | view ranking by user [response details](#get-event-ranking-user-detail)
| <kbd>POST /events</kbd>     | register a new event see [request details](#post-event-detail)
| <kbd>POST /subscription/{prettyName} or /{userId} </kbd>     | registers at an event alone or by recommendation. By indication, just add the /idUser you indicated see [request details](#post-event-subscription-detail)



<h3 id="get-event-detail">GET /events</h3>

**RESPONSE**
```json
{
        "eventID": 6,
        "title": "CodeCraft Summit 2027",
        "prettyname": "codecraft-summit-2027",
        "location": "Online",
        "price": 0.0,
        "startDate": "2027-03-16",
        "endDate": "2027-03-18",
        "startTime": "19:00:00",
        "endTime": "21:00:00"
    },
    {
        "eventID": 7,
        "title": "CodeCraft Summit 2028",
        "prettyname": "codecraft-summit-2028",
        "location": "Online",
        "price": 0.0,
        "startDate": "2027-03-16",
        "endDate": "2027-03-18",
        "startTime": "19:00:00",
        "endTime": "21:00:00"
    }
```

<h3 id="get-event-prettyname-detail">GET /events/{prettyName}</h3>

**RESPONSE**
```json
{
    "eventID": 7,
    "title": "CodeCraft Summit 2028",
    "prettyname": "codecraft-summit-2028",
    "location": "Online",
    "price": 0.0,
    "startDate": "2027-03-16",
    "endDate": "2027-03-18",
    "startTime": "19:00:00",
    "endTime": "21:00:00"
}
```

<h3 id="get-event-ranking-detail">GET /subscription/{prettyName}/ranking </h3>

**RESPONSE**
```json
    {
        "subscribers": 7,
        "userId": 5,
        "name": "Marcos"
    },
    {
        "subscribers": 4,
        "userId": 6,
        "name": "Lucio"
    },
    {
        "subscribers": 2,
        "userId": 11,
        "name": "Maria"
    }
```

<h3 id="get-event-ranking-user-detail">GET /subscription/{prettyName}/ranking/{userId} </h3>

**RESPONSE**
```json
{
    "item": {
        "subscribers": 2,
        "userId": 11,
        "name": "Maria"
    },
    "position": 3
}
```

<h3 id="post-event-detail">POST /events</h3>

**REQUEST**
```json
{
  "title": "CodeCraft Summit 2030",
  "location": "Online",
  "price": 10.0,
  "startDate": "2027-03-16",
  "endDate": "2027-03-18",
  "startTime": "19:00:00",
  "endTime": "21:00:00"
}
​
```

**RESPONSE**
```json
{
    "eventID": 8,
    "title": "CodeCraft Summit 2030",
    "prettyname": "codecraft-summit-2030",
    "location": "Online",
    "price": 10.0,
    "startDate": "2027-03-16",
    "endDate": "2027-03-18",
    "startTime": "19:00:00",
    "endTime": "21:00:00"
}
```

<h3 id="post-event-subscription-detail">POST /subscription/{prettyName}</h3>

**REQUEST**
```json
{
  "name": "silva",
  "email": "silva@silva.com"
}
​
```

**RESPONSE**
```json
{
    "subscriptionNumber": 25,
    "designation": "http://codecraft.com/subscription/codecraft-summit-2030/11"
}
```

<h2 id="license">📑 License</h2>

This project is under the MIT license.

---

Made with ♥ by Marcos Brhemem 👋