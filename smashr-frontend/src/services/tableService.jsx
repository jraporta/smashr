import API from "./api";

async function getTables() {
    try {
        const response = await API.get('/tables');
        return response.data.map(mapToTable);
    } catch (error) {
        console.error('Failed to fetch tables:', error.response || error.message);
        throw error;
    }
}

function mapToTable(table) {
    return {
        ...table,
        id: table.id ?? 'unknown',
        name: table.name ?? 'unknown',
    }
}

export default getTables;