# GlovoJPA

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

### **For local run**

- *active profile "default"*
- *testing profile "test"*
- *postgresDB run from docker*


### **API**

- *Create Order example curl*

```
curl --location 'http://localhost:8080/api/v1/orders' \
--header 'Content-Type: application/json' \
--data '{
            "date": "2024-03-23",
            "cost": 420.50,
            "products": [
                {
                    "name": "pen",
                    "cost": 20.25
                },
                {
                    "name": "book",
                    "cost": 400.25
                }
            ]
}'
```

- *Get Order example curl*

```
  curl --location 'http://localhost:8080/api/v1/orders' \
```

- *Put Order example curl*

```
curl --location --request PUT 'http://localhost:8080/api/v1/orders/1' \
--header 'Content-Type: application/json' \
--data '{
            "id": 1,
            "date": "2024-03-23",
            "cost": 115.50,
            "products": [
                {
                    "id": 1,
                    "name": "pencil",
                    "cost": 15.25
                },
                {
                    "id": 2,
                    "name": "notebook",
                    "cost": 100.25
                }
            ]
}'
```
- *Delete Order example curl*

```
curl --location --request DELETE 'http://localhost:8080/api/v1/orders/1' \
```