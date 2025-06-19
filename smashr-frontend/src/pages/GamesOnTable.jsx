import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useState } from "react";
import { gameService } from "../services/gameService";
import GameList from "../components/GameList";

const GamesOnTable = () => {
  const { id, name } = useParams();
  const [games, setGames] = useState([]);

  async function fetchGames() {
    try {
      const data = await gameService.getGamesFromTable(id);
      setGames(data);
      console.log("Games data for table retrieved from API");
    } catch (err) {
      console.error("Failed to load the games from table");
    }
  }

  useEffect(() => {
    fetchGames();
  }, []);

  return <GameList games={games} emptyHeading="No games found for table" />;
};

export default GamesOnTable;
