import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../login";
import Register from "../Register";
import Home from "../home";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function AppRoutes(){
    return(
        <BrowserRouter>
            <ToastContainer/>
            <Routes>
                <Route path="/Login" element={<Login/>}/>
                <Route path="/Register" element={<Register/>}/>
                <Route path="/Home" element={<Home/>}></Route>
            </Routes>
        </BrowserRouter>
    );

}export default AppRoutes;