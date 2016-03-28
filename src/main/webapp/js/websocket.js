
//alert("start");

var ws = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;

}

function output(message) {
   // alert("output function");
    var console = document.getElementById('console');
    
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
    while (console.childNodes.length > 25) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
}

function connect() {

    var target = "ws://127.0.0.1:8080/VocabularyTrainer/websocket/echoAnnotation";
    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function () {
        output('Info: WebSocket connection opened.');
        setConnected(true);
    };
    ws.onmessage = function (event) {
        output('Received: ' + event.data);
        selectWords(event.data)
    };
    ws.onclose = function (event) {
        setConnected(false);
        output('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}

function echo() {
    if (ws != null) {
        var message = document.getElementById('message').value;
        output('Sent: ' + message);
        ws.send(message + "wayne");
    } else {
        alert('WebSocket connection not established, please connect.');
    }
}

function echo(value) {
    if (ws != null) {
        var id = 'message'+ value;
        var message = document.getElementById(id).value;
        output('Sent: ' + message);
        ws.send(message + "wayne");
        //ws.send();
        selectPic();
    } else {
        alert('WebSocket connection not established, please connect.');
    }
}

function selectWords(value){
    if(ws!= null){
        var id = 'word';
        //alert(value);
        document.getElementById(id).innerHTML = value;

    }else {
        alert('WebSocket connection not established, please connect.');
    }
}


function selectPic(){
    var num = Math.round(Math.random()*10);
    //alert(num);
    document.getElementById('word').src = "./img/"+ num +".jpg";
}

