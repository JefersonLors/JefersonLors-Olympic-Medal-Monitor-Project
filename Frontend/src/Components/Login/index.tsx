import React, { useEffect } from "react";
import "./login.css";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { apiService } from "../Services";
import { toast } from "react-toastify";

function Login() {
  const [login, setLogin] = useState("");
  const [password, setPassword] =  useState("");
  const [isloading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  localStorage.clear();

  async function confirmLogin(){
    setIsLoading(true);
    if(validateCredencials()){
        await apiService.login({login: login, password: password})
                        .then(async (responseA)=>{
                            await apiService.getUserByEmail(login)
                                      .then(async(responseB)=>{
                                        localStorage.setItem('user', JSON.stringify(responseB.data));
                                        localStorage.setItem('authToken', responseA.data);
                                        await apiService.getUserRoles({value: localStorage.getItem('authToken')})
                                                        .then((responseC)=>{
                                                          console.log(responseC.data)
                                                          localStorage.setItem('userRoles', responseC.data);
                                                          navigate("/Home")
                                                        }).catch((error)=>{
                                                            toast.error(error.response.data.message);
                                                            console.log("error durante a recuperação das roles do usuário.", error)
                                                      });
                                      }).catch((error)=>{
                                          toast.error(error.response.data.message);
                                          console.log("Erro durante a recuperação do usuário por email", error);
                                      });
                        }).catch((error)=>{
                          toast.error(error.response.data.message);
                          console.log("Erro durante validar credenciais do usuário", error);
                        });      
    }
    setIsLoading(false);
  }
  function validateCredencials(){
    if( login == null || login.length < 1 ){
      toast.error("O login é obrigatório.");
      return false;
    }

    if( password == null || password.length < 1 ){
      toast.error("A senha é obrigatória.");
      return false;
    }
    return true;
  }

  if(isloading){
    return(
      <div className="base">
        <div className="loader"></div>
      </div>
    );
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
