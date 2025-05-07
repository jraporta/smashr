import React from "react";

function TableListElement({ table }) {
    return (
        <h2>{table.name}</h2>
    );
}

function TableList({ tables, emptyHeading }) {
    const count = tables.length || 0;
    let heading = emptyHeading;
    if (count >0) {
        heading = `${count} Available ${count > 1 ? 'Tables' : 'Table'}`;
    }
    return (
        <>
            <h1>{heading}</h1>
            {tables.map(table =>
                <TableListElement key={table.id} table={table} />
                )}
        </>
    );
}

export default TableList;