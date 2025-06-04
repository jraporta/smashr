import API from "./api";

async function login(username, password) {
    const data = { username, password };
    try {
        const response = await API.post('http://localhost:8081/user/login', data);
        return mapToUser(response.data);
    } catch (error) {
        console.error('Failed to login:', error.response || error.message);
        throw error;
    }
}

function mapToUser(user) {
    return {
        ...user,
        id: user.id ?? 'unknown',
        username: user.username ?? 'unknown',
    }
}

export default login;