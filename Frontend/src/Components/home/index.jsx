import { useEffect, useState } from "react";
import { apiService } from "../Services";
import { toast } from "react-toastify";
import"./Home.css";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();
  const [countriesList, setCountryList] = useState([{
    country:{
      id:"",
      name:"",
      flag:"",
  },medals:{
      ouro:"",
      prata:"",
      bronze:""
  }} ]);
  const [followedCountries, setFollowedCountries] = useState({userId:"", countriesId:[""]});

  const user = JSON.parse(localStorage.getItem("user")??"");

  //const [role, setRole] = useState("");
  //setRole("2");
  let role = 1;
  let pos = 1;

  useEffect(() => {
    async function loadCountries() {
      await apiService
        .getCountries()
        .then(async (response) => {
          setCountryList(response.data)
          console.log("response: ", response.data);
        })
        .catch((error) => {
          console.log("erro ao recuperar países: ", error);
          toast(error.response.message);
        });
    }    
    loadCountries();

    async function getFollowedCountries(){
      await apiService.getFollowedCountries(Number.parseInt(user.id))
      .then(async(response)=>{
          setFollowedCountries(response.data);
          console.log(response.data)
      }).catch((error)=>{
          console.log("Erro ao recuperar os países que o usuário segue:", error);
      });
    }
    getFollowedCountries();
  }, []);

  function Logout() {
    navigate("/Login");
  }

  return (
    <div className="mainContainer">
      <div className="searchContainer">
        <div className="logoutDiv">
          <img
            src="src\assets\logout-image.jpeg"
            className="imgLogout"
            onClick={() => {
              Logout();
            }}
            alt="logout"
          />
        </div>
        <div className="olympicRingsDiv">
          <img
            src="src\assets\olympic-rings-2.jpg"
            className="imgOlympicRing"
            alt="Olympic Rings"
          />
        </div>
        <div className="titlePageDiv">
          <h1 className="titlePage">Olympic Medal Monitor</h1>
        </div>
      </div>
      <div className="countriesContainer">
        <table className="tableStyle">
          <thead className="theadStyle">
            <tr>
              <th>Pos</th>
              <th>País</th>
              <th>Ouro</th>
              <th>Prata</th>
              <th>Bronze</th>
              <th>Total</th>
              <th>Seguindo</th>
            </tr>
          </thead>
          <tbody className="tbodyStyle">
            {countriesList.map((item, index) => {
              return (
                <tr
                  key={index}
                  className=""
                  onClick={() => {
                    navigate(role == "2" ? `/CountryCard/${item.country.id}` : `/CountryAdminView/${item.country.id}`);
                  }}
                >
                  <td>{pos++}</td>
                  <td>
                    <img
                      decoding="async"
                      src={item.country.flag}
                      alt="imagem do card 1 html e css"
                      className="imgFlag"
                    />
                    {item.country.name}
                  </td>
                  <td>{item.medals.ouro}</td>
                  <td>{item.medals.prata}</td>
                  <td>{item.medals.bronze}</td>
                  <td>{item.medals.ouro + item.medals.prata + item.medals.bronze}</td>
                  <td>{followedCountries.countriesId.some((followedCountryId)=>{ return followedCountryId == item.country.id}) ? "Sim" :"Não" }</td>
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
