import React from "react";
import "./login.css";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { apiService } from "../Services";
import { toast } from "react-toastify";

function Login() {
  const [login, setLogin] = useState("");
  const [password, setPassword] =  useState("");
  const navigate = useNavigate();

  async function confirmLogin(){
    const credencials ={
      login: login,
      password: password
    };

    await apiService.login(credencials)
                    .then((response)=>{
                        navigate("/Home")
                        localStorage.setItem('authToken', response.data);
                    }).catch((err)=>{
                      toast.error(err.response.data.message);
                    });
  }

  return (
    <div className="container">
      <div className="center">
        <h1>Login</h1>
        <form>
          <div className="txt_field">
            <input 
              id="emailField" 
              type="text" 
              name="emailField" 
              value={login}
              onChange={(e)=>{setLogin(e.target.value)}}
              required  />
            <span></span>
            <label>E-mail</label>
          </div>

          <div className="txt_field">
            <input 
              id="passwordField" 
              type="password" 
              name="passwordField"
              value={password} 
              onChange={(e)=>{setPassword(e.target.value)}}
              required />
            <span></span>
            <label>Password</label>
          </div>
          
          <button
            type="button"
            id="confirmButton" 
            name="confirmButton"
            className="buttonLogin"
            onClick={()=>{confirmLogin()}}
          >
            Confirm
          </button>

          <div className="signup_link">
            Not a Member ? <Link to="/Register">Register</Link>
          </div>

        </form>
      </div>
    </div>
  );
}
export default Login;
