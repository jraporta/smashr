import API from "./api";

const server = 'http://localhost:8082';

async function getGamesFromTable(id) {
    try {
        const response = await API.get(server + '/games?tableId=' + id);
        return response.data.map(mapToGame);
    } catch (error) {
        console.error('Failed to fetch games data:', error.response || error.message);
        throw error;
    }
}

function mapToGame(game) {
    return {
        ...game,
    }
}

export const gameService = {
    getGamesFromTable,
}