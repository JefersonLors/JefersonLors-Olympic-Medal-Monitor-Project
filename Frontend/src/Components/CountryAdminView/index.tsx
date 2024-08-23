import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CountryAdminView.css"
import { toast } from "react-toastify";
import { apiService } from "../Services";

function CountryAdminView(){
    const {id} = useParams();
    const [country, setCountry] = useState({id:"", name:"",  flag:""});
    const [modalities, setModalities] = useState([{id:"",  name:"", description:""}])
    const [medalTypes, setMetalTypes] = useState([{id:"", type:""}])
    const navigate = useNavigate();
    
    const [selectedRadio, setSelectedRadio] = useState();
    const [selectedOption, setSelectedOption] = useState();
  
    function handleRadioChange (event) {
      setSelectedRadio(event.target.value);
    };
  
    const handleOptionChange = (event) => {

      setSelectedOption(event.target.value);
    };

    const handleSave = ()=>{
        console.log(selectedOption, selectedRadio)
        apiService.addMedal({country: id, medal:selectedRadio, sport:selectedOption})
                    .then((response)=>{
                        toast.success("Medalha adicionada com sucesso!");
                    }).catch((error)=>{
                        toast.error(error.response.data.message);
                        console.log(error);
                    });

        apiService.notifyUser({countryId: id, sportModalityId: selectedOption, medalId: selectedRadio, medalsWon: 1})
                    .then((response)=>{

                    }).catch((error)=>{
                        toast.error(error.response.data.message);
                        console.log(error);
                    })
    }

    useEffect(()=>{
        function loadCountry(){
            console.log(id)
             apiService.getCountryById(id)
                            .then((response)=>{
                                console.log(response);
                                setCountry(response.data.country);
                            }).catch((error)=>{
                                toast.error(error.response.data.message);
                                console.log("Erro ao recuperar informações do país: ", error);
                            });
                            
        }
        loadCountry();

        function loadModalityOptions(){
            apiService.getAllModalities()
                        .then((response)=>{
                            setModalities(response.data)
                        }).catch((error)=>{
                            toast.error(error.response.data.message);
                            console.log(error);
                        })
        }
        loadModalityOptions();

        function loadMedalTypes(){
            apiService.getAllMedalTypes()
                        .then((response)=>{
                            console.log("medal types: ", response.data)
                            setMetalTypes(response.data);
                        }).catch((error)=>{
                            toast.error(error.response.data.message);
                            console.log(error);
                        })

        }
        loadMedalTypes();
    }, []);

    return(
        <div className="countryCardAdminDiv">
            <div className="headerCardCountryAdminDiv">
                <div className="backDivAdmin">
                    <img 
                        src="https://static.vecteezy.com/system/resources/previews/000/589/654/original/vector-back-icon.jpg" 
                        className="imgBack" 
                        onClick={()=>{navigate("/Home")}}
                        alt="back"
                    />
                </div>
                <div className="changeViewDiv">
                    <button 
                        id="buttonSave" 
                        className="buttonChangeView" 
                        onClick={()=>{navigate(`/CountryCard/${id}`)}}
                    >
                        User
                    </button>
                </div>
            </div>
            <div className="contentCardAdminDiv">
                <div className="titleAdminDiv">
                    <h3>{country.name}</h3>
                </div>
                <div className="imgFlagAdminDiv">
                    <img
                        decoding="async"
                        src={country.flag}
                        alt="imagem do card 1 html e css"
                        className="imgFlagAdmin1"
                    />
                </div>
                <div className="addMedalAdminDiv">
                    <div className="medalContainer">
                        {
                            medalTypes.map((medalType, index)=>{
                                return(
                                    <div className="typeMedalButton" key={index}>
                                        <input type="radio" 
                                            key={index}
                                            id={medalType.type}
                                            value={medalType.id} 
                                            checked={selectedRadio === medalType.id} 
                                            onChange={()=>handleRadioChange(event)}
                                         />
                                        <label htmlFor={medalType.type}> {medalType.type.toLowerCase()}</label>
                                    </div>
                                );
                            })
                        }
                    </div>
                    <div className="modalityContainer">
                        <select id="modalitiesSelect" value={selectedOption} onChange={handleOptionChange} className="selectInput">
                            <option value="">Selecione</option>
                                {modalities.map((option, index) => (
                                    <option id={option.id}  key={index} value={option.id}>
                                        {option.name}
                                    </option>
                                    ))
                                }
                        </select>
                    </div>
                </div>
                <div className="saveSection">
                    <button id="buttonSave" className="buttonSave" onClick={handleSave}>Save</button>
                </div>
            </div>
        </div>
    );
}export default CountryAdminView;