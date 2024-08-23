import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.css';

function Header() {
    const navigate = useNavigate();
    return (
        <div className="logoutDiv">
            <img
                src="src\assets\logout-image.jpeg"
                className="imgLogout"
                onClick={() => {
                    navigate('/Login');
                }}
                alt="logout"
            />
        </div>
    );
}
export default Header;
