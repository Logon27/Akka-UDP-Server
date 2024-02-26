import socket

# Specify the target IP address and port
target_ip = '127.0.0.1'
target_port = 12346

# Create a UDP socket
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Message to be sent
message = b"Hello, UDP!"

# Send the UDP packet
udp_socket.sendto(message, (target_ip, target_port))

# Close the socket
udp_socket.close()