import { useEffect, useState } from "react";
import { apiService } from "../Services";
import { toast } from "react-toastify";
import "./Home.css";
import { useNavigate } from "react-router-dom";

function Home() {
  const [countries, setCountries] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    async function loadCountries() {
      await apiService
        .getCountries()
        .then((response) => {
          setCountries(response.data);
          console.log("response: ", response.data);
        })
        .catch((error) => {
          console.log("error: ", error);
          toast(error.response.message);
        });
    }
    loadCountries();
  }, []);

  function Logout(){
    localStorage.clear('authToken');
    navigate("/Login")
  }

  return (
    <div className="mainContainer">
      <div className="searchContainer">
        <div className="logoutDiv">
            <img src="src\assets\logout-image.jpeg" className="imgLogout" onClick={()=>{Logout()}} alt="logout"/>
        </div>
        <div className="olympicRingsDiv">
            <img src="src\assets\olympic-rings2.jpg" className="imgOlympicRing" alt="Olympic Rings"/>
        </div>
        <div className="titlePageDiv">
            <h1 className="titlePage">
                Olympic Medal Monitor
            </h1>
        </div>
        <div className="searchSection">
            <img src="src\assets\search.jpeg" className="imgSearch" alt="Buscar"/>
            <input type="text" id="txtBusca" placeholder="Search a country..."/>
        </div>
      </div>
      <div className="countriesContainer">
        <ul className="country-list">
            {countries.map((item, index) => {
                return (
                    <section className="secao4" key={index} id="sobre">
                        <div className="secao4-div">
                            <div className="secao4-div-card">
                            <img
                                decoding="async"
                                src="https://th.bing.com/th/id/OIP.RgwzDihAMttqCx8f4GGTVAHaFL?rs=1&pid=ImgDetMain"
                                alt="imagem do card 1 html e css"
                            />
                            <h3>{item.country.id}</h3>
                            <p>{item.country.name}</p>
                            </div>
                        </div>
                    </section>
                );
            })}
        </ul>

      </div>
    </div>
  );
}
export default Home;
