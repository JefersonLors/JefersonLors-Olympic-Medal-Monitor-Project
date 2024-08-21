import React, { useState } from "react";
import { useParams } from "react-router-dom";
import "./Country.css"

function CountryCard() {
    const params = useParams();
    const [country, setCountry] = useState();

    
    function handleFollow(){
        const button = document.getElementById('buttonFollow');
        
        if( button?.classList.contains('buttonFollow') ){
            button.classList.remove('buttonFollow')
            button.classList.toggle('buttonFollowing');
            button.textContent = 'Seguindo';
            
        } else if( button?.classList.contains('buttonFollowing') ){
            button.classList.remove('buttonFollowing')
            button.classList.toggle('buttonFollow');
            button.textContent = 'Seguir';
        }
    }
  return (
    <div className="countryCardDiv">
        <div className="contentCardDiv">
            <div className="titleDiv">
                <h3>Country</h3>
            </div>
            <div className="imgFlagDiv">
                <img
                    decoding="async"
                    src="https://th.bing.com/th/id/OIP.RgwzDihAMttqCx8f4GGTVAHaFL?rs=1&pid=ImgDetMain"
                    alt="imagem do card 1 html e css"
                    className="imgFlag1"
                />
            </div>
            <div className="informationDiv">
                <table className="tableStyle">
                    <thead>
                        <tr>
                            <th>Modality</th>
                            <th>Medal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr >
                            <td>Futebol</td>
                            <td>Prata</td>
                         </tr>
                         <tr >
                            <td>Futebol</td>
                            <td>Ouro</td>
                         </tr>
                         <tr >
                            <td>Futebol</td>
                            <td>Bronze</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Prata</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Ouro</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Bronze</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Prata</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Ouro</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Bronze</td>
                         </tr>
                    </tbody>
                </table>
            </div>
            <div className="followedSection">
                <button id="buttonFollow" className="buttonFollow" onClick={()=>{handleFollow()}}>Seguir</button>
            </div>
        </div>
    </div>
  );
}
export default CountryCard;
