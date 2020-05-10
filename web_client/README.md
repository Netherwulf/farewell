# farewell Web Client
Web client

## Technologies used:
* Docker
* NodeJS
* React

## Commands
* Running - execute inside <code>farewell/web_client</code> directory:
    * <code>npm install</code>
    * <code>npm start</code>
* Create Docker image and tag it - execute inside <code>farewell/web_client</code> directory:
    * <code>docker build -t web_client .</code>
    * <code>docker run -d -p 3000:80 web_client .</code>
    * container can be accessed at <code>(docker-machine ip):3000</code>
