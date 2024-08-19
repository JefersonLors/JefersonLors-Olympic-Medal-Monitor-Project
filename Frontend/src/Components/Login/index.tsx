import React from "react";
import "./login.css";
import { Link } from "react-router-dom";

function Login() {
  return (
    <div className="container">
      <div className="center">
        <h1>Login</h1>
        <form action="" method="POST">
          <div className="txt_field">
            <input type="text" name="text" required />
            <span></span>
            <label>E-mail</label>
          </div>

          <div className="txt_field">
            <input type="password" name="password" required />
            <span></span>
            <label>Password</label>
          </div>
          
          <input name="submit" type="Submit" value="Confirm" />

          <div className="signup_link">
            Not a Member ? <Link to="/Register">Register</Link>
          </div>

        </form>
      </div>
    </div>
  );
}
export default Login;
