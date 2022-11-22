# importing the requests library
import requests
import random
import sys
from random_word import RandomWords

certsCounter = int(sys.argv[1])

for i in range (certsCounter):
    tagCounter = random.randint(1, 5)

    tags = []
    for x in range(tagCounter):
        tagID = random.randint(25, 1000)
        URL = "http://localhost:8080/tags/" + str(tagID)
        r = requests.get(url = URL)
        data = r.json()
        tags.append({'name': (data['name'])})

    r = RandomWords()
    certName = str(r.get_random_word())
    js = {
        "name": certName,
        "description": certName + " Description",
        "price": round(random.uniform(1,999999), 2),
        "duration": random.randint(1, 364),
        "tags": tags
    }

    response = requests.post('http://localhost:8080/certificates', json=js)

    print("Status code: ", response.status_code)
    print("Printing Entire Post Request")
    print(response.json())

# to create 10000 certificates type in terminal: python .\create_certificate.py 10000