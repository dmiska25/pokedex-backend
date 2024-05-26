
# Pokemon API v1.0

This is an api used to retrieve pokemon information and keep track
of trainer account information. Pokemon information can be retrieved
individualy or as a listed page response. Trainers can keep track
of pokemon they have captured and retrieve this information on demand.

---
## Tech Stack Information
IDE: Intellij
DB: Docker:Postgres  
Runtime: JVM v1.5.10  
Framework: Spring Boot v2.5.0
---

## Requirements
1. Intellij
2. Docker
3. Java 8 or 9
## Installation
```shell
git clone git@bitbucket.org:myriadmobile/dylan-miska-backend-challenge.git  
```
The database can be started from the docker-compose file:
```shell
docker compose up
```
The app can then be started via intellij start

---

## Retrieving Pokemon Information

Endpoints available:
1. /pokemon-api/pokemon  
   Will retrieve all pokemon information as a paginated
   json response. json response sample below.
2. /pokemon-api/pokemon/{id}  
   Will retrieve pokemon information for specified
   pokemon as a json response or return error code 404. json response sample below.

Sample json response of pokemon:
```json
{  
    "id": 5,  
    "name": "Bulbasaur",  
    "types": [  
        "poison",
        "grass"  
    ],  
    "height": 7.0,  
    "weight": 69.0,  
    "abilities": [  
        "chlorophyll",  
        "overgrow"  
    ],  
    "egg_groups": [  
        "plant",  
        "monster"  
    ],  
    "stats": {  
        "hp": 45,  
        "speed": 45,  
        "attack": 49,  
        "defense": 49,  
        "special-attack": 65,  
        "special-defense": 45  
    },  
    "genus": "Seed Pokémon",  
    "description": "Bulbasaur can be seen napping in bright sunlight.\nThere is a seed on its back. By soaking up the sun’s rays,\nthe seed grows progressively larger."  
}

```
---

## Trainer Information

Endpoints available:  
1. /pokemon-api/trainer/register  
   Allows a trainer to register for an
   account by passing a json body including a username and password.
   Will return 400 if username already exists or if username or
   password is invalid.  
   Sample json body:
   ```json
   {
       "username": "user",
       "password": "password"
   }
   ```
2. /pokemon-api/trainer/authenticate  
   Allows a trainer to retrieve a jwt
   by passing a json body including their username and password.
   Will return 403 if username/password is invalid.
3. /pokemon-api/trainer/pokedex  
   Allows a trainer to retrieve a paginated
   list of their captured pokemon by including a authentication header
   including the string "Bearer " followed by their valid jwt. Will
   return 403 on invalid Auth token.
4. /pokemon-api/trainer/addcapturedpokemon  
   Allows a trainer to add a
   captured pokemon to their list by including a json body including a
   pokemonId known to the API along with an authentication header 
   including the string "Bearer " followed by their valid jwt.
   Will return 400 on invalid json body. Will return 404 if no
   pokemon is associated with the passed Id. Will return 403 on
   invalid Auth token.  
   Sample json body:  
   ```json
    {
        "pokemonId": 5
    }
   ```
   