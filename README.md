# assigment

create databse: CREATE DATABASE vessel_management;
swagger api  documentation - http://localhost:8080/swagger-ui/index.html#/

POST /addOwner
{
    "ownerName": "TestOwner"
}

POST /addOwnerToShip/{ownerId}/{categoryId}
DELETE /removeOwnerFromShip/{ownerId}/{categoryId}
DELETE /deleteOwner/{ownerId}

POST /addShipCategory
{
    "shipType": "Container",
    "shipTonnage": 30000
}

POST /addShip
{
    "shipName": "TestShip",
    "imoNumber": "IMO1234567",
    "shipId": {category id}
}

PUT /updateShip/{id}
{
    "shipName": "UpdatedShipName",
    "imoNumber": "IMO7654321",
    "shipId": 1
}

PUT /updateShipCategory/{id}
{
    "shipType": "UpdatedType",
    "shipTonnage": 40000
}

GET /getAllShips
GET /getShip/{id}
GET /getShipCategory
DELETE /deleteShip/{id}
DELETE /deleteShipCategory/{id}
