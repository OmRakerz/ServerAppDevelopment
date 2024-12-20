let username = "";
let isLoggedIn = false;
let socket;

// Получение ссылок на элементы DOM
const chatWindow = document.getElementById('chat-window');
const messageInput = document.getElementById('message-input');
const usernameInput = document.getElementById('username');
const logoutButton = document.getElementById('logout-button');

// Функция для открытия модального окна
function openModal() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'block';
}

// Функция для закрытия модального окна и инициализации чата
function closeModalAndInitializeChat() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'none';

    const usernameInput = document.getElementById('username');
    username = usernameInput.value || "Аноним";
    usernameInput.setAttribute('disabled', true);
    isLoggedIn = true;
    logoutButton.style.display = username === "Аноним" ? 'none' : 'inline-block';

    // Подключение к серверу через WebSocket
    socket = new WebSocket('ws://localhost:1501');

    socket.onopen = () => {
        console.log('Подключение к серверу установлено');
        // Отправляем сообщение о присоединении пользователя
        socket.send(`${username} присоединился к чату`);
    };

    // Обработка сообщений от сервера
    socket.onmessage = (event) => {
        const messages = event.data.split('\n');
        messages.forEach((message) => {
            if (message) {
                const messageDiv = document.createElement('div');
                messageDiv.classList.add('message');
                messageDiv.innerHTML = message; // Используем innerHTML для поддержки HTML-тегов
                chatWindow.appendChild(messageDiv);
            }
        });
        chatWindow.scrollTop = chatWindow.scrollHeight;
    };

    socket.onclose = () => {
        console.log('Подключение к серверу закрыто');
    };

    socket.onerror = (error) => {
        console.error('Ошибка WebSocket:', error);
        alert('Не удалось подключиться к серверу. Попробуйте перезагрузить страницу.');
    };
}

// Функция для отправки сообщения
function sendMessage() {
    if (!isLoggedIn) {
        alert('Вы должны войти в чат, чтобы отправлять сообщения.');
        return;
    }

    const message = messageInput.value.trim();

    if (message !== '') {
        // Проверяем, открыто ли соединение WebSocket
        if (socket.readyState === WebSocket.OPEN) {
            socket.send(`<span class="username">${username}</span>: ${message}`);
            messageInput.value = '';
        } else {
            alert('Соединение с сервером еще не установлено. Подождите несколько секунд.');
        }
    } else {
        alert('Введите текстовое сообщение.');
    }
}

// Обработка нажатия клавиши Enter
messageInput.addEventListener('keydown', function(event) {
    if (event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault();
        sendMessage();
    }
});

// Функция для выхода из чата
function logout() {
    if (socket.readyState === WebSocket.OPEN) {
        socket.send(`${username} вышел из чата`);
    }
    username = "";
    usernameInput.value = "";
    usernameInput.removeAttribute('disabled');
    logoutButton.style.display = 'none';
    isLoggedIn = false;
}

// Открываем модальное окно при загрузке страницы
window.onload = openModal;