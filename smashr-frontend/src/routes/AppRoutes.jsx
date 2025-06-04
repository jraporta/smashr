import { BrowserRouter, Routes, Route } from "react-router-dom";
import Tables from "../pages/Tables";
import Home from "../pages/Home";
import Layout from "../pages/Layout";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<Layout />}>
                    <Route path="/" element={<Home />} />
                    <Route path="/tables" element={<Tables />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;