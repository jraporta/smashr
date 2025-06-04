import React, { useState } from "react";
import login from "../services/loginService";

export default function LoginBar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const user = await login(username, password);
            console.log('Logged user with id:%s, and username:%s', user.id, user.username);
            setUsername(user.username);
            setPassword("");
            setIsLoggedIn(true);
        } catch (error) {
            console.error("Login failed", error);
        }
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setUsername("")
        setPassword("")
    };

    return (
        <div className="border-b border-gray-950/5 bg-white text-black flex h-14 items-center justify-between gap-8 px-4 sm:px-6">
                <a className="flex items-center gap-1" href="/">
                    <img className="size-10 shrink-0" src="/images/smashr.png" alt="image"/>
                    <h1 className="text-xl font-semibold">Smashr</h1>
                </a>

            {isLoggedIn ? (
                <div className="flex items-center gap-4">
                <span>Welcome, {username}!</span>
                <button
                    onClick={handleLogout}
                    className="rounded-full bg-sky-500 px-5 py-2 text-sm leading-5 font-semibold text-white hover:bg-sky-700"
                >
                    Logout
                </button>
                </div>
            ) : (
                <form onSubmit={handleLogin} className="flex items-center gap-2">
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="px-2 py-1 rounded-lg border-2 border-gray-300 text-gray-600 placeholder:text-gray-400 focus:border-pink-600 focus:outline-none"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="px-2 py-1 rounded-lg border-2 border-gray-300 text-gray-700 placeholder:text-gray-400  focus:border-pink-600 focus:outline-none"
                    />
                    <button
                        type="submit"
                        className="rounded-full bg-sky-500 px-5 py-2 text-sm font-semibold text-white hover:bg-sky-700"
                    >
                        Login
                    </button>
                </form>
            )}
        </div>
    );
}