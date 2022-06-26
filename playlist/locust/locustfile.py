from locust import HttpUser, task, between

import logging
from locust import events
import requests

payload = {'username':'sioravw@gmail.com','password':'boe123','client_id':'djcollab-vue','grant_type':'password'}
response = requests.post(
    "http://keycloak.siora.nl/auth/realms/djcollab-realm/protocol/openid-connect/token",
    data=payload
).json()
print(response)
auth_token = response['access_token']

@events.quitting.add_listener
def _(environment, **kw):
    if environment.stats.total.fail_ratio > 0.01:
        logging.error("Test failed due to failure ratio > 1%")
        environment.process_exit_code = 1
    elif environment.stats.total.avg_response_time > 200:
        logging.error("Test failed due to average response time ratio > 200 ms")
        environment.process_exit_code = 1
    elif environment.stats.total.get_response_time_percentile(0.95) > 800:
        logging.error("Test failed due to 95th percentile response time > 800 ms")
        environment.process_exit_code = 1
    else:
        environment.process_exit_code = 0

def put_playlist(self, id):
    self.client.put(f"/playlist/{id}", json={"id": id, "name": "rock playlist", "userId": "dde77171-7f42-4228-9fde-7cada73bb9da", "genre": "ROCK", "userName": "user"}, name="/playlist/id")

def get_specific_playlist(self, id):
    self.client.get(f"/playlist/{id}", name="/playlist/id")

def delete_playlist(self, id):
    self.client.delete(f"/playlist/{id}", name="/playlist/id")

class HelloWorldUser(HttpUser):
    wait_time = between(1, 5)

    def on_start(self):
        self.client.headers = {"Authorization": "Bearer " + auth_token}

    @task(3)
    def get_playlist(self):
        self.client.get("/playlist")

    @task
    def add_playlist(self):
         response = self.client.post("/playlist", json={"name": "playlist", "userId": "dde77171-7f42-4228-9fde-7cada73bb9da", "genre": "POP", "userName": "user"})
         if response.status_code == 200:
            playlistId = response.json()['id']
            put_playlist(self, playlistId)
            get_specific_playlist(self, playlistId)
            wait(3)
            delete_playlist(self, playlistId)
