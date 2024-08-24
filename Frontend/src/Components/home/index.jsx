import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { apiService } from '../Services';
import './Home.css';

function Home() {
    const navigate = useNavigate();
    const [countriesList, setCountryList] = useState([
        {
            country: {
                id: '',
                name: '',
                flag: '',
            },
            medals: {
                ouro: '',
                prata: '',
                bronze: '',
            },
        },
    ]);

    const [followedCountries, setFollowedCountries] = useState({ userId: '', countriesId: [''] });
    const user = JSON.parse(localStorage.getItem('user') ?? '');
    const roles = localStorage.getItem('userRoles') ?? ''.split(',');

    useEffect(() => {
        async function loadCountries() {
            await apiService
                .getCountries()
                .then(async (response) => {
                    setCountryList(response.data);
                })
                .catch((error) => {
                    console.log('erro ao recuperar países: ', error);
                    toast(error.response.message);
                });
        }
        loadCountries();

        async function getFollowedCountries() {
            await apiService
                .getFollowedCountries(Number.parseInt(user.id))
                .then(async (response) => {
                    setFollowedCountries(response.data);
                })
                .catch((error) => {
                    console.log('Erro ao recuperar os países que o usuário segue:', error);
                });
        }
        getFollowedCountries();
    }, []);

    function Logout() {
        navigate('/Login');
    }
    return (
        <div className="mainContainer">
            <div className="searchContainer">
                <div className="headerHomeDiv">
                    <div className="nameDiv">
                        <h1 className='h1UserName'>{user.name}</h1>
                    </div>
                    <div className="logoutDiv">
                        <img
                            src="https://media.istockphoto.com/id/1254028816/vector/emergency-exit-solid-icon-navigation-concept-exit-sign-on-white-background-exit-door-icon-in.jpg?s=612x612&w=0&k=20&c=HeN4c-m7hbjFO1irDgmMNLF8c821F6dqmdyc-BiHxQ0="
                            className="imgLogout"
                            onClick={() => {
                                Logout();
                            }}
                            alt="logout"
                        />
                    </div>
                </div>
                <div className="olympicRingsDiv">
                    <img src="src\assets\olympic-rings-2.jpg" className="imgOlympicRing" alt="Olympic Rings" />
                </div>
                <div className="titlePageDiv">
                    <h1 className="titlePage">Monitor de Medalha Olímpica</h1>
                </div>
            </div>
            <div className="countriesContainer">
                <table className="tableStyle">
                    <thead className="theadStyle">
                        <tr>
                            <th>Pos.</th>
                            <th>País</th>
                            <th><img src="src\assets\gold-medal.png" className="medalIcon" alt="Ouro" /></th>
                            <th><img src="src\assets\silver-medal.png" className="medalIcon" alt="Prata" /></th>
                            <th><img src="src\assets\bronze-medal.png" className="medalIcon" alt="Bronze" /></th>
                            <th>Total</th>
                            <th>Seguindo</th>
                        </tr>
                    </thead>
                    <tbody className="tbodyStyle">
                        {countriesList
                            .map((item) => ({
                                ...item,
                                total: item.medals.ouro + item.medals.prata + item.medals.bronze,
                            }))
                            .sort((a, b) => b.total - a.total)
                            .map((item, index) => {
                                return (
                                    <tr
                                        key={index}
                                        className=""
                                        onClick={() => {
                                            navigate(roles.includes('ROLE_ADMIN') ? `/CountryAdminView/${item.country.id}` : `/CountryCard/${item.country.id}`);
                                        }}
                                    >
                                        <td>{index + 1}</td>
                                        <td>
                                            <img decoding="async" src={item.country.flag} alt="flag" className="imgFlag" />
                                            {item.country.name}
                                        </td>
                                        <td>{item.medals.ouro}</td>
                                        <td>{item.medals.prata}</td>
                                        <td>{item.medals.bronze}</td>
                                        <td>{item.total}</td>
                                        <td>{followedCountries.countriesId.length > 0 
                                                && followedCountries.countriesId.some((followedCountryId) => followedCountryId == item.country.id) 
                                                ? 
                                                <img 
                                                    src="https://static.vecteezy.com/system/resources/previews/013/743/605/original/golden-star-icon-png.png"
                                                    alt='alt'
                                                    className='starImgFollowing'
                                                />
                                                : 
                                                ''
                                            }</td>
                                    </tr>
                                );
                            })}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default Home;
