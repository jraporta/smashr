import { BrowserRouter, Routes, Route } from "react-router-dom";
import Tables from "../pages/Tables";
import Home from "../pages/Home";
import Layout from "../pages/Layout";
import Table from "../pages/Table";
import TableSample from "../pages/TableSample";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<Layout />}>
                    <Route path="/" element={<Home />} />
                    <Route path="/tables" element={<Tables />} />
                    <Route path="/tables/:id/:name" element={<Table />} />
                    <Route path="/tables/sample" element={<TableSample />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;