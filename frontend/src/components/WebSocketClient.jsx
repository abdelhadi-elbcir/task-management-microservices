import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const WebSocketClient = () => {
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        // Create a WebSocket connection using SockJS
        const socket = new SockJS('http://localhost:8083/ws'); // Spring Boot WebSocket endpoint

        // Create a new Stomp client
        const stompClient = new Client({
            webSocketFactory: () => socket,
            debug: (str) => console.log(str), // Optional: For debugging
            reconnectDelay: 5000, // Automatically reconnect after 5 seconds
            onConnect: (frame) => {
                console.log('Connected: ' + frame);

                // Subscribe to the notifications topic
                stompClient.subscribe('/topic/notifications', (message) => {
                    if (message.body) {
                        const newNotification = message.body;
                        setNotifications((prevNotifications) => [...prevNotifications, newNotification]);
                    }
                });
            },
            onStompError: (frame) => {
                console.error('Broker error: ', frame.headers['message']);
                console.error('Details: ', frame.body);
            }
        });

        // Activate the client
        stompClient.activate();

        // Cleanup on component unmount
        return () => {
            stompClient.deactivate();
        };
    }, []);

    return (
        <div>
            <h2>Notifications</h2>
            <ul>
                {notifications?.map((notification, index) => (
                    <li key={index}>{notification}</li>
                ))}
            </ul>
        </div>
    );
};

export default WebSocketClient;
