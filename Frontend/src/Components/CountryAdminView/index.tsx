import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { apiService } from '../Services';
import './CountryAdminView.css';

function CountryAdminView() {
    const { id } = useParams();
    const [country, setCountry] = useState({ id: '', name: '', flag: '' });
    const [modalities, setModalities] = useState([{ id: '', name: '', description: '' }]);
    const [medalTypes, setMetalTypes] = useState([{ id: '', type: '' }]);
    const navigate = useNavigate();

    const [selectedMedal, setSelectedMedal] = useState();
    const [selectedModality, setSelectedModality] = useState();

    function handleRadioChange(event) {
        setSelectedMedal(event.target.value);
    }

    const handleOptionChange = (event) => {
        setSelectedModality(event.target.value);
    };

    const handleSave = async () => {
        if (validateSelectedOptions()) {
            await apiService
                .addMedal({ country: id, medal: selectedMedal, sport: selectedModality })
                .then((response) => {
                    toast.success('Medalha adicionada com sucesso!');
                    apiService.notifyUser({ countryId: id, sportModalityId: selectedModality, medalId: selectedMedal, medalsWon: 1 }).catch((error) => {
                        toast.error(error.response.data.message);
                        console.log('Erro ao notificar seguidores do país: ', error);
                    });
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao adicionar medalha: ', error);
                });
        }
    };

    useEffect(() => {
        function loadCountry() {
            apiService
                .getCountryById(id)
                .then((response) => {
                    setCountry(response.data.country);
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao recuperar informações do país: ', error);
                });
        }
        loadCountry();

        function loadModalityOptions() {
            apiService
                .getAllModalities()
                .then((response) => {
                    setModalities(response.data);
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao recuperar as modalidades esportivas: ', error);
                });
        }
        loadModalityOptions();

        function loadMedalTypes() {
            apiService
                .getAllMedalTypes()
                .then((response) => {
                    setMetalTypes(response.data);
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao recuperar os tipos de medalha disponíveis: ', error);
                });
        }
        loadMedalTypes();
    }, []);

    function validateSelectedOptions() {
        if (selectedModality == null || selectedModality == '') {
            toast.error('A modalidade é obrigatória.');
            return false;
        }
        if (selectedMedal == null || selectedMedal == '') {
            toast.error('A medalha é obrigatória.');
            return false;
        }
        return true;
    }

    function resolveIcon(type){
        if( type == "ouro")
            return "https://img.icons8.com/?size=512w&id=33486&format=png"
        if( type == "prata")
            return "https://img.icons8.com/?size=512w&id=23876&format=png"
        if( type == "bronze")
            return "https://img.icons8.com/?size=512w&id=23875&format=png"
        return ""
    }

    return (
        <div className="countryCardAdminDiv">
            <div className="headerCardCountryAdminDiv">
                <div className="backDivAdmin">
                    <img
                        src="https://th.bing.com/th/id/OIP.m8smT-nQUAC02ciI31r1CwAAAA?rs=1&pid=ImgDetMain"
                        className="imgBackAdmin"
                        onClick={() => {
                            navigate('/Home');
                        }}
                        alt="back"
                    />
                </div>
                <div className="changeViewDiv">
                    <img 
                        id="changeViewToUserButon"
                        src='https://static.vecteezy.com/system/resources/previews/008/302/462/original/eps10-grey-user-icon-or-logo-in-simple-flat-trendy-modern-style-isolated-on-white-background-free-vector.jpg'
                        onClick={() => {
                            navigate(`/CountryCard/${id}`);
                        }}
                        className="buttonChangeView"
                        alt='userIcon'
                    />
                </div>
            </div>
            <div className="contentCardAdminDiv">
                <div className="titleAdminDiv">
                    <h3>{country.name}</h3>
                </div>
                <div className="imgFlagAdminDiv">
                    <img decoding="async" src={country.flag} alt="imagem do card 1 html e css" className="imgFlagAdmin1" />
                </div>
                <div className="addMedalAdminDiv">
                    <div className="medalContainer">
                        {medalTypes.map((medalType, index) => {
                            return (
                                <div className="radioContainer" key={index}>
                                    <div className="typeMedalButton">
                                        <input 
                                            type="radio" 
                                            key={index} id={medalType.type} 
                                            value={medalType.id} 
                                            
                                            checked={selectedMedal === medalType.id} 
                                            onChange={() => handleRadioChange(event)} 
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor={medalType.type} > 
                                            <img 
                                                src={resolveIcon(medalType.type.toLocaleLowerCase())}
                                                className='iconMedal'
                                                alt={medalType.type.toLocaleLowerCase()}
                                            />
                                        </label>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                    <div className="modalityContainer">
                        <select id="modalitiesSelect" value={selectedModality} onChange={handleOptionChange} className="selectInput">
                            <option value="">Selecione</option>
                            {modalities.map((option, index) => (
                                <option id={option.id} key={index} value={option.id}>
                                    {option.name}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>
                <div className="addMedalSection">
                    <button id="buttonAddMedal" className="buttonAdd" onClick={handleSave}>
                        Adicionar
                    </button>
                </div>
            </div>

        </div>
    );
}
export default CountryAdminView;
