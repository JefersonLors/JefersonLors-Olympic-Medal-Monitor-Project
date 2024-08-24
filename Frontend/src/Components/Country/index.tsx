import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { apiService } from '../Services';
import './Country.css';

function CountryCard() {
    const { id } = useParams();
    const [country, setCountry] = useState({ name: '', id: '', flag: '' });
    const [medals, setMedals] = useState([{ medal: { type: '', id: '' }, sport: { name: '' } }]);
    const [isFollowing, setIsFollowing] = useState(Boolean);
    const [isloading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem('user') ?? '');
    const roles = localStorage.getItem('userRoles') ?? ''.split(',');

    function changeButtonState() {
        const button = document.getElementById('buttonFollow');
        if (button?.classList.contains('buttonFollow')) {
            button.classList.remove('buttonFollow');
            button.classList.toggle('buttonFollowing');
            button.textContent = 'Unfollow';
        } else if (button?.classList.contains('buttonFollowing')) {
            button.classList.remove('buttonFollowing');
            button.classList.toggle('buttonFollow');
            button.textContent = 'Follow';
        }
    }

    async function handleFollow() {
        const followCountry = {
            userId: user.id,
            countryId: id,
        };

        if (!isFollowing) {
            await apiService
                .followCountry(followCountry)
                .then(async (response) => {
                    setIsFollowing(true);
                    toast.success('País seguido com sucesso!');
                    await changeButtonState();
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao seguir o país: ', error);
                });
        } else {
            await apiService
                .unfollowCountry(followCountry)
                .then(async (response) => {
                    setIsFollowing(false);
                    toast.success('Você deixou de seguir esse país com sucesso!');
                    await changeButtonState();
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao deixar de seguir o país: ', error);
                });
        }
    }

    useEffect(() => {
        function adjustsCountryFontSize() {
            const div = document.getElementById('titleDiv');
            if (div && div.innerText.length > 12) {
                console.log(div.innerText);
                div!.style.fontSize = '20px';
            }
        }
        adjustsCountryFontSize();
    }, [country.name]);

    useEffect(() => {
        setIsLoading(true);
        async function loadCountry() {
            await apiService
                .getCountryById(id)
                .then((response) => {
                    setCountry(response.data.country);
                    setMedals(response.data.medals); ///a atualização desses valores é assíncrona
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro ao recuperar informações do país: ', error);
                });
        }
        loadCountry();

        async function handleIfUserAlreadyFollows() {
            await apiService
                .getFollowedCountries(Number.parseInt(user.id))
                .then(async (response) => {
                    if (response.data.countriesId.some((item) => item == id)) {
                        setIsFollowing(true);
                    }
                })
                .catch((error) => {
                    setIsFollowing(false);
                    console.log('Erro ao pegar os países que o usuário segue:', error);
                });
        }
        handleIfUserAlreadyFollows();
        setIsLoading(false);
    }, []);

    function resolveIcon(type) {
        if (type == 'ouro') return 'https://img.icons8.com/?size=512w&id=33486&format=png';
        if (type == 'prata') return 'https://img.icons8.com/?size=512w&id=23876&format=png';
        if (type == 'bronze') return 'https://img.icons8.com/?size=512w&id=23875&format=png';
        return '';
    }

    if (isloading) {
        return (
            <div className="loading">
                <div className="loading-circle"></div>
            </div>
        );
    }
    return (
        <div className="countryCardDiv">
            <div className="headerCardCountryUserDiv">
                <div className="backUserDiv">
                    <img
                        src="https://th.bing.com/th/id/OIP.m8smT-nQUAC02ciI31r1CwAAAA?rs=1&pid=ImgDetMain"
                        className="imgBack"
                        onClick={() => {
                            navigate('/Home');
                        }}
                        alt="back"
                    />
                </div>
                <div className="changeViewUserDiv">
                    {roles.includes('ROLE_ADMIN') ? (
                        <img
                            id="changeViewToUserButon"
                            src="https://s3.amazonaws.com/freestock-prod/450/freestock_569747701.jpg"
                            onClick={() => {
                                navigate(`/CountryAdminView/${id}`);
                            }}
                            className="buttonChangeUserView"
                            alt="userIcon"
                        />
                    ) : (
                        <></>
                    )}
                </div>
            </div>
            <div className="contentCardDiv">
                <div className="headerCardDiv">
                    <div id="titleDiv" className="titleDiv">
                        <h3>{country.name}</h3>
                    </div>
                    <div className="followedSection">
                        <button
                            id="buttonFollow"
                            className={isFollowing ? 'buttonFollowing' : 'buttonFollow'}
                            onClick={() => {
                                handleFollow();
                            }}
                        >
                            {isFollowing ? 'Following' : 'Follow'}
                        </button>
                    </div>
                </div>
                <div className="imgFlagDiv">
                    <img decoding="async" src={country.flag} alt="imagem do card 1 html e css" className="imgFlag1" />
                </div>
                <div className="informationDiv">
                    <table className="tableStyle">
                        <thead className="theadStyle2">
                            <tr>
                                <th></th>
                                <th>Modalidade</th>
                                <th>Medalha</th>
                            </tr>
                        </thead>
                        <tbody>
                            {medals
                                .map((item) => {
                                    if (item.medal.type.toLocaleLowerCase() == 'ouro') item.medal.id = '1';
                                    if (item.medal.type.toLocaleLowerCase() == 'prata') item.medal.id = '2';
                                    if (item.medal.type.toLocaleLowerCase() == 'bronze') item.medal.id = '3';
                                    return item;
                                })
                                .sort((itemA, itemB) => {
                                    return itemA.medal.id.localeCompare(itemB.medal.id);
                                })
                                .map((item, index) => {
                                    return (
                                        <tr key={index} className="">
                                            <td>{index + 1}</td>
                                            <td>{item.sport.name}</td>
                                            <td>
                                                <img src={resolveIcon(item.medal.type.toLocaleLowerCase())} alt={item.medal.type.toLocaleLowerCase()} className="iconMedalUser" />
                                            </td>
                                        </tr>
                                    );
                                })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
export default CountryCard;
