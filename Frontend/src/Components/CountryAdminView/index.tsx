import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CountryAdminView.css"
import { toast } from "react-toastify";

function CountryAdminView(){
    const params = useParams();
    const [country, setCountry] = useState();
    const navigate = useNavigate();
    
    const [selectedRadio, setSelectedRadio] = useState();
    const [selectedOption, setSelectedOption] = useState();
  
    const options = [
      { value: 'option1', label: 'futebol' },
      { value: 'option2', label: 'ginástica' },
      { value: 'option3', label: 'nado' },
      { value: 'option4', label: 'corrida' },
    ];
  
    const handleRadioChange = (event) => {
      setSelectedRadio(event.target.value);
    };
  
    const handleOptionChange = (event) => {
      setSelectedOption(event.target.value);
    };

    function handleSave(){
        toast.error("Esse país já tem uma medalha nessa modalidade.")
        toast.success("Medalha adicionada com sucesso!");
    }
    function filterFunction(){

    }
    return(
        <div className="countryCardAdminDiv">
            <div className="backDiv">
                <img src="https://static.vecteezy.com/system/resources/previews/000/589/654/original/vector-back-icon.jpg" className="imgBack" onClick={()=>{navigate("/Home")}} alt="back"/>
            </div>
            <div className="contentCardAdminDiv">
                <div className="titleAdminDiv">
                    <h3>Country</h3>
                </div>
                <div className="imgFlagAdminDiv">
                    <img
                        decoding="async"
                        src="https://th.bing.com/th/id/OIP.RgwzDihAMttqCx8f4GGTVAHaFL?rs=1&pid=ImgDetMain"
                        alt="imagem do card 1 html e css"
                        className="imgFlagAdmin1"
                    />
                </div>
                <div className="addMedalAdminDiv">
                    <div className="medalContainer">
                        <div className="typeMedalButton">
                            <input type="radio" id="Ouro" value="Ouro" checked={selectedRadio === 'Ouro'} onChange={handleRadioChange} />
                            <label htmlFor="Ouro">Ouro</label>
                        </div>
                        <div className="typeMedalButton">
                            <input type="radio" id="Prata" value="Prata" checked={selectedRadio === 'Prata'} onChange={handleRadioChange} />
                            <label htmlFor="Prata">Prata</label>
                        </div>
                        <div className="typeMedalButton">
                            <input type="radio" id="Bronze" value="Bronze" checked={selectedRadio === 'Bronze'} onChange={handleRadioChange} />
                            <label htmlFor="Bronze">Bronze</label>
                        </div>
                    </div>
                    <div className="modalityContainer">
                        <select value={selectedOption} onChange={handleOptionChange} className="selectInput">
                            <option value="">Selecione</option>
                            {options.map((option) => (
                                <option key={option.value} value={option.value}>
                                    {option.label}
                                </option>
                                ))
                            }
                        </select>
                    </div>
                </div>
                <div className="saveSection">
                    <button id="buttonSave" className="buttonSave" onClick={()=>{handleSave()}}>Save</button>
                </div>
            </div>
        </div>
    );
}export default CountryAdminView;