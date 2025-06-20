import React from "react";
import { useNavigate } from "react-router-dom";
import slugify from "slugify";

function TableListElement({ table }) {
    const navigate = useNavigate();

    return (
        <h2
            className="cursor-pointer w-2xs p-3 m-3 rounded-full border-1 border-l-stone-800 text-center"
            onClick={() => navigate("/tables/" + table.id + "/" + slugify(table.name, { lower:true }))}
            >{table.name}
        </h2>
    );
}

function TableList({ tables, emptyHeading }) {
    const count = tables.length || 0;
    let heading = emptyHeading;
    if (count >0) {
        heading = `Found ${count} ${count > 1 ? 'Tables' : 'Table'}:`;
    }
    return (
        <div className="flex flex-col items-center m-9">
            <h1 className="text-4xl pb-5 font-semibold">{heading}</h1>
            {tables.map(table =>
                <TableListElement key={table.id} table={table} />
                )}
        </div>
    );
}

export default TableList;