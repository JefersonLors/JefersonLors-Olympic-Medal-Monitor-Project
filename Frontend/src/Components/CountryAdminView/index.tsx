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
    
    const [selectedMedal, setSelectedMedal] = useState();
    const [selectedModality, setSelectedModality] = useState();
  
    function handleRadioChange (event) {
      setSelectedMedal(event.target.value);
    };
  
    const handleOptionChange = (event) => {

      setSelectedModality(event.target.value);
    };

    const handleSave = async ()=>{
        if( validateSelectedOptions() ){
            await apiService.addMedal({country: id, medal:selectedMedal, sport:selectedModality})
                        .then((response)=>{
                            toast.success("Medalha adicionada com sucesso!");
                            apiService.notifyUser({countryId: id, sportModalityId: selectedModality, medalId: selectedMedal, medalsWon: 1})
                                        .catch((error)=>{
                                            toast.error(error.response.data.message);
                                            console.log("Erro ao notificar seguidores do país: ", error);
                                        })
                        }).catch((error)=>{
                            toast.error(error.response.data.message);
                            console.log("Erro ao adicionar medalha: ", error);
                        });
    

        }

    }

    useEffect(()=>{
        function loadCountry(){
             apiService.getCountryById(id)
                            .then((response)=>{
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
                            console.log("Erro ao recuperar as modalidades esportivas: ", error);
                        })
        }
        loadModalityOptions();

        function loadMedalTypes(){
            apiService.getAllMedalTypes()
                        .then((response)=>{
                            setMetalTypes(response.data);
                        }).catch((error)=>{
                            toast.error(error.response.data.message);
                            console.log("Erro ao recuperar os tipos de medalha disponíveis: ", error);
                        })

        }
        loadMedalTypes();
    }, []);

    function validateSelectedOptions(){
        if(  selectedModality == null || selectedModality == "" ){
            toast.error("A modalidade é obrigatória.");
            return false;
        }
        if( selectedMedal == null || selectedMedal == ""){
            toast.error("A medalha é obrigatória.");
            return false;
        }
        return true;
    }

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
                                            checked={selectedMedal === medalType.id} 
                                            onChange={()=>handleRadioChange(event)}
                                         />
                                        <label htmlFor={medalType.type}> {medalType.type.toLowerCase()}</label>
                                    </div>
                                );
                            })
                        }
                    </div>
                    <div className="modalityContainer">
                        <select id="modalitiesSelect" value={selectedModality} onChange={handleOptionChange} className="selectInput">
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