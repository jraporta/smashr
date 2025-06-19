import React from "react";

function GameListElement({ game }) {

    return (
        <h2
            className="cursor-pointer w-2xs p-3 m-3 rounded-full border-1 border-l-stone-800 text-center"
            >{game.id}
        </h2>
    );
}

function GameList({ games, emptyHeading }) {
    const count = games.length || 0;
    let heading = emptyHeading;
    if (count > 0) {
        heading = `Found ${count} ${count > 1 ? 'games' : 'game'}:`;
    }
    return (
        <div className="flex flex-col items-center m-9">
            <h1 className="text-4xl pb-5 font-semibold">{heading}</h1>
            {games.map(game =>
                <GameListElement key={game.id} game={game} />
                )}
        </div>
    );
}

export default GameList;