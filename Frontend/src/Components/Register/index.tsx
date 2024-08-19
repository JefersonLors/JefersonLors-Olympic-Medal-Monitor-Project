import React from "react";
import "./register.css"
import { Link } from "react-router-dom";

function Register() {
  return (
    <div className="container">
      <div className="center">
        <h1>Register</h1>
        <form action="" method="POST">
          <div className="txt_field">
            <input type="text" name="text" required />
            <span></span>
            <label>Username</label>
          </div>

          <div className="txt_field">
            <input type="password" name="password" required />
            <span></span>
            <label>Password</label>
          </div>
          <input name="submit" type="Submit" value="Confirm" />
          <div className="signup_link">
            Already a Member ? <Link to="/Login">Login</Link>
          </div>
        </form>
      </div>
    </div>
  );
}
export default Register;
