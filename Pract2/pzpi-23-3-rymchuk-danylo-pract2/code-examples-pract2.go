1  // WebSocket підключення до Discord Gateway (Node.js)
2  const WebSocket = require('ws');
3  
4  const ws = new WebSocket('wss://gateway.discord.gg/?v=9&encoding=json');
5  
6  ws.on('open', function open() {
7      // Відправка ідентифікації
8      ws.send(JSON.stringify({
9          op: 2, // Opcode: Identify
10         d: {
11             token: 'YOUR_BOT_TOKEN',
12             intents: 1023, // Subscribed events
13             properties: {
14                 $os: 'linux',
15                 $browser: 'my_library',
16                 $device: 'my_library'
17             }
18         }
19     }));
20 });
21  
22 ws.on('message', function incoming(data) {
23     const packet = JSON.parse(data);
24  
25     switch(packet.t) {
26         case 'READY':
27             console.log('Connected as', packet.d.user.username);
28             break;
29         case 'MESSAGE_CREATE':
30             console.log('New message:', packet.d.content);
31             break;
32         case 'PRESENCE_UPDATE':
33             console.log('User status changed');
34             break;
35     }
36 });
37  
38 // REST API приклад надсилання повідомлення (Node.js)
39 const fetch = require('node-fetch');
40  
41 async function sendMessage(channelId, content) {
42     const response = await fetch(
43         `https://discord.com/api/v9/channels/${channelId}/messages`,
44         {
45             method: 'POST',
46             headers: {
47                 'Authorization': 'Bot YOUR_BOT_TOKEN',
48                 'Content-Type': 'application/json'
49             },
50             body: JSON.stringify({
51                 content: content,
52                 tts: false
53             })
54         }
55     );
56  
57     const data = await response.json();
58     return data;
59 }
60  
61 // Мікросервіс обробки повідомлень (Go)
62 package main
63  
64 import (
65     "encoding/json"
66     "net/http"
67     "log"
68 )
69  
70 type Message struct {
71     ID        string `json:"id"`
72     ChannelID string `json:"channel_id"`
73     Content   string `json:"content"`
74     AuthorID  string `json:"author_id"`
75     Timestamp string `json:"timestamp"`
76 }
77  
78 type MessageService struct {
79     db    *Database
80     queue *MessageQueue
81 }
82  
83 func (s *MessageService) CreateMessage(w http.ResponseWriter, r *http.Request) {
84     var msg Message
85     if err := json.NewDecoder(r.Body).Decode(&msg); err != nil {
86         http.Error(w, err.Error(), http.StatusBadRequest)
87         return
88     }
89  
90     // Збереження у базі даних
91     if err := s.db.Save(&msg); err != nil {
92         http.Error(w, err.Error(), http.StatusInternalServerError)
93         return
94     }
95  
96     // Публікація події у чергу повідомлень
97     if err := s.queue.Publish("message.created", msg); err != nil {
98         log.Printf("Failed to publish event: %v", err)
99     }
100 
101    // Відповідь клієнту
102    w.Header().Set("Content-Type", "application/json")
103    w.WriteHeader(http.StatusCreated)
104    json.NewEncoder(w).Encode(msg)
105 }
106 
107 func main() {
108    service := &MessageService{
109        db:    NewDatabase(),
110        queue: NewMessageQueue(),
111    }
112 
113    http.HandleFunc("/messages", service.CreateMessage)
114    log.Fatal(http.ListenAndServe(":8080", nil))
115 }