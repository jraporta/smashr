import React from "react";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const Home = () => {
    const navigate = useNavigate();
    const [location, setLocation] = useState("");

    return (
        <div className="w-full">
            <div className="relative w-full">
                <img className="absolute inset-0 z-0 h-full w-full object-cover opacity-90 mask-b-from-3 mask-l-from-3" src="\images\background4.png" alt="" />
                <div className="flex w-full justify-center relative z-10 py-32">
                    <div className="flex flex-col w-full max-w-[--page-body-max-width] px-4 justify-start gap-0 py-0 items-start">
                        <h1 className="text-balance tracking-tighter text-7xl md:text-8x1 text-left px-2 font-bold text-gray-700">
                            <span className="text-green-500">Join</span>, <span className="text-green-500">Meet</span> & <span className="text-green-500">Smash</span>!
                        </h1>
                        <div className="text-left pt-8 [&_p]:mb-0 [&_p]:text-xl [&_p]:md:text-2xl/7 font-semibold px-3 text-gray-700">
                            <p className="mb-4 text-lg leading-8 py-1">
                            Fancy a <b className="text-orange-400">Table-Tennis</b> game?
                            </p>
                            <p className="mb-4 text-lg leading-8 py-1">
                            We help you find tables and players around you.
                            </p>
                        </div>
                        <div className="flex items-center gap-3">
                            <form action="/tables" className="w-full max-w-md py-7">
                                <input
                                    type="text"
                                    placeholder="Address, club, city..."
                                    onChange={(e) => setLocation(e.target.value)}
                                    className="w-full bg-white border border-gray-300 rounded-full p-3 text-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                                />
                            </form>
                            <button className="rounded-full bg-sky-500 px-8 py-3 font-semibold text-white hover:bg-sky-700" onClick={() => navigate("/tables")}>Go</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Home;