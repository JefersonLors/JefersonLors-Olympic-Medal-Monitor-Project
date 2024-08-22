import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Country.css"
import { apiService } from "../Services";
import { toast } from "react-toastify";

function CountryCard() {
    const {id} = useParams();
    const [country, setCountry] = useState({name:""});
    const [medals, setMedals] = useState([{medal:{type:""}, sport:{name:""}}]);
    const [user, setUser] = useState({id:"", name:"", email:""})
    const navigate = useNavigate();
    
    function changeButtonState(){
        const button = document.getElementById('buttonFollow');
        
        if( button?.classList.contains('buttonFollow') ){
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
        await changeButtonState();
        
        setUser(JSON.parse(localStorage.getItem("user")??""));
        

    }

    useEffect(()=>{
        async function follow(){
            const followCountry = {
                userId: user.id,
                countryId: id
            }
            console.log(user)
            await apiService.followCountry(followCountry)
                            .then((response)=>{
                                toast.success("País seguido com sucesso!");
                            }).catch((error)=>{
                                console.log(error);
                            })
        }
        follow();
    }, [user])

    useEffect(()=>{
        async function loadCountry(){
            console.log(id)
            await apiService.getCountryById(id)
                            .then((response)=>{
                                console.log(response);
                                setCountry(response.data.country);
                                setMedals(response.data.medals); ///a atualização desses valores é assíncrona
                            }).catch((error)=>{
                                toast.error(error.response.data.message);
                                console.log(error);
                            });
                            
        }
        loadCountry();
    }, []);

  return (
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
                <button id="buttonFollow" className="buttonFollow" onClick={()=>{handleFollow()}}>Follow</button>
            </div>
        </div>
    </div>
  );
}
export default CountryCard;
