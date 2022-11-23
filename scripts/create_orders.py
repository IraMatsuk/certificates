# importing the requests library
import requests
import random
import sys

ordersCounter = int(sys.argv[1])

for i in range (ordersCounter):

    js = {
        "certificateId": random.randint(90, 2900),
        "userId": random.randint(1, 1016)
    }

    response = requests.post('http://localhost:8080/orders', json=js)

    print("Status code: ", response.status_code)
    print("Printing Entire Post Request")
    print(response.json())

# to create 10000 certificates type in terminal: python .\create_certificate.py 3000