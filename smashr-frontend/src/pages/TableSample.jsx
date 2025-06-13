import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useState } from "react";
import TableElement from "../components/TableElement";

const TableSample = () => {
  const { id, name } = useParams();
  const [table, setTable] = useState([]);

  function createSampleTable() {
    setTable({
        id:"1234",
        name:"Taules Ping-Pong a Trias i Giró",
        type:"Ping-Pong",
        address:"Carrer de Trias i Giró 17, 08034, Barcelona",
        description:"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In accumsan fermentum nunc, eu rhoncus diam mattis a. Donec eu arcu sed diam mollis accumsan at vitae odio. Donec enim tellus, semper ut porta ac, porttitor sed risus. Proin quis odio ornare, tristique lorem quis, mollis nisi. Nulla ornare vulputate leo, id feugiat nulla tempor vel.",
        "coordinates": [425884.673, 4582036.643],
        images: ["https://estatics-nasia.dtibcn.cat/nasia-pro/media/trias_i_giro.d8c5ff8ab.JPG","https://estatics-nasia.dtibcn.cat/nasia-pro/media/99400368008-deudits.optimized.c7f827ce.jpg"],
        image_thumb:"https://estatics-nasia.dtibcn.cat/nasia-pro/media/trias_i_giro.thumb-150x150.d8c5ff8a.jpg",
        image_optimized:"https://estatics-nasia.dtibcn.cat/nasia-pro/media/trias_i_giro.optimized.d8c5ff8a.jpg",
        ratings:3.95,
        reviews:36,
    });
  }

  useEffect(() => {
    createSampleTable();
  }, []);

  return <TableElement table={table} />;
};

export default TableSample;
