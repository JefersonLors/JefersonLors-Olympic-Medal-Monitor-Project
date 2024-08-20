import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../login";
import Register from "../Register";
import Home from "../home";

function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/Login" element={<Login/>}/>
                <Route path="/Register" element={<Register/>}/>
                <Route path="/Home" element={<Home/>}></Route>
            </Routes>
        </BrowserRouter>
    );

}export default AppRoutes;