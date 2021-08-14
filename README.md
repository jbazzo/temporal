# temporal

Instructions:
- clone this repo
- cd into the folder
- run command: ./temporal.sh

If everything works well, it should:
- compile and create an image for Fraud module
- compile and create an image for Ochestrator
- downaload Temporal.io images and start docker-compose

Tmporalio. UI accesible at: locahost:8088
To start a new instance of the orders workflow send a post request using curl

curl --location --request POST 'http://localhost:8888/api/v1/workflow/BasicOrdersWorkflow'

Enjoy
