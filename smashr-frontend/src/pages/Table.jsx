import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useState } from "react";
import TableElement from "../components/TableElement";
import { tableService } from "../services/tableService";

const Table = () => {
    const { id, name } = useParams();
    const [table, setTable] = useState([]);

    async function fetchTable() {
            try {
                const data = await tableService.getTable(id);
                setTable(data);
                console.log('Table element data retrieved from API');
            } catch (err) {
                console.error('Failed to load table element');
            }
        }
    
        useEffect(() => {
            fetchTable();
        }, []);
    

    return (
        <TableElement table={table} />
    );
};

export default Table;