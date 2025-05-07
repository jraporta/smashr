import React, { useEffect } from "react";
import { useState } from "react";
import getTables from "../services/tableService";
import TableList from "../components/TableList";

const Tables = () => {
    const [tables, setTables] = useState([]);

    async function fetchTables() {
        try {
            const data = await getTables();
            setTables(data);
            console.log('Table list retrieved from API');
        } catch (err) {
            console.error('Failed to load tables');
        }
    }

    useEffect(() => {
        fetchTables();
    }, []);

    return (
        <TableList tables={tables} emptyHeading="No data found"/>
    );
};

export default Tables;