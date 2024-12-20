const WebSocket = require('ws'); // Добавляем библиотеку WebSocket

const TCP_PORT = 1501;

let messages = []; // Хранилище сообщений
let lastBroadcastTime = Date.now(); // Время последней рассылки

// Создаем WebSocket-сервер для обработки клиентских соединений
const wss = new WebSocket.Server({ port: TCP_PORT });

// Обработка сообщений от клиента
wss.on('connection', (ws) => {
    console.log('Новый клиент подключился через WebSocket');

    // Отправляем текущие сообщения новому клиенту
    ws.send(messages.join('\n'));

    ws.on('message', (message) => {
        const messageText = message.toString().trim();
        console.log(`Получено сообщение через WebSocket: ${messageText}`);

        // Добавляем сообщение в хранилище
        messages.push(messageText);
    });

    ws.on('close', () => {
        console.log('Клиент отключился');
    });
});

console.log(`WebSocket-сервер запущен на порту ${TCP_PORT}`);

// Функция для рассылки всех сообщений через WebSocket
function broadcastMessages() {
    if (messages.length === 0) return; // Если нет новых сообщений, ничего не делаем

    const messagePacket = messages.join('\n');
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(messagePacket);
        }
    });

    console.log(`Отправлено ${messages.length} сообщений через WebSocket`);
    messages = []; // Очищаем хранилище сообщений
    lastBroadcastTime = Date.now(); // Обновляем время последней рассылки
}

// Рассылаем сообщения каждые 10 секунд
setInterval(broadcastMessages, 10000);