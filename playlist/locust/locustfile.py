from locust import HttpUser, task, between

import logging
from locust import events
import requests

payload = {'username':'sioravw@gmail.com','password':'boe123','client_id':'djcollab-vue','grant_type':'password'}
response = requests.post(
    "http://keycloak.siora.nl/auth/realms/djcollab-realm/protocol/openid-connect/token",
    data=payload
).json()
auth_token = response['access_token']

@events.quitting.add_listener
def _(environment, **kw):
    if environment.stats.total.fail_ratio > 0.05:
        logging.error("Test failed due to failure ratio > 1%")
        environment.process_exit_code = 1
    elif environment.stats.total.avg_response_time > 400:
        logging.error("Test failed due to average response time ratio > 200 ms")
        environment.process_exit_code = 1
    elif environment.stats.total.get_response_time_percentile(0.95) > 800:
        logging.error("Test failed due to 95th percentile response time > 800 ms")
        environment.process_exit_code = 1
    else:
        environment.process_exit_code = 0

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
            self.client.put("/playlist", json={"id": playlistId, "name": "rock playlist", "userId": "dde77171-7f42-4228-9fde-7cada73bb9da", "genre": "ROCK", "userName": "user"})
            self.client.delete(f"/playlist/{playlistId}", name="/playlist/id")
