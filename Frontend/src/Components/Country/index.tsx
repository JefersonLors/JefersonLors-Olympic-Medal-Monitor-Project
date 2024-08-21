import React, { useState } from "react";
import { useParams } from "react-router-dom";
import "./Country.css"

function CountryCard() {
    const params = useParams();
    const [country, setCountry] = useState();


    
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
                    className="imgFlag"
                />
            </div>
            <div className="informationDiv">
                <table className="tableStyle">
                    <thead>
                        <tr>
                            <th>Modality</th>
                            <th>Medal</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr >
                            <td>Futebol</td>
                            <td>Prata</td>
                            <td>{4}</td>
                         </tr>
                         <tr >
                            <td>Futebol</td>
                            <td>Ouro</td>
                            <td>{6}</td>
                         </tr>
                         <tr >
                            <td>Futebol</td>
                            <td>Bronze</td>
                            <td>{2}</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Prata</td>
                            <td>{4}</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Ouro</td>
                            <td>{6}</td>
                         </tr>
                         <tr >
                            <td>Ginástica</td>
                            <td>Bronze</td>
                            <td>{2}</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Prata</td>
                            <td>{4}</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Ouro</td>
                            <td>{6}</td>
                         </tr>
                         <tr >
                            <td>Canoagem</td>
                            <td>Bronze</td>
                            <td>{2}</td>
                         </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  );
}
export default CountryCard;
