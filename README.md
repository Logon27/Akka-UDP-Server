# Akka / Alpakka UDP Server Quickstart

There were no good examples online to create a UDP server with Akka / Alpakka. And I had a bunch of trouble getting the server to work due to dependency issues. This acts as a simple quickstart UDP server that reads in the UDP packets and prints them to stdoout.

Start the server:  
```mvn compile exec:exec```

Send a UDP packet:  
```python send-packet.py```