import React, { useState } from "react";
import "./register.css"
import { Link, useNavigate } from "react-router-dom";
import { apiService } from "../Services";
import { toast } from "react-toastify";

function Register() {
  const [login, setLogin] = useState("");
  const [password, setPassword] =  useState("");
  const navigate = useNavigate();

  async function confirmRegister(){
    const credencials ={
      login: login,
      password: password,
      rolesId:[1]
    };

    await apiService.register(credencials)
                    .then((response)=>{
                        navigate("/Login")
                        toast.success(response.data);
                    }).catch((err)=>{
                      toast.error(err.response.data.message);
                    });
  }
  return (
    <div className="container">
      <div className="center">
        <h1>Register</h1>
        <form>
          <div className="txt_field">
            <input 
              type="text" 
              name="registerLoginField" 
              value={login}
              onChange={(e)=>{setLogin(e.target.value)}}
              required />
            <span></span>
            <label>Username</label>
          </div>

          <div className="txt_field">
            <input 
              type="password" 
              name="registerPasswordField"
              value={password}
              onChange={(e)=>{setPassword(e.target.value)}}
              required />
            <span></span>
            <label>Password</label>
          </div>
          <button 
            name="confirmRegisterButton" 
            type="button" 
            value="confirmRegister"
            className="buttonRegister"
            onClick={()=>{confirmRegister()}}
          >
                Confirm
          </button>
          <div className="signup_link">
            Already a Member ? <Link to="/Login">Login</Link>
          </div>
        </form>
      </div>
    </div>
  );
}
export default Register;
