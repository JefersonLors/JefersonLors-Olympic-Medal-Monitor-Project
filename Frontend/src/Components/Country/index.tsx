import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Country.css"
import { apiService } from "../Services";
import { toast } from "react-toastify";

function CountryCard() {
    const {id} = useParams();
    const [country, setCountry] = useState({name:"", id:"", flag:""});
    const [medals, setMedals] = useState([{medal:{type:""}, sport:{name:""}}]);
    const [isFollowing, setIsFollowing] = useState(Boolean);
    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem("user")??"");

    function changeButtonState(){
        const button = document.getElementById('buttonFollow');
        console.log("CHANGING STATE")
        if( button?.classList.contains('buttonFollow') ){
            console.log("tá aqui")
            button.classList.remove('buttonFollow')
            button.classList.toggle('buttonFollowing');
            button.textContent = 'Unfollow';
        } else if( button?.classList.contains('buttonFollowing') ){
            button.classList.remove('buttonFollowing')
            button.classList.toggle('buttonFollow');
            button.textContent = 'Follow';
        }
    }

    async function handleFollow(){
        const followCountry = {
            userId: user.id,
            countryId: id
        }
        
        if( !isFollowing ){
            console.log(user)
            await apiService.followCountry(followCountry)
                            .then(async (response)=>{
                                setIsFollowing(true);
                                toast.success("País seguido com sucesso!");
                                await changeButtonState();
                            }).catch((error)=>{
                                toast.error(error.response.data.message);
                                console.log("Erro ao seguir o país: ", error);
                            })
        }else{
            console.log(user)
            await apiService.unfollowCountry(followCountry)
                            .then(async (response)=>{
                                setIsFollowing(false);
                                toast.success("Você deixou de seguir esse país com sucesso!");
                                await changeButtonState();
                            }).catch((error)=>{
                                toast.error(error.response.data.message);
                                console.log("Erro ao deixar de seguir o país: ", error);
                            })
        }
    }

    useEffect(()=>{
        function loadCountry(){
            console.log(id)
             apiService.getCountryById(id)
                            .then((response)=>{
                                console.log(response);
                                setCountry(response.data.country);
                                setMedals(response.data.medals); ///a atualização desses valores é assíncrona
                            }).catch((error)=>{
                                toast.error(error.response.data.message);
                                console.log("Erro ao recuperar informações do país: ", error);
                            });
                            
        }
        loadCountry();
        function handleIfUserAlreadyFollows(){
             apiService.getFollowedCountries(Number.parseInt(user.id))
                            .then(async(response)=>{
                                if( response.data.countriesId.some((item)=>item == id)){
                                    setIsFollowing(true);
                                }
                            }).catch((error)=>{
                                setIsFollowing(false);
                                console.log("Erro ao pegar os países que o usuário segue:", error);
                            });
        }
        handleIfUserAlreadyFollows();
    }, []);

  return (
    <div className="mainDiv">
    <div className="countryCardDiv">
        <div className="backDiv">
            <img src="https://static.vecteezy.com/system/resources/previews/000/589/654/original/vector-back-icon.jpg" className="imgBack" onClick={()=>{navigate("/Home")}} alt="back"/>
        </div>
        <div className="contentCardDiv">
            <div className="titleDiv">
                <h3>{country.name}</h3>
            </div>
            <div className="imgFlagDiv">
                <img
                    decoding="async"
                    src={country.flag}
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
                         {medals.map((item, index) => {
                            return (
                                <tr key={index} className="">
                                    <td>{item.sport.name}</td>
                                    <td>{item.medal.type}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            <div className="followedSection">
                <button 
                    id="buttonFollow"
                    className={isFollowing ? "buttonFollowing" : "buttonFollow" } 
                    onClick={()=>{handleFollow()}}
                >{isFollowing ? "Following" : "Follow" }</button>
            </div>
        </div>
    </div>
    </div>
  );
}
export default CountryCard;
