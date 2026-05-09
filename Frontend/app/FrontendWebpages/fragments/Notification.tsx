import { Client } from '@stomp/stompjs';
import {toast} from "@heroui/react";
// import SockJS from 'sockjs-client';

let client: Client | null = null;

export function notification() {

    if (client && client.active) {
        return;
    }

    client = new Client({
        //Never using SockJS again... WHY DID WE USE IT IN THE FIRST PLACE????
        // webSocketFactory: () => new SockJS('http://localhost:8050/ws'),
        webSocketFactory: () => new WebSocket('http://localhost:8050/websocket/ws'),
        connectHeaders: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        debug: (str) => {
            console.log(str);
        },
    });

    client.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        client?.subscribe('/user/queue/notifications', (message) => {
            console.log('You got mail! -> ' + message.body);
            toast(!message.body.includes("accepted") && !message.body.includes("rejected") ? "You got mail!" : "You recived a response for one of your applications", {
                actionProps: {
                    children: "Dismiss",
                    onPress: () => toast.clear(),
                    variant: "tertiary",
                },
                indicator: message.body.includes("accepted") ? <img src="/images/assets/checkmark@4x.png" alt="X" width={17} height={17}/> : message.body.includes("rejected") ? <img src="/images/assets/xmark@4x.png" alt="X" width={17} height={17}/> : <img src="/images/assets/bell.fill@4x.png" alt="Bell" width={17} height={17}/>,
                description: message.body,
                variant: message.body.includes("accepted") ? "success" : message.body.includes("rejected") ? "danger" : "default",
            })
        });
    };

    client.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    client.activate();
}