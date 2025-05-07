import { BrowserRouter, Routes, Route } from "react-router-dom";
import Tables from "../pages/Tables";
import Home from "../pages/Home";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/tables" element={<Tables />} />
            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;