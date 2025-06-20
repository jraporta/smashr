import React from "react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import { Plus } from "lucide-react";
import { parse } from "iso8601-duration";

// Helper to format duration string like "PT30M" to "30 min"
const formatDuration = (durationStr) => {
  const parsed = parse(durationStr); // { minutes: 30 }
  const parts = [];
  if (parsed.hours) parts.push(`${parsed.hours}h`);
  if (parsed.minutes) parts.push(`${parsed.minutes}min`);
  return parts.join(" ");
};

const formatGameDate = (startDate, duration) => {
  const date = new Date(startDate);
  const day = format(date, "EEEE, d 'de' MMMM", { locale: es });
  const time = format(date, "HH:mm");
  return `${day} | ${time} (${formatDuration(duration)})`;
};

const PlayerSlot = ({ playerId, playerName, onJoin }) => {
  const isEmpty = !playerId;

  return (
    <div className="flex-1 flex flex-col items-center"
        >
        <button
        className={`w-16 h-16 text-xl rounded-full shadow-xs flex items-center justify-center mb-1 transition ${ 
            isEmpty ? 
                "bg-gray-200 text-gray-500 hover:bg-gray-300"
                : "bg-blue-500 text-white hover:bg-blue-600"
            }`}
        onClick={() => isEmpty && onJoin && onJoin()}
        >
            {isEmpty ? <Plus className="w-6 h-6" /> : playerName?.[0]?.toUpperCase() || "P"}
        </button>
        <span className="text-xs text-center text-gray-700">
            {isEmpty ? "Join" : playerName || playerId}
        </span>
    </div>
  );
};

function GameListElement({ game, onJoin }) {
    const { schedule, durationMinutes, players, table } = game;

    const isSingles = players.type === "SINGLES";
    const totalSlots = isSingles ? 2 : 4;
    const slots = Array.from( { length: totalSlots }, (_, i) => players.players[i] || null);

    return (
        <div className="rounded-xl border border-gray-200 bg-white p-4 shadow-sm mb-4">
            <div className="text-md font-semibold text-gray-800 mb-2 py-1">
                {formatGameDate(schedule.startDateTime, schedule.duration)}
            </div>
            <div className="flex gap-6 m-4">
                {slots.map((playerId, idx) => (
                <PlayerSlot key={idx} playerId={playerId} onJoin={onJoin} />
                ))}
            </div>
            <div className="text-x1 text-gray-500 py-1">
                {`table: ${table}`}
            </div>
        </div>
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
            <div>
                {games.map(game =>
                    <GameListElement key={game.id} game={game} onJoin={() => console.log("To implement")} />
                    )}
            </div>
        </div>
    );
}

export default GameList;