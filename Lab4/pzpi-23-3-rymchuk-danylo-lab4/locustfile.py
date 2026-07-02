from locust import HttpUser, task, between
import random

class HobbyRentUser(HttpUser):
    wait_time = between(1, 3)
    
    @task(5)
    def get_equipment_list(self):
        self.client.get("/api/equipment", name="/api/equipment")
    
    @task(1)
    def create_equipment(self):
        self.client.post("/api/equipment", 
            json={
                "title": f"Test Equipment {random.randint(1, 1000)}",
                "category": "Туризм",
                "hourlyRate": 50.0,
                "status": "AVAILABLE"
            },
            name="/api/equipment [POST]")